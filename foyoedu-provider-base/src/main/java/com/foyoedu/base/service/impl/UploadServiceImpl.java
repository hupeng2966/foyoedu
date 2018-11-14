package com.foyoedu.base.service.impl;

import com.foyoedu.base.service.UploadService;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@Service
public class UploadServiceImpl implements UploadService {

    @Override
    public String uploadFile(MultipartFile file) throws Throwable {
        String URI = FoyoUtils.saveFdfsFile(file);
        Map result = new HashMap();
        result.put("url", URI);
        return FoyoUtils.ok(result);
    }
}
