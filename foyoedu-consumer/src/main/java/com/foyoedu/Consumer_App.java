package com.foyoedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.foyoedu.common.service"})
//@ComponentScan(value = "com.foyoedu")
public class Consumer_App {
    public static void main(String[] args) {
        SpringApplication.run(Consumer_App.class, args);
    }
}
