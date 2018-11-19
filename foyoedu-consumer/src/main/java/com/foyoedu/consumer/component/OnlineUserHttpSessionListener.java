package com.foyoedu.consumer.component;

import com.alibaba.fastjson.JSON;
import com.foyoedu.common.pojo.OnlineUser;
import com.foyoedu.common.utils.FoyoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@WebListener
public class OnlineUserHttpSessionListener implements HttpSessionListener {
    @Autowired
    private StringRedisTemplate template;

    @Value("${spring.application.name}")
    private String APPLICATION_NAME;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpServletRequest request = FoyoUtils.getRequest();
        OnlineUser user = new OnlineUser();
        String sessionId = se.getSession().getId();
        user.setSessionId(sessionId);
        user.setIp(request.getRemoteAddr());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        user.setFirstTime(sdf.format(new Date()));
        template.opsForValue().append("ONLINE:" + APPLICATION_NAME + ":" + sessionId, JSON.toJSONString(user));
        template.expire("ONLINE:" + APPLICATION_NAME + ":" + sessionId, 12, TimeUnit.HOURS);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        template.delete("ONLINE:" + APPLICATION_NAME + ":" + se.getSession().getId());
    }
}
