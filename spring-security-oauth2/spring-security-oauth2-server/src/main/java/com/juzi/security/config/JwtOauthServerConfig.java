package com.juzi.security.config;

import com.juzi.security.service.UserService;
import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Arrays;

//@Configuration
//@EnableAuthorizationServer
public class JwtOauthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private JwtTokenConvert jwtTokenConvert;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${auth.rsa.keyFile}")
    private String keyFile;

    // 数据库连接池对象
    @Autowired
    private DataSource dataSource;

    // password模式管理对象
    @Autowired
    private AuthenticationManager authenticationManager;

    // 用户认证对象
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    // Token保存策略
    @Bean
    TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    // 客户端信息来源
    @Bean
    ClientDetailsService jdbcClientDetailsService(){
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }

    // 授权码信息来源
    @Bean
    AuthorizationCodeServices redisAuthorizationCodeService(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    // 授权信息来源
    @Bean
    ApprovalStore approvalStore(){
        return new JdbcApprovalStore(dataSource);
    }




    // oauth2的端点配置
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(jwtTokenStore())
                .userDetailsService(userService)
                .approvalStore(approvalStore())
                .authorizationCodeServices(redisAuthorizationCodeService());
    }

    // 客户端信息配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .withClientDetails(jdbcClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
        security.allowFormAuthenticationForClients();   // 允许客户端使用表单认证
    }

    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        KeyProperties keyProperties = keyProperties();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new PathResource(keyFile), "juzi123".toCharArray())
                .getKeyPair("juzi");
        jwtAccessTokenConverter.setKeyPair(keyPair);
        DefaultAccessTokenConverter converter = (DefaultAccessTokenConverter) jwtAccessTokenConverter.getAccessTokenConverter();

        converter.setUserTokenConverter(jwtTokenConvert);

        return jwtAccessTokenConverter;
    }


    @Bean
    KeyProperties keyProperties(){
        return new KeyProperties();
    }


}
