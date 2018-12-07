package com.guangde.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ZHL on 2018/11/29.
 */
public class ApiProductSpecification implements Serializable {

    private Integer id;

    private Integer productId;

    private String productSpecification;

    private BigDecimal price;

    private Date ceateTime;

    private Date updateTime;

    private Integer productSpecificationId;

    private String productSpecificationName;

    private Integer stock;

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getProductSpecificationId() {
        return productSpecificationId;
    }

    public void setProductSpecificationId(Integer productSpecificationId) {
        this.productSpecificationId = productSpecificationId;
    }

    public String getProductSpecificationName() {
        return productSpecificationName;
    }

    public void setProductSpecificationName(String productSpecificationName) {
        this.productSpecificationName = productSpecificationName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCeateTime() {
        return ceateTime;
    }

    public void setCeateTime(Date ceateTime) {
        this.ceateTime = ceateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ProductSpecification{" +
                "id=" + id +
                ", productId=" + productId +
                ", productSpecification='" + productSpecification + '\'' +
                ", price=" + price +
                ", ceateTime=" + ceateTime +
                ", updateTime=" + updateTime +
                ", productSpecificationId=" + productSpecificationId +
                ", productSpecificationName='" + productSpecificationName + '\'' +
                '}';
    }
}
