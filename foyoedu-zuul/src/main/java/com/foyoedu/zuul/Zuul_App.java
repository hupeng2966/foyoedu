package com.foyoedu.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class Zuul_App {
    public static void main(String[] args) {
        SpringApplication.run(Zuul_App.class, args);
    }
}
