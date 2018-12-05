package com.foyoedu.consumer.aop;

import com.foyoedu.common.utils.FoyoUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Web层日志切面
 */
@Aspect
//@Order(2)
@Component
@Slf4j
public class WebLogAspect {
    @Autowired
    WriterLog writerLog;
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    @Pointcut("execution(public * com.foyoedu.consumer.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = FoyoUtils.getRequest();
        startTime.set(System.currentTimeMillis());
        writerLog.beforLog(joinPoint,request);
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        writerLog.afterLog(ret,startTime.get());
    }
}

