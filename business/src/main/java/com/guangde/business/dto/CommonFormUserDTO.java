package com.guangde.business.dto;

import com.guangde.business.entry.CommonFormUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ZHL on 2018/11/27.
 */
public class CommonFormUserDTO implements Serializable {

    private Integer id;

    private String formName;

    private Date createTime;

    private List<CommonFormUser> commonFormUserList;

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

    public List<CommonFormUser> getCommonFormUserList() {
        return commonFormUserList;
    }

    public void setCommonFormUserList(List<CommonFormUser> commonFormUserList) {
        this.commonFormUserList = commonFormUserList;
    }
}
