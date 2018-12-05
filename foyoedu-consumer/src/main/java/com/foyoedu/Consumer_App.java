package com.foyoedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan
@EnableAsync
@EnableFeignClients(basePackages = {"com.foyoedu.common.service"})
@EnableSwagger2
public class Consumer_App {
    public static void main(String[] args) {
        SpringApplication.run(Consumer_App.class, args);
    }
}
