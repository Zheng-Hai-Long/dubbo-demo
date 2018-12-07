package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.ProjectUserInfo;
import com.guangde.business.exception.BaseException;

public interface ProjectUserInfoService
{
    /**
     * 项目人员信息 入库
     * @param projectFeedback
     * @throws BaseException 
     */
    void saveProjectUserInfo(ProjectUserInfo projectUserInfo)
        throws BaseException;
    
    /**
     * 项目人员信息修改
     * @param projectUserInfo
     * @throws BaseException 
     */
    void updateProjectUserInfo(ProjectUserInfo projectUserInfo)
        throws BaseException;
    
    /**
     * 查找项目人员信息
     * @param projectUserInfo
     * @return
     */
    ProjectUserInfo queryProjectUserInfo(ProjectUserInfo projectUserInfo);
    
    /**
     * 查找项目人员列表
     * @param projectUserInfo
     * @return
     */
    List<ProjectUserInfo> queryProjectUserInfoList(ProjectUserInfo projectUserInfo);
    
}
