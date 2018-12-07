package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * xjj  2018/04/17
 */
public class ApiProjectTag implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 标签名称
     */
    private String name;
    /**
     * 是否隐藏
     */
    private Integer isHide;

    /**
     * 创建时间
     */
    private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ApiProjectTagProject [id=" + id + ", name=" + name
				+ ", isHide=" + isHide + ", createTime=" + createTime + "]";
	}

}
