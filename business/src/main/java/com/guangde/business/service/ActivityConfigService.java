package com.guangde.business.service;

import com.guangde.business.entry.ActivityConfig;
import com.guangde.business.exception.BaseException;

import java.util.List;

public interface ActivityConfigService
{
	void updateActivityConfig(ActivityConfig activityConfig)
	        throws BaseException;

	void saveActivityConfig(ActivityConfig activityConfig)
	        throws BaseException;
    /**
     * 根据id查询活动
     * @return 
     */
    ActivityConfig queryById(Integer id);

    /**
     * 根据条件查询活动列表
     * @param param
     * @return
     */
    List<ActivityConfig> queryActivityPageByParam(ActivityConfig param, int pageNum, int pageSize);

    List<ActivityConfig> queryCommonFormUserPageByParam(Integer userId, int pageNum, int pageSize);

}
