package com.guangde.home.controller.project;

import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.entry.ApiFrontUser;
import com.guangde.entry.ApiNewLeaveWord;
import com.guangde.entry.ApiProjectFeedback;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.EnterpriseConstants;
import com.guangde.home.constant.UrlConstants;
import com.guangde.home.utils.CommonUtils;
import com.guangde.home.utils.ResultUtil;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by ZHL on 2018/11/30.
 */
@Controller
@RequestMapping("/feedback")
public class ProjectFeedbackController {

    @Autowired
    private IProjectFacade projectFacade;

    @Autowired
    private IUserFacade userFacade;

    //项目反馈
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult feedbackBack(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  @RequestParam(value = "projectId", required = true) Integer projectId) {
        ApiProjectFeedback feedBack = new ApiProjectFeedback();
        feedBack.setProjectId(projectId);
        ApiPage<ApiProjectFeedback> page = projectFacade.queryH5ProjectFeedbckByCondition(feedBack, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }

    //留言
    @ResponseBody
    @RequestMapping("/add/newLeave")
    public ApiResult addNewLeaveWord(ApiNewLeaveWord model,
                                     HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        ApiResult result = CommonUtils.valUserLogin(request);
        if (result.getCode() != 1) {
            return result;
        }
        //type:0:反馈；1：捐赠；
        Integer userId = Integer.valueOf(result.getData().toString());

        ApiFrontUser user = userFacade.queryById(userId);

        model.setLeavewordName(user.getNickName());
        model.setLeavewordUserId(userId);
        model.setState(EnterpriseConstants.STATUS_SUCCESS);
        model.setIsRead(0);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        return projectFacade.saveNewLeaveWord(model);
    }

    //反馈留言列表
    /*@RequestMapping(value = "/leaveWord/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult feedbackLeaveWordList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                           @RequestParam("feedbackId") Integer feedbackId){


    }*/
    //捐赠留言列表
}
