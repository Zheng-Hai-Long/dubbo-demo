package com.guangde.entry;

import java.io.Serializable;

public class ApiAuditProject implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3557574220715214893L;
	private Integer id;
	/**用户id*/
	private Integer userId;
	/**项目id*/
	private Integer projectId;
	/**介绍与受助人的详情情况*/
	private String information;
	/**与受助人的关系*/
	private String relationship;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 身份证
	 */
	private String idCard;
	/**
	 * 手机号码
	 */
	private String MobileNum;
	/**
	 * 头像id
	 */
	private Integer coverImageId;
	
	/**
	 * 头像的url 
	 * 
	 */
	private String  headUrl;
	
	/**
	 * 状态,200：未填写实名验证信息；201：实名验证未审核；202：实名验证审核未通过；203：实名验证审核通过
	 */
	private Integer realState;
	
	public Integer getRealState() {
		return realState;
	}
	public void setRealState(Integer realState) {
		this.realState = realState;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMobileNum() {
		return MobileNum;
	}
	public void setMobileNum(String mobileNum) {
		MobileNum = mobileNum;
	}
	public Integer getCoverImageId() {
		return coverImageId;
	}
	public void setCoverImageId(Integer coverImageId) {
		this.coverImageId = coverImageId;
	}
	public String  getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
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
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
}
