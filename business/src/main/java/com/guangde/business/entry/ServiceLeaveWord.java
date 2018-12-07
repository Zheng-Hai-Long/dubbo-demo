package com.guangde.business.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceLeaveWord extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6982108516470645901L;
	
	private Integer id;
	/**
	 * 主题id
	 */
	private Integer serviceId;
	/**
	 * 网站用户id
	 */
	private Integer userId;
	/**
	 * 用户昵称
	 */
	private String userNickName;
	/**
	 * 客服id
	 */
	private Integer customerId;
	/**
	 * 客服姓名
	 */
	private String customerName;
	/**
	 * 客服回复内容
	 */
	private String reply;
	/**
	 * 内容图片 id
	 */
	private String contentImageId;
	/**
	 * 联系电话
	 */
	private String mobile;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 扩展id（后期用）
	 */
	private Integer pid;
	/**
	 * 回复状态 300 未回复 302 已回复
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 图片
	 */
	private List<BFile> bfList = new ArrayList<BFile>();
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
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getContentImageId() {
		return contentImageId;
	}
	public void setContentImageId(String contentImageId) {
		this.contentImageId = contentImageId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
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
	public List<BFile> getBfList() {
		return bfList;
	}
	public void setBfList(List<BFile> bfList) {
		this.bfList = bfList;
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
	@Override
	public String toString() {
		return "ServiceLeaveWord [id=" + id + ", serviceId=" + serviceId
				+ ", userId=" + userId + ", userNickName=" + userNickName
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", reply=" + reply + ", contentImageId="
				+ contentImageId + ", mobile=" + mobile + ", name=" + name
				+ ", pid=" + pid + ", state=" + state + ", createTime="
				+ createTime + ", bfList=" + bfList + "]";
	}
	
}
