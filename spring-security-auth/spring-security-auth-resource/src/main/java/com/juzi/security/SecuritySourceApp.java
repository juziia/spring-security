package com.juzi.security;


import com.juzi.security.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SecuritySourceApp {
    public static void main(String[] args) {
        SpringApplication.run(SecuritySourceApp.class,args);
    }
}
