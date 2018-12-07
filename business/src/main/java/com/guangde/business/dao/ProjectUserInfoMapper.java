package com.guangde.business.dao;

import com.guangde.business.entry.ProjectUserInfo;

public interface ProjectUserInfoMapper extends BaseMapper<ProjectUserInfo>
{
    
    ProjectUserInfo queryProjectUserInfo(ProjectUserInfo projectUserInfo);
}
