package com.community.mapper;

import com.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luoyelun
 * @date 2020/4/24 22:26
 */
@Mapper
@Repository
public interface QuestionMapper {
    @Insert("insert into QUESTION(title,description,gmt_create,gmt_modify,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModify},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question order by id desc")
    List<Question> list();

    @Select("select * from question where CREATOR=#{accountId} order by id desc")
    List<Question> listByAccountId(@Param("accountId") long accountId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") long id);

    @Update("update question set VIEW_COUNT=VIEW_COUNT+1 where id=#{id}")
    void updateViewCount(@Param("id") long id);

    @Update("update question set TITLE=#{title},DESCRIPTION=#{description},GMT_MODIFY=#{gmtModify},TAG=#{tag} where id=#{id}")
    void updateQuestionById(Question question);

}
