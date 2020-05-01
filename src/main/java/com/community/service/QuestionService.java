package com.community.service;

import com.community.Exception.CustomizeErrorCode;
import com.community.Exception.CustomizeException;
import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionExtMapper;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.model.Question;
import com.community.model.QuestionExample;
import com.community.model.User;
import com.community.model.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    /**
     * 首页问题列表
     */
    public PageInfo<QuestionDTO> pageList(int pageNum, String search) {
        //分页
        PageHelper.startPage(pageNum, 10);
        //获得分页信息、相应条数的question
        PageInfo<Question> questions;
        //查找所有
        if (StringUtils.isEmpty(search)) {
            QuestionExample questionExample = new QuestionExample();
            questionExample.setOrderByClause("GMT_CREATE desc");
            questions = new PageInfo<>(questionMapper.selectByExample(questionExample), 5);
        } else {
            //根据关键词查找
            questions = new PageInfo<>(questionExtMapper.search(search));
        }
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
    public PageInfo<QuestionDTO> list(Integer pageNum, long accountId) {
        PageHelper.startPage(pageNum, 10);
        //获得该用户发布的问题
        QuestionExample example = new QuestionExample();
        example.createCriteria().
                andCreatorEqualTo(accountId);
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
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);

        questionDTO.setUser(findByAccountId(question.getCreator().longValue()));
        return questionDTO;
    }

    /**
     * 更改阅读数
     */
    public void updateViewCount(Integer id) {
        questionExtMapper.updateViewCount(id);
    }

    /**
     * 插入或更新question
     */
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            questionMapper.insertSelective(question);
        } else {
            question.setGmtModify(System.currentTimeMillis());
            int updated = questionMapper.updateByPrimaryKeySelective(question);
            if (updated == 0) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    /**
     * 查找user通过创建者ID
     */
    public User findByAccountId(Long creator) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(creator);
        return userMapper.selectByExample(example).get(0);
    }

    public void deleteQuestion(Integer id) {
        questionMapper.deleteByPrimaryKey(id);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isEmpty(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String replace = StringUtils.replace(queryDTO.getTag(), ",", "|");
//        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
//        String regexpTag = Arrays
//                .stream(tags)
//                .filter(StringUtils::isEmpty)
//                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
//                .filter(StringUtils::isEmpty)
//                .collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(replace);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
