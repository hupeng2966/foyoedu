package com.foyoedu.provider.standart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {
    @RequestMapping(value = "/dept/hello")
    public String hello() {
        return "helloStandart8004";
    }
}
