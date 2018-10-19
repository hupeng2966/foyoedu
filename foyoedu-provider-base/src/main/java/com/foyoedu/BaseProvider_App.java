package com.foyoedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.foyoedu.dao")
/*@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient //服务发现*/
public class BaseProvider_App {
    public static void main(String[] args)
    {
        SpringApplication.run(BaseProvider_App.class, args);
    }
}
