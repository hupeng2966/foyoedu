package com.foyoedu.consumer.component;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptor;

@Component
public class MyDeferredResultInterceptor implements DeferredResultProcessingInterceptor {
    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {

    }

    @Override
    public <T> void preProcess(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {

    }

    @Override
    public <T> void postProcess(NativeWebRequest request, DeferredResult<T> deferredResult, Object concurrentResult) throws Exception {

    }

    @Override
    public <T> boolean handleTimeout(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        return false;
    }

    @Override
    public <T> boolean handleError(NativeWebRequest request, DeferredResult<T> deferredResult, Throwable t) throws Exception {
        return false;
    }

    @Override
    public <T> void afterCompletion(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {

    }
}
