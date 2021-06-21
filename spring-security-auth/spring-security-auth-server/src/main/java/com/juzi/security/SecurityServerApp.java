package com.juzi.security;

import com.juzi.security.config.RsaKeyProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 *  认证服务器
 */

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@MapperScan("com.juzi.security.mapper")
public class SecurityServerApp {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApp.class,args);
    }

}
