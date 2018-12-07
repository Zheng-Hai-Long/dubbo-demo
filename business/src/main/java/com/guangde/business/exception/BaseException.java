package com.guangde.business.exception;

import com.guangde.business.enums.ResultEnum;
import com.guangde.business.util.ResultCode;

public class BaseException extends Exception
{
    
    /**
    * 注释内容
    */
    
    private static final long serialVersionUID = 3988874950233746140L;
    
    private ResultCode resultCode;

    private int code;

    private String msg;

    public BaseException()
    {
        super();
    }
    
    public BaseException(ResultCode resultCode)
    {
        super(resultCode.getDescription());
        this.resultCode = resultCode;
    }

    public BaseException(ResultEnum resultEnum)
    {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public BaseException(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public BaseException(ResultCode resultCode, Throwable t)
    {
        super(resultCode.getDescription(), t);
        this.resultCode = resultCode;
    }

    @Override
    public String toString()
    {
        return "errorCode:" + resultCode.getCode() + ", msg:" + getMessage();
    }
    
    public ResultCode getResultCode()
    {
        return resultCode;
    }
    
}

