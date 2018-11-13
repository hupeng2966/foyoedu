package com.foyoedu.common.service;

import com.foyoedu.common.service.hystrix.UploadClientServiceFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "FOYOEDU-ZUUL", fallbackFactory = UploadClientServiceFactory.class)
@RequestMapping(value = "/base")
public interface UploadClientService {
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart MultipartFile file);
}
