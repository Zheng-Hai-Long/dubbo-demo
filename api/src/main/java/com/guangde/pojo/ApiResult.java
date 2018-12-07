package com.guangde.pojo;

import com.guangde.enums.ResultEnum;

import java.io.Serializable;

public class ApiResult<T> implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2384153615176547748L;

    private int code;

    private String msg;

    private T data;

    public ApiResult(){}

    public ApiResult(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(ResultEnum resultEnum) {
        super();
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public ApiResult(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result [code=" + code + ", msg=" + msg + ", data=" + data
                + "]";
    }
    
}
