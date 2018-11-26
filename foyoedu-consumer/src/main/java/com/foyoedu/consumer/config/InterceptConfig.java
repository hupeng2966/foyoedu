package com.foyoedu.consumer.config;

import com.foyoedu.consumer.component.MyCallableInterceptor;
import com.foyoedu.consumer.component.MyDeferredResultInterceptor;
import com.foyoedu.consumer.component.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
public class InterceptConfig extends WebMvcConfigurationSupport {
    @Autowired
    private TimeInterceptor timeInterceptor;
    @Autowired
    private MyCallableInterceptor myCallableInterceptor;
    @Autowired
    private MyDeferredResultInterceptor myDeferredResultInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor);
    }

    @Override
    protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.registerCallableInterceptors(myCallableInterceptor);
        configurer.registerDeferredResultInterceptors(myDeferredResultInterceptor);
    }
}
