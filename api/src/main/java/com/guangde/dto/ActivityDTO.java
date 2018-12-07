package com.guangde.dto;

import java.io.Serializable;

/**
 * Created by ZHL on 2018/11/21.
 */
public class ActivityDTO implements Serializable{

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
        return "ActivityDTO{" +
                "id=" + id +
                ", activityName='" + activityName + '\'' +
                ", launchName='" + launchName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", state=" + state +
                '}';
    }
}
