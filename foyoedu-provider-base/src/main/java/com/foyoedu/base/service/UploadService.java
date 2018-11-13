package com.foyoedu.base.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    public String uploadFile(MultipartFile file) throws Throwable;
}
