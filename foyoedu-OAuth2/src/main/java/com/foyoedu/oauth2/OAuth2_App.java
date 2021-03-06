package com.foyoedu.oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan(value = "com.foyoedu.oauth2.dao")
@EnableConfigurationProperties()
public class OAuth2_App {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2_App.class, args);
    }
}
