package com.guangde.home.vo.deposit;

/**
 * Created by ZHL on 2018/11/29.
 */
public class WeixinPayVO {

    private String tradeNo;
    private String appId;
    private String timestamp;
    private String noncestr;
    private String packageValue;
    private String paySign;
    private String paysignType;
    private String config_timestamp;
    private String config_noncestr;
    private String signature;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getPaysignType() {
        return paysignType;
    }

    public void setPaysignType(String paysignType) {
        this.paysignType = paysignType;
    }

    public String getConfig_timestamp() {
        return config_timestamp;
    }

    public void setConfig_timestamp(String config_timestamp) {
        this.config_timestamp = config_timestamp;
    }

    public String getConfig_noncestr() {
        return config_noncestr;
    }

    public void setConfig_noncestr(String config_noncestr) {
        this.config_noncestr = config_noncestr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "WeixinPayVO{" +
                "tradeNo='" + tradeNo + '\'' +
                ", appId='" + appId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", packageValue='" + packageValue + '\'' +
                ", paySign='" + paySign + '\'' +
                ", paysignType='" + paysignType + '\'' +
                ", config_timestamp='" + config_timestamp + '\'' +
                ", config_noncestr='" + config_noncestr + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
