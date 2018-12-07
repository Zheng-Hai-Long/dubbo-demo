package com.guangde.business.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ActivitySign implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2660221452392430281L;
	
	private Integer id;
	
	private Integer userId;
	
	private Integer activityId;
	
	private Date signTime;
	
	private Date signOutTime;
	
	private Date createTime;
	
	private BigDecimal timeNum;
	
	/**
	 * 姓名
	 */
	private String name;
	
	private String coverImageUrl;

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Date getSignOutTime() {
		return signOutTime;
	}

	public void setSignOutTime(Date signOutTime) {
		this.signOutTime = signOutTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(BigDecimal timeNum) {
		this.timeNum = timeNum;
	}

	@Override
	public String toString() {
		return "ActivitySign [id=" + id + ", userId=" + userId
				+ ", activityId=" + activityId + ", signTime=" + signTime
				+ ", signOutTime=" + signOutTime + ", createTime=" + createTime
				+ ", timeNum=" + timeNum + "]";
	}
	
}
