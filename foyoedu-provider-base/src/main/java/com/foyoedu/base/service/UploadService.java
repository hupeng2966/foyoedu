package com.foyoedu.base.service;

import com.foyoedu.common.pojo.FoyoResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    public FoyoResult uploadFile(MultipartFile file) throws Throwable;
}
