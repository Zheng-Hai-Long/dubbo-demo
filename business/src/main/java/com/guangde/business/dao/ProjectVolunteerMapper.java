package com.guangde.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guangde.business.entry.ProjectVolunteer;

public interface ProjectVolunteerMapper extends BaseMapper<ProjectVolunteer>
{

	/**
	 * 报名明细（姓名，性别，地址，职业，签到次数，累计时长）
	 * @param activityId
	 * @param state(0:未签到；1：已签到；2：全部)
	 * @return
	 */
	List<ProjectVolunteer> queryEntryInfo(@Param("activityId") Integer activityId,
			@Param("state") Integer state);
	

	ProjectVolunteer queryByVolunteerIdAndFormId(@Param("userId")Integer userId,@Param("formId")Integer formId);

	/**
     * 报名明细统计
     * @param activityId
     * @param state
     * @return
     */
    int countEntry(@Param("activityId") Integer activityId, @Param("state") Integer state);
    
    /**
     * 判断用户是否是此活动的志愿者
     * @param userId
     * @param activityId
     * @return
     */
    int isOrNotActivityVolunteer(@Param("userId")Integer userId, @Param("activityId") Integer activityId);
}
