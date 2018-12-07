package com.guangde.business.dto;

import java.io.Serializable;

/**
 * 企业账户中心 
 *      head 信息
 * @author Administrator
 *
 */
public class CompanyDto implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 账户余额
     */
    private Double balanbe;
    
    /**
     * 当前捐助项目次数
     */
    private Integer donateNumber;
    
    /**
     * 当前捐助总金额
     */
    private Double donateTotalAmount;
    
    /**
     * 企业助善次数
     */
    private Integer goodHelpNumber;
    
    /**
     * 助善总金额
     */
    private Double goodHelpTotalAmount;
    
    /**
     * 号召的网友数
     */
    private Integer totalCallNumber;
    
    /**
     * 善员工数
     */
    private Integer employeeNumber;
    
    /**
     * 已帮助的家庭
     */
    private Integer helpFamilyNumber;
    
    public Double getBalanbe()
    {
        return balanbe;
    }
    
    public void setBalanbe(Double balanbe)
    {
        this.balanbe = balanbe;
    }
    
    public Integer getHelpFamilyNumber()
    {
        return helpFamilyNumber;
    }
    
    public void setHelpFamilyNumber(Integer helpFamilyNumber)
    {
        this.helpFamilyNumber = helpFamilyNumber;
    }
    
    public Double getGoodHelpTotalAmount()
    {
        return goodHelpTotalAmount;
    }
    
    public void setGoodHelpTotalAmount(Double goodHelpTotalAmount)
    {
        this.goodHelpTotalAmount = goodHelpTotalAmount;
    }
    
    public Integer getDonateNumber()
    {
        return donateNumber;
    }
    
    public void setDonateNumber(Integer donateNumber)
    {
        this.donateNumber = donateNumber;
    }
    
    public Double getDonateTotalAmount()
    {
        return donateTotalAmount;
    }
    
    public void setDonateTotalAmount(Double donateTotalAmount)
    {
        this.donateTotalAmount = donateTotalAmount;
    }
    
    public Integer getGoodHelpNumber()
    {
        return goodHelpNumber;
    }
    
    public void setGoodHelpNumber(Integer goodHelpNumber)
    {
        this.goodHelpNumber = goodHelpNumber;
    }
    
    public Integer getTotalCallNumber()
    {
        return totalCallNumber;
    }
    
    public void setTotalCallNumber(Integer totalCallNumber)
    {
        this.totalCallNumber = totalCallNumber;
    }
    
    public Integer getEmployeeNumber()
    {
        return employeeNumber;
    }
    
    public void setEmployeeNumber(Integer employeeNumber)
    {
        this.employeeNumber = employeeNumber;
    }
    
    @Override
    public String toString()
    {
        return "CompanyDto [balanbe=" + balanbe + ", donateNumber=" + donateNumber + ", donateTotalAmount=" + donateTotalAmount + ", employeeNumber=" + employeeNumber + ", goodHelpNumber="
            + goodHelpNumber + ", goodHelpTotalAmount=" + goodHelpTotalAmount + ", totalCallNumber=" + totalCallNumber + ",helpFamilyNumber=" + helpFamilyNumber + "]";
    }
    
}
