package com.juzi.security.controller.advice;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public String handler(){

        return "redirect:/403.jsp";
    }

    @ExceptionHandler(Throwable.class)
    public String handlerException(){

        return "redirect:/failer.jsp";
    }

}
