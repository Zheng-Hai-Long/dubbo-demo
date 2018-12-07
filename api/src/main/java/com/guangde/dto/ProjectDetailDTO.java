package com.guangde.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZHL on 2018/11/13.
 */
public class ProjectDetailDTO implements Serializable {

    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 求救金额
     */
    private Double cryMoney;

    /**
     * 已募捐金额
     */
    private Double donatAmount;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 最后一次更新时间
     */
    private Date lastUpdateTime;

    /**
     * 捐款笔数
     */
    private Integer donationNum;

    /**
     * 项目截止时间
     */
    private Date deadline;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 项目标题图
     */
    private String coverImageUrl;

    /**
     * 项目标题图 h5
     */
    private String coverImageUrlMiddle;

    /**
     * 已捐百分比
     */
    private String donatePercent ;
    /**
     * 还需金额
     */
    private Double leaveCryMoney ;

    /**
     * 发布时间
     */
    private Date issueTime;

    /**
     * 视频封面中图
     */
    private String videoCoverImageMiddleUrl;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCryMoney() {
        return cryMoney;
    }

    public void setCryMoney(Double cryMoney) {
        this.cryMoney = cryMoney;
    }

    public Double getDonatAmount() {
        return donatAmount;
    }

    public void setDonatAmount(Double donatAmount) {
        this.donatAmount = donatAmount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getDonationNum() {
        return donationNum;
    }

    public void setDonationNum(Integer donationNum) {
        this.donationNum = donationNum;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getCoverImageUrlMiddle() {
        return coverImageUrlMiddle;
    }

    public void setCoverImageUrlMiddle(String coverImageUrlMiddle) {
        this.coverImageUrlMiddle = coverImageUrlMiddle;
    }

    public String getDonatePercent() {
        return donatePercent;
    }

    public void setDonatePercent(String donatePercent) {
        this.donatePercent = donatePercent;
    }

    public Double getLeaveCryMoney() {
        return leaveCryMoney;
    }

    public void setLeaveCryMoney(Double leaveCryMoney) {
        this.leaveCryMoney = leaveCryMoney;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public String getVideoCoverImageMiddleUrl() {
        return videoCoverImageMiddleUrl;
    }

    public void setVideoCoverImageMiddleUrl(String videoCoverImageMiddleUrl) {
        this.videoCoverImageMiddleUrl = videoCoverImageMiddleUrl;
    }
}
