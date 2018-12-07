package com.guangde.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.ActivityConfigMapper;
import com.guangde.business.dao.BFileMapper;
import com.guangde.business.entry.ActivityConfig;
import com.guangde.business.entry.BFile;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.ActivityConfigService;
import com.guangde.business.service.BaseService;
import com.guangde.business.util.ConfigLoader;
import com.guangde.business.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityConfigServiceImpl extends BaseService implements ActivityConfigService
{
    
    @Autowired
    private ActivityConfigMapper activityConfigMapper;

    @Autowired
    private BFileMapper bFileMapper;

	@Override
	public ActivityConfig queryById(Integer id) {
		ActivityConfig activityConfig = activityConfigMapper.queryById(id);
		String resUrl = ConfigLoader.getResPictrueURL();
		if(activityConfig != null && activityConfig.getLogoId() != null){
			BFile bFile = bFileMapper.queryById(activityConfig.getLogoId());
			if(bFile != null)
				activityConfig.setLogoUrl(resUrl+bFile.getUrl());
		}
		return activityConfig;
	}

	@Override
	public List<ActivityConfig> queryActivityPageByParam(ActivityConfig param, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ActivityConfig> activityConfigList = activityConfigMapper.queryByParam(param);
		return activityConfigList;
	}

	@Override
	public List<ActivityConfig> queryCommonFormUserPageByParam(Integer userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ActivityConfig> activityConfigList = activityConfigMapper.queryCommonFormUserPageByParam(userId);
		return activityConfigList;
	}


	@Override
	public void updateActivityConfig(ActivityConfig activityConfig)
			throws BaseException
	{
		try {
			activityConfigMapper.update(activityConfig);
		} catch (Exception e) {
			logger.error(e);
            throw new BaseException(ResultCode.Error);
		}
	}
    
	@Override
	public void saveActivityConfig(ActivityConfig activityConfig)
			throws BaseException
	{
		try {
			activityConfigMapper.save(activityConfig);
		} catch (Exception e) {
			logger.error(e);
            throw new BaseException(ResultCode.Error);
		}
	}

}
