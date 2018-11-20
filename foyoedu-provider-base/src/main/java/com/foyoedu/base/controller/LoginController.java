package com.foyoedu.base.controller;

import com.foyoedu.base.service.LoginService;
import com.foyoedu.common.pojo.FoyoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public FoyoResult login(@RequestParam("loginId") String loginId, @RequestParam("pwd") String pwd) {
        return loginService.userLogin(loginId, pwd);
    }
}
