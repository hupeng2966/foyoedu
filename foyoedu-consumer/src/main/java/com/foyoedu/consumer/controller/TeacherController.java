package com.foyoedu.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.foyoedu.common.pojo.ExcelData;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.Teacher;
import com.foyoedu.common.service.TeacherClientService;
import com.foyoedu.common.utils.ExcelUtil;
import com.foyoedu.common.utils.FoyoUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/foyo")
public class TeacherController {

    @Autowired
    private TeacherClientService service;

    @PostMapping("/import")
    @ApiOperation("批量导入教师帐号")
    public FoyoResult addTeacherData(@RequestParam(value = "file",required = true) MultipartFile file) {
        return  service.addTeacherData(file);
    }

    @PostMapping("/export")
    @ApiOperation("导出教师帐号")
    public void findTeacherData() {
        FoyoResult result = service.findTeacherData();
        if(result.getStatus() != 200) {
            FoyoUtils.outPutResponse(FoyoUtils.getResponse(),result);
            return;
        }
        List<Teacher> list = JSON.parseArray(JSON.toJSONString(result.getData()), Teacher.class);
        //List<Teacher> list = (List<Teacher>) JSONPath.eval(result, "$.data");
        ExcelData excelData = new ExcelData();
        excelData.setName("教师用户信息");
        //设置导出列名
        List<String> titles = new ArrayList();
        titles.add("教师姓名");
        titles.add("登录帐号");
        titles.add("初始密码");
        titles.add("校区号");
        titles.add("手机号码");
        titles.add("是否停用");
        excelData.setTitles(titles);
        //添加导出数据
        List<List<Object>> rows = new ArrayList();
        list.forEach(item->{
            List<Object> row = new ArrayList();
            row.add(item.getUserName());
            row.add(item.getLoginId());
            row.add(item.getPwd());
            row.add(item.getCampusId());
            row.add(item.getPhone());
            row.add(item.isDelete());
            rows.add(row);
        });
        excelData.setRows(rows);
        try {
            ExcelUtil.exportExcel("教师用户信息.xlsx",excelData);
        } catch (Exception e) {
            FoyoUtils.outPutResponse(FoyoUtils.error(500,e.getMessage()));
        }
    }

    @PostMapping("/rabbitsend")
    @ApiOperation("学生抢课")
    public FoyoResult rabbitmqSend(@ApiParam(value = "课程id", allowEmptyValue = false) @RequestParam("courseid") Integer courseId){
        return service.rabbitmqSend(courseId);
    }
}
