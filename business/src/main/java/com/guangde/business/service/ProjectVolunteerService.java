package com.guangde.business.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guangde.business.entry.ProjectVolunteer;
import com.guangde.business.exception.BaseException;



public interface ProjectVolunteerService
{
	void save(ProjectVolunteer volunteer) throws  BaseException;
	void update(ProjectVolunteer volunteer) throws BaseException;
	
	List<ProjectVolunteer> queryByParam(Integer pageNum,Integer pageSize,ProjectVolunteer projectVolunteer);
	
	ProjectVolunteer queryById(Integer id);
	
	int count (ProjectVolunteer projectVolunteer);
	
	List<ProjectVolunteer> queryEntryInfo(Integer activityId, Integer state, Integer pageNum, Integer pageSize);
	
	/**
     * 报名明细统计
     * @param activityId
     * @param state
     * @return
     */
    int countEntry(Integer activityId, Integer state);

	
	ProjectVolunteer queryByVolunteerIdAndFormId(@Param("userId")Integer userId,@Param("formId")Integer formId);

	/**
     * 判断用户是否是此活动的志愿者
     * @param userId
     * @param activityId
     * @return
     */
    int isOrNotActivityVolunteer(Integer userId, Integer activityId);
	
}
