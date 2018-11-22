package com.foyoedu.common.pojo;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;


public class Teacher implements Serializable {
    public interface TeacherWithoutPwd {};
    public interface TeacherWithPwd {};

    private Long userId;

    private String userName;

    private String loginId;

    private String pwd;

    private Integer campusId;

    private Boolean isDelete;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @JsonView(TeacherWithPwd.class)
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public Boolean isDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
