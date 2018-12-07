package com.guangde.business.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guangde.business.dao.ProjectUserInfoMapper;
import com.guangde.business.entry.ProjectUserInfo;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ProjectUserInfoService;
import com.guangde.business.util.ConfigLoader;
import com.guangde.business.util.ResultCode;

@Service("projectUserInfoService")
public class ProjectUserInfoServiceImpl extends BaseService implements ProjectUserInfoService
{
    @Autowired
    private ProjectUserInfoMapper projectUserInfoMapper;
    
    @Override
    public void saveProjectUserInfo(ProjectUserInfo projectUserInfo)
        throws BaseException
    {
        logger.info("saveProjectUserInfo  param  projectUserInfo = " + projectUserInfo);
        try
        {
            ProjectUserInfo puf = new ProjectUserInfo();
            puf.setProjectId(projectUserInfo.getProjectId());
            puf.setRealName(projectUserInfo.getRealName());
            puf.setPersonType(projectUserInfo.getPersonType());
            ProjectUserInfo p = projectUserInfoMapper.queryProjectUserInfo(puf);
            
            if (p == null)
            {
                projectUserInfoMapper.save(projectUserInfo);
                
            }
            
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public ProjectUserInfo queryProjectUserInfo(ProjectUserInfo projectUserInfo)
    {
        return projectUserInfoMapper.queryProjectUserInfo(projectUserInfo);
    }
    
    @Override
    public List<ProjectUserInfo> queryProjectUserInfoList(ProjectUserInfo projectUserInfo)
    {
    	String resUrl = ConfigLoader.getResPictrueURL();
    	List<ProjectUserInfo> list = projectUserInfoMapper.queryByParam(projectUserInfo);
    	for(ProjectUserInfo  userInfo : list){
    		if(!StringUtils.isEmpty(userInfo.getHeadImageUrl())){
    			userInfo.setHeadImageUrl(resUrl+userInfo.getHeadImageUrl());
    		}
    		if(!StringUtils.isEmpty(userInfo.getLitterImageUrl())){
    			userInfo.setLitterImageUrl(resUrl+userInfo.getLitterImageUrl());
    		}
    	}
        return list;
    }
    
    @Override
    public void updateProjectUserInfo(ProjectUserInfo projectUserInfo)
        throws BaseException
    {
        logger.info("updateProjectUserInfo  param  projectUserInfo = " + projectUserInfo);
        try
        {
            
            projectUserInfoMapper.update(projectUserInfo);
            
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
}
