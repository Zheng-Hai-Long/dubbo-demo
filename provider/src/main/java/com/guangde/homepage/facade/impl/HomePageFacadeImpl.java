package com.guangde.homepage.facade.impl;

import com.github.pagehelper.Page;
import com.guangde.api.homepage.IHomePageFacade;
import com.guangde.business.dto.CommonFormUserDTO;
import com.guangde.business.entry.ActivityConfig;
import com.guangde.business.entry.CommonForm;
import com.guangde.business.entry.CommonFormUser;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.ActivityConfigService;
import com.guangde.business.service.CommonFormService;
import com.guangde.business.util.DateUtil;
import com.guangde.dto.ActivityDTO;
import com.guangde.dto.ActivityDetailDTO;
import com.guangde.entry.ApiActivityConfig;
import com.guangde.entry.ApiCommonFormUser;
import com.guangde.enums.ResultEnum;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("homePageFacade")
public class HomePageFacadeImpl implements IHomePageFacade{

	private static final Logger logger = LoggerFactory.getLogger(HomePageFacadeImpl.class);

	@Autowired
	private ActivityConfigService activityConfigService;

	@Autowired
	private CommonFormService commonFormService;

	@Override
	public ActivityDetailDTO queryById(Integer id) {
		ActivityConfig config = activityConfigService.queryById(id);
		ActivityDetailDTO activityDetailDTO = new ActivityDetailDTO();
		if(config != null){
			config.setLogoUrl(config.getLogoMiddleUrl());
			Integer state = getActivityState(config.getStartTime(), config.getEndTime(), config.getLimitNum(), config.getEnterNum());
			config.setState(state);
			BeanUtils.copyProperties(config, activityDetailDTO);
			return activityDetailDTO;
		}
		return null;
	}

	@Override
	public ApiPage<ActivityDTO> queryActivityPageByParam(ApiActivityConfig param, int pageNum, int pageSize) {
		logger.info("queryActivityPageByParam param {}", param);

		ActivityConfig activityConfig = BeanUtil.copy(param, ActivityConfig.class);

		if(activityConfig == null){
			new ApiPage<ApiActivityConfig>(pageNum, pageSize);
		}

		List<ActivityConfig> activityConfigList = activityConfigService.queryActivityPageByParam(activityConfig, pageNum, pageSize);
		for (ActivityConfig config : activityConfigList) {
			Integer state = getActivityState(config.getStartTime(), config.getEndTime(), config.getLimitNum(), config.getEnterNum());
			config.setState(state);
		}

		//List<ActivityDTO> activityDTOList = BeanUtil.copyList(activityConfigList, ActivityDTO.class);

		ApiPage<ActivityDTO> page = BeanUtil.copyPage((Page<ActivityConfig>)activityConfigList, ActivityDTO.class);
		return page;
	}

	@Override
	public ApiPage<ApiActivityConfig> queryCommonFormUserPageByParam(Integer userId, int pageNum, int pageSize) {
		logger.info("queryCommonFormUserPageByParam userId {}", userId);

		List<ActivityConfig> activityConfigList = activityConfigService.queryCommonFormUserPageByParam(userId, pageNum, pageSize);
		for (ActivityConfig config : activityConfigList) {
			Integer state = getActivityState(config.getStartTime(), config.getEndTime(), config.getLimitNum(), config.getEnterNum());
			config.setState(state);
		}

		ApiPage<ApiActivityConfig> page = BeanUtil.copyPage((Page<ActivityConfig>)activityConfigList, ApiActivityConfig.class);
		return page;
	}

	@Override
	public ApiResult queryCommonFormById(Integer id) {
		logger.info("queryCommonFormById id " + id);

		CommonForm commonForm = commonFormService.queryConformById(id);
		if(commonForm != null){
			return new ApiResult(ResultEnum.Success, commonForm);
		}
		return new ApiResult(ResultEnum.EmptyData);
	}

	@Override
	public ApiResult submitForm(List<ApiCommonFormUser> param, Integer userId) {
		logger.info("submitForm List<ApiCommonFormUser> = {}， userId = ", param, userId);
		try {
			List<CommonFormUser> model = BeanUtil.copyList(param, CommonFormUser.class);
			Result result = commonFormService.submitForm(model, userId);
			ApiResult apiResult = new ApiResult();
			BeanUtils.copyProperties(result, apiResult);
			return apiResult;
		}catch (BaseException e){
			e.printStackTrace();
		}
		return ApiResultUtil.ERROR;
	}

	@Override
	public ApiResult queryCommonFormInfo(Integer formId, Integer userId) {
		logger.info("queryCommonFormInfo formId = {}， userId = ", formId, userId);
		CommonFormUserDTO commonFormUserDTO = commonFormService.queryCommonFormInfo(formId, userId);
		if(commonFormUserDTO != null){
			return new ApiResult(ResultEnum.Success, commonFormUserDTO);
		}

		return new ApiResult(ResultEnum.EmptyData);
	}

	private Integer getActivityState(Date startTime, Date endTime, Integer limitNum, Integer enterNum){
		//报名人数大于或等于最大人数  超出截止时间
		Date now = new Date();
		if(enterNum >= limitNum || DateUtil.getNowOverDate(now, endTime)){
			//活动结束
			return 300;
		}else if(!DateUtil.getNowOverDate(now, startTime)){
			//正常
			return 200;
		}else{
			//活动未开始
			return 100;
		}
	}
	
}
