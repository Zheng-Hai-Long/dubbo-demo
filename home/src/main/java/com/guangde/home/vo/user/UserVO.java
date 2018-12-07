package com.guangde.home.vo.user;

/**
 * Created by Administrator on 2018/8/27.
 */
public class UserVO {

    /**
     * 手机验证码
     */
    private String phoneCode;

    /**
     * 手机号
     */
    private String mobileNum;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 分享参数
     */
    private WxShareVO wxShareVO;

    private String token;

    public UserVO(){

    }

    public UserVO(String mobileNum, String phoneCode) {
        this.mobileNum = mobileNum;
        this.phoneCode = phoneCode;
    }

    public UserVO(String token, WxShareVO wxShareVO) {
        this.token = token;
        this.wxShareVO = wxShareVO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public WxShareVO getWxShareVO() {
        return wxShareVO;
    }

    public void setWxShareVO(WxShareVO wxShareVO) {
        this.wxShareVO = wxShareVO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
