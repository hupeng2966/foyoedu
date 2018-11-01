package com.foyoedu.common.service;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.hystrix.DeptClientServiceFactory;
import com.foyoedu.common.service.hystrix.LoginClientServiceFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "FOYOEDU-ZUUL", fallbackFactory = LoginClientServiceFactory.class)
@RequestMapping(value = "/base")
public interface LoginClientService {
    @PostMapping("/login")
    public FoyoResult login(@RequestParam("loginId") String loginId, @RequestParam("pwd") String pwd);
}
