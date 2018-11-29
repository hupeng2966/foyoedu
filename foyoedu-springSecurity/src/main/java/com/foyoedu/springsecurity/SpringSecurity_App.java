package com.foyoedu.springsecurity;

import com.foyoedu.springsecurity.configBean.SecurityProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan(value = "com.foyoedu.oauth2.dao")
@EnableConfigurationProperties(value = SecurityProperties.class)
public class SpringSecurity_App {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurity_App.class, args);
    }
}
