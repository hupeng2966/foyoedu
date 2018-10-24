package com.foyoedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.foyoedu.common.service"})
@ComponentScan(value = "com.foyoedu.consumer")
public class BaseConsumer_App {
    public static void main(String[] args) {
        SpringApplication.run(BaseConsumer_App.class, args);

    }

}
