package com.foyoedu.consumer.component;

import com.foyoedu.common.config.CommonConfig;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/foyo/*", filterName = "fileSizeFilter")
public class FileSizeFilter implements Filter {

    @Autowired
    private CommonConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String ct = request.getHeader("Content-Type");
        if(!StringUtils.isEmpty(ct)) {
            if(request.getHeader("Content-Type").contains("multipart/form-data")
                    && Integer.parseInt(request.getHeader("Content-Length")) >= config.getFILTER_FILESIZE()*1024*1024) {
                FoyoUtils.outPutResponse(response, FoyoUtils.error(500,"上传的文件不能超过" + config.getFILTER_FILESIZE() +"MB"));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
