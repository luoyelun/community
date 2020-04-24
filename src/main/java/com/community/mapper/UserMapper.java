package com.community.mapper;

import com.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author luoyelun
 * @date 2020/4/24 13:14
 */
@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modify) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModify})")
    void insert(User user);
}
