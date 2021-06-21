package com.juzi.security;

import com.juzi.security.config.RsaKeyProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.juzi.security.mapper")
@EnableConfigurationProperties(RsaKeyProperties.class)
public class OauthServerApp {

    public static void main(String[] args) {
        SpringApplication.run(OauthServerApp.class,args);
    }

}
