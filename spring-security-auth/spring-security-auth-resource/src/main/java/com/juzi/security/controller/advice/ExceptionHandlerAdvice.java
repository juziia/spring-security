package com.juzi.security.controller.advice;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


//@RestControllerAdvice
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
