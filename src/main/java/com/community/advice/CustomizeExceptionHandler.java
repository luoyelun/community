package com.community.advice;

import com.community.Exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luoyelun
 * @date 2020/4/28 9:52
 * 拦截springmvc handle的异常
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
        if (e instanceof CustomizeException) {
            model.addAttribute("msg", e.getMessage());
        } else {
            model.addAttribute("msg", "服务器冒烟了，要不然你等会再试试。");
        }
        return new ModelAndView("error");
    }
}
