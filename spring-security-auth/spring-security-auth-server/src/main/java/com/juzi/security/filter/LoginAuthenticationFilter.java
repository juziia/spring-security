package com.juzi.security.filter;

import com.juzi.security.config.RsaKeyProperties;
import com.juzi.security.domain.SysRole;
import com.juzi.security.domain.SysUser;
import com.juzi.security.utils.JsonUtils;
import com.juzi.security.utils.JwtUtils;
import org.apache.catalina.Authenticator;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.management.remote.JMXAuthenticator;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private RsaKeyProperties rsaKeyProperties;
    private AuthenticationManager authenticationManager;

    public LoginAuthenticationFilter(RsaKeyProperties rsaKeyProperties, AuthenticationManager authenticationManager) {
        this.rsaKeyProperties = rsaKeyProperties;
        this.authenticationManager = authenticationManager;
    }

    /**
     *  重写认证过滤器中的逻辑, 将原本采用Post方式的表单验证 修改为 请求体中获取数据进行验证
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            SysUser sysUser = JsonUtils.mapper.readValue(request.getInputStream(), SysUser.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    sysUser.getUsername(), sysUser.getPassword());

            return authenticationManager.authenticate(authRequest);
        } catch (Exception e) {

            throw new RuntimeException("用户名或者密码错误");
        }
    }

    // 认证成功后会调用此方法, 重写方法逻辑, 认账成功颁发令牌 token
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

//        SecurityContextHolder.getContext().setAuthentication(authResult);


        SysUser sysUser = new SysUser();
        sysUser.setUsername(authResult.getName());
        sysUser.setRoles((List<SysRole>) authResult.getAuthorities());
        String token = JwtUtils.generateTokenExpireInMinutes(sysUser, rsaKeyProperties.getPrivateKey(), 30);

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Authorization","Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String,String> map = new HashMap<String, String>();
        map.put("errorMsg","认证失败");
        map.put("code","401");
        String str = JsonUtils.toString(map);
        response.getWriter().write(str);
    }
}
