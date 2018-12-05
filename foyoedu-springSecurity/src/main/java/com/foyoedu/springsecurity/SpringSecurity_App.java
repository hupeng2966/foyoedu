package com.foyoedu.springsecurity;

import com.foyoedu.springsecurity.config.properties.SecurityProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(value = "com.foyoedu.springsecurity.dao")
@EnableConfigurationProperties(value = SecurityProperties.class)
@EnableScheduling
public class SpringSecurity_App {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurity_App.class, args);
    }
}
