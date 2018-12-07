package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ApiSystemTypeModel extends BaseBean implements Serializable
{
    /**
    * xjj  2018.5.7
    */
    private static final long serialVersionUID = 5324425192052577486L;
    
    private Integer id;
    
    /**
     * 按钮名称
     */
    private String buttonName;
    
    /**
     * 链接
     */
    private String link;
    
    private Date createTime;
    /**
     * id集合
     */
    private List<Integer> ids;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "ApiSystemTypeModel [id=" + id + ", buttonName=" + buttonName
				+ ", link=" + link + ", createTime=" + createTime + "]";
	}
    
}
