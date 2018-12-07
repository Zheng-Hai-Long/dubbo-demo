package com.guangde.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by ZHL on 2018/11/29.
 */
public class UserDTO implements Serializable {

    private Integer id;

    private String coverImageUrl;

    private String realName;

    private String nickName;

    private String mobileNum;

    private Integer donateNum;

    private BigDecimal totalAmount;

    private  String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public Integer getDonateNum() {
        return donateNum;
    }

    public void setDonateNum(Integer donateNum) {
        this.donateNum = donateNum;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
