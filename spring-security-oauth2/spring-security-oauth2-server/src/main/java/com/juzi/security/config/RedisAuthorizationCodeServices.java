package com.juzi.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.concurrent.TimeUnit;

public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private static final String PREFIX = "auth:code:";


    private RedisTemplate redisTemplate;

    public RedisAuthorizationCodeServices(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        redisTemplate.opsForValue().set(PREFIX + code, authentication,15, TimeUnit.MINUTES);
    }

    @Override
    protected OAuth2Authentication remove(String code) {

        code = PREFIX + code;
        OAuth2Authentication authentication = null;
        try {
            authentication = (OAuth2Authentication) redisTemplate.opsForValue().get(code);
        } catch (Exception e) {
            return null;
        }

        if (authentication != null) {
            redisTemplate.delete(code);
        }

        return authentication;
    }
}
