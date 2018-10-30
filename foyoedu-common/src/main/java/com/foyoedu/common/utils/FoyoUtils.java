package com.foyoedu.common.utils;

import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PublicKey;

@Slf4j
public class FoyoUtils {

    public static FoyoResult errorMessage(Throwable throwable) {
        //Logger logger = LoggerFactory.getLogger(FoyoUtils.class);
        String msg = throwable.getMessage();
        log.error(msg);
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
