package com.juzi.security.config;

import com.juzi.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;


/**
 * JdbcTokenStore
 * JdbcClientDetailsService
 * JdbcAuthoriaztion
 */
@EnableAuthorizationServer
@Configuration
public class OauthSecurityServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 用户认证业务对象
    @Autowired
    private UserService userService;

    // 授权模式对象
    @Autowired
    private AuthenticationManager authenticationManager;

    // 数据库连接池对象
    @Autowired
    private DataSource dataSource;


    // 客户端信息来源业务对象
    @Bean
    ClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    // token信息持久化对象
    @Bean
    TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    // 授权信息保存策略对象
    @Bean
    ApprovalStore jdbcApprovalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    // 授权码模式数据来源对象
    @Bean
    AuthorizationCodeServices jdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    // 配置客户端信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(jdbcClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .approvalStore(jdbcApprovalStore())
                .tokenStore(jdbcTokenStore())
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(jdbcAuthorizationCodeServices())
                .userDetailsService(userService);       // 在刷新令牌时需要用到该对象
    }

    // 检查token的策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许以form表单方式传递
        security.allowFormAuthenticationForClients();
        // 检查token需要已认证
        security.checkTokenAccess("isAuthenticated()");
    }
}
