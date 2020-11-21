package com.fzl.mall.common.api;

/**
 * 封装了常用的API操作码
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200,"操作成功"),
    FAILED(500,"操作失败"),
    VALIDATE_FAILED(404,"参数检验失败"),
    UNAUTHORIZED(401,"暂未登录或者token失效"),
    FORBIDDEN(403,"没有相关权限");
    private long code;
    private String message;

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
