package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class ActionUser implements Serializable{

	private static final long serialVersionUID = -3596855079018420022L;
	
	private Integer id;
	private String username;
	private String password;
	private String department;
	private String realName;
	private Integer sex;
	private String mobile;
	private Integer status;
	private Integer createname;
	private Date createtime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCreatename() {
		return createname;
	}
	public void setCreatename(Integer createname) {
		this.createname = createname;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString() {
		return "ActionUser [id=" + id + ", username=" + username
				+ ", password=" + password + ", department=" + department
				+ ", realName=" + realName + ", sex=" + sex + ", mobile="
				+ mobile + ", status=" + status + ", createname=" + createname
				+ ", createtime=" + createtime + "]";
	}
	
	
	
}
