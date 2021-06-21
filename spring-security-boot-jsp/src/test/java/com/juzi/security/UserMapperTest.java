package com.juzi.security;


import com.juzi.security.domain.SysUser;
import com.juzi.security.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void test(){
        SysUser sysUser = userMapper.findByName("xiaoming");
        System.out.println(sysUser);
    }
}
