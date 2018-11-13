package com.foyoedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ServletComponentScan
@EnableFeignClients(basePackages = {"com.foyoedu.common.service"})
//@EnableEurekaClient
public class Consumer_App {
    public static void main(String[] args) {
        SpringApplication.run(Consumer_App.class, args);
    }
}
