package com.juzi.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;


@EnableResourceServer // 当前是一个 资源服务器
@Configuration
public class OauthResourcesConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     *  TokenStore:  Token持久化方案
     * @return
     */
    TokenStore jdbcTokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId("product_api")
                .tokenStore(jdbcTokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 设置请求方式对应的scope
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST,"/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT,"/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE,"/**").access("#oauth2.hasScope('write')")
                .anyRequest().authenticated()
                .and()
                .headers()
                .addHeaderWriter((request,response) -> {
                  // 域检请求
                   if (request.getMethod().equals("OPTIONS")) {
                       response.addHeader("Access-Control-Allow-Origin","*");
                       response.addHeader("Access-Control-Allow-Methods",request.getHeader("Access-Control-Request-Methods"));
                       response.addHeader("Access-Control-Allow-Headers",request.getHeader("Access-Control-Request-Headers"));
                   }
                });
    }
}
