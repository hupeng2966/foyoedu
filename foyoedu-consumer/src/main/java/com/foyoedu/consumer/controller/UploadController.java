package com.foyoedu.consumer.controller;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.UploadClientService;
import com.foyoedu.common.utils.FoyoUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/foyo")
public class UploadController {
    @Autowired
    private UploadClientService service;

    @PostMapping(value = "/provider/upload")
    @ApiOperation("上传文件到微服务层，存储到fastDFS文件服务器")
    public FoyoResult providerUploadFile(@RequestParam(value = "file",required = true) MultipartFile file) {
        return service.uploadFile(file);
    }
    @PostMapping(value = "/consumer/upload")
    @ApiOperation("上传文件到client层，存储到fastDFS文件服务器")
    public FoyoResult consumerUploadFile(@RequestParam(value = "file",required = true) MultipartFile file) {
        try {
            String url = FoyoUtils.saveFdfsFile(file);
            Map result = new HashMap();
            result.put("url", url);
            return FoyoUtils.ok(result);
        } catch (Throwable throwable) {
            return FoyoUtils.error(500,throwable.getMessage());
        }
    }
}
