package com.guangde.home.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ZHL on 2018/11/19.
 */
public class FrontUserAddressForm {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;


    /**
     * 收货人联系号码
     */
    @NotBlank(message = "收货人手机号不能为空")
    private String mobile;
    /**
     * 收货人姓名
     */
    @NotBlank(message = "收货人姓名不能为空")
    private String name;

    @NotBlank(message = "收货人省市区不能为空")
    private String province;

    @NotBlank(message = "收货人省市区不能为空")
    private String city;

    @NotBlank(message = "收货人省市区能为空")
    private String area;

    @NotBlank(message = "收货人详细地址不能为空")
    private String detailAddress;

    private Date createTime;

    private Date lastUpdateTime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮编
     */
    private String code;

    /**
     * 是否默认 选中  0 是  1不是
     */
    @NotNull(message = "是否默认不能为空")
    private Integer isSelected;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "FrontUserAddressForm{" +
                "id=" + id +
                ", userId=" + userId +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
