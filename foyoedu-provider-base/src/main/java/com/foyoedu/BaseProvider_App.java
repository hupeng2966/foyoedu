package com.foyoedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan(value = "com.foyoedu.base.dao")
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableRabbit
public class BaseProvider_App {
    public static void main(String[] args)
    {
        SpringApplication.run(BaseProvider_App.class, args);
    }
}
