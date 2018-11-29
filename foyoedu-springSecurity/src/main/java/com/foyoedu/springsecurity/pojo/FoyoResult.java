package com.foyoedu.springsecurity.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FoyoResult implements Serializable {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String errMsg;

    // 响应中的数据
    private Object data;

    public static FoyoResult ok(Object data){
        FoyoResult result = new FoyoResult();
        result.setStatus(200);
        result.setData(data);
        return  result;
    }

    public static FoyoResult error(Integer status, String errMsg){
        FoyoResult result = new FoyoResult();
        result.setStatus(status);
        result.setErrMsg(errMsg);
        return  result;
    }
}
