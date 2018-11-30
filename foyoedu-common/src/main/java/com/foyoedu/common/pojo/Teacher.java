package com.foyoedu.common.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.foyoedu.common.annotation.Phone;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Teacher implements Serializable {
    public interface TeacherWithoutPwd {};
    public interface TeacherWithPwd {};

    @ApiModelProperty(value = "用户id", allowEmptyValue = true)
    private Long userId;

    @ApiModelProperty(value = "姓名", allowEmptyValue = false)
    @NotBlank(message = "姓名不能为空")
    private String userName;

    @ApiModelProperty(value = "帐号", allowEmptyValue = false)
    @NotBlank(message = "登录号不能为空")
    private String loginId;

    @ApiModelProperty(value = "密码", allowEmptyValue = false)
    private String pwd;

    @Phone
    @ApiModelProperty(value = "手机号码", allowEmptyValue = true)
    private String phone;

    @ApiModelProperty(value = "校区号", allowEmptyValue = true)
    private Integer campusId;

    @ApiModelProperty(value = "是否被冻结", allowEmptyValue = true)
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Boolean isDelete() {
        return isDelete;
    }
}
