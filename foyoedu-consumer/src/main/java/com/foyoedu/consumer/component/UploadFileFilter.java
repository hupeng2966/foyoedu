package com.foyoedu.consumer.component;

import com.foyoedu.common.config.CommonConfig;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Component
@WebFilter(urlPatterns = "/foyo/*", filterName = "uploadFileFilter", asyncSupported = true)
public class UploadFileFilter implements Filter {

    @Autowired
    private CommonConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
//        String ct = request.getHeader("Content-Type");
//        if(!StringUtils.isEmpty(ct)) {
//            if(request.getHeader("Content-Type").contains("multipart/form-data")) {
//                if(Integer.parseInt(request.getHeader("Content-Length")) >= config.getFILTER_FILESIZE()*1024*1024) {}
//            }
//        }
        MultipartResolver res = new StandardServletMultipartResolver();
        if(res.isMultipart(request)) {
            MultipartHttpServletRequest multipartRequest = res.resolveMultipart(request);
            Map<String, MultipartFile> files = multipartRequest.getFileMap();
            Iterator<String> iterator = files.keySet().iterator();
            while (iterator.hasNext()) {
                String formKey = (String) iterator.next();
                MultipartFile multipartFile = multipartRequest.getFile(formKey);
                if (!StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
                    String filename = multipartFile.getOriginalFilename();
                    if(!checkFile(filename)){
                        FoyoUtils.outPutResponse(response, FoyoUtils.error(500,"不允许上传此类型文件"));
                        return;
                    }
                    if(multipartFile.getSize() >= config.getFILTER_FILESIZE()*1024*1024) {
                        FoyoUtils.outPutResponse(response, FoyoUtils.error(500,"上传的文件不能超过" + config.getFILTER_FILESIZE() +"MB"));
                        return;
                    }
                }else {
                    FoyoUtils.outPutResponse(response, FoyoUtils.error(500,"上传的文件名不能为空"));
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private  boolean checkFile(String fileName){
        boolean flag = false;
        String suffixList = config.getFILTER_FILETYPE();
        //获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);

        if(suffixList.contains(suffix.trim().toLowerCase())){
            flag = true;
        }
        return flag;
    }

    @Override
    public void destroy() {

    }
}
