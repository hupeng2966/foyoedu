package com.foyoedu.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Data
public class CommonConfig {
    @Value("${cookie.token_key}")
    private String TOKEN_KEY;

    @Value("${zuul.username}")
    private String ZUUL_USERNAME;

    @Value("${zuul.pwd}")
    private String ZUUL_PWD;

    @Value("${redis.session.expire}")
    private Long REDIS_SESSION_EXPIRE;

    @Value("${fastdfs.storage.service-url}")
    private String FASTDFS_STORAGE_URL;

}
