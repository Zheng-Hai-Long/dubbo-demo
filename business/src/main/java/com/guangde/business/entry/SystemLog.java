package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/2/7.
 */
public class SystemLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 用户登陆名
     */
    private String username;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作IP
     */
    private String IP;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 操作URL
     */
    private String url;

    /**
     * 操作内容
     */
    private String content;

    private String details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ApiSystemLog{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", operateTime=" + operateTime +
                ", IP='" + IP + '\'' +
                ", operateType='" + operateType + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
