package com.community.service;

import com.community.mapper.UserMapper;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author luoyelun
 * @date 2020/4/27 15:35
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        if (userMapper.findByAccountId(user.getAccountId()) == null) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //更新
            user.setGmtModify(System.currentTimeMillis());
            userMapper.updateUser(user);
        }
    }
}
