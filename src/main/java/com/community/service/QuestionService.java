package com.community.service;

import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author luoyelun
 * @date 2020/4/25 15:12
 */
@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 首页问题列表
     */
    public PageInfo<QuestionDTO> pageList(int pageNum) {
        //分页
        PageHelper.startPage(pageNum, 10);
        //获得分页信息、相应条数的question
        PageInfo<Question> questions = new PageInfo<>(questionMapper.list(), 5);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questions, pageInfo);
        pageInfo.setList(new ArrayList<QuestionDTO>());
        for (Question question : questions.getList()) {
            //查找问题创建者用户信息
            User user = userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            pageInfo.getList().add(questionDTO);
        }
        return pageInfo;
    }

    /**
     * 查找我的问题，通过当前用户id
     */
    public PageInfo<QuestionDTO> list(Integer pageNum, long id) {
        PageHelper.startPage(pageNum, 5);
        System.out.println(id);
        //获得该用户发布的问题
        PageInfo<Question> questions = new PageInfo<>(questionMapper.listByAccountId(id), 5);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questions, pageInfo);
        pageInfo.setList(new ArrayList<QuestionDTO>());
        for (Question question : questions.getList()) {
            //查找问题创建者用户信息
            User user = userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            pageInfo.getList().add(questionDTO);
        }
        return pageInfo;
    }

    /**
     * 通过问题的id获得question
     * 将question和user set到questionDto并返回
     */
    public QuestionDTO getById(long id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(userMapper.findByAccountId(question.getCreator()));
        return questionDTO;
    }

    /**
     * 更改阅读数
     */
    public void updateViewCount(long id) {
        questionMapper.updateViewCount(id);
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            questionMapper.create(question);
        } else {
            question.setGmtModify(System.currentTimeMillis());
            questionMapper.updateQuestionById(question);
        }
    }
}
