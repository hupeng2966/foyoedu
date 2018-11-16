package com.foyoedu.common.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Teacher implements Serializable {
    private Long userId;
    private String userName;
    private String loginId;
    private String pwd;
    private Integer campusId;
    private boolean isDelete;
}
