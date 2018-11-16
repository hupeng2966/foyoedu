package com.foyoedu.consumer.controller;

import com.alibaba.fastjson.JSON;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.PageResult;
import com.foyoedu.common.pojo.Teacher;
import com.foyoedu.common.service.TeacherClientService;
import com.foyoedu.common.utils.FoyoUtils;
import com.foyoedu.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/foyo")
public class TeacherController {

    @Autowired
    private TeacherClientService service;

    @PostMapping("/import")
    public String addTeacherData(@RequestParam(value = "file",required = true) MultipartFile file) {
        return service.addTeacherData(file);
    }

    @PostMapping("/export")
    public void findTeacherData() {
        String teacherData = service.findTeacherData();
        FoyoResult foyoResult = JsonUtils.jsonToPojo(teacherData, FoyoResult.class);
        if(foyoResult.getStatus() != 200) {
            FoyoUtils.outPutResponse(FoyoUtils.getResponse(),teacherData);
            return;
        }
        List<Teacher> list = (List<Teacher>) foyoResult.getData();
    }
}
