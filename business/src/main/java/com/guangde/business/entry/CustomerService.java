package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class CustomerService extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5350439213089241115L;
	
	private Integer id;
	private Integer userId;
	/**
	 * 回复状态 300 未回复 302 已回复
	 */
	private Integer state;
	private Integer visit;
	/**
	 * 类型：0 咨询 1 建议 2 投诉
	 */
	private Integer type;
	/**
	 * 是否隐藏 0 显示 1 隐藏
	 */
	private Integer isHide;
	/**
	 * 用户咨询、建议、投诉内容
	 */
	private String content;
	/**
	 * 用户方案
	 */
	private String scheme;
	private String name;
	/**
	 * 用户昵称
	 */
	private String userNickName;
	private String mobile;
	/**
	 * 内容的图片id
	 */
	private String contentImageId;
	/**
	 * 备用字段
	 */
	private String spare;
	private Date createTime;
	private Date replyTime;
	private Date lastUpdateTime;
	/**
	 * 头像id
	 */
	private Integer headId;
	/**
	 * 头像地址
	 */
	private String headUrl;
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
	public Integer getVisit() {
		return visit;
	}
	public void setVisit(Integer visit) {
		this.visit = visit;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIsHide() {
		return isHide;
	}
	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getScheme() {
		return scheme;
	}
	public void setScheme(String scheme) {
		this.scheme = scheme;
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
	public String getContentImageId() {
		return contentImageId;
	}
	public void setContentImageId(String contentImageId) {
		this.contentImageId = contentImageId;
	}
	public String getSpare() {
		return spare;
	}
	public void setSpare(String spare) {
		this.spare = spare;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getHeadId() {
		return headId;
	}
	public void setHeadId(Integer headId) {
		this.headId = headId;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	@Override
	public String toString() {
		return "CustomerService [id=" + id + ", userId=" + userId + ", state="
				+ state + ", visit=" + visit + ", type=" + type + ", isHide="
				+ isHide + ", content=" + content + ", scheme=" + scheme
				+ ", name=" + name + ", mobile=" + mobile + ", contentImageId="
				+ contentImageId + ", spare=" + spare + ", createTime="
				+ createTime + ", replyTime=" + replyTime + ", lastUpdateTime="
				+ lastUpdateTime + "]";
	}
	

}
