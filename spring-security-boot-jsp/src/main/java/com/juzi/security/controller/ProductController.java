package com.juzi.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/product")
public class ProductController {


    @PreAuthorize("hasAnyRole('ROLE_PRODUCT','ROLE_ADMIN')")
    @RequestMapping("/findAll")
    public String findAll(){
        return "product-list";
    }
}
