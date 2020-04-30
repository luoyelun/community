package com.community.mapper;

import com.community.model.Comment;
import com.community.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentExtMapper {
    void updateCommentCount(@Param("id") Integer id);
}