package com.guangde.business.entry;

import java.io.Serializable;

/**
 * 数据字典
 * @author phx
 *
 */
public class Datadictionary implements Serializable{

	private static final long serialVersionUID = 5163161838524051392L;

	private Integer id;
	
	/**
	 * 类别名称
	 */
	private String typeName;
	
	/**
	 * 类别编号
	 */
	private String typeCode;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 编号
	 */
	private String code;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 描述
	 */
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Datadictionary [id=" + id + ", typeName=" + typeName
				+ ", typeCode=" + typeCode + ", name=" + name + ", code="
				+ code + ", state=" + state + ", description=" + description
				+ "]";
	}
	
	
}
