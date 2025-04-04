package com.zhao.shorturl.project.common.convention.exception;

import com.zhao.shorturl.project.common.convention.errorcode.BaseErrorCode;
import com.zhao.shorturl.project.common.convention.errorcode.IErrorCode;

/**
 * 客户端异常
 * 小赵
 */
public class ClientException extends BaseException {

    public ClientException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, BaseErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code = '" + errorCode + "'," +
                "message = '" + errorMessage + "'" +
                '}';
    }
}
