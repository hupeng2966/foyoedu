package com.foyoedu.consumer.controller;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.LoginClientService;
import com.foyoedu.common.utils.CookieUtils;
import com.foyoedu.common.utils.FoyoUtils;
import com.foyoedu.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class LoginController {
    @Autowired
    private LoginClientService loginClientService;
    @Value("${cookie.token_key}")
    private String TOKEN_KEY;

    @PostMapping("/login")
    public String login(@RequestParam("loginId") String loginId, @RequestParam("pwd") String pwd) throws Throwable {
        String result = loginClientService.login(loginId, pwd);
        FoyoResult foyoResult = JsonUtils.jsonToPojo(result, FoyoResult.class);
        //判断登录是否成功
        if(foyoResult.getStatus() == 200) {
            HttpServletResponse response = FoyoUtils.getResponse();
            String token = foyoResult.getData().toString();
            CookieUtils.setCookie(FoyoUtils.getRequest(), response, TOKEN_KEY, token);
            response.sendRedirect("/foyo/main.html");
        }
        return result;
    }

}
