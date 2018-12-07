package com.guangde.dto;

import java.io.Serializable;

/**
 * Created by ZHL on 2018/11/29.
 */
public class ApiDepositFormDTO implements Serializable {
    private Integer projectId;

    private Double money;

    private Integer nameOpen;

    private Integer userId;

    private String source;

    private String leaveWord;

    private Integer extensionPeople;

    private String transNum;

    private String realName;

    private String mobile;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public String getLeaveWord() {
        return leaveWord;
    }

    public void setLeaveWord(String leaveWord) {
        this.leaveWord = leaveWord;
    }

    public Integer getExtensionPeople() {
        return extensionPeople;
    }

    public void setExtensionPeople(Integer extensionPeople) {
        this.extensionPeople = extensionPeople;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getNameOpen() {
        return nameOpen;
    }

    public void setNameOpen(Integer nameOpen) {
        this.nameOpen = nameOpen;
    }

    @Override
    public String toString() {
        return "ApiDepositFormDTO{" +
                "projectId=" + projectId +
                ", money=" + money +
                ", nameOpen=" + nameOpen +
                ", userId=" + userId +
                ", source='" + source + '\'' +
                ", leaveWord='" + leaveWord + '\'' +
                ", extensionPeople=" + extensionPeople +
                ", transNum='" + transNum + '\'' +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
