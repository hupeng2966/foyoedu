package com.foyoedu.springsecurity.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface LoginValidateDao {

    @Select("select isLogin from qrlogin where sessionId=#{sessionId}")
    public boolean findqrlogin(@Param("sessionId") String sessionId);

    @Insert("replace into qrlogin(sessionId,isLogin,createTime) values(#{sessionId},#{isLogin},#{createTime})")
    public boolean addqrlogin(@Param("sessionId") String sessionId, @Param("isLogin") boolean isLogin, @Param("createTime") Long createTime);

    @Update("update qrlogin set isLogin=#{isLogin} where sessionId=#{sessionId}")
    public boolean updateqrlogin(@Param("sessionId") String sessionId, @Param("isLogin") boolean isLogin);

    @Insert("replace into weixin(loginId,openId,sessionId,createTime) values(#{loginId},#{openId},#{sessionId},#{createTime})")
    public boolean addweixin(@Param("loginId") String loginId, @Param("openId") String openId, @Param("sessionId") String sessionId, @Param("createTime") Long createTime);

    @Select("select count(*) from weixin where openId=#{openId}")
    public boolean isExistsOpenId(@Param("openId") String openId);

    @Update("update weixin set sessionId=#{sessionId} where openId=#{openId}")
    public boolean updateweixin(@Param("openId") String openId, @Param("sessionId") String sessionId);

    @Select("select loginId from weixin where sessionId=#{sessionId}")
    public String findweixin(@Param("sessionId") String sessionId);
}
