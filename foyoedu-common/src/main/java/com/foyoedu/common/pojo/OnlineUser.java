package com.foyoedu.common.pojo;

import lombok.Data;

/**
 * 在线用户实体类
 *
 */
@Data
public class OnlineUser {
    //当前用户的session id
    private String sessionId;
    //当前用户的ip地址
    private String ip;
    //当前用户第一次访问的时间
    private String firstTime;
}

