package com.juzi.security.service.impl;

import com.juzi.security.domain.SysUser;
import com.juzi.security.mapper.RoleMapper;
import com.juzi.security.mapper.UserMapper;
import com.juzi.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        SysUser sysUser = userMapper.findByName(s);
        System.out.println(sysUser);
        return sysUser;
    }
}
