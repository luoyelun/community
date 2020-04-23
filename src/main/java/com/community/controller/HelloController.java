package com.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author luoyelun
 * @date 2020/4/23 17:25
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(@RequestParam("name") String name, Model model) {
        model.addAttribute("msg", "你好，" + name);
        return "hello";
    }
}
