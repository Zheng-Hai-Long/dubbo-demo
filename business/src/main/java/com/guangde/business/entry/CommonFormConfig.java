package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class CommonFormConfig extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;

	/**
	 * 选项类型(单行文本框：textBox，多行文本框：textarea，单选框：radio，复选框：checkBox，下拉框：dropdownBox)
	 */
	private String optionType;

	/**
	 * 选项名称
	 */
	private String optionName;

	/**
	 * 是否多选项
	 */
	private Integer isMultiple;

	/**
	 * 多选项的值（用逗号隔开）
	 */
	private String multipleValue;

	/**
	 * 表单id
	 */
	private Integer formId;

	/**
	 * 关联用户信息（mobile:用户手机号；realName:用户真实姓名；idCard:身份证），其他为不关联
	 */
	private String relationUser;

	/**
	 * 是否必填（1是；0否）
	 */
	private Integer isRequire;

	private Date createTime;
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public Integer getIsMultiple() {
		return isMultiple;
	}

	public void setIsMultiple(Integer isMultiple) {
		this.isMultiple = isMultiple;
	}

	public String getMultipleValue() {
		return multipleValue;
	}

	public void setMultipleValue(String multipleValue) {
		this.multipleValue = multipleValue;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public String getRelationUser() {
		return relationUser;
	}

	public void setRelationUser(String relationUser) {
		this.relationUser = relationUser;
	}

	public Integer getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(Integer isRequire) {
		this.isRequire = isRequire;
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
		return "ApiCommonFormConfig{" +
				"id=" + id +
				", optionType=" + optionType +
				", optionName=" + optionName +
				", isMultiple=" + isMultiple +
				", multipleValue=" + multipleValue +
				", formId=" + formId +
				", relationUser=" + relationUser +
				", isRequire=" + isRequire +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
