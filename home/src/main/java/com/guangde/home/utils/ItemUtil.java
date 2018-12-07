package com.guangde.home.utils;

import com.alibaba.fastjson.JSONObject;
import com.guangde.pojo.ApiResult;

/**
 * Created by Administrator on 2018/4/8.
 */
public class ItemUtil {


    public JSONObject otherResult(String msg){
        JSONObject item = new JSONObject();
        item.put("code", 3);
        item.put("msg", msg);
        return item;
    }

    public JSONObject otherResult(String msg, JSONObject result){
        JSONObject item = new JSONObject();
        item.put("code", 3);
        item.put("msg", msg);
        item.put("result", result);
        return item;
    }

    public JSONObject noLogin(String browser){
        JSONObject item = new JSONObject();
        JSONObject result = new JSONObject();
        result.put("browserType", browser);
        item.put("result", result);
        item.put("code", 0);
        item.put("msg", "未登录");
        return item;
    }

    public JSONObject noLogin(String browser, JSONObject result){
        JSONObject item = new JSONObject();
        result.put("browserType", browser);
        item.put("result", result);
        item.put("code", 0);
        item.put("msg", "未登录");
        return item;
    }

    public JSONObject success(JSONObject result){
        JSONObject item = new JSONObject();
        item.put("result", result);
        item.put("code", 1);
        item.put("msg", "成功");
        return item;
    }

    public JSONObject success(){
        JSONObject item = new JSONObject();
        item.put("code", 1);
        item.put("msg", "成功");
        return item;
    }

    public JSONObject error(){
        JSONObject item = new JSONObject();
        item.put("code", -1);
        item.put("msg", "服务器错误");
        return item;
    }

    public JSONObject error(ApiResult res){
        JSONObject item = new JSONObject();
        item.put("code", res.getCode());
        ///item.put("msg", res.getMessage());
        return item;
    }

    public JSONObject result(ApiResult res){
        JSONObject item = new JSONObject();
        item.put("code", res.getCode());
        //item.put("msg", res.getMessage());
        return item;
    }
}
