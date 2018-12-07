package com.guangde.business.dto;

import java.io.Serializable;
import java.util.Date;

import com.guangde.business.entry.BaseBean;

/**
 * 善员工动态
 * @author Administrator
 *
 */
public class EmployeeDto extends BaseBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 账号名
     */
    private String userName;
    
    /**
     * 项目名
     */
    private String title;
    
    /**
     * 项目
     * 已募捐金额
     */
    private Double donatAmount;
    
    /**
     * 项目求救金额
     */
    private Double cryMoney;
    
    /**
     * 用户最新募捐时间
     */
    private Date donatTime;
    
    /**
     * 
     * 项目id
     */
    private Integer projectId;
    
    /**
     * 项目领域
     */
    private String field;
    
    public String getUserName()
    {
        return userName;
    }
    
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getField()
    {
        return field;
    }
    
    public void setField(String field)
    {
        this.field = field;
    }
    
    public Integer getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public Double getDonatAmount()
    {
        return donatAmount;
    }
    
    public void setDonatAmount(Double donatAmount)
    {
        this.donatAmount = donatAmount;
    }
    
    public Double getCryMoney()
    {
        return cryMoney;
    }
    
    public void setCryMoney(Double cryMoney)
    {
        this.cryMoney = cryMoney;
    }
    
    public Date getDonatTime()
    {
        return donatTime;
    }
    
    public void setDonatTime(Date donatTime)
    {
        this.donatTime = donatTime;
    }
    
}
