package com.guangde.home.vo.user;

/**
 * Created by zhl on 2018/6/26.
 */
public class ExportUser {

    private Integer userId;

    private String nickName;

    private String userName;

    private String phone;

    private String headImgUrl;

    private String password;

    private Integer actionUserId;

    public ExportUser(){

    }
    public ExportUser(Integer userId, String nickName, String userName, String phone, String headImgUrl){
        this.userId = userId;
        this.nickName = nickName;
        this.userName = userName;
        this.phone = phone;
        this.headImgUrl = headImgUrl;
    }

    public Integer getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(Integer actionUserId) {
        this.actionUserId = actionUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ExportUser{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                '}';
    }
}
