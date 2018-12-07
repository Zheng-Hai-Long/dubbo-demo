package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class SystemNotify extends BaseBean implements Serializable
{
    
    /**
    * 注释内容
    */
    
    private static final long serialVersionUID = 5901072212941978911L;
    
    private Integer id;
    
    private Integer userId;
    
    private String sender;
    
    private String subject;
    
    private String content;
    
    private Integer state;
    
    private Integer isShow;
    
    private Date createTime;
    
    private String typeModelId;
    
    private Integer projectId;
    
    public String getSubject()
    {
        return subject;
    }
    
    public void setSubject(String subject)
    {
        this.subject = subject;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
    public Integer getIsShow()
    {
        return isShow;
    }
    
    public void setIsShow(Integer isShow)
    {
        this.isShow = isShow;
    }
    
    public String getSender()
    {
        return sender;
    }
    
    public void setSender(String sender)
    {
        this.sender = sender;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Integer getUserId()
    {
        return userId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

	public String getTypeModelId() {
		return typeModelId;
	}

	public void setTypeModelId(String typeModelId) {
		this.typeModelId = typeModelId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
    
}
