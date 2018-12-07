package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ZHL on 2018/11/29.
 */
public class ProductStockInfo implements Serializable {

    private Integer id;

    private Integer actionUserId;

    private Integer productId;

    private Integer productName;

    private Integer changeNumber;

    private Integer everStockNum;

    private Integer outStockNum;

    private Date createTime;

    private Date updateTime;

    private Integer stock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(Integer actionUserId) {
        this.actionUserId = actionUserId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductName() {
        return productName;
    }

    public void setProductName(Integer productName) {
        this.productName = productName;
    }

    public Integer getChangeNumber() {
        return changeNumber;
    }

    public void setChangeNumber(Integer changeNumber) {
        this.changeNumber = changeNumber;
    }

    public Integer getEverStockNum() {
        return everStockNum;
    }

    public void setEverStockNum(Integer everStockNum) {
        this.everStockNum = everStockNum;
    }

    public Integer getOutStockNum() {
        return outStockNum;
    }

    public void setOutStockNum(Integer outStockNum) {
        this.outStockNum = outStockNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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
        return "ProductStockInfo{" +
                "id=" + id +
                ", actionUserId=" + actionUserId +
                ", productId=" + productId +
                ", productName=" + productName +
                ", changeNumber=" + changeNumber +
                ", everStockNum=" + everStockNum +
                ", outStockNum=" + outStockNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", stock=" + stock +
                '}';
    }
}
