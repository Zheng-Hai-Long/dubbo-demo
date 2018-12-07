package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * 前端用户表
 * 
 * @author zhl
 * 
 */

public class FrontUser extends BaseBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -7848163355551519111L;

  private Integer id;

  /**
   * 捐赠次数
   */
  private Integer donateNum;

  /**
   * 捐赠项目数
   */
  private Integer donateProjectNum;

  /**
   * 用户类型
   */
  private String userType;

  /**
   * 用户名
   */
  private String userName;

  /**
   * 密码
   */
  private String userPass;

  /**
   * 昵称
   */
  private String nickName;

  /**
   * 手机号验证状态
   */
  private Integer mobileState;

  /**
   * 头像id
   */
  private Integer coverImageId;

  /**
   * 头像地址
   */
  private String coverImageUrl;

  /**
   * 注册时间
   */
  private Date registrTime;

  /**
   * 真实姓名
   */
  private String realName;

  /**
   * 身份证号
   */
  private String idCard;

  /**
   * 手机号码
   */
  private String mobileNum;

  /**
   * 最后一次实名验证时间
   */
  private Date lastAutoTime;

  /**
   * 最后一次登陆时间
   */
  private Date lastLoginTime;

  /**
   * 捐款总金额
   */
  private Double totalAmount;

  /**
   * 注册来源
   */
  private String registrSoure;

  /**
   * 说明
   */
  private String description;

  /**
   * 可用余额
   */
  private Double availableBalance;

  /**
   * 前端显示余额
   */
  private Double balance;

  private String lastLoginIP;

  private String registerIP;


  /**
   * 年龄
   */
  private Integer age;

  /**
   * 性别
   */
  private String sex;

  /**
   * 工作单位
   */
  private String workUnit;

  /**
   * 职位
   */
  private String persition;

  /**
   * 捐赠时间字符串
   */
  private String donateTimeStr;

  private String openid;

  /**
   * 是否是志愿者
   * 0：不是；1：是
   */
  private Integer isVolunteer;

  public Integer getIsVolunteer() {
    return isVolunteer;
  }

  public void setIsVolunteer(Integer isVolunteer) {
    this.isVolunteer = isVolunteer;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getDonateTimeStr() {
    return donateTimeStr;
  }

  public void setDonateTimeStr(String donateTimeStr) {
    this.donateTimeStr = donateTimeStr;
  }

  public Integer getDonateNum() {
    return donateNum;
  }

  public void setDonateNum(Integer donateNum) {
    this.donateNum = donateNum;
  }

  public Integer getDonateProjectNum() {
    return donateProjectNum;
  }

  public void setDonateProjectNum(Integer donateProjectNum) {
    this.donateProjectNum = donateProjectNum;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getLastLoginIP()
  {
    return lastLoginIP;
  }

  public Integer getAge()
  {
    return age;
  }

  public void setAge(Integer age)
  {
    this.age = age;
  }

  public String getSex()
  {
    return sex;
  }

  public void setSex(String sex)
  {
    this.sex = sex;
  }

  public String getWorkUnit()
  {
    return workUnit;
  }

  public void setWorkUnit(String workUnit)
  {
    this.workUnit = workUnit;
  }

  public String getPersition()
  {
    return persition;
  }

  public void setPersition(String persition)
  {
    this.persition = persition;
  }

  public void setLastLoginIP(String lastLoginIP)
  {
    this.lastLoginIP = lastLoginIP;
  }

  public String getRegisterIP()
  {
    return registerIP;
  }

  public void setRegisterIP(String registerIP)
  {
    this.registerIP = registerIP;
  }

  public Double getAvailableBalance()
  {
    return availableBalance;
  }

  public void setAvailableBalance(Double availableBalance)
  {
    this.availableBalance = availableBalance;
  }

  public Double getBalance()
  {
    return balance;
  }

  public void setBalance(Double balance)
  {
    this.balance = balance;
  }

  public String getUserType()
  {
    return userType;
  }

  public void setUserType(String userType)
  {
    this.userType = userType;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getUserPass()
  {
    return userPass;
  }

  public void setUserPass(String userPass)
  {
    this.userPass = userPass;
  }

  public String getNickName()
  {
    return nickName;
  }

  public void setNickName(String nickName)
  {
    this.nickName = nickName;
  }

  public Integer getMobileState()
  {
    return mobileState;
  }

  public void setMobileState(Integer mobileState)
  {
    this.mobileState = mobileState;
  }

  public Integer getCoverImageId()
  {
    return coverImageId;
  }

  public void setCoverImageId(Integer coverImageId)
  {
    this.coverImageId = coverImageId;
  }

  public String getCoverImageUrl()
  {
    return coverImageUrl;
  }

  public void setCoverImageUrl(String coverImageUrl)
  {
    this.coverImageUrl = coverImageUrl;
  }

  public Date getRegistrTime()
  {
    return registrTime;
  }

  public void setRegistrTime(Date registrTime)
  {
    this.registrTime = registrTime;
  }

  public String getRealName()
  {
    return realName;
  }

  public void setRealName(String realName)
  {
    this.realName = realName;
  }

  public String getIdCard()
  {
    return idCard;
  }

  public void setIdCard(String idCard)
  {
    this.idCard = idCard;
  }

  public String getMobileNum()
  {
    return mobileNum;
  }

  public void setMobileNum(String mobileNum)
  {
    this.mobileNum = mobileNum;
  }

  public Date getLastAutoTime()
  {
    return lastAutoTime;
  }

  public void setLastAutoTime(Date lastAutoTime)
  {
    this.lastAutoTime = lastAutoTime;
  }

  public Date getLastLoginTime()
  {
    return lastLoginTime;
  }

  public void setLastLoginTime(Date lastLoginTime)
  {
    this.lastLoginTime = lastLoginTime;
  }

  public Double getTotalAmount()
  {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount)
  {
    this.totalAmount = totalAmount;
  }

  public String getRegistrSoure()
  {
    return registrSoure;
  }

  public void setRegistrSoure(String registrSoure)
  {
    this.registrSoure = registrSoure;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  @Override
  public String toString() {
    return "ApiFrontUser{" +
            "id=" + id +
            ", donateNum=" + donateNum +
            ", userType='" + userType + '\'' +
            ", userName='" + userName + '\'' +
            ", userPass='" + userPass + '\'' +
            ", nickName='" + nickName + '\'' +
            ", mobileState=" + mobileState +
            ", realName='" + realName + '\'' +
            ", idCard='" + idCard + '\'' +
            ", mobileNum='" + mobileNum + '\'' +
            ", totalAmount=" + totalAmount +
            ", availableBalance=" + availableBalance +
            ", balance=" + balance +
            ", workUnit='" + workUnit + '\'' +
            ", persition='" + persition + '\'' +
            ", openid='" + openid + '\'' +
            ", isVolunteer=" + isVolunteer +
            '}';
  }



}
