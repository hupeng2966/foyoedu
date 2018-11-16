package com.foyoedu.base.controller;

import com.foyoedu.base.service.TeacherService;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService service;

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addTeacherData(@RequestPart MultipartFile file) throws Throwable {
        return service.addTeacherData(file);
    }

    @PostMapping("/export")
    public String findTeacherData() throws Throwable {
        return FoyoUtils.ok(service.list("",""));
    }
}
