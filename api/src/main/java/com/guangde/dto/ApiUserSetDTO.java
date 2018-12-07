package com.guangde.dto;

import java.io.Serializable;

/**
 * Created by ZHL on 2018/12/1.
 */
public class ApiUserSetDTO implements Serializable {

    private String coverImageUrl;

    private String nickName;

    private Integer mobileState;

    private Integer addressNum;

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getMobileState() {
        return mobileState;
    }

    public void setMobileState(Integer mobileState) {
        this.mobileState = mobileState;
    }

    public Integer getAddressNum() {
        return addressNum;
    }

    public void setAddressNum(Integer addressNum) {
        this.addressNum = addressNum;
    }
}
