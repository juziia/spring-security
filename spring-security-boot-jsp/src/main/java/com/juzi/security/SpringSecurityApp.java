package com.juzi.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@MapperScan("com.juzi.security.mapper")
@SpringBootApplication
public class SpringSecurityApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApp.class,args);
        System.out.println(">>>>> ");
    }

}

