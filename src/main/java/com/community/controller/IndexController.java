package com.community.controller;

import com.community.mapper.UserMapper;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luoyelun
 * @date 2020/4/23 17:25
 */
@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @GetMapping({"/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        /*
           1.获得cookies
           2.判断cookie中是否存在token
           3.存在则通过token在数据库中查找用户
           4.把用户信息添加到session
         */
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
