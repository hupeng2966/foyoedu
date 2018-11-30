package com.foyoedu.consumer.controller;

import com.foyoedu.common.config.CommonConfig;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.LoginClientService;
import com.foyoedu.common.utils.CookieUtils;
import com.foyoedu.common.utils.FoyoUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @Autowired
    private CommonConfig config;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public FoyoResult login(@ApiParam(value = "帐号", allowEmptyValue = false) @RequestParam("loginId") String loginId, @ApiParam(value = "密码", allowEmptyValue = false) @RequestParam("pwd") String pwd) throws Throwable {
        FoyoResult result = loginClientService.login(loginId, pwd);
        //判断登录是否成功
        if(result.getStatus() == 200) {
            HttpServletResponse response = FoyoUtils.getResponse();
            String token = result.getData().toString();
            CookieUtils.setCookie(FoyoUtils.getRequest(), response, config.getTOKEN_KEY(), token);
            response.sendRedirect("/foyo/main.html");
        }
        return result;
    }

}
