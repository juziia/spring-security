package com.juzi.security.config;

import com.juzi.security.domain.SysUser;
import com.juzi.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtTokenConvert extends DefaultUserAuthenticationConverter {

    @Autowired
    UserService userService;

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        LinkedHashMap response = new LinkedHashMap();
        String name = authentication.getName();
        response.put("username", name);

        Object principal = authentication.getPrincipal();
        SysUser SysUser = null;
        if(principal instanceof  SysUser){
            SysUser = (SysUser) principal;
        }else{
            //refresh_token默认不去调用userdetailService获取用户信息，这里我们手动去调用，得到 SysUser
            UserDetails userDetails = userService.loadUserByUsername(name);
            SysUser = (SysUser) userDetails;
        }
        response.put("username", SysUser.getUsername());
        response.put("id", SysUser.getId());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }
}
