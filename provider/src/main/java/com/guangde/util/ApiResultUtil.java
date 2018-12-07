package com.guangde.util;

import com.guangde.business.util.ResultCode;
import com.guangde.enums.ResultEnum;
import com.guangde.pojo.ApiResult;

public class ApiResultUtil
{
    /**
     * 成功
     */
    public final static ApiResult SUCCESS = new ApiResult(ResultEnum.Success);
    
    /**
     * 失败
     */
    public final static ApiResult ERROR = new ApiResult(ResultEnum.Error);

    public static ApiResult getParameterError()
    {
        return getApiResult(ResultCode.ParameterError);
    }
    
    public static ApiResult getParameterError(String msg)
    {
        ApiResult ret = getApiResult(ResultCode.ParameterError);
        //ret.setMessage(ret.getMessage() + ":" + msg);
        
        return ret;
    }

    public static ApiResult getApiResult(ResultCode resultCode)
    {
        
        if (resultCode != null)
        {
            ApiResult apiResult = new ApiResult();
            apiResult.setCode(resultCode.getCode());
            //apiResult.setMessage(resultCode.getDescription());
            return apiResult;
        }
        else
        {
            return ERROR;
        }
        
    }
}
