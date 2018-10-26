package com.foyoedu.common.utils;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.PageResult;

import java.security.PublicKey;

public class FoyoUtils {

    public static FoyoResult errorMessage(Throwable throwable) {
        return new FoyoResult(throwable.getMessage());
    }

    public static FoyoResult ok(Object data) {
        return new FoyoResult(data);
    }

    public static FoyoResult ok() {
        return new FoyoResult(null);
    }

    public static PageResult pageResult(Integer total, Object data) {
        return new PageResult(total, data);
    }

}
