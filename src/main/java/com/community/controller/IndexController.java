package com.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author luoyelun
 * @date 2020/4/23 17:25
 */
@Controller
public class IndexController {
    @GetMapping({"/", "/index", "/index.html"})
    public String index() {
        return "index";
    }
}
