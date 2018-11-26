package com.foyoedu.consumer.component;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;

import java.util.concurrent.Callable;

@Component
public class MyCallableInterceptor implements CallableProcessingInterceptor {
    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest request, Callable<T> task) throws Exception {

    }

    @Override
    public <T> void preProcess(NativeWebRequest request, Callable<T> task) throws Exception {

    }

    @Override
    public <T> void postProcess(NativeWebRequest request, Callable<T> task, Object concurrentResult) throws Exception {

    }

    @Override
    public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
        return null;
    }

    @Override
    public <T> Object handleError(NativeWebRequest request, Callable<T> task, Throwable t) throws Exception {
        return null;
    }

    @Override
    public <T> void afterCompletion(NativeWebRequest request, Callable<T> task) throws Exception {

    }
}
