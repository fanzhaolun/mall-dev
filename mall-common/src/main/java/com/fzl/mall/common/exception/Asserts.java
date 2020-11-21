package com.fzl.mall.common.exception;

import com.fzl.mall.common.api.IErrorCode;

/**
 * 断言处理类，用于抛出各种异常
 */
public class Asserts {
    public static void fail(String message){
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode){
        throw new ApiException(errorCode);
    }
}
