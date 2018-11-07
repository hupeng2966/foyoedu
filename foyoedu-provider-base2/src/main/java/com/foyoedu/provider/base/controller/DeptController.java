package com.foyoedu.provider.base.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {
    @GetMapping(value = "/dept/hello")
    public String hello(){
        return "hello8003";
    }
}
