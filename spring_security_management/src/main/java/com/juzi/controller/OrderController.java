package com.juzi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    // 拥有管理员, 订单角色可以访问
//    @Secured({"ROLE_ADMIN","ROLE_ORDER"})
//    @RolesAllowed({"ROLE_ADMIN","ROLE_ORDER"})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ORDER')")
    @RequestMapping("/findAll")
    public String findAll(){
        return "order-list";
    }
}
