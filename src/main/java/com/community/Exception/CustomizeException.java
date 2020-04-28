package com.community.Exception;

/**
 * @author luoyelun
 * @date 2020/4/28 10:09
 */

public class CustomizeException extends RuntimeException {
    private String message;


    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
