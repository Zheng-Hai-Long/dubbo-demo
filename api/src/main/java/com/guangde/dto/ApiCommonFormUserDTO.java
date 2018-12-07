package com.guangde.dto;

import com.guangde.entry.ApiCommonFormUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHL on 2018/11/27.
 */
public class ApiCommonFormUserDTO implements Serializable {

    private Integer id;

    private String formName;

    private Date createTime;

    private List<ApiCommonFormUser> commonFormUserList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<ApiCommonFormUser> getCommonFormUserList() {
        return commonFormUserList;
    }

    public void setCommonFormUserList(List<ApiCommonFormUser> commonFormUserList) {
        this.commonFormUserList = commonFormUserList;
    }
}
