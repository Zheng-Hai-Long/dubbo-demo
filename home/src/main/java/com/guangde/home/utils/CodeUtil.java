package com.guangde.home.utils;

import com.guangde.api.commons.ICommonFacade;
import com.guangde.entry.ApiAnnounce;
import com.guangde.home.constant.AnnounceConstans;
import com.guangde.home.utils.storemanage.StoreManage;
import com.guangde.pojo.ApiResult;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

//验证统一处理类
public class CodeUtil {
    
	//页面验证码业务代码
	//public static final String certificationprex="certification";//个人认证
	//public static final String enterprise_validate="enterprise_validate";

	//设置随机数
	public static Random random = new Random();
	//设置字符
	public static final char[] chars="1234567890QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();

	//获取4位随机数
	public static String getRandomString(){
		StringBuffer buffer = new StringBuffer();
		int index;
		//获取随机chars下标
		for(int i=0;i<4;i++){ index = random.nextInt(chars.length);
			//获取随机chars下标
			buffer.append(chars[index]);
		}
		return buffer.toString();
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

	/**
	 * 验证验证码类型
	 * @param type
	 * @return
	 */
	public static Integer valCodeType(String type){
		if(StringUtils.isBlank(type)){
			return -1;
		}else if(AnnounceConstans.TYPE_LOGIN.equals(type)){//登录
			return AnnounceConstans.TYPE_LOGIN_102;
		}else if(AnnounceConstans.TYPE_REGISTER.equals(type)){//注册
			return AnnounceConstans.TYPE_REGISTER_100;
		}else if(AnnounceConstans.TYPE_RESET.equals(type)){//重置密码
			return AnnounceConstans.TYPE_EDIT_104;
		}else{
			return -1;
		}
	}


	/**
	 * 生成图片验证码
	 * @param key
	 * @param storeManage
	 * @return
	 * @throws Exception
     */
    public static BufferedImage imgCode(String key,StoreManage storeManage, String code) throws Exception{
    	/*String code = StringUtil.randonNums(4);
    	BufferedImage img = ImageUtil.codeImg(code);*/
		storeManage.put(key, code);
    	return codeImg(code);
    }

	//获取随机颜色
	public static Color getRandomColor(){
		return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)); }
	//返回某颜色的反色
	public static Color getReverseColor(Color c){
		//防止反色和原色相近
		if(c.getRed()<130&&c.getRed()>125 && c.getBlue()<130&& c.getBlue()>125 && c.getGreen()<130&&c.getGreen()>125){
			return new Color(255,255,255);
		}else{
			return new Color(255-c.getRed(),255-c.getGreen(),255-c.getBlue());
		}
	}
	public static BufferedImage codeImg(String code) throws ServletException, IOException {
		int width = 100;
		//图片宽度
		int height = 30;
		//图片高度
		Color color = getRandomColor();
		//随机色，用于背景色
		Color reverse = getReverseColor(color);
		//反色，用于前景色 * * 生成带字符串的文本图片
		// 1.创建图片缓存区 传参为宽高和图片类型
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//2.获取画笔并绘画
		Graphics g = bi.getGraphics();
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
		//设置字体
		g.setColor(color);
		//设置画笔颜色
		g.fillRect(0, 0, width, height);
		//绘制背景
		g.setColor(reverse);
		//设置画笔颜色
		g.drawString(code, 18, 20);
		//绘制字符  设置最多100个噪音点
		for(int i=0,n = random.nextInt(100);i<n;i++){
			g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
		}

		// 图象生效
		g.dispose();

		return bi;
	}

    /**
     * 
     * @param key
     * @param storeManage
     * @param code
     * @param delete
     * @return -1 校验码不存在或过期 ; 1 校验成功 ; 0 校验失败
     */
    public static int VerifiCode(String key,StoreManage storeManage,String code,boolean delete){
    	String tcode = (String)storeManage.get(key);
    	if(tcode==null){
    		return -1;
    	}
    	if(tcode.toLowerCase().equals(code.toLowerCase())){
    		if(delete){
    		  storeManage.delete(key);
    		}
    		return 1;
    	}else{
    		return 0;
    	}
    }
    /**
     * 删除验证码
     * @param key
     * @param storeManage
     */
    public static void deleteCode(String key,StoreManage storeManage){
    	storeManage.delete(key);
    }
    //token验证吗
    public static String tokenCode(String key,StoreManage storeManage){
    	String code = StringUtil.randonNums(12);
    	storeManage.put(key, code);
    	return code;
    }

	//手机验证码
	public static String phoneCode(String key, String mobile, Integer type, StoreManage storeManage){
		String code = StringUtil.randonNums(6);
		ICommonFacade commonFacade = SpringContextUtil.getBean("commonFacade", ICommonFacade.class);
		ApiAnnounce apiAnnounce = new ApiAnnounce();
		String str = CodeTypeStr(type);
		if(StringUtils.isNotBlank(str)){
			apiAnnounce.setContent("验证码为"+code+" ,您正在通过手机验证码"+ str +"，请不要把验证码泄露给任何人。");
		}else{
			apiAnnounce.setContent("验证码为"+code+"，请不要把验证码泄露给任何人。");
		}

		apiAnnounce.setCause(AnnounceConstans.CAUSE_1+"");
		apiAnnounce.setDestination(mobile);
		apiAnnounce.setType(type);
		apiAnnounce.setPriority(1);
		ApiResult result = commonFacade.sendSms(apiAnnounce, true);
		if(result.getCode()==1){
			storeManage.put(key, code);
			return code;
		}else{
			return null;
		}
	}

	/**
	 * 验证验证码类型中文
	 * @param type
	 * @return
	 */
	public static String CodeTypeStr(Integer type){
		if(AnnounceConstans.TYPE_BIND_101.equals(type)){//绑定手机号
			return "绑定手机号";
		}else if(AnnounceConstans.TYPE_EDIT_103.equals(type)){//修改手机号
			return "修改手机号";
		}else if(AnnounceConstans.TYPE_LOGIN_102.equals(type)){//登录
			return "登录";
		}else if(AnnounceConstans.TYPE_REGISTER_100.equals(type)){//注册
			return "注册";
		}else if(AnnounceConstans.TYPE_EDIT_104.equals(type)){//重置密码
			return "重置密码";
		}else if(AnnounceConstans.TYPE_RESET_TRANSACT_105.equals(type)){//重置交易密码
			return "重置交易密码";
		}else if(AnnounceConstans.TYPE_EDIT_TRANSACT_106.equals(type)){//修改交易密码
			return "修改交易密码";
		}else if(AnnounceConstans.TYPE_SET_TRANSACT_107.equals(type)){//设置交易密码
			return "设置交易密码";
		}else if(AnnounceConstans.TYPE_AUDIT_108.equals(type)){//认证
			return "认证账号";
		}else if(AnnounceConstans.TYPE_AUDIT_112.equals(type)){//修改或绑定银行卡
			return "绑定银行卡";
		}
		return "";
	}
}
