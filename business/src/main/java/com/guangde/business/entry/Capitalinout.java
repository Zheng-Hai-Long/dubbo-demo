package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * 奖金进出记录表
 * 
 * @author phx
 * 
 */

public class Capitalinout extends BaseBean implements Serializable
{
    
    private static final long serialVersionUID = -7151166672575850356L;

    private Integer id;

    private Integer type;

    private Double money;

    private String tranNum;

    private String payNum;

    private String source;

    private String payType;

    private Integer payState;

    private Integer userId;

    private Date createTime;

    private Date updateTime;

    /**
     * 资金进类型  0 ： 募捐  1： 充值
     */
    private Integer inType;

    /**
     * 余额
     */
    private Double balance;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Double getBalance()
    {
        return balance;
    }

    public void setBalance(Double balance)
    {
        this.balance = balance;
    }

    public Integer getInType()
    {
        return inType;
    }

    public void setInType(Integer inType)
    {
        this.inType = inType;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Double getMoney()
    {
        return money;
    }

    public void setMoney(Double money)
    {
        this.money = money;
    }

    public String getTranNum()
    {
        return tranNum;
    }

    public void setTranNum(String tranNum)
    {
        this.tranNum = tranNum;
    }

    public String getPayNum()
    {
        return payNum;
    }

    public void setPayNum(String payNum)
    {
        this.payNum = payNum;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getPayType()
    {
        return payType;
    }

    public void setPayType(String payType)
    {
        this.payType = payType;
    }


    public Integer getPayState()
    {
        return payState;
    }

    public void setPayState(Integer payState)
    {
        this.payState = payState;
    }


    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ApiCapitalinout{" +
                "id=" + id +
                ", type=" + type +
                ", money=" + money +
                ", tranNum='" + tranNum + '\'' +
                ", payNum='" + payNum + '\'' +
                ", source='" + source + '\'' +
                ", payType='" + payType + '\'' +
                ", payState=" + payState +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", inType=" + inType +
                ", balance=" + balance +
                '}';
    }
    
}
