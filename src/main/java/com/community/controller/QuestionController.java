package com.community.controller;

import com.community.dto.QuestionDTO;
import com.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author luoyelun
 * @date 2020/4/27 12:28
 */
@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model) {
        if (id != null) {
            questionService.updateViewCount(id);
            QuestionDTO questionDTO = questionService.getById(id);
            model.addAttribute("question", questionDTO);
            return "question";
        }
        return "redirect:/";
    }
}
