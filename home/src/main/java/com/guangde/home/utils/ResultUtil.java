package com.guangde.home.utils;

import com.guangde.enums.ResultEnum;
import com.guangde.pojo.ApiResult;

/**
 * Created by ZHL on 2018/11/13.
 */
public class ResultUtil {

    public static ApiResult SUCCESS(){
        ApiResult result = new ApiResult();
        result.setCode(ResultEnum.Success.getCode());
        result.setMsg(ResultEnum.Success.getMsg());
        return result;
    }

    public static ApiResult SUCCESS(Object object){
        ApiResult result = new ApiResult();
        result.setCode(ResultEnum.Success.getCode());
        result.setMsg(ResultEnum.Success.getMsg());
        result.setData(object);
        return result;
    }

}
