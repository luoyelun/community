package com.community.enums;

/**
 * @author luoyelun
 * @date 2020/4/28 16:33
 */

public enum CommentTypeEnum {
    //
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExits(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (type.equals(value.getType())) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
