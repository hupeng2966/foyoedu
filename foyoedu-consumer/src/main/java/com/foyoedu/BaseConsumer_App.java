package com.foyoedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BaseConsumer_App {
    public static void main(String[] args) {
        SpringApplication.run(BaseConsumer_App.class, args);

    }

}
