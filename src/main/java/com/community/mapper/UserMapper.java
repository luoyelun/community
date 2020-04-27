package com.community.mapper;

import com.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author luoyelun
 * @date 2020/4/24 13:14
 */
@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modify,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModify},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where ACCOUNT_ID=#{accountId}")
    User findByAccountId(@Param("accountId") long accountId);

    @Update("update user set " +
            "NAME=#{name}," +
            "TOKEN=#{token}," +
            "GMT_MODIFY=#{gmtModify}," +
            "BIO=#{bio}," +
            "AVATAR_URL=#{avatarUrl} where ACCOUNT_ID=#{accountId}")
    void updateUser(User user);
}
