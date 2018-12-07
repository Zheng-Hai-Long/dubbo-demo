package com.guangde.home.utils;

import com.alibaba.fastjson.JSONObject;
import com.guangde.api.commons.ICommonFacade;
import com.guangde.api.commons.IFileFacade;
import com.guangde.api.homepage.IProjectFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.entry.ApiBFile;
import com.guangde.entry.ApiConfig;
import com.guangde.entry.ApiFrontUser;
import com.guangde.entry.ApiProjectUserInfo;
import com.guangde.home.constant.PengPengConstants;
import com.redis.utils.RedisService;
import com.tenpay.demo.H5Demo;
import com.tenpay.utils.TenpayUtil;
import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2018/4/9.
 */
public class WxUtil {

    Logger logger = LoggerFactory.getLogger(WxUtil.class);

    public static IFileFacade fileFacade = SpringContextUtil.getBean("fileFacade",IFileFacade.class);
    public static IUserFacade userFacade = SpringContextUtil.getBean("userFacade",IUserFacade.class);
    public static RedisService redisService = SpringContextUtil.getBean("redisService",RedisService.class);
    public static ICommonFacade commonFacade = SpringContextUtil.getBean("commonFacade",ICommonFacade.class);
    public static IProjectFacade projectFacade = SpringContextUtil.getBean("projectFacade",IProjectFacade.class);

    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
                              ModelAndView mv, String uuid, String flag){
        String openId ="";
        String token = "";
        String unionid = "";
        StringBuffer url = request.getRequestURL();
        String queryString = request.getQueryString();
        String perfecturl = url + "?" + queryString;
        String browser = UserUtil.Browser(request);
        ApiFrontUser user = new ApiFrontUser();
        if(browser.equals("wx")){
            String weixin_code = request.getParameter("code");
            Map<String, Object> mapToken = new HashMap<String, Object>(8);
            try {
                Object OToken = redisService.queryObjectData("weixin_token");
                token = (String)OToken;
                logger.info("wxlogin >> weixin_code = "+weixin_code + "  openId = "+openId+"  OToken = "+OToken);

                if ("".equals(openId) || openId == null || OToken == null) {
                    if ("".equals(weixin_code) || weixin_code == null
                            || weixin_code.equals("authdeny")) {
                        String url_weixin_code = H5Demo.getCodeRequest(perfecturl);
                        mv = new ModelAndView("redirect:" + url_weixin_code);
                        return mv;
                    }
                    mapToken = CommonUtils.getAccessTokenAndopenidRequest(weixin_code);
                    openId = mapToken.get("openid").toString();
                    token = mapToken.get("access_token").toString();
                    unionid = mapToken.get("unionid").toString();
                    logger.info("wxlogin >> CommonUtils.getAccessTokenAndopenidRequest openId "+openId+" token = "+token);
                    redisService.saveObjectData("weixin_token", token, DateUtil.DURATION_HOUR_S);
                }
                //user = CommonUtils.queryUser(request,openId,token,unionid,response);
                redisService.saveObjectData(uuid, user.getId(), CookieManager.EXPIRED_30MINUTE/30);
            } catch (Exception e) {
                logger.error("微信登录处理出现问题"+ e);
                e.printStackTrace();
            }

            if (user.getCoverImageId() == null) {
                user.setCoverImageUrl("https://www.17xs.org/res/images/user/4.jpg"); // 个人头像
            } else {
                if(user.getCoverImageId() != null && user.getCoverImageId() == 0){
                    ApiBFile aBFile = fileFacade.queryBFileById(Integer.valueOf(user.getCoverImageId()));
                    user.setCoverImageUrl(aBFile.getUrl()); // 个人头像
                }
            }
            try{
                // 自动登录
                SSOUtil.login(user, request, response);
            }
            catch(Exception e)
            {
                logger.error("SOUtil.login",e);
                e.printStackTrace();
            }
        }
        else
        {
            mv = new ModelAndView("redirect:/ucenter/user/Login_H5.do");
            if(!"".equals(flag)){
                mv.addObject("flag", flag);
            }
            return mv ;

        }
        return mv;
    }

    public ModelAndView share(HttpServletRequest request, ModelAndView mv){
        String browser = UserUtil.Browser(request);
        if(browser.equals("wx")){
            //微信调度的唯一凭证AccessToken失效，要同时更新jsTicket这个临时凭证
            String jsTicket = (String) redisService.queryObjectData("JsapiTicket");
            String accessToken = (String) redisService.queryObjectData("AccessToken");
            if(jsTicket == null || accessToken == null){
                accessToken = TenpayUtil.queryAccessToken();
                redisService.saveObjectData("AccessToken" , accessToken, DateUtil.DURATION_HOUR_S);
                jsTicket = TenpayUtil.queryJsapiTicket(accessToken);
                redisService.saveObjectData("JsapiTicket" , jsTicket, DateUtil.DURATION_HOUR_S);
            }
            String url2 = request.getRequestURL().toString();
            url2 = url2.replace("http", "https");
            String queryString2 = request.getQueryString();
            String perfecturl2 = url2 + "?" + queryString2;
            SortedMap<String, String> map = null;
            try {
                map = H5Demo.getConfigweixin(jsTicket,perfecturl2);
            } catch (JDOMException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mv.addObject("appId",map.get("appId"));
            mv.addObject("timestamp",map.get("timeStamp"));
            mv.addObject("noncestr",map.get("nonceStr"));
            mv.addObject("signature",map.get("signature"));
        }
        return mv;
    }

    public JSONObject share(HttpServletRequest request, JSONObject result){
        JSONObject data = new JSONObject();
        String browser = UserUtil.Browser(request);
        if(browser.equals("wx")){
            //微信调度的唯一凭证AccessToken失效，要同时更新jsTicket这个临时凭证
            String jsTicket = (String) redisService.queryObjectData("JsapiTicket");
            String accessToken = (String) redisService.queryObjectData("AccessToken");
            if(jsTicket == null || accessToken == null){
                accessToken = TenpayUtil.queryAccessToken();
                redisService.saveObjectData("AccessToken" , accessToken, DateUtil.DURATION_HOUR_S);
                jsTicket = TenpayUtil.queryJsapiTicket(accessToken);
                redisService.saveObjectData("JsapiTicket" , jsTicket, DateUtil.DURATION_HOUR_S);
            }
            String url2 = request.getRequestURL().toString();
            url2 = url2.replace("http", "https");
            String queryString2 = request.getQueryString();
            String perfecturl2 = url2 + "?" + queryString2;
            SortedMap<String, String> map = null;
            try {
                map = H5Demo.getConfigweixin(jsTicket,perfecturl2);
            } catch (JDOMException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            data.put("appId",map.get("appId"));
            data.put("timestamp",map.get("timeStamp"));
            data.put("noncestr",map.get("nonceStr"));
            data.put("signature",map.get("signature"));
        }
        result.put("share", data);
        return result;
    }

    public ModelAndView getRandomLeaveWord(ModelAndView mv){
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setConfigKey(StringUtil.LEAVEWORD);
        List<ApiConfig> configs = commonFacade.queryList(apiConfig);
        if(configs!=null&&configs.get(0)!=null&&configs.get(0).getConfigValue()!=null&&configs.get(0).getConfigValue()!=""){
            String[] configss = configs.get(0).getConfigValue().split("。");
            Random random = new Random();
            int i = random.nextInt(configss.length);
            mv.addObject("leaveWord", configss[i]);
        }
        return mv;
    }

    public JSONObject getRandomLeaveWord(JSONObject result){
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setConfigKey(StringUtil.LEAVEWORD);
        List<ApiConfig> configs = commonFacade.queryList(apiConfig);
        if(configs!=null&&configs.get(0)!=null&&configs.get(0).getConfigValue()!=null&&configs.get(0).getConfigValue()!=""){
            String[] configss = configs.get(0).getConfigValue().split("。");
            Random random = new Random();
            int i = random.nextInt(configss.length);
            result.put("leaveWord", configss[i]);
        }
        return result;
    }


    public ModelAndView getProjectUserInfo(ModelAndView mv, Integer projectId){
        ApiProjectUserInfo userInfo = new ApiProjectUserInfo();
        userInfo.setProjectId(projectId);
        List<ApiProjectUserInfo> userInfos = projectFacade.queryProjectUserInfoList(userInfo);
        for (ApiProjectUserInfo u : userInfos) {
            if (u.getPersonType() == 0) {
                mv.addObject("shouzhu", u);
            } else if (u.getPersonType() == 1) {
                mv.addObject("zhengming", u);
            } else if (u.getPersonType() == 2) {
                mv.addObject("fabu", u);
            }
        }
        return mv;
    }

    public void saveProjectClick(Integer projectId){
        try
        {
            //获取当天剩余缓存时间
            long startTime = (DateUtil.getCurrentDayEnd().getTime() - new Date().getTime())/1000;
            String clickRate = (String) redisService.queryObjectData(PengPengConstants.PROJECT_CLICKRATE_H5+projectId);
            if(StringUtils.isEmpty(clickRate))
            {
                redisService.saveObjectData(PengPengConstants.PROJECT_CLICKRATE_H5+projectId, "t_h5_"+projectId+"_"+1,startTime);
            }
            else
            {
                String cstr[] = clickRate.split("_");
                if(null != cstr && cstr.length >= 4)
                {
                    Integer click = Integer.parseInt(cstr[3]);
                    if(null == click)
                    {
                        click = 1 ;
                    }
                    else
                    {
                        click += 1 ;
                    }

                    redisService.saveObjectData(PengPengConstants.PROJECT_CLICKRATE_H5+projectId, "t_h5_"+projectId+"_"+click,startTime);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
