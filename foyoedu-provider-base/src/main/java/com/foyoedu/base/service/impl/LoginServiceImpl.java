package com.foyoedu.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.foyoedu.base.dao.TeacherDao;
import com.foyoedu.base.service.LoginService;
import com.foyoedu.common.config.CommonConfig;
import com.foyoedu.common.pojo.FoyoResult;
import com.foyoedu.common.pojo.Teacher;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TeacherDao dao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CommonConfig config;

    @Override
    public FoyoResult userLogin(String loginId, String pwd) {
        Teacher user = dao.findByLoginId(loginId);
        if(user == null) {
            return FoyoUtils.error(401, "用户名不存在");
        }
        if(!DigestUtils.md5DigestAsHex(pwd.getBytes()).equals(user.getPwd())) {
            return FoyoUtils.error(401, "用户名或密码不正确");
        }
        if(user.isDelete()) {
            return FoyoUtils.error(401,"该用户目前已处于停用状态，请联系系统管理员!");
        }
        // 如果正确生成token
        String token = "SESSION:" + UUID.randomUUID().toString();
        // 把用户信息写入redis、key：token value：用户信息
        user.setPwd("");

        stringRedisTemplate.opsForValue().append(token, JSON.toJSONString(user));
        // 设置Session的过期时间
        stringRedisTemplate.expire(token, config.getREDIS_SESSION_EXPIRE(), TimeUnit.MINUTES);
        // 把token返回
        return FoyoUtils.ok(token);
    }
}
