package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * 留言表
 */
public class LeaveWord implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private Integer projectFeedback_id;
    
    private Integer userId;
    
    private Integer pid;
    
    private String content;
    
    private Date createTime;
    
    private String leaveWord;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getLeaveWord() {
		return leaveWord;
	}

	public void setLeaveWord(String leaveWord) {
		this.leaveWord = leaveWord;
	}

	public Integer getProjectFeedback_id()
    {
        return projectFeedback_id;
    }
    
    public void setProjectFeedback_id(Integer projectFeedbackId)
    {
        projectFeedback_id = projectFeedbackId;
    }
    
    public Integer getUserId()
    {
        return userId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    public Integer getPid()
    {
        return pid;
    }
    
    public void setPid(Integer pid)
    {
        this.pid = pid;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
}
