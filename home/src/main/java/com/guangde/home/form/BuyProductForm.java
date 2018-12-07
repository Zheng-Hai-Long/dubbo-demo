package com.guangde.home.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by ZHL on 2018/12/1.
 */
public class BuyProductForm implements Serializable {

    @NotNull(message = "商品id不能为空")
    private Integer productId;

    @Min(value = 1, message = "商品数量不合法")
    private Integer shipmentNum;

    private Integer userId;

    private String transNum;

    @NotNull(message = "商品规格id不能为空")
    private Integer specificationId;

    private String source;

    @NotBlank(message = "收件人不能为空")
    private String receiverName;

    @NotNull(message = "收件人电话不能为空")
    private String receiverPhone;

    @NotNull(message = "收件人地址不能为空")
    private String addresseeAddress;

    private String weixinUrl;

    public String getWeixinUrl() {
        return weixinUrl;
    }

    public void setWeixinUrl(String weixinUrl) {
        this.weixinUrl = weixinUrl;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getAddresseeAddress() {
        return addresseeAddress;
    }

    public void setAddresseeAddress(String addresseeAddress) {
        this.addresseeAddress = addresseeAddress;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getShipmentNum() {
        return shipmentNum;
    }

    public void setShipmentNum(Integer shipmentNum) {
        this.shipmentNum = shipmentNum;
    }
}
