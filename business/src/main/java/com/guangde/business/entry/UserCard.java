package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户银行卡
 * @author Administrator
 *
 */
public class UserCard implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private Integer userId;
    
    private String bankName;
    
    private String province;
    
    private String area;
    
    private String city;
    
    private String card;
    
    private Integer useState;
    
    private Date bindingTime;
    
    private String bankType;
    
    private Integer isSelected;
    
    /**
     * 账户类型  0 ： 对公  1 ： 对私
     */
    private Integer accountType;
    
    /**
     * 开户名
     */
    private String accountName;
    
    private Integer projectId;
    
    public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Integer getAccountType()
    {
        return accountType;
    }
    
    public void setAccountType(Integer accountType)
    {
        this.accountType = accountType;
    }
    
    public String getAccountName()
    {
        return accountName;
    }
    
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }
    
    public Integer getIsSelected()
    {
        return isSelected;
    }
    
    public void setIsSelected(Integer isSelected)
    {
        this.isSelected = isSelected;
    }
    
    public String getBankType()
    {
        return bankType;
    }
    
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }
    
    public Integer getUserId()
    {
        return userId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    public String getBankName()
    {
        return bankName;
    }
    
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getCard()
    {
        return card;
    }
    
    public void setCard(String card)
    {
        this.card = card;
    }
    
    public Integer getUseState()
    {
        return useState;
    }
    
    public void setUseState(Integer useState)
    {
        this.useState = useState;
    }
    
    public Date getBindingTime()
    {
        return bindingTime;
    }
    
    public void setBindingTime(Date bindingTime)
    {
        this.bindingTime = bindingTime;
    }
    
    public String getArea()
    {
        return area;
    }
    
    public void setArea(String area)
    {
        this.area = area;
    }
    
    @Override
    public String toString()
    {
        return "UserCard [bankName=" + bankName + ", bankType=" + bankType + ", bindingTime=" + bindingTime + ", card=" + card + ", city=" + city + ", id=" + id + ", province=" + province
            + ", useState=" + useState + ", userId=" + userId + "]";
    }
    
}
