package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员审核表
 * @author phx
 *
 */

public class AuditStaff implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8208274837512602545L;
	private Integer id;
	private Integer userId;
	private Integer state;
	private String fileId;
	private String reviewContent;
	private String personType;
	private Date reviewTime;
	private Integer reviewPerson;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public Date getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	public Integer getReviewPerson() {
		return reviewPerson;
	}
	public void setReviewPerson(Integer reviewPerson) {
		this.reviewPerson = reviewPerson;
	}
	@Override
	public String toString() {
		return "AuditStaff [id=" + id + ", userId=" + userId + ", state="
				+ state + ", fileId=" + fileId + ", reviewContent="
				+ reviewContent + ", personType=" + personType
				+ ", reviewTime=" + reviewTime + ", reviewPerson="
				+ reviewPerson + "]";
	}
	
	
}
