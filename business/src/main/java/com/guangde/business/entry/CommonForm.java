package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CommonForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2965825885661583323L;
	private Integer id;

	private String formName;

	private Date createTime;

	private List<CommonFormConfig> commonFormConfigList;

	public List<CommonFormConfig> getCommonFormConfigList() {
		return commonFormConfigList;
	}

	public void setCommonFormConfigList(List<CommonFormConfig> commonFormConfigList) {
		this.commonFormConfigList = commonFormConfigList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CommonForm{" +
				"id=" + id +
				", formName='" + formName + '\'' +
				", createTime=" + createTime +
				", commonFormConfigList=" + commonFormConfigList +
				'}';
	}
}
