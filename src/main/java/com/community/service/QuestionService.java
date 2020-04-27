package com.community.service;

import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.QuestionExample;
import com.community.model.User;
import com.community.model.UserExample;
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
        PageInfo<Question> questions = new PageInfo<>(questionMapper.selectByExample(new QuestionExample()), 5);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questions, pageInfo);
        pageInfo.setList(new ArrayList<QuestionDTO>());
        for (Question question : questions.getList()) {
            //查找问题创建者用户信息
            User user = findByAccountId(question.getCreator());
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
        //获得该用户发布的问题
        QuestionExample example = new QuestionExample();
        example.createCriteria().
                andCreatorEqualTo(Math.toIntExact(id));
        PageInfo<Question> questions = new PageInfo<>(questionMapper.selectByExample(example), 5);
        PageInfo<QuestionDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questions, pageInfo);
        pageInfo.setList(new ArrayList<QuestionDTO>());
        for (Question question : questions.getList()) {
            //查找问题创建者用户信息
            User user = findByAccountId(question.getCreator());
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
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);

        questionDTO.setUser(findByAccountId(question.getCreator()));
        return questionDTO;
    }

    /**
     * 更改阅读数
     */
    public void updateViewCount(Integer id) {
        questionMapper.updateViewCount(id);
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            questionMapper.insertSelective(question);
        } else {
            question.setGmtModify(System.currentTimeMillis());
            questionMapper.updateByPrimaryKey(question);
        }
    }

    //查找user通过创建者ID
    public User findByAccountId(Long creator) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(creator);
        return userMapper.selectByExample(example).get(0);
    }
}
