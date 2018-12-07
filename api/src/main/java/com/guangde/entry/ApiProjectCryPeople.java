package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目求助者
 * @author Administrator
 *
 */
public class ApiProjectCryPeople implements Serializable
{
    private static final long serialVersionUID = 1L;

	private Integer id;

	private String realName;

	private Integer sex;

	private String address;

	private String mobile;

	private Integer idCardImageId;

	private String helpCause;

	private String remark;

	private Date createTime;

	private String idCardImageUrl;

	private Integer userId;

	private Integer state;

	private Date updateTime;

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIdCardImageId() {
		return idCardImageId;
	}

	public void setIdCardImageId(Integer idCardImageId) {
		this.idCardImageId = idCardImageId;
	}

	public String getHelpCause() {
		return helpCause;
	}

	public void setHelpCause(String helpCause) {
		this.helpCause = helpCause;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIdCardImageUrl() {
		return idCardImageUrl;
	}

	public void setIdCardImageUrl(String idCardImageUrl) {
		this.idCardImageUrl = idCardImageUrl;
	}

	@Override
	public String toString() {
		return "ProjectCryPeople{" +
				"id=" + id +
				", realName='" + realName + '\'' +
				", sex=" + sex +
				", address='" + address + '\'' +
				", mobile='" + mobile + '\'' +
				", idCardImageId=" + idCardImageId +
				", helpCause='" + helpCause + '\'' +
				", remark='" + remark + '\'' +
				", createTime=" + createTime +
				", idCardImageUrl='" + idCardImageUrl + '\'' +
				", userId=" + userId +
				", state=" + state +
				'}';
	}
}
