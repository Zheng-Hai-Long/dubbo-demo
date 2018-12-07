package com.guangde.business.pojo;

import com.guangde.business.entry.BaseBean;
import com.guangde.business.enums.ResultEnum;
import com.guangde.business.util.ResultCode;

import java.io.Serializable;

public class Result<T> extends BaseBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 799313542455010253L;

    private int code;

    private String msg;

    private ResultCode resultCode;

    private T data;

    public Result(){}

    public Result(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public Result(ResultEnum resultEnum) {
        super();
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public Result(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = data;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
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
