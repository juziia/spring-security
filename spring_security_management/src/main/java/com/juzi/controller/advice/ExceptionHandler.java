package com.juzi.controller.advice;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

//@Component
@ControllerAdvice
public class ExceptionHandler/* implements HandlerExceptionResolver */{


    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public String handlerException(){

        return "redirect:/403.jsp";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public String handlerLoginFFailer(){

        return "redirect:/failer.jsp";
    }

//    @Override
//    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//       ModelAndView mav = new ModelAndView();
//        if (e instanceof AccessDeniedException) {
//
//           mav.setViewName("redirect:/403.jsp");
//           return mav;
//       }
//
//       mav.setViewName("redirect:/failer.jsp");
//        return mav;
//    }
}
