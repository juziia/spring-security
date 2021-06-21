package com.juzi.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {


    @RequestMapping("/findAll")
    @PreAuthorize("hasRole('ROLE_ORDER')")
    public String findAll(){
        return "order-list";
    }
}
