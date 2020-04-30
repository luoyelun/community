package com.community.dto;

import com.community.model.Comment;
import com.community.model.User;

/**
 * @author luoyelun
 * @date 2020/4/29 10:57
 */

public class CommentDTO extends Comment {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
