package com.foyoedu.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foyoedu.base.dao.TeacherDao;
import com.foyoedu.base.service.TeacherService;
import com.foyoedu.common.pojo.Teacher;
import com.foyoedu.common.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher> implements TeacherService {

    public TeacherServiceImpl() {
        super("teacher", "userId", Teacher.class);
    }
    @Autowired
    private TeacherDao teacherDao;

    public List<Teacher> findTeacherData() throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        objectMapper.writerWithView(Teacher.TeacherWithoutPwd.class).writeValue(bos, super.list("",""));
        return JSON.parseArray(bos.toString(),Teacher.class);
    }

    @Override
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
            list.add(teacher);
            //获取总列数(空格的不计算)
            //System.out.println("总列数：" + row.getPhysicalNumberOfCells());
            //System.out.println("最大列数：" + row.getLastCellNum());
            //不要用以下for循环写法，因为会不扫描空格列 for (Cell cell : row) { System.out.println(cell);}
//            for (int i = 0; i < rowCellNum; i++) {
//                Cell cell = row.getCell(i);
//                if(cell == null) {
//                    System.out.print("" + "\t");
//                    continue;
//                }
//                Object obj = ExcelUtil.getValue(cell);
//                System.out.print(obj + "\t");
//            }
        }
        return teacherDao.addTeacherList(list);
    }
}
