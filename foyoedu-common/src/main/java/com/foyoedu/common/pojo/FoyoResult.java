package com.foyoedu.common.pojo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * 复尧自定义响应结构
 */
@Data
public class FoyoResult implements Serializable {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String errMsg;

    // 响应中的数据
    private Object data;

    public FoyoResult() {}
    public FoyoResult(Object data) {
        this.status = 200;
        this.data = data;
    }

    public FoyoResult(Integer status, String errMsg) {
        this.status = status;
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
