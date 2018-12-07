package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;

public class ApiProjectMoneyConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4636458486807211993L;
	private Integer id;
	/**
	 * 项目id
	 */
	private Integer projectId;
	/**
	 * 排序
	 */
	private Integer priority;
	/**
	 * 金额
	 */
	private Double money;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date createtime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@Override
	public String toString(){
		return "ProjectMoneyConfig [id=" + id + ", projectId=" + projectId
				+ ", priority=" + priority + ", money=" + money
				+ ", content=" + content + ", createtime=" + createtime + "]";
	}
}
