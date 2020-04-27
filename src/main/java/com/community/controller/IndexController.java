package com.community.controller;

import com.community.dto.QuestionDTO;
import com.community.mapper.UserMapper;
import com.community.service.QuestionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luoyelun
 * @date 2020/4/23 17:25
 */
@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer pageNum) {
        PageInfo<QuestionDTO> pageList = questionService.pageList(pageNum);
        model.addAttribute("pageList", pageList);
        return "index";
    }
}
