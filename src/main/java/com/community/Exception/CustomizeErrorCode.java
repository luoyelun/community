package com.community.Exception;

/**
 * @author luoyelun
 * @date 2020/4/28 10:26
 */

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    //question不存在
    QUESTION_NOT_FOUND(2001, "没找到这个问题,要不你换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中问题或评论进行回复"),
    NO_LOGIN(2003, "未登录,请登陆后重试"),
    SYS_ERROR(2004, "服务器冒烟了，要不然你等会再试试。"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUNT(2006, "回复的评论不存在"),
    NOTIFICATION_NOT_FOUND(2007, "消息已经不翼而飞？");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

}
