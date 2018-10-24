package com.foyoedu.eureka;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Eureka_App {
    public static void main(String[] args) {
        SpringApplication.run(Eureka_App.class, args);
    }
}
