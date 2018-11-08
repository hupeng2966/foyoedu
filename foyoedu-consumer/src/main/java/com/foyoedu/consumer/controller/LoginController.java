package com.foyoedu.consumer.controller;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.LoginClientService;
import com.foyoedu.common.utils.CookieUtils;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {

    @Autowired
    private LoginClientService loginClientService;
    @Value("${cookie.token_key}")
    private String TOKEN_KEY;

    @PostMapping("/login")
    public FoyoResult login(@RequestParam("loginId") String loginId, @RequestParam("pwd") String pwd) throws Throwable {
        FoyoResult result = loginClientService.login(loginId, pwd);
        //判断登录是否成功
        if(result.getStatus() == 200) {
            HttpServletResponse response = FoyoUtils.getResponse();
            String token = result.getData().toString();
            CookieUtils.setCookie(FoyoUtils.getRequest(), response, TOKEN_KEY, token);
            response.sendRedirect("/foyo/main.html");
        }
        return result;
    }

}
