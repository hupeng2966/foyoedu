package com.foyoedu.standart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping(value = "/dept/hello")
    public String hello(){
        return "helloStandart8002";
    }
}
