package com.guangde.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.ProjectVolunteerMapper;
import com.guangde.business.entry.ProjectVolunteer;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ProjectVolunteerService;
import com.guangde.business.util.ResultCode;

@Service("projectVolunteerService")
public class ProjectVolunteerServiceImpl extends BaseService implements ProjectVolunteerService
{
	@Autowired
	private ProjectVolunteerMapper projectVolunteerMapper ;

	@Override
	public List<ProjectVolunteer> queryByParam(Integer pageNum,
			Integer pageSize, ProjectVolunteer projectVolunteer) 
			{
		PageHelper.startPage(pageNum, pageSize);
		List<ProjectVolunteer> list = projectVolunteerMapper.queryByParam(projectVolunteer);
		return list;
	}

	@Override
	public void save(ProjectVolunteer volunteer) throws BaseException {
		try
		{
			projectVolunteerMapper.save(volunteer);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public void update(ProjectVolunteer volunteer) throws BaseException {
		try
		{
			projectVolunteerMapper.update(volunteer);
		}
		catch(Exception e)
		{
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public ProjectVolunteer queryById(Integer id) {
		
		return projectVolunteerMapper.queryById(id);
	}
	
	@Override
	public ProjectVolunteer queryByVolunteerIdAndFormId(Integer userId,Integer formId) {
		
		return projectVolunteerMapper.queryByVolunteerIdAndFormId(userId,formId);
	}

	@Override
	public int count(ProjectVolunteer projectVolunteer) {
		
		return projectVolunteerMapper.count(projectVolunteer);
	}

	@Override
	public List<ProjectVolunteer> queryEntryInfo(Integer activityId, Integer state,
			Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ProjectVolunteer> list = projectVolunteerMapper.queryEntryInfo(activityId, state);
		return list;
	}
   
	public int countEntry(Integer activityId, Integer state){
		int res = projectVolunteerMapper.countEntry(activityId, state);
		return res;
	}

	@Override
	public int isOrNotActivityVolunteer(Integer userId, Integer activityId) {
		return projectVolunteerMapper.isOrNotActivityVolunteer(userId, activityId);
	}
   
    
}
