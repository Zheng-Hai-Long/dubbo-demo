package com.guangde.home.utils;

import com.alibaba.fastjson.JSONObject;
import com.guangde.api.commons.ICommonFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.entry.ApiFrontUser;
import com.guangde.entry.ApiPayConfig;
import com.guangde.entry.ApiThirdUser;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.RedisConstans;
import com.guangde.home.constant.UserConstans;
import com.guangde.home.utils.storemanage.StoreManage;
import com.guangde.home.vo.user.UserVO;
import com.guangde.home.vo.user.WxShareVO;
import com.guangde.pojo.ApiResult;
import com.redis.utils.RedisService;
import com.tenpay.demo.H5Demo;
import com.tenpay.utils.TenpayUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * 本类会调用接口的方法，变成静态的方法
 * 
 * @author guoqw
 * 
 */
public class CommonUtils {
	
	Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	
	public static ICommonFacade commonFacade = SpringContextUtil.getBean("commonFacade",ICommonFacade.class);
	public static IUserFacade userFacade = SpringContextUtil.getBean("userFacade",IUserFacade.class);
	public static RedisService redisService = SpringContextUtil.getBean("redisService",RedisService.class);
	/**获取微信的识别信息**/
	public static final String GetAccess_tokenRequest="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+H5Demo.appid+"&secret="+H5Demo.appsecret+"&grant_type=authorization_code";
	/**获取微信的用户信息**/
	public static final String GetAccess_userinfoRequest = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";
	

	
	
	/**
	 * 根据code获取微信的识别信息openid，unionid，access_token
	 * @param code
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getAccessTokenAndopenidRequest(String code) {
		
		Map<String, Object> map = new HashMap<String, Object>(2);
		String result = GetAccess_tokenRequest+"&code="+code;
		try {
			URL urlGet = new URL(result);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("POST"); // POST方式请求
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setUseCaches(false);
			
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = JSONObject.parseObject(message);
			result = demoJson.getString("errcode");
			//code过期进行清除
			if(StringUtils.isNotEmpty(result)){
				redisService.saveObjectData(RedisConstans.AccessToken, null, 0);
			}
			map.put("openid", demoJson.getString("openid"));
			//map.put("unionid", demoJson.getString("unionid"));
			map.put("access_token", demoJson.getString("access_token"));
			redisService.saveObjectData(RedisConstans.AccessToken, demoJson.getString("access_token"),DateUtil.DURATION_HOUR_S);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据code获取微信的识别信息openid，unionid，access_token
	 * @param code
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getAccessTokenAndopenidRequest(String code,ApiPayConfig apiConfig) {

		Map<String, Object> map = new HashMap<String, Object>(2);
		String result = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+apiConfig.getWeixinAppId()+"&secret="+apiConfig.getWeixinAppSecret()+"&grant_type=authorization_code"+"&code="+code;
		try {
			URL urlGet = new URL(result);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("POST"); // POST方式请求
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setUseCaches(false);

			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = JSONObject.parseObject(message);
			result = demoJson.getString("errcode");
			//code过期进行清除
			if(StringUtils.isNotEmpty(result)){
				redisService.saveObjectData(RedisConstans.AccessToken, null, 0);
			}
			map.put("openid", demoJson.getString("openid"));
			map.put("unionid", demoJson.getString("unionid"));
			map.put("access_token", demoJson.getString("access_token"));
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据openid，access_token取到微信上的用户信息
	 * @param openid
	 * @param access_token
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getWeixinUserInfo(String openid,String access_token) {

		Map<String, Object> map = new HashMap<String, Object>(8);
		String result = null;
		result = GetAccess_userinfoRequest+"&openid="+openid+"&access_token="+access_token;
		try {
			URL urlGet = new URL(result);
			HttpURLConnection http = (HttpURLConnection) urlGet
					.openConnection();
			http.setRequestMethod("POST"); // POST方式请求
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = JSONObject.parseObject(message);
			map.put("openid", openid);
			map.put("province", demoJson.getString("province"));
			map.put("city", demoJson.getString("city"));
			map.put("nickname", demoJson.getString("nickname"));
			map.put("sex", demoJson.getString("sex"));
			map.put("headimgurl", demoJson.getString("headimgurl"));
			is.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
		return map;

	}



	public static ApiResult queryUser(String openId, String Token, String unionid, HttpServletRequest request){

		if(StringUtils.isBlank(openId)){
			return new ApiResult(ResultEnum.OpenIdOrUnionIdEmpty);
		}
		ApiFrontUser user = new ApiFrontUser();
		//判断用户是否存在 存在就登录,不存在就注册 最后返回用户信息
		ApiResult result = userFacade.queryThirdUserByParam(openId);
		if(result.getCode() == 1){//正常
			ApiThirdUser thirdUser = (ApiThirdUser) result.getData();
			if(thirdUser != null && thirdUser.getUserId() != null){
				user = userFacade.queryById(thirdUser.getUserId());
				if(user == null){
					return new ApiResult(ResultEnum.Error);
				}
			}
			return new ApiResult<>(ResultEnum.Success,user.getId());
		}else if(result.getCode() == 3){//需注册 缓存openid unionid 头像 昵称
			Map<String, Object> mapTourist = getWeixinUserInfo(openId, Token);
			String tName = (String) mapTourist.get("nickname");
			String headimgurl = (String) mapTourist.get("headimgurl");
			tName = StringUtil.filterEmoji(tName);

			ApiFrontUser frontUser = new ApiFrontUser();
			ApiThirdUser thirdUser = new ApiThirdUser();

			frontUser.setNickName(tName);
			frontUser.setCoverImageUrl(headimgurl);

			String addressId = SSOUtil.getUserIP(request);
			frontUser= CommonUtils.getRegisterResource(frontUser, request);
			frontUser.setRegisterIP(addressId);
			frontUser.setLastLoginIP(addressId);

			thirdUser.setUnionid(unionid);
			thirdUser.setAccountNum(openId);

			 result =userFacade.thirdUserRegistered(frontUser, thirdUser);
			return result;
		}else{//查询出多个用户或者查询出错
			return new ApiResult(ResultEnum.Error);
		}
	}

	/**
	 * 验证用户是否登录
	 * @param request
	 * @return
	 */
	public static ApiResult valUserLogin(HttpServletRequest request){
		String token = request.getHeader("token");
		System.out.println("token = " +token);
		if(StringUtils.isBlank(token)){
			return new ApiResult(ResultEnum.NotLogin);
		}
		//String ip = SSOUtil.getUserIP(request);
		Integer userId = null;
		try {
			Claims claims = JwtHelper.parseJWT(token);
			userId = Integer.valueOf(claims.getId());
			System.out.println("userId = " + userId);
		}catch (Exception e){
			return new ApiResult(ResultEnum.NotLogin);
		}

		if(userId == null){
			return new ApiResult(ResultEnum.NotLogin);
		}

		String realSessionId = token;//+ "_" + ip;
		//System.out.println("realSessionId = " + realSessionId);
		Object obj = redisService.queryObjectData(userId + JwtHelper.USER_ID_STR_PRE);
		//System.out.println("obj = " + obj);

		if(obj == null || "".equals(obj) || !realSessionId.equals(obj)){
			return new ApiResult(ResultEnum.NotLogin);
		}
		redisService.saveObjectData(userId + JwtHelper.USER_ID_STR_PRE, realSessionId, CookieManager.DAY);
		return new ApiResult(ResultEnum.Success, userId);
	}

	public static ApiResult loginOut(HttpServletRequest request){
		String token = request.getHeader("token");
		if(StringUtils.isBlank(token)){
			return new ApiResult(ResultEnum.EMPTY_TOKEN);
		}
		Integer userId = null;
		try {
			Claims claims = JwtHelper.parseJWT(token);
			userId = Integer.valueOf(claims.getId());
			//System.out.println("VAL userID " + userId);
		}catch (Exception e){
			e.printStackTrace();
			return new ApiResult(ResultEnum.Success);
		}

		if(userId == null){
			return new ApiResult(ResultEnum.Success);
		}
		redisService.deleteData(userId + JwtHelper.USER_ID_STR_PRE);
		return new ApiResult(ResultEnum.Success, userId);
	}


	/**
	 * 验证手机号是否注册过
	 * @param mobileNum
	 * @return
	 */
	public static ApiResult valMobileIsOrNotRegistered(String mobileNum){
		ApiFrontUser user = userFacade.queryByMobile(mobileNum);
		if(user != null){
			return new ApiResult(ResultEnum.MobileRegistered);
		}
		return new ApiResult(ResultEnum.Success);
	}

	/**
	 * 验证手机号是否绑定过微信
	 * @param mobileNum
	 * @return
	 */
	/*public static ApiCommonResult valMobileIsOrNotBind(String mobileNum){
		ApiUser user = userFacade.queryByMobile(mobileNum);
		if(user != null){
			ApiThirdUser apiThirdUser = new ApiThirdUser();
			apiThirdUser.setUserId(user.getId());
			ApiCommonResult result = userFacade.queryThirdUserByParam(apiThirdUser);
			if(result.getCode() == 1){
				return new ApiCommonResult(ResultEnum.MobileIsBindWx.getCode(), ResultEnum.MobileIsBindWx.getMsg());
			}
		}
		return new ApiCommonResult(ResultEnum.Success.getCode(), ResultEnum.Success.getMsg());
	}*/

	/**
	 * 验证用户名是否注册过
	 * @param userName
	 * @return
	 */
	/*public static ApiResult valUserNameIsOrNotRegistered(String userName){
		ApiUser user = new ApiUser();
		user.setUserName(userName);
		user = userFacade.queryOneUserByParam(user);
		if(user != null){
			return new ApiResult(ResultEnum.UserNameRegistered);
		}
		return new ApiResult(ResultEnum.Success);
	}*/

	/**
	 * 登录
	 * @param user
	 * @param request
	 * @param response
	 * @param userFacade
	 */
	public static String login(ApiFrontUser user,
							 HttpServletRequest request, HttpServletResponse response,
							 IUserFacade userFacade) {
		//保存cookie,session
		try {
			//记录用户最后登录ip 时间
			String ip = SSOUtil.getUserIP(request);
			ApiFrontUser newUser = new ApiFrontUser();
			newUser.setId(user.getId());
			newUser.setLastLoginIP(ip);
			newUser.setLastLoginTime(new Date());
			userFacade.updateFrontUser(newUser);

			Map<String, Object> loginInfo = new HashMap<>();
			loginInfo.put("userId", user.getId());
			String sessionId = JwtHelper.createJWT(user.getId()+"", loginInfo.toString(), JwtHelper.EXPIRED_ONE_DAY);

			String realSessionId = sessionId ;//+ "_" + ip;
			redisService.saveObjectData(user.getId() + JwtHelper.USER_ID_STR_PRE, realSessionId, CookieManager.DAY);
			return sessionId;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取注册来源,并放到ApiUser里
	 * @param user
	 * @param request
	 * @return
	 */
	public static ApiFrontUser getRegisterResource(ApiFrontUser user, HttpServletRequest request){
		String browser = UserUtil.Browser(request);
		if(UserConstans.REGISTR_SOURCE_WX.equals(browser)){
			user.setRegistrSoure(UserConstans.REGISTR_SOURCE_WX);
		}else{
			String ua = (String)request.getSession().getAttribute("ua");
			if(UserConstans.REGISTR_SOURCE_MOBILE.equals(ua)){
				user.setRegistrSoure(UserConstans.REGISTR_SOURCE_H5);
			}else{
				user.setRegistrSoure(UserConstans.REGISTR_SOURCE_PC);
			}
		}
		return user;
	}

	public static WxShareVO shareInfo(String url){
		//微信调度的唯一凭证AccessToken失效，要同时更新jsTicket这个临时凭证
		String jsTicket = (String) redisService.queryObjectData(RedisConstans.JsapiTicket);
		String accessToken = (String) redisService.queryObjectData(RedisConstans.AccessToken);
		if(StringUtils.isBlank(jsTicket) || StringUtils.isBlank(accessToken)){
			accessToken = TenpayUtil.queryAccessToken();
			System.out.println("aWYCXZH_ccessToken = " + accessToken);
			redisService.saveObjectData(RedisConstans.AccessToken , accessToken, DateUtil.DURATION_HOUR_S);
			jsTicket = TenpayUtil.queryJsapiTicket(accessToken);
			System.out.println("WYCXZH_jsTicket = " + jsTicket);
			redisService.saveObjectData(RedisConstans.JsapiTicket , jsTicket, DateUtil.DURATION_HOUR_S);
		}
		SortedMap<String, String> map = null;
		try {
			map = H5Demo.getConfigweixin(jsTicket,url);
			WxShareVO shareVO = new WxShareVO(map.get("appId"), map.get("timeStamp"), map.get("nonceStr"), map.get("signature"));
			return shareVO;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 校验手机号、验证码
	 * @param userVO
	 * @param request
	 * @return
	 */
	public static ApiResult valMobile(UserVO userVO, String type, HttpServletRequest request){
		Integer typeTemp = CodeUtil.valCodeType(type);
		if(typeTemp == -1){
			return new ApiResult(ResultEnum.UnknownBusinessType);
		}

		String phonekey = null;
		int codeR;
		StoreManage storeManage = StoreManage.create(StoreManage.STROE_TYPE_SESSION, request.getSession());
		// 1.校验参数
		String msg = "";
		//校验手机号格式
		if (StringUtil.isMobile(userVO.getMobileNum())) {
			//校验手机验证码
			phonekey = type + "_" + userVO.getMobileNum();
			codeR = CodeUtil.VerifiCode(phonekey, storeManage, userVO.getPhoneCode(), true);
			if(codeR==-1){
				return  new ApiResult(ResultEnum.VerificationCodeExpires);
			}else if(codeR==0){
				return new ApiResult(ResultEnum.VerificationCodeError);
			}
		}else{
			return new ApiResult(ResultEnum.IncorrectMobileFormat);
		}
		return new ApiResult(ResultEnum.Success);
	}

	public static String getUuid(HttpServletRequest request) {
		String uuid = CookieManager.retrieve(CookieManager.COOKIE_UUID_NAME,
				request, true);
		if (uuid == null) {
			return request.getSession().getId();
		} else {
			return uuid;
		}
	}

}
