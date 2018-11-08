package com.foyoedu.consumer.controller;

import com.foyoedu.common.pojo.CommonConfig;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.utils.FastDFSClient;
import com.foyoedu.common.utils.FoyoUtils;
import com.foyoedu.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping(value = "/foyo")
public class UploadController {
    @Autowired
    FastDFSClient fastDFSClient;
    @Autowired
    CommonConfig config;

    @PostMapping(value = "/upload")
    public FoyoResult uploadFile(@RequestParam(value = "file",required = true) MultipartFile file) {
        try {
            //接收上传文件
            //取扩展名
            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.indexOf(".") + 1);
            //上传到图片服务器
            fastDFSClient.init("classpath:fastDFSClient.properties");
            String url = fastDFSClient.uploadFile(file.getBytes(), extName);
            //响应上传图片的url
            url = config.getFASTDFS_STORAGE_URL() + url;
            Map result = new HashMap();
            result.put("url", url);
            return FoyoUtils.ok(result);
        } catch (Exception e) {
            return FoyoUtils.error(500, e.getMessage());
        }
    }
}
