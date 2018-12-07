package com.guangde.home.vo.user;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/12.
 */
public class CommonRecordVO {

    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private  String mobile;

    /**
     * 签到状态：0:未签到；1:已签到
     */
    private Integer state;

    /**
     * 签到时间
     */
    private String signDate;

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
