package com.juzi.security.filter;

import com.juzi.security.config.RsaKeyProperties;
import com.juzi.security.domain.Payload;
import com.juzi.security.domain.SysUser;
import com.juzi.security.utils.JsonUtils;
import com.juzi.security.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AuthVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyProperties rsaKeyProperties;


    public AuthVerifyFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKeyProperties) {
        super(authenticationManager);
        this.rsaKeyProperties = rsaKeyProperties;
    }


    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        // 没有携带token
        if (header == null || !header.startsWith("Bearer ")) {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Map<String, String> map = new HashMap<String, String>();
            map.put("code", "4001");
            map.put("message", "没有认证");
            String str = JsonUtils.toString(map);
            response.getWriter().write(str);
            return;
        }
        // 携带了token
        String token = header.substring(7);
        Payload<SysUser> payload = JwtUtils.getInfoFromToken(token, rsaKeyProperties.getPublicKey(), SysUser.class);
        SysUser userInfo = payload.getUserInfo();

        if (userInfo != null) {
            UsernamePasswordAuthenticationToken authResult = new
                    UsernamePasswordAuthenticationToken(userInfo.getUsername(),null,userInfo.getRoles());

            // 设置到上下文中, 用于后续的权限认证
            SecurityContextHolder.getContext().setAuthentication(authResult);
            // tokeny有效 继续执行
            chain.doFilter(request, response);

        } else {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Map<String, String> map = new HashMap<String, String>();
            map.put("code", "4002");
            map.put("message", "token无效");
            String str = JsonUtils.toString(map);
            response.getWriter().write(str);
            return;
        }

    }

}
