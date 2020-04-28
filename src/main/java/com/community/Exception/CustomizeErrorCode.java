package com.community.Exception;

/**
 * @author luoyelun
 * @date 2020/4/28 10:26
 */

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    //question不存在
    QUESTION_NOT_FOUND("没找到这个问题,要不你换个试试？");

    private final String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
