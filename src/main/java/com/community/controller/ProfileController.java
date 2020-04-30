package com.community.controller;

import com.community.dto.NotificationDTO;
import com.community.dto.QuestionDTO;
import com.community.model.User;
import com.community.service.NotificationService;
import com.community.service.QuestionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luoyelun
 * @date 2020/4/26 15:06
 */
@Controller
public class ProfileController {
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        User user = (User) request.getSession().getAttribute("user");
        if (action == null || "questions".equals(action)) {
            PageInfo<QuestionDTO> pageList = questionService.list(pageNum, user.getAccountId());
            model.addAttribute("section", "我的提问");
            model.addAttribute("pageList", pageList);
        } else if ("replies".equals(action)) {
            PageInfo<NotificationDTO> pageList = notificationService.list(pageNum, user.getAccountId());
            model.addAttribute("section", "最新回复");
            model.addAttribute("pageList", pageList);
        }
        model.addAttribute("this", action);

        return "profile";
    }

}
