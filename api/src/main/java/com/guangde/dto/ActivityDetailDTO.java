package com.guangde.dto;

import java.io.Serializable;

/**
 * Created by ZHL on 2018/11/21.
 */
public class ActivityDetailDTO implements Serializable{

    private Integer id;

    private String activityName;

    private String launchName;

    private String logoUrl;

    /**
     * 活动状态
     * 100：活动未开始
     * 200：活动正常
     * 300：活动已结束
     * 400：报名人数已满
     */
    private Integer state;

    private String endTime;

    private String address;

    private String content;

    private Integer limitNum;

    private Integer enterNum;

    private Integer isForm;

    public Integer getIsForm() {
        return isForm;
    }

    public void setIsForm(Integer isForm) {
        this.isForm = isForm;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public Integer getEnterNum() {
        return enterNum;
    }

    public void setEnterNum(Integer enterNum) {
        this.enterNum = enterNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getLaunchName() {
        return launchName;
    }

    public void setLaunchName(String launchName) {
        this.launchName = launchName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ActivityDetailDTO{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", launchName='" + launchName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", state=" + state +
                ", endTime='" + endTime + '\'' +
                ", address='" + address + '\'' +
                ", content='" + content + '\'' +
                ", limitNum=" + limitNum +
                ", enterNum=" + enterNum +
                '}';
    }
}
