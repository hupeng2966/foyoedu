package com.foyoedu.common.utils;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class FoyoUtils {
    public static final String TOKEN_KEY = "cookie.token_key";
    public static final String LOGIN_URI = "login.uri";
    public static final String REDIS_HOST = "spring.redis.host";

    public static FoyoResult errorMessage(Throwable throwable) {
        String msg = throwable.getMessage();
        log.error(msg);
        Integer status = Integer.parseInt(msg.split(" ")[1]);
        return error(status, throwable.getMessage());
    }

    public static FoyoResult ok(Object data) {
        return new FoyoResult(data);
    }

    public static FoyoResult error(Integer status, String errMsg) {
        return  new FoyoResult(status, errMsg);
    }

    public static PageResult pageResult(Integer total, Object data) {
        return new PageResult(total, data);
    }

    public static Object getSevletContextByArg(String arg) {
        return getRequest().getServletContext().getAttribute(arg);
    }

    public static Object getSevletContextByArg(String arg, ServletContext servletContext) {
        return servletContext.getAttribute(arg);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }

}
