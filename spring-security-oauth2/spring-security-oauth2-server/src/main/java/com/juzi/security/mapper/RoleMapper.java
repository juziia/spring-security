package com.juzi.security.mapper;

import com.juzi.security.domain.SysRole;

import java.util.List;

public interface RoleMapper {

    List<SysRole> findByUid(Integer uid);
}
