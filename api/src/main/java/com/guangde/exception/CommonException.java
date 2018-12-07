package com.guangde.exception;

/**
 * Created by ZHL on 2018/12/7.
 */
public class CommonException extends RuntimeException {

    private int code;

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
