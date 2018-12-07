package com.guangde.home.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.guangde.api.commons.ICommonFacade;
import com.guangde.api.commons.IFileFacade;
import com.guangde.api.homepage.IHomePageFacade;
import com.guangde.api.homepage.INewsFacade;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.homepage.IProjectVolunteerFacade;
import com.guangde.api.user.ICompanyFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.entry.*;
import com.guangde.home.constant.PengPengConstants;
import com.guangde.home.utils.DateUtil;
import com.guangde.home.utils.StringUtil;
import com.guangde.home.utils.UserUtil;
import com.guangde.home.utils.webUtil;
import com.redis.utils.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("resposibilityReport")
public class ResposibilityReportController {
    private final Logger logger = LoggerFactory.getLogger(ResposibilityReportController.class);

    @Autowired
    private IProjectVolunteerFacade projectVolunteerFacade;

    @Autowired
    private INewsFacade newsFacade;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IFileFacade fileFacade;

    @Autowired
    private IUserFacade userFacade;

    @Autowired
    private IHomePageFacade homePageFacade;

    @Autowired
    private IProjectFacade projectFacade;

    @Autowired
    private ICommonFacade commonFacade;

    @Autowired
    private ICompanyFacade companyFacade;

    @RequestMapping(value = "entryFormView")
    public ModelAndView entryFormView(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "id", required = false) Integer id) {
        ModelAndView view = new ModelAndView("commonForm/entryForm");
        ApiCommonForm model = new ApiCommonForm();
        model.setId(id);
        ApiCommonForm apiCommonForm = projectVolunteerFacade.selectByParam(model);
        Date currentTimeDate = new Date();
        view.addObject("apiCommonForm", apiCommonForm);
        view.addObject("currentTime", currentTimeDate);
        view.addObject("userId", UserUtil.getUserId(request, response));
        return view;
    }



    /**
     * 加载表单
     *
     * @param id
     * @return
     */
    @RequestMapping("entryFormNewList")
    @ResponseBody
    public Map<String, Object> entryFormNewList(@RequestParam(value = "id") Integer id) {
        ApiCommonForm model = new ApiCommonForm();
        model.setId(id);
        ApiCommonForm apiCommonForm = projectVolunteerFacade.selectByParam(model);
        return webUtil.successRes(apiCommonForm);
    }





    /**
     * 发起人接口
     *
     * @param request
     * @param response
     * @param projectId
     * @return
     */
    @RequestMapping(value = "getInitiatorDetail", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject initiator_detail(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "projectId", required = true) Integer projectId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject item = new JSONObject();
        JSONObject result = new JSONObject();

        ApiProjectUserInfo userInfo = new ApiProjectUserInfo();
        userInfo.setProjectId(projectId);
        userInfo.setPersonType(2);
        String key = PengPengConstants.PROJECT_USERINFO_LIST + "_" + projectId;
        userInfo.initNormalCache(false, DateUtil.DURATION_HOUR_S, key);
        List<ApiProjectUserInfo> userInfos = projectFacade.queryProjectUserInfoList(userInfo);
        if (userInfos != null && userInfos.size() > 0) {
            result.put("headImage", (userInfos.get(0).getHeadImageUrl() == null || userInfos.get(0).getHeadImageUrl() == "") ? "https://www.17xs.org/res/images/detail/people_avatar.jpg" : userInfos.get(0).getHeadImageUrl());
            result.put("realName", userInfos.get(0).getRealName());
            result.put("linkMan", userInfos.get(0).getLinkMan());
            result.put("mobile", userInfos.get(0).getLinkMobile());
            result.put("familyAddress", userInfos.get(0).getFamilyAddress());

        } else {
            item.put("result", result);
            item.put("code", -1);
            item.put("msg", "error");
        }
        item.put("result", result);
        item.put("code", 1);
        item.put("msg", "success");
        return item;
    }

    /**
     * 项目详情接口
     *
     * @param request
     * @param response
     * @param projectId
     * @return
     */
    @RequestMapping(value = "getProjectDetail", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject project_detail(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "projectId", required = true) Integer projectId,
                                     @RequestParam(value = "userId", required = false) Integer userId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject item = new JSONObject();
        JSONObject result = new JSONObject();
        ApiFrontUser user = new ApiFrontUser();
        if (userId != null) {
            user = userFacade.queryById(userId);
            result.put("realName", user.getRealName());
            result.put("mobileNum", user.getMobileNum());
        } else {
            result.put("realName", "");
            result.put("mobileNum", "");
        }
        ApiProject p = projectFacade.queryProjectDetail(projectId);
        if (p != null) {
            result.put("projectId", p.getId());
            result.put("projectLogo", p.getCoverImageUrl());
            result.put("projectTitle", p.getTitle());
            result.put("projectSubTitle", p.getSubTitle());
            result.put("totalMoney", p.getCryMoney());
            result.put("donatAmount", p.getDonatAmount());
            result.put("donationNum", p.getDonationNum());
            double process = 0.0;
            if (p.getCryMoney() >= 0.001) {
                process = p.getDonatAmount() / p.getCryMoney();
            }
            result.put("donatePercent", process > 1 ? "100" : StringUtil.doublePercentage(p.getDonatAmount(), p.getCryMoney(), 0));
            //随机留言
            ApiConfig apiConfig = new ApiConfig();
            apiConfig.setConfigKey(StringUtil.LEAVEWORD);
            List<ApiConfig> configs = commonFacade.queryList(apiConfig);
            if (configs != null && configs.get(0) != null && configs.get(0).getConfigValue() != null && configs.get(0).getConfigValue() != "") {
                String[] configss = configs.get(0).getConfigValue().split("。");
                Random random = new Random();
                int i = random.nextInt(configss.length);
                result.put("leaveWord", configss[i]);
            }
        } else {
            item.put("result", result);
            item.put("code", -1);
            item.put("msg", "项目不存在error");
            return item;
        }
        item.put("result", result);
        item.put("code", 1);
        item.put("msg", "success");
        return item;
    }

    /**
     * 项目内容转化成小程序识别的html格式
     *
     * @param request
     * @param response
     * @param projectId
     * @return
     */
    @RequestMapping(value = "getProjectContent", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject project_content(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(value = "projectId", required = true) Integer projectId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        JSONObject item = new JSONObject();
        JSONObject result = new JSONObject();

        ApiProject p = projectFacade.queryProjectDetail(projectId);
        if (p != null) {//详情接口(content)  span(text) img(image)  strong、b(<view class="b"></view) p(view class="pp")  br(\n) &nbsp( )
			/*String content = p.getContent().replaceAll("span", "text").replaceAll("img", "image").replaceAll("<br/>", "\n")
					.replaceAll("<strong", "<view class=\"b\"").replaceAll("</strong>", "</view>")
					.replaceAll("<b", "<view class=\"b\"").replaceAll("</b>", "</view>")
					.replaceAll("<p", "<view class=\"pp\"").replaceAll("</p>", "</view>")
					.replaceAll("&nbsp", " ");*/

            result.put("projectId", p.getId());
            result.put("projectContent", p.getContent());
        } else {
            item.put("result", result);
            item.put("code", -1);
            item.put("msg", "项目不存在error");
            return item;
        }
        item.put("result", result);
        item.put("code", 1);
        item.put("msg", "success");
        return item;
    }



    private JSONObject getCommonFormInfo(Integer id, JSONObject result) {
        try {
            ApiCommonForm apiCommonForm = new ApiCommonForm();
            apiCommonForm.setId(id);
            apiCommonForm = projectVolunteerFacade.selectByParam(apiCommonForm);
            result.put("commonForm", apiCommonForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}
