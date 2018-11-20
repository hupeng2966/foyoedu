package com.foyoedu.common.service.hystrix;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.service.LoginClientService;
import com.foyoedu.common.utils.FoyoUtils;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class LoginClientServiceFactory implements FallbackFactory<LoginClientService> {

    @Override
    public LoginClientService create(Throwable throwable) {
        final Throwable t = throwable;
        return new LoginClientService() {
            public FoyoResult login(String loginId, String password) {
                return FoyoUtils.errorMessage(t);
            }
        };
    }
}
