package com.guangde.home.controller.wx;

import com.guangde.api.user.IUserFacade;
import com.guangde.entry.ApiFrontUser;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.RedisConstans;
import com.guangde.home.utils.CommonUtils;
import com.guangde.home.utils.DateUtil;
import com.guangde.home.utils.StringUtil;
import com.guangde.home.utils.UserUtil;
import com.guangde.home.vo.user.UserVO;
import com.guangde.home.vo.user.WxShareVO;
import com.guangde.pojo.ApiResult;
import com.redis.utils.RedisService;
import com.tenpay.demo.H5Demo;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/28.
 */
@Controller
@RequestMapping("/wx")
public class WxController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserFacade userFacade;

    @Autowired
    private RedisService redisService;


    /**
     * 微信登录
     * @param response
     * @param request
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    @RequestMapping(value="/login",method= RequestMethod.GET)
    @ResponseBody
    public ApiResult wxLoginHtml(@RequestParam(value="url",required=true)String url,
                                 HttpServletResponse response, HttpServletRequest request) throws JDOMException, IOException {
        ApiResult result = new ApiResult<>();

        Integer userId = null;

        String sessionId = "";

        String browser = UserUtil.Browser(request);
        String openId ="";
        String token = "";
        String unionid = "";
        StringBuffer url2 = request.getRequestURL();
        String queryString = request.getQueryString();
        if(browser.equals("wx")){
            String weixin_code = request.getParameter("code");
            Map<String, Object> mapToken = new HashMap<String, Object>(8);
            try {
                Object OToken = redisService.queryObjectData(RedisConstans.AccessToken);
                token = (String)OToken;
                if ("".equals(openId) || openId == null || OToken == null) {
                    if ("".equals(weixin_code) || weixin_code == null
                            || weixin_code.equals("authdeny")) {
                        String url_weixin_code = H5Demo.getCodeRequest(url);
                        return new ApiResult(ResultEnum.GetWeixinCode,url_weixin_code);
                    }
                    mapToken = CommonUtils.getAccessTokenAndopenidRequest(weixin_code);
                    openId = mapToken.get("openid").toString();
                    token = mapToken.get("access_token").toString();
                    //unionid = mapToken.get("unionid").toString();
                    redisService.saveObjectData(RedisConstans.AccessToken, token, DateUtil.DURATION_HOUR_S);
                }
                result = CommonUtils.queryUser(openId,token,unionid,request);
                System.out.println("result = " + result);
                if(result.getData() != null) {
                    try {
                        Integer userId1 = Integer.valueOf(result.getData().toString());
                        ApiFrontUser apiFrontUser = new ApiFrontUser();
                        apiFrontUser.setId(userId1);
                        sessionId = CommonUtils.login(apiFrontUser, request, response, userFacade);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("url = " + (url2 + StringUtil.getQueryUrl(queryString)));
                String url_weixin_code = H5Demo.getCodeRequest(url2 + StringUtil.getQueryUrl(queryString));
                return new ApiResult(ResultEnum.GetWeixinCode,url_weixin_code);
            }
        }
        else
        {
            //不是微信浏览器
            return new ApiResult(ResultEnum.NotWeixinBrowser);
        }

        if(result.getCode() == ResultEnum.Error.getCode() || result.getCode() == ResultEnum.OpenIdOrUnionIdEmpty.getCode()){//
            return result;
        }

        WxShareVO shareVO = new WxShareVO();
        if(browser.equals("wx")){
           shareVO = CommonUtils.shareInfo(url);
        }
        System.out.println("sessionId -----------------------" +sessionId);
        UserVO userVO = new UserVO(sessionId, shareVO);
        return new ApiResult(ResultEnum.Success, userVO);
    }

    @RequestMapping(value = "/share", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult share(HttpServletRequest request, @RequestParam("weixinUrl")String weixinUrl){

        String browser = UserUtil.Browser(request);
        WxShareVO shareVO = new WxShareVO();
        if(browser.equals("wx")){
            shareVO = CommonUtils.shareInfo(weixinUrl);
            return new ApiResult(ResultEnum.Success, shareVO);
        }
        return new ApiResult(ResultEnum.NotWeixinBrowser);
    }

}
