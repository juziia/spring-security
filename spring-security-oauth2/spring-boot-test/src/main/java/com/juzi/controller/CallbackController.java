package com.juzi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("callback")
@RestController
public class CallbackController {


    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("verify")
    public String verify(@RequestParam(value = "code", required = false)String code){

//        MultiValueMap<String,String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("grant_type","authorization_code");
//        requestBody.add("client_id","juzi");
//        requestBody.add("client_secret","123");
//        requestBody.add("code",code);
//        HttpEntity httpEntity = new HttpEntity(requestBody,null);
//
//        ResponseEntity<Map> responseEntity = restTemplate.exchange("http://localhost:9003/oauth/token", HttpMethod.POST, httpEntity, Map.class);
//        Map<String,String> result = responseEntity.getBody();
//        String access_token = result.get("access_token");
//
//
//        String body = restTemplate.exchange("http://localhost:9002/product/findAll?access_token=" + access_token, HttpMethod.GET, null, String.class).getBody();

        return "";
    }

}
