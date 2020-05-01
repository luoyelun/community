package com.community.mapper;

import com.community.model.Question;
import com.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionExtMapper {
    void updateViewCount(Integer id);

    void updateCommentCount(Question record);

    List<Question> selectRelated(Question question);

    List<Question> search(@Param("search") String search);
}