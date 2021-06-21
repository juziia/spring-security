package com.juzi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

//@EnableResourceServer
//@Configuration
public class OauthSecurityResourceConfig extends ResourceServerConfigurerAdapter {



    // Token持久化方式
    @Bean
    TokenStore redisTokenStore(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTokenStore redisTokenStore = new RedisTokenStore(lettuceConnectionFactory);
        redisTokenStore.setPrefix("token:");
        return redisTokenStore;
    }

}
