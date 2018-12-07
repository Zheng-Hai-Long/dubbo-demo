package com.guangde.home.controller.activity;

import com.guangde.api.homepage.IHomePageFacade;
import com.guangde.dto.ActivityDTO;
import com.guangde.dto.ActivityDetailDTO;
import com.guangde.entry.ApiActivityConfig;
import com.guangde.entry.ApiCommonFormUser;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.UrlConstants;
import com.guangde.home.utils.CommonUtils;
import com.guangde.home.utils.ResultUtil;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by ZHL on 2018/11/21.
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private IHomePageFacade homePageFacade;


    /**
     * 活动列表
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult activityList(ApiActivityConfig param, HttpServletResponse response,
                                  @RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                                  @RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize){
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        ApiPage<ActivityDTO> page = homePageFacade.queryActivityPageByParam(param, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }

    /**
     * 活动详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult projectDetail(HttpServletResponse response, @RequestParam("id") Integer id){
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ActivityDetailDTO activityConfig = homePageFacade.queryById(id);
        if(activityConfig == null){
            return new ApiResult(ResultEnum.EmptyData);
        }
        return new ApiResult(ResultEnum.Success, activityConfig);
    }

    /**
     * 根据id查询表单配置
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "/form/config", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult fromConfig(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("id") Integer id){

        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }
        return homePageFacade.queryCommonFormById(id);
    }

    /**
     * 提交表单
     * @param request
     * @param response
     * @param param
     * @return
     */
    @RequestMapping(value = "/form/submit", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult submitForm(HttpServletRequest request, HttpServletResponse response,
                                @RequestBody List<ApiCommonFormUser> param) {
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if (result.getCode() != 1) {
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());
        System.out.println(param);
        return homePageFacade.submitForm(param, userId);
    }

    /**
     * 提交详情
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "/form/info", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult formInfo(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam("id") Integer id){

        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }
        Integer userId = Integer.valueOf(result.getData().toString());
        return homePageFacade.queryCommonFormInfo(id, userId);
    }

    //报名记录
    @RequestMapping(value = "/record", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult record(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                            @RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize){

        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }
        Integer userId = Integer.valueOf(result.getData().toString());
        ApiPage<ApiActivityConfig> page = homePageFacade.queryCommonFormUserPageByParam(userId, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }
}
