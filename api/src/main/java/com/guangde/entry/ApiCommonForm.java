package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ApiCommonForm implements Serializable{

	private static final long serialVersionUID = 569408424302044866L;

	private Integer id;

	private String formName;

	private Date createTime;
	private List<ApiCommonFormConfig> commonFormConfigList;

	public List<ApiCommonFormConfig> getCommonFormConfigList() {
		return commonFormConfigList;
	}

	public void setCommonFormConfigList(List<ApiCommonFormConfig> commonFormConfigList) {
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
		return "ApiCommonForm{" +
				"id=" + id +
				", formName='" + formName + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
