package com.foyoedu.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foyoedu.base.component.DeferredResultHolder;
import com.foyoedu.base.dao.TeacherDao;
import com.foyoedu.base.service.TeacherService;
import com.foyoedu.common.pojo.Teacher;
import com.foyoedu.common.utils.ExcelUtil;
import com.foyoedu.common.utils.FoyoUtils;
import com.foyoedu.common.utils.RedisLock;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher> implements TeacherService {

    public TeacherServiceImpl() {
        super("teacher", "userId", Teacher.class);
    }
    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    private static final int TIMEOUT = 10*1000;//超时时间 10s

    public List<Teacher> findTeacherData() throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        objectMapper.writerWithView(Teacher.TeacherWithoutPwd.class).writeValue(bos, super.list("",""));
        return JSON.parseArray(bos.toString(),Teacher.class);
    }

    @Override
    @SuppressWarnings(value={"unchecked", "deprecation"})
    public int addTeacherData(MultipartFile file) throws Throwable {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        InputStream in = file.getInputStream(); // 文件流
        ExcelUtil.checkExcelVaild(file);
        // 同时支持Excel 2003、2007
        //Workbook workbook = ExcelUtil.getWorkbok(in,file);
        Workbook workbook = WorkbookFactory.create(in); // 这种方式 Excel2003/2007/2010都是可以处理的
        //int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
        Sheet sheet = workbook.getSheetAt(0);   // 遍历第一个Sheet
        //int rowNum = sheet.getLastRowNum();       //获取总行数

        // 为跳过第一行目录设置count
        int count = 0;
        int rowCellNum = sheet.getRow(0).getLastCellNum();
        String pwd = DigestUtils.md5DigestAsHex("123456".getBytes());
        ArrayList<Teacher> list = new ArrayList<>();

        for (Row row : sheet) {
            // 跳过第一和第二行的目录
            if(count < 2 ) {
                count++;
                continue;
            }
            //如果当前行没有数据，跳出循环
            if(row.getCell(0).toString().equals("")){
                break;
            }
            Teacher teacher = new Teacher();
            teacher.setLoginId(row.getCell(0).getStringCellValue());
            teacher.setUserName(row.getCell(2).getStringCellValue());
            teacher.setPwd(pwd);
            teacher.setPhone(row.getCell(3).getStringCellValue());
            FoyoUtils.validatePojo(teacher);
            list.add(teacher);
            //获取总列数(空格的不计算)
            /*System.out.println("总列数：" + row.getPhysicalNumberOfCells());
            System.out.println("最大列数：" + row.getLastCellNum());
            不要用以下for循环写法，因为会不扫描空格列 for (Cell cell : row) { System.out.println(cell);}
            for (int i = 0; i < rowCellNum; i++) {
                Cell cell = row.getCell(i);
                if(cell == null) {
                    System.out.print("" + "\t");
                    continue;
                }
                Object obj = ExcelUtil.getValue(cell);
                System.out.print(obj + "\t");
            }*/
        }
        return teacherDao.addTeacherList(list);
    }

    @Override
    public void addUserChoiceCourse(Integer courseId, String orderNumber) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("token", FoyoUtils.getToken());
        map.put("orderNumber", orderNumber);

        //申请课程订单放入队列之前，检查redis中是否还有库存
        String course = "COURSE:" + courseId;
        if (!stringRedisTemplate.hasKey(course)) {
            int num = teacherDao.findCourseNumById((Integer) map.get("courseId"));
            stringRedisTemplate.opsForValue().increment(course, num);
        }
        if (Integer.parseInt(stringRedisTemplate.opsForValue().get(course)) > 0) {
            //加锁
            long time = System.currentTimeMillis() + TIMEOUT;
            if(!redisLock.lock(courseId.toString(), String.valueOf(time))){
                deferredResultHolder.getMap().get(orderNumber).setResult(FoyoUtils.error(500,"很抱歉，人太多了，换个姿势再试试~~"));
                return;
            }
            stringRedisTemplate.boundValueOps(course).increment(-1);
            //解锁
            redisLock.unlock(courseId.toString(),String.valueOf(time));
            //课程申请已在排队中
            rabbitTemplate.convertAndSend("amq.fanout","foyo.CourseSelection", map);
        }else {
            //teacherDao.updateCourse(courseId);
            deferredResultHolder.getMap().get(orderNumber).setResult(FoyoUtils.error(500,"本课程已经被抢光"));
        }
    }

    @RabbitListener(queues = "foyo.CourseSelection")
    public void addUserChoiceCourseRabbitListener(Map<String,Object> map, Message message) {
        /*System.out.println("消息头信息：" + message.getMessageProperties());
        System.out.println("消息体内容：" + message.getBody());*/
        if (!deferredResultHolder.getMap().containsKey(map.get("orderNumber"))) return;
        String json = stringRedisTemplate.opsForValue().get(map.get("token"));
        if (StringUtils.isEmpty(json)) return;
        Teacher teacher = (Teacher) JSON.parseObject(json,Teacher.class);
        String course = "COURSE:" + map.get("courseId");
        boolean result = false;
        try {
            result = teacherDao.addUserChoiceCourse(teacher.getLoginId(), (Integer) map.get("courseId"));
        }catch (Exception e) {
            stringRedisTemplate.boundValueOps(course).increment(1);
            deferredResultHolder.getMap().get(map.get("orderNumber")).setResult(FoyoUtils.error(500,"选课失败"));
            return;
        }
        if (result) {
            deferredResultHolder.getMap().get(map.get("orderNumber")).setResult(FoyoUtils.ok(result));
        }else {
            stringRedisTemplate.boundValueOps(course).increment(1);
            deferredResultHolder.getMap().get(map.get("orderNumber")).setResult(FoyoUtils.error(500,"选课失败"));
        }
    }
}
