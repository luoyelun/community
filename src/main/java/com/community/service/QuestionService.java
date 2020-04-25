package com.community.service;

import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<QuestionDTO> list(int pageNum) {
        PageHelper.startPage(pageNum, 10);
        //从数据库中获得所有问题列表
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            //查找问题创建者用户信息
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public PageInfo<QuestionDTO> pageList(int pageNum) {
        //分页
        PageHelper.startPage(pageNum, 15);
        //获得分页信息、相应条数的question
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionMapper.list());
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo, pageInfo);
        pageInfo.setList(new ArrayList<QuestionDTO>());
        for (Question question : questionPageInfo.getList()) {
            //查找问题创建者用户信息
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            pageInfo.getList().add(questionDTO);
        }
        pageInfo.setNavigatePages(5);
        return pageInfo;
    }
}
