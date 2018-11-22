package com.foyoedu.oath2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan(value = "com.foyoedu.base.dao")
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
public class OAuth2_App {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2_App.class, args);
    }
}
