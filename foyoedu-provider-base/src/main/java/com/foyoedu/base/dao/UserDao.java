package com.foyoedu.base.dao;

import com.foyoedu.common.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface UserDao {
    @Select("select * from t_user where loginId=#{loginId}")
    public User findByLoginId(@Param("loginId") String loginId);
}
