package com.juzi.security.config;

import com.juzi.security.service.UserService;
import com.juzi.security.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 继承 WebSecurityConfigurerAdapter类, 配置SpringSecurity
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
    private UserService userService;

/*    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/


    //配置用户认证的方式
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/*        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder());*/
        auth.inMemoryAuthentication();

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**","/img/**","/plugins/**","/login.jsp").permitAll()
                .antMatchers("/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                /*.loginPage("/login.jsp")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.jsp")*/
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.jsp")
                .permitAll()
                .and()
                .csrf()
                .disable();
                //.rememberMe()/*
                //.;*/
    }


}
