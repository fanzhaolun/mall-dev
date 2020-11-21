package com.fzl.mall.common.exception;

import com.fzl.mall.common.api.IErrorCode;

/**
 * 自定义Api异常
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }


    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
