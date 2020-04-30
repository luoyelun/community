package com.community.enums;

/**
 * @author luoyelun
 * @date 2020/4/30 16:54
 */

public enum NotificationStatusEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
