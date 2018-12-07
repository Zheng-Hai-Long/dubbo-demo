package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class ActivityConfig implements Serializable{
	private Integer id;

	private Integer logoId;

	private String activityName;

	private String content;

	private Date createTime;

	private Integer formId;

	private String launchName;


	/**
	 * 地点
	 */
	private String address;

	/**
	 * 限制人数
	 */
	private Integer limitNum;

	/**
	 * 报名人数
	 */
	private Integer enterNum;

	/**
	 * 截止时间
	 */
	private Date endTime;

	/**
	 * 开始时间
	 */
	private Date startTime;

	private Date updateTime;

	private String logoUrl;

	private String logoMiddleUrl;

	private String logoLittleUrl;

	/**
	 * 活动状态
	 * 100：活动未开始
	 * 200：活动正常
	 * 300：活动已结束
	 * 400：报名人数已满
	 */
	private Integer state;
	private Integer isForm;

	private Integer isHide;

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public Integer getIsForm() {
		return isForm;
	}

	public void setIsForm(Integer isForm) {
		this.isForm = isForm;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getLogoMiddleUrl() {
		return logoMiddleUrl;
	}

	public void setLogoMiddleUrl(String logoMiddleUrl) {
		this.logoMiddleUrl = logoMiddleUrl;
	}

	public String getLogoLittleUrl() {
		return logoLittleUrl;
	}

	public void setLogoLittleUrl(String logoLittleUrl) {
		this.logoLittleUrl = logoLittleUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLogoId() {
		return logoId;
	}

	public void setLogoId(Integer logoId) {
		this.logoId = logoId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getLaunchName() {
		return launchName;
	}

	public void setLaunchName(String launchName) {
		this.launchName = launchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "ApiActivityConfig{" +
				"id=" + id +
				", logoId=" + logoId +
				", activityName='" + activityName + '\'' +
				", content='" + content + '\'' +
				", createTime=" + createTime +
				", formId=" + formId +
				", launchName='" + launchName + '\'' +
				", address='" + address + '\'' +
				", limitNum=" + limitNum +
				", enterNum=" + enterNum +
				", endTime=" + endTime +
				", startTime=" + startTime +
				", updateTime=" + updateTime +
				'}';
	}
}
