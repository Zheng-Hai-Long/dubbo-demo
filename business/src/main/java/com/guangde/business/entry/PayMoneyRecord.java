package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

public class PayMoneyRecord extends BaseBean implements Serializable
{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8099344326917910651L;

	private Integer id;
    
    private Integer capitalinoutId;
    
    private Integer projectId;
    
    private Double panMoney;
    
    private Integer state;
    
    private String recipientName;
    
    private String recipientBankType;
    
    private String account;
    
    private Date payMoneyTime;
    
    private Integer operator;
    
    private String payNum;
    
    private String tranNum;
    
    private String payType;
    
    private String source;
    
    private Integer userId;
    
    /**
     * 申请打款时间
     */
    private Date createTime;
    
    /**
     * 收据 fileID
     */
    private String receiptImageId;
    
    /**
     * 收据url
     */
    private String receiptImageIdUrl;
    
    /**
     * 申请金额
     */
    private Double applyMoney;
    
    /**
     * 项目标题
     */
    private String projectTitle;
    /**
     * 项目领域
     */
    private String field;
    
    private Integer panMoneyNum ; 
    
    private Double donatAmount;
    
    private String realName ; 
    /**收款人*/
    private String accountName;
    
    public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Integer getPanMoneyNum() {
		return panMoneyNum;
	}

	public void setPanMoneyNum(Integer panMoneyNum) {
		this.panMoneyNum = panMoneyNum;
	}

	public Double getDonatAmount() {
		return donatAmount;
	}

	public void setDonatAmount(Double donatAmount) {
		this.donatAmount = donatAmount;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getProjectTitle()
    {
        return projectTitle;
    }
    
    public void setProjectTitle(String projectTitle)
    {
        this.projectTitle = projectTitle;
    }
    
    public Integer getUserId()
    {
        return userId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getReceiptImageId() {
		return receiptImageId;
	}

	public void setReceiptImageId(String receiptImageId) {
		this.receiptImageId = receiptImageId;
	}
    
    public String getReceiptImageIdUrl()
    {
        return receiptImageIdUrl;
    }
    
    public void setReceiptImageIdUrl(String receiptImageIdUrl)
    {
        this.receiptImageIdUrl = receiptImageIdUrl;
    }
    
    public Double getApplyMoney()
    {
        return applyMoney;
    }
    
    public void setApplyMoney(Double applyMoney)
    {
        this.applyMoney = applyMoney;
    }
    
    public String getSource()
    {
        return source;
    }
    
    public void setSource(String source)
    {
        this.source = source;
    }
    
    public String getPayNum()
    {
        return payNum;
    }
    
    public void setPayNum(String payNum)
    {
        this.payNum = payNum;
    }
    
    public String getTranNum()
    {
        return tranNum;
    }
    
    public void setTranNum(String tranNum)
    {
        this.tranNum = tranNum;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public Integer getCapitalinoutId()
    {
        return capitalinoutId;
    }
    
    public void setCapitalinoutId(Integer capitalinoutId)
    {
        this.capitalinoutId = capitalinoutId;
    }
    
    public Integer getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    
    public Double getPanMoney()
    {
        return panMoney;
    }
    
    public void setPanMoney(Double panMoney)
    {
        this.panMoney = panMoney;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
    public String getRecipientName()
    {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    public String getRecipientBankType()
    {
        return recipientBankType;
    }
    
    public void setRecipientBankType(String recipientBankType)
    {
        this.recipientBankType = recipientBankType;
    }
    
    public String getAccount()
    {
        return account;
    }
    
    public void setAccount(String account)
    {
        this.account = account;
    }
    
    public Date getPayMoneyTime()
    {
        return payMoneyTime;
    }
    
    public void setPayMoneyTime(Date payMoneyTime)
    {
        this.payMoneyTime = payMoneyTime;
    }
    
    public Integer getOperator()
    {
        return operator;
    }
    
    public void setOperator(Integer operator)
    {
        this.operator = operator;
    }

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return "PayMoneyRecord [account=" + account + ", applyMoney="
				+ applyMoney + ", capitalinoutId=" + capitalinoutId
				+ ", createTime=" + createTime + ", id=" + id + ", operator="
				+ operator + ", panMoney=" + panMoney + ", payMoneyTime="
				+ payMoneyTime + ", payNum=" + payNum + ", payType=" + payType
				+ ", projectId=" + projectId + ", projectTitle=" + projectTitle
				+ ", field="+field+", receiptImageId=" + receiptImageId + ", receiptImageIdUrl="
				+ receiptImageIdUrl + ", recipientBankType="
				+ recipientBankType + ", recipientName=" + recipientName
				+ ", source=" + source + ", state=" + state + ", tranNum="
				+ tranNum + ", userId=" + userId + "]";
	}
    
   
    
}
