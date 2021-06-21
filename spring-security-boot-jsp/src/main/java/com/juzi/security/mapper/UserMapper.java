package com.juzi.security.mapper;

import com.juzi.security.domain.SysUser;

public interface UserMapper {

    SysUser findByName(String username);
}
