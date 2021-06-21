package com.juzi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {


    // 只有 产品角色才能访问
//    @Secured("ROLE_PRODUCT")
//    @RolesAllowed("ROLE_PRODUCT")
    @PreAuthorize("hasRole('ROLE_PRODUCT')")
    @RequestMapping("/findAll")
    public String findAll() {
        // 获取当前用户
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = (User) currentUser;
        System.out.println(user.getUsername()+"==" + "==" + user.getPassword() + "==" + user.getAuthorities());
        // 获取当前用户名称
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("username: " + name);

        return "product-list";
    }
}
