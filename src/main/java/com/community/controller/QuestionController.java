package com.community.controller;

import com.community.dto.CommentDTO;
import com.community.dto.QuestionDTO;
import com.community.enums.CommentTypeEnum;
import com.community.service.CommentService;
import com.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author luoyelun
 * @date 2020/4/27 12:28
 */
@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model) {
        if (id != null) {
            //更新阅读数
            questionService.updateViewCount(id);
            //根据id获得问题信息
            QuestionDTO questionDTO = questionService.getById(id);
            List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
            List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
            model.addAttribute("question", questionDTO);
            model.addAttribute("comments", comments);
            model.addAttribute("relatedQuestions", relatedQuestions);
            return "question";
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable("id") Integer id) {
        questionService.deleteQuestion(id);
        return "redirect:/";
    }
}
