package com.guangde.homepage.facade.impl;

import com.guangde.api.homepage.IProjectVolunteerFacade;
import com.guangde.business.entry.CommonFormConfig;
import com.guangde.business.entry.ProjectCryPeople;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.CommonFormService;
import com.guangde.business.service.ProjectCryPeopleService;
import com.guangde.business.service.ProjectVolunteerService;
import com.guangde.entry.ApiCommonForm;
import com.guangde.entry.ApiCommonFormConfig;
import com.guangde.entry.ApiCommonFormUser;
import com.guangde.entry.ApiProjectCryPeople;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("projectVolunteerFacade")
public class ProjectVolunteerFacadeImpl implements IProjectVolunteerFacade
{
    private Logger logger = Logger.getLogger(getClass());
    @Autowired
    private ProjectVolunteerService projectVolunteerService ;
    
    @Autowired
    private ProjectCryPeopleService projectCryPeopleService ;
    

	@Autowired
	private CommonFormService commonFormService;


	@Override
	public ApiResult save(ApiProjectCryPeople apiProjectCryPeople) {
		
		logger.info("save apiProjectCryPeople >> "+apiProjectCryPeople);
		
		if(apiProjectCryPeople == null )
		{
			return ApiResultUtil.getParameterError();
		}
		ProjectCryPeople cryPeople = BeanUtil.copy(apiProjectCryPeople, ProjectCryPeople.class);
		try
		{
			projectCryPeopleService.save(cryPeople);
			return ApiResultUtil.SUCCESS;
			
		}
		catch(BaseException e)
		{
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}


	@Override
	public ApiCommonForm selectByParam(ApiCommonForm model) {
		return null;
	}

	@Override
	public int save(ApiCommonFormUser model) {
		return 0;
	}

	@Override
	public int update(ApiCommonFormUser model) {
		return 0;
	}

	@Override
	public int update(ApiCommonForm model) {
		return 0;
	}

	@Override
	public int countCommonFormUserByParam(ApiCommonFormUser model) {
		return 0;
	}

	@Override
	public ApiCommonFormUser queryCommonFormUserDetailByParam(ApiCommonFormUser model) {
		return null;
	}

	@Override
	public ApiPage<ApiCommonFormUser> queryCommonFormList(ApiCommonFormUser apiCommonFormUser, Integer pageNum, Integer pageSize) {
		return null;
	}


	@Override
	public boolean isOrNotActivityVolunteer(Integer userId, Integer activityId) {
		int res = projectVolunteerService.isOrNotActivityVolunteer(userId, activityId);
		if(res > 0){
			return true;
		}
		return false;
	}

	@Override
	public ApiResult saveCommformAndUpdateCommonformUser(ApiCommonFormUser apiCommonFormUser, ApiCommonForm commonForm) {
		return null;
	}

	@Override
	public ApiResult saveCommonRecord(ApiCommonFormConfig data) {
		logger.info("saveCommonRecord param is " + data);
		CommonFormConfig commonFormConfig = BeanUtil.copy(data, CommonFormConfig.class);
		try
		{
			commonFormService.save(commonFormConfig);
			return ApiResultUtil.SUCCESS;
		}
		catch(BaseException e)
		{
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}
}
