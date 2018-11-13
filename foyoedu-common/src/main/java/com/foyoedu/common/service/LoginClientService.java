package com.foyoedu.common.service;

import com.foyoedu.common.service.hystrix.LoginClientServiceFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(value = "FOYOEDU-PROVIDER-BASE", fallbackFactory = LoginClientServiceFactory.class)
@FeignClient(value = "FOYOEDU-ZUUL", fallbackFactory = LoginClientServiceFactory.class)
@RequestMapping(value = "/base")
public interface LoginClientService {
    @PostMapping(value = "/login")
    public String login(@RequestParam("loginId") String loginId, @RequestParam("pwd") String pwd);
}
