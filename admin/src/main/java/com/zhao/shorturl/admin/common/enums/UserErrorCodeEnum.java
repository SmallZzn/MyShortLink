

package com.zhao.shorturl.admin.common.enums;

import com.zhao.shorturl.admin.common.convention.errorcode.IErrorCode;

/**
 * 用户错误码
 * 小赵
 */
public enum UserErrorCodeEnum implements IErrorCode {

    USER_NULL("B000200", "该用户记录不存在"),

    USER_NAME_EXIST("B000201", "该用户名已存在"),

    USER_EXIST("B000202", "该用户记录已存在"),

    USER_SAVE_ERROR("B000203", "该用户记录新增失败");

    private final String code;

    private final String message;

    UserErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
