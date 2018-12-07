package com.guangde.home.utils;

import com.guangde.entry.ApiFrontUser;
import com.tenpay.utils.MD5Util;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class UserUtil {

	 /**
     * 检查用户登录名
     *
     * @param userName
     * @return null :正确,其他错误信息
     */
    public static String verifyUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return "请输入用户名";
        }
        int len;
		try {
			len = userName.getBytes("gbk").length;
		} catch (UnsupportedEncodingException e) {
			 return "验证用户名失败";
		}
        if (len < 4 || len > 36) {
            return "用户名不能超过18个字符";
        }
        String reg = "^([a-z|A-Z]+|[ \\u4e00-\\u9fa5]+|[0-9]+)+$";
        if (!userName.matches(reg)) {
            return "用户名只能由汉字、数字、字母组成";
        }
        return null;
    }
    
    /**
     * 检查密码
     *
     * @param password
     * @return null :正确,其他错误信息
     */
    public static String verifyPassword(String password) {
        if (StringUtils.isBlank(password)) {
            return "请输入登录密码";
        }
        int lent;
		try {
			lent = password.getBytes("gbk").length;
		} catch (UnsupportedEncodingException e) {
			 return "验证密码失败";
		}
        if (lent < 8 || lent > 24) {
            return "登录密码必须在8-24个字符之间";
        }
        String reg = "^([^\\u4e00-\\u9fa5]+)+$";
        if (!password.matches(reg)) {
            return "密码只能由数字‘字母组成";
        }
        return null;
    }
    
    public static Integer getUserId(HttpServletRequest request, HttpServletResponse response){
    	String userId = SSOUtil.verifyAuth(request, response);
    	if(userId == null){
    		return null;
    	}
    	boolean isNum = userId.matches("[0-9]+"); 
    	if(!isNum){
    		return null;
    	}
    	return Integer.valueOf(userId);
    }


    /**
     * 获取推广人id
     * @param request
     * @param response
     * @return
     */
    /*public static String getExtensionPeople(HttpServletRequest request,HttpServletResponse response){
    
    	String extensionPeople = SSOUtil.extensionPeople(request,response);
    	
    	return extensionPeople;
    }*/
    
    public static String getUserHead(HttpServletRequest request){
    	String url = CookieManager.retrieve(CookieManager.USER_HEAD, request, false);
    	if(StringUtils.isBlank(url))
    		return null;
    	try {
			url = URLDecoder.decode(url, "utf-8");
			if("good".equals(url))
				return null;
			return url;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    public static String Browser (HttpServletRequest request)
	{
    	//判断是浏览器访问  
    	String agent = request.getHeader("User-Agent");
    	//微信浏览器
    	if(agent.toLowerCase().matches("(.)*(micromessenger)(.)*")){
    		return "wx";
    	}else {
    		//未知
    		return "wz";
		}
	}

	/**
	 * 用户密码加密
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public static String md5PassWord(String userName, String passWord){
		return MD5Util.MD5Encode(userName + passWord, "UTF-8");
	}

}
