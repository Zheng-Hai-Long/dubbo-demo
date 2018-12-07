package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class ProjectFeedbackPraise extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 976089441809611393L;
	
	private Integer id;
	
	private Integer userId;
	
	private Integer feedbackId;
	
	private Integer teamId;
	
	private Integer state;
	
	private Date createTime;

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

	public Integer getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ApiProjectFeedbackPraise [id=" + id + ", userId=" + userId
				+ ", feedbackId=" + feedbackId + ", teamId=" + teamId
				+ ", state=" + state + ", createTime=" + createTime + "]";
	}
	

}
