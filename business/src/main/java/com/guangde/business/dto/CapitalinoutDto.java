package com.guangde.business.dto;

import java.io.Serializable;

/**
 * 资金明细详情
 * @author Administrator
 *
 */
public class CapitalinoutDto implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 项目id
     */
    private Integer projectId;
    
    /**
     * 项目标题
     */
    private String projectTitle;
    
    /**
     * 项目退款原因
     */
    private String stopReason;
    
    /**
     * 支付方式
     */
    private String payType;
    
    /**
     * 支付流水号
     */
    private String payNum;
    
    /**
     * 银行名称
     */
    private String recipientName;
    
    /**
     * 银行卡号
     */
    private String account;
    
    /**
     * 领域
     */
    private String field;
    
    public Integer getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    
    public String getField()
    {
        return field;
    }
    
    public void setField(String field)
    {
        this.field = field;
    }
    
    public String getProjectTitle()
    {
        return projectTitle;
    }
    
    public void setProjectTitle(String projectTitle)
    {
        this.projectTitle = projectTitle;
    }
    
    public String getStopReason()
    {
        return stopReason;
    }
    
    public void setStopReason(String stopReason)
    {
        this.stopReason = stopReason;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public String getPayNum()
    {
        return payNum;
    }
    
    public void setPayNum(String payNum)
    {
        this.payNum = payNum;
    }
    
    public String getRecipientName()
    {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    public String getAccount()
    {
        return account;
    }
    
    public void setAccount(String account)
    {
        this.account = account;
    }
    
}
