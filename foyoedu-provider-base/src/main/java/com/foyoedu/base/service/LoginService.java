package com.foyoedu.base.service;

import com.foyoedu.common.pojo.FoyoResult;

public interface LoginService {

    public FoyoResult userLogin(String loginId, String pwd);

}
