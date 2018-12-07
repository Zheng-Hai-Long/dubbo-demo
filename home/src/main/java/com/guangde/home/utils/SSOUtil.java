package com.guangde.home.utils;

import com.guangde.entry.ApiFrontUser;
import com.guangde.home.constant.PengPengConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SSOUtil {

	private static Logger logger = LoggerFactory.getLogger(SSOUtil.class);

	public static void login(ApiFrontUser user, HttpServletRequest request,
							 HttpServletResponse response) {
		// 写入session
		writeLoginSession(user, request);
		// 写入cookie
		writeLoginCookie(user, response);
	}

	public static void loginOut(HttpServletRequest request,
								HttpServletResponse response) {
		request.getSession().invalidate();
		CookieManager.remove(CookieManager.NICK_NAME, response, false);
		CookieManager.remove(CookieManager.USER_OPENID, response, true);
		CookieManager.remove(CookieManager.USER_UNIONID, response, true);
		CookieManager.remove(CookieManager.USER_HEAD, response, false);
		CookieManager.remove(CookieManager.LOGIN_ID, response, true);
		request.getSession().removeAttribute(CookieManager.LOGIN_ID);
	}

	public static void cacheWxUserInfo(HttpServletResponse response, String nickName, String openId, String unionId, String coverImageUrl){
		try {
			CookieManager
					.create(CookieManager.NICK_NAME, nickName,
							CookieManager.EXPIRED_DEFAULT_MINUTE, response, false);
			CookieManager
					.create(CookieManager.USER_OPENID, openId,
							CookieManager.EXPIRED_DEFAULT_MINUTE, response, true);
			CookieManager
					.create(CookieManager.USER_UNIONID, unionId,
							CookieManager.EXPIRED_DEFAULT_MINUTE, response, true);
			CookieManager
					.create(CookieManager.USER_HEAD, coverImageUrl,
							CookieManager.EXPIRED_DEFAULT_MINUTE, response, false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void writeLoginSession(ApiFrontUser user,
										 HttpServletRequest request) {
		request.getSession()
				.setAttribute(CookieManager.LOGIN_ID, user.getId().toString());
	}

	public static void writeLoginCookie(ApiFrontUser user,
										HttpServletResponse response) {
		// 保存登录cookie信息
		try {
			CookieManager
					.create(CookieManager.LOGIN_ID, user.getId().toString(),
							CookieManager.EXPIRED_DEFAULT_MINUTE, response, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeUserHead(HttpServletResponse response,String url){
		try{
			if(!StringUtils.isBlank(url)){
				CookieManager.create(CookieManager.USER_HEAD, URLEncoder.encode(url,"utf-8"),
						CookieManager.EXPIRED_DEFAULT_MINUTE, response, false);
			}else{
				//good 代表没有头像
				CookieManager.create(CookieManager.USER_HEAD, URLEncoder.encode("good","utf-8"),
						CookieManager.EXPIRED_DEFAULT_MINUTE, response, false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 用户id
	 * @param request
	 * @return
	 */
	public static String verifyAuth(HttpServletRequest request, HttpServletResponse responser) {
		String uid = null;
		try {
			uid = CookieManager.retrieve(CookieManager.LOGIN_ID, request, true);
		} catch (Exception e) {
			e.printStackTrace();
			uid = null;
		}
		return uid;
	}

	/**
	 * 用户昵称
	 * @param request
	 * @return
	 */
	public static String getNickName(HttpServletRequest request){
		String nickName = CookieManager.retrieve(CookieManager.NICK_NAME, request, false);
		if(nickName==null){
			return null;
		}else{
			try {
				return URLDecoder.decode(nickName,"utf-8");
			} catch (UnsupportedEncodingException e) {
				return nickName;
			}
		}
	}

	/**
	 * 用户微信头像
	 * @param request
	 * @return
	 */
	public static String getUserHeadImg(HttpServletRequest request){
		String headImg = CookieManager.retrieve(CookieManager.USER_HEAD, request, false);
		if(headImg==null){
			return null;
		}else{
			try {
				return URLDecoder.decode(headImg,"utf-8");
			} catch (UnsupportedEncodingException e) {
				return headImg;
			}
		}
	}

	/**
	 * 获取用户等级
	 * @param request
	 * @return
	 */
	public static String getUserLevel(HttpServletRequest request){
		String grade = CookieManager.retrieve(CookieManager.USER_LEVEL, request, true);
		if(grade==null){
			return null;
		}else{
			return grade;
		}
	}

	/**
	 * 获取用户openId
	 * @param request
	 * @return
	 */
	public static String getUserOpenId(HttpServletRequest request){
		String openId = CookieManager.retrieve(CookieManager.USER_OPENID, request, true);
		if(openId==null){
			return null;
		}else{
			return openId;
		}
	}

	/**
	 * 获取用户unionId
	 * @param request
	 * @return
	 */
	public static String getUserUnionId(HttpServletRequest request){
		String unionId = CookieManager.retrieve(CookieManager.USER_UNIONID, request, true);
		if(unionId==null){
			return null;
		}else{
			return unionId;
		}
	}

	/**
	 * 用户电话
	 * @param request
	 * @return
	 */
	public static String getMobile(HttpServletRequest request){
		String mobile = CookieManager.retrieve(CookieManager.USER_MOBILE, request, true);
		if(mobile==null){
			return null;
		}else{
			return mobile;
		}
	}



	/**
	 * 取得用户的 IP 地址.
	 *
	 * @param request
	 *            WEB 请求
	 * @return
	 */
	public static String getUserIP(HttpServletRequest request) {
		String ipaddress = request.getHeader("X-Real-IP");
		if (ipaddress == null) {
			ipaddress = request.getHeader("X-Forwarded-For");
		}
		if (ipaddress == null) {
			ipaddress = request.getRemoteAddr();
		} else {
			int index = ipaddress.indexOf(',');
			if (index != -1) {
				ipaddress = ipaddress.substring(0, index);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ipaddress = " + ipaddress);
		}

		return ipaddress;
	}

	/**
	 * 取得该请求的路径和参数，参数和路径之间的 ? 用 | 代替，并 URL Encode ，以便这个路径作为一个参数被传递到另一个地址.
	 * <p>
	 * 例如一个请求地址为 >> http://www.cp2y.com/my/viewMember.m5?memberId=1&sex=0
	 * ，转换后 >>>
	 * http%3A%2F%2Fwww.cp2y.com%2Fmy%2FviewMember.m5%7CmemberId%3D1%26sex%3D0
	 * </p>
	 *
	 * @param request Http 请求
	 * @return 字符串
	 * @throws java.io.UnsupportedEncodingException
	 *          字符集异常
	 */
	public static String extractEntrance(HttpServletRequest request) throws UnsupportedEncodingException {
		StringBuffer entrance = new StringBuffer(request.getRequestURL());
		if (request.getQueryString() != null) {
			entrance.append("?");
			entrance.append(new String(request.getQueryString().getBytes("8859_1"), PengPengConstants.ENCODING));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("entrance before encode = " + entrance.toString());
		}
		return URLEncoder.encode(entrance.toString(), "GBK");
	}
}
