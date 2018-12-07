package com.guangde.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/3.
 */
public class UserPassWord implements Serializable {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 手机号
     */
    private String mobileNum;

    /**
     * 验证码
     */
    private String phoneCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String surePassword;

    /**
     * 验证码类型
     */
    private String type;

    /**
     * 原密码
     */
    private String oldPassWord;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 图片验证码
     */
    private String code;

    /**
     * 昵称
     */
    private String nickName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurePassword() {
        return surePassword;
    }

    public void setSurePassword(String surePassword) {
        this.surePassword = surePassword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
