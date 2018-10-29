package com.foyoedu.common.utils;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.PageResult;

import java.security.PublicKey;

public class FoyoUtils {

    public static FoyoResult errorMessage(Throwable throwable) {
        String msg = throwable.getMessage();
        Integer status = Integer.parseInt(msg.split(" ")[1]);
        return new FoyoResult(status, throwable.getMessage());
    }

    public static FoyoResult ok(Object data) {
        return new FoyoResult(data);
    }

    public static PageResult pageResult(Integer total, Object data) {
        return new PageResult(total, data);
    }

}
