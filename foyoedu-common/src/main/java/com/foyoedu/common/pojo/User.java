package com.foyoedu.common.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long userId;
    private String userName;
    private String loginId;
    private String pwd;
    private boolean isDelete;
}
