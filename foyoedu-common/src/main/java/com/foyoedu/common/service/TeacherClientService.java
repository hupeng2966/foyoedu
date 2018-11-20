package com.foyoedu.common.service;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.hystrix.TeacherClientServiceFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "FOYOEDU-ZUUL", fallbackFactory = TeacherClientServiceFactory.class)
@RequestMapping(value = "/base")
public interface TeacherClientService {
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FoyoResult addTeacherData(@RequestPart MultipartFile file);

    @PostMapping("/export")
    public FoyoResult findTeacherData();
}
