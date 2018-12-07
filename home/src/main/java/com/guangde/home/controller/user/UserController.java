package com.guangde.home.controller.user;

import com.guangde.api.user.IUserFacade;
import com.guangde.dto.UserPassWord;
import com.guangde.entry.ApiFrontUser;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.UrlConstants;
import com.guangde.home.utils.*;
import com.guangde.home.utils.storemanage.StoreManage;
import com.guangde.home.vo.user.UserVO;
import com.guangde.pojo.ApiResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@Controller
@RequestMapping("/user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserFacade userFacade;


	//个人设置
	@RequestMapping(value = "/set", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult userSet(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		ApiResult result = CommonUtils.valUserLogin(request);
		if (result.getCode() != 1) {
			return result;
		}
		Integer userId = Integer.valueOf(result.getData().toString());
		return userFacade.queryUserSet(userId);
	}

	/**
	 * 用户信息
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult userInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		ApiResult result = CommonUtils.valUserLogin(request);
		if (result.getCode() != 1) {
			return result;
		}
		Integer userId = Integer.valueOf(result.getData().toString());
		return userFacade.queryUserInfo(userId);
	}

	/**
	 * 登出
	 * @param request
     * @return
     */
	@RequestMapping(value = "/login/out", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult loginOut(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		ApiResult result = CommonUtils.valUserLogin(request);
		if (result.getCode() != 1) {
			return ResultUtil.SUCCESS();
		}
		return CommonUtils.loginOut(request);
	}
	/**
	 * 获取页面图片验证码
	 *
	 * @param type
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "{type}/code", method = RequestMethod.GET)
	public void code(HttpServletRequest request, HttpServletResponse response,
					 @PathVariable("type")String type) {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		try {
			String codeKey = getImgCode(type, request);
			String code = CodeUtil.getRandomString();
			BufferedImage image = CodeUtil.imgCode(codeKey,
					StoreManage.create(StoreManage.STROE_TYPE_SESSION,
							request.getSession()), code);
			// 禁止图像缓存。
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			// 将图像输出到输出流中。
			ServletOutputStream sos = response.getOutputStream();
			ImageIO.write(image, "jpeg", sos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 校验手机号、验证码
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

	private String getImgCode(String type, HttpServletRequest request) {
		return type + "_" + CodeUtil.getUuid(request);
	}

	/**
	 * 获取手机验证码
	 * @param mobileNum
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{mobileNum}/{imageCode}/phoneCode", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult phoneCode(HttpServletResponse response,
									 @PathVariable("mobileNum") String mobileNum,
									 @PathVariable("imageCode") String imageCode,
									 @RequestParam("type")String type,
									 HttpServletRequest request) {
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		logger.info("type = " + type + " imgage code = " + imageCode);
		// 验证业务参数
		Integer typeTemp = CodeUtil.valCodeType(type);
		if(typeTemp == -1){
			return new ApiResult(ResultEnum.UnknownBusinessType);
		}

		// 发送验证码
		if (StringUtil.isMobile(mobileNum)) {
			// 校验图片验证码
			String codekey = getImgCode(type, request);
			logger.info("codeKey="+codekey);
			// 可以替换为其他的存储方式
			StoreManage storeManage = StoreManage.create(
					StoreManage.STROE_TYPE_SESSION, request.getSession());
			int codeR = CodeUtil.VerifiCode(codekey, storeManage, imageCode, false);
			if (codeR == -1) {
				return new ApiResult(ResultEnum.VerificationCodeExpires);
			} else if (codeR == 0) {
				return new ApiResult(ResultEnum.VerificationCodeError);
			}

			String phonekey = type + "_" + mobileNum;
			String result = CodeUtil.phoneCode(phonekey, mobileNum, typeTemp, storeManage);
			if (result == null) {
				return new ApiResult(ResultEnum.Error);
			}
		} else {
			return new ApiResult(ResultEnum.IncorrectMobileFormat);
		}
		return new ApiResult(ResultEnum.Success);
	}


	/**
	 * 手机验证码登录
	 * @param mobile
	 * @param mobileCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/{mobile}/{mobileCode}/login", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult login(@PathVariable("mobile") String mobile, @PathVariable("mobileCode") String mobileCode,
								 @RequestParam("type") String type,
								 HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		//校验手机号格式、验证码
		UserVO userVO = new UserVO(mobile, mobileCode);
		ApiResult mobileResult = CommonUtils.valMobile(userVO, type, request);
		if(mobileResult.getCode() != 1){
			return mobileResult;
		}
		ApiFrontUser user = userFacade.queryByMobile(mobile);
		System.out.println("user = " + user);
		if(user != null){
			String sessionId = CommonUtils.login(user, request, response, userFacade);
			return new ApiResult(ResultEnum.Success, sessionId);
		}
		//注册
		String addressIp = SSOUtil.getUserIP(request);
		user = new ApiFrontUser();
		user= CommonUtils.getRegisterResource(user, request);
		user.setRegisterIP(addressIp);
		user.setLastLoginIP(addressIp);
		user.setUserName("用户"+ StringUtil.uniqueCode());
		user.setMobileNum(mobile);

		ApiResult result = userFacade.registered(user);
		if(result.getCode() == 1){
			user = userFacade.queryByMobile(mobile);
			String sessionId = CommonUtils.login(user, request, response, userFacade);
			return new ApiResult(ResultEnum.Success, sessionId);
		}
		return new ApiResult(ResultEnum.RegisterError);
	}

	/**
	 * 账号密码登录
	 * @param request
	 * @param response
	 * @param userPassWord
	 * @return
	 */
	@RequestMapping(value = "/password/login", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult passWordLogin(HttpServletRequest request, HttpServletResponse response,
								   UserPassWord userPassWord){
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		//TODO 账号密码登录是否考虑一段时间后，验证码登录

		if(StringUtils.isBlank(userPassWord.getPassword()) || StringUtils.isBlank(userPassWord.getUserName())
				|| StringUtils.isBlank(userPassWord.getType()) || StringUtils.isBlank(userPassWord.getCode())){
			return new ApiResult(ResultEnum.ParameterError);
		}

		// 校验图片验证码
		String codekey = getImgCode(userPassWord.getType(), request);
		// 可以替换为其他的存储方式
		StoreManage storeManage = StoreManage.create(
				StoreManage.STROE_TYPE_SESSION, request.getSession());
		int codeR = CodeUtil.VerifiCode(codekey, storeManage, userPassWord.getCode(), false);
		if (codeR == -1) {
			return new ApiResult(ResultEnum.VerificationCodeExpires);
		} else if (codeR == 0) {
			return new ApiResult(ResultEnum.VerificationCodeError);
		}

		ApiFrontUser user = new ApiFrontUser();
		user.setUserName(userPassWord.getUserName());
		user = userFacade.queryOneUserByParam(user);
		if(user == null && StringUtil.isMobile(userPassWord.getUserName())){
			user = new ApiFrontUser();
			user.setMobileNum(userPassWord.getUserName());
			user = userFacade.queryOneUserByParam(user);
		}

		if(user != null){
			String pass = UserUtil.md5PassWord(user.getUserName(), userPassWord.getPassword());
			if(pass.equals(user.getUserPass())){
				String sessionId = CommonUtils.login(user, request, response, userFacade);
				return new ApiResult(ResultEnum.Success, sessionId);
			}
		}

		if(user == null){
			return new ApiResult(ResultEnum.NotExistUser);
		}

		return new ApiResult(ResultEnum.PassWordError);
	}


	/**
	 * 注册
	 * @param userPassWord
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult register(UserPassWord userPassWord,
							  HttpServletRequest request, HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		userPassWord.setType("register");
		//校验手机号是否注册过
		ApiFrontUser user = userFacade.queryByMobile(userPassWord.getMobileNum());
		if(user != null){
			return new ApiResult(ResultEnum.MobileRegistered);
		}

		//校验手机号格式、验证码
		UserVO userVO = new UserVO(userPassWord.getMobileNum(), userPassWord.getPhoneCode());
		ApiResult mobileResult = CommonUtils.valMobile(userVO, userPassWord.getType(), request);
		if(mobileResult.getCode() != 1){
			return mobileResult;
		}

		//注册
		String addressIp = SSOUtil.getUserIP(request);
		user = new ApiFrontUser();
		user= CommonUtils.getRegisterResource(user, request);
		user.setRegisterIP(addressIp);
		user.setLastLoginIP(addressIp);
		user.setUserName(userPassWord.getMobileNum());
		user.setMobileNum(userPassWord.getMobileNum());
		user.setNickName(StringUtil.filterEmoji(userPassWord.getNickName()));
		user.setUserPass(userPassWord.getPassword());

		ApiResult result = userFacade.registered(user);
		if(result.getCode() == 1){
			user = userFacade.queryByMobile(userPassWord.getMobileNum());
			String sessionId = CommonUtils.login(user, request, response, userFacade);
			return new ApiResult(ResultEnum.Success, sessionId);
		}
		return new ApiResult(ResultEnum.RegisterError);
	}
	
}
