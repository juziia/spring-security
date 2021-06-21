package com.juzi.service.impl;

import com.juzi.dao.UserDao;
import com.juzi.domain.SysRole;
import com.juzi.domain.SysUser;
import com.juzi.service.RoleService;
import com.juzi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Override
    public void save(SysUser user) {
        userDao.save(user);
    }

    @Override
    public List<SysUser> findAll() {
        return userDao.findAll();
    }

    @Override
    public Map<String, Object> toAddRolePage(Integer id) {
        List<SysRole> allRoles = roleService.findAll();
        List<Integer> myRoles = userDao.findRolesByUid(id);
        Map<String, Object> map = new HashMap<>();
        map.put("allRoles", allRoles);
        map.put("myRoles", myRoles);
        return map;
    }

    @Override
    public void addRoleToUser(Integer userId, Integer[] ids) {
        userDao.removeRoles(userId);
        for (Integer rid : ids) {
            userDao.addRoles(userId, rid);
        }
    }

    /**
     *
     * @param username  form表单的用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userDao.findByName(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名或密码错误!" );
        }

        List<SysRole> roles = sysUser.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (SysRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        User user = new User(sysUser.getUsername(),sysUser.getPassword(),authorities);


        return user;
    }
}
