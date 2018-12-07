package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class CommonFormUser implements Serializable{

	private static final long serialVersionUID = -3277958400790271563L;

	private Integer id;
	/**
	 * 通用表单id
	 */
	private Integer formId;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 选项id
	 */
	private Integer optionId;
	/**
	 * 选项名称
	 */
	private String optionName;
	/**
	 * 选项值
	 */
	private String optionValue;
	private Date createTime;
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "CommonFormUser{" +
				"id=" + id +
				", formId=" + formId +
				", userId=" + userId +
				", nickName='" + nickName + '\'' +
				", optionId=" + optionId +
				", optionName='" + optionName + '\'' +
				", optionValue='" + optionValue + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
