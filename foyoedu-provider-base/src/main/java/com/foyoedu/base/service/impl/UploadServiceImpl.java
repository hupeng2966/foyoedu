package com.foyoedu.base.service.impl;

import com.foyoedu.base.service.UploadService;
import com.foyoedu.common.config.CommonConfig;
import com.foyoedu.common.utils.FoyoUtils;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private CommonConfig config;

    @Override
    public String uploadFile(MultipartFile file) throws Throwable {

        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        StorePath storePath = storageClient.uploadFile(file.getBytes(), extName);
        //响应上传图片的url
        String url = config.getFASTDFS_STORAGE_URL() + storePath.getFullPath();

        Map result = new HashMap();
        result.put("url", url);
        return FoyoUtils.ok(result);
    }
}
