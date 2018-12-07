package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class NewLeaveWord extends BaseBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6121280244703606727L;
	
	private Integer id;
	private Integer type;
	private Integer projectId	;
	private Integer projectDonateId;
	private Integer projectFeedbackId;
	private Integer leavewordUserId;
	private Integer replyUserId;
	private String leavewordName;
	private String replyName;
	private String content	;
	private Date updateTime;
	private Date createTime;
	private Integer isRead;
	/**
	 * 201:未审核；202:审核失败；203:审核成功；
	 */
	private Integer state;

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getProjectDonateId() {
		return projectDonateId;
	}
	public void setProjectDonateId(Integer projectDonateId) {
		this.projectDonateId = projectDonateId;
	}
	public Integer getProjectFeedbackId() {
		return projectFeedbackId;
	}
	public void setProjectFeedbackId(Integer projectFeedbackId) {
		this.projectFeedbackId = projectFeedbackId;
	}
	public Integer getLeavewordUserId() {
		return leavewordUserId;
	}
	public void setLeavewordUserId(Integer leavewordUserId) {
		this.leavewordUserId = leavewordUserId;
	}
	public Integer getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(Integer replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getLeavewordName() {
		return leavewordName;
	}
	public void setLeavewordName(String leavewordName) {
		this.leavewordName = leavewordName;
	}
	public String getReplyName() {
		return replyName;
	}
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "NewLeaveWord{" +
				"id=" + id +
				", type=" + type +
				", projectId=" + projectId +
				", projectDonateId=" + projectDonateId +
				", projectFeedbackId=" + projectFeedbackId +
				", leavewordUserId=" + leavewordUserId +
				", replyUserId=" + replyUserId +
				", leavewordName='" + leavewordName + '\'' +
				", replyName='" + replyName + '\'' +
				", content='" + content + '\'' +
				", updateTime=" + updateTime +
				", createTime=" + createTime +
				", isRead=" + isRead +
				", state=" + state +
				'}';
	}
}
