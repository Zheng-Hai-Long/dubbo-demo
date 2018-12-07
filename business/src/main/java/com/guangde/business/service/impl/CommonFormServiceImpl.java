package com.guangde.business.service.impl;

import com.guangde.business.dao.CommonFormConfigMapper;
import com.guangde.business.dao.CommonFormMapper;
import com.guangde.business.dao.CommonFormUserMapper;
import com.guangde.business.dao.FrontUserMapper;
import com.guangde.business.dto.CommonFormUserDTO;
import com.guangde.business.entry.CommonForm;
import com.guangde.business.entry.CommonFormConfig;
import com.guangde.business.entry.CommonFormUser;
import com.guangde.business.entry.FrontUser;
import com.guangde.business.enums.ResultEnum;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.CommonFormService;
import com.guangde.business.util.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service("commonRecordService")
public class CommonFormServiceImpl extends BaseService implements CommonFormService {

	@Autowired
	private CommonFormConfigMapper commonFormConfigMapper;

	@Autowired
	private CommonFormMapper commonFormMapper;

	@Autowired
	private CommonFormUserMapper commonFormUserMapper;

	@Autowired
	private FrontUserMapper frontUserMapper;


	@Override
	public void save(CommonFormConfig commonFormConfig) throws BaseException{
		try {
			commonFormConfigMapper.save(commonFormConfig);
		} catch (Exception e) {
			logger.error(e);
			throw new BaseException(ResultCode.Error);
		}

	}

	@Override
	public CommonForm queryConformById(Integer id) {
		CommonForm commonForm = commonFormMapper.queryById(id);
		if(commonForm == null){
			return null;
		}
		CommonFormConfig config = new CommonFormConfig();
		config.setFormId(id);
		List<CommonFormConfig> commonFormConfigList = commonFormConfigMapper.queryByParam(config);
		if(!CollectionUtils.isEmpty(commonFormConfigList)){
			commonForm.setCommonFormConfigList(commonFormConfigList);
		}
		return commonForm;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public Result submitForm(List<CommonFormUser> param, Integer userId) throws BaseException {
		FrontUser user = frontUserMapper.queryById(userId);
		CommonFormUser commonFormUser2 = new CommonFormUser();
		commonFormUser2.setFormId(param.get(0).getFormId());
		commonFormUser2.setUserId(userId);
		List<CommonFormUser> commonFormUserList = commonFormUserMapper.queryByParam(commonFormUser2);
		if(!CollectionUtils.isEmpty(commonFormUserList) && commonFormUserList.size() > 0){
			return new Result(ResultEnum.OtherError.getCode(), "不能重复提交");
		}
		for (CommonFormUser commonFormUser : param) {
			commonFormUser.setUserId(userId);
			commonFormUser.setCreateTime(new Date());
			commonFormUser.setUpdateTime(new Date());
			commonFormUser.setNickName(user.getNickName());
			int res = commonFormUserMapper.save(commonFormUser);
			if(res <= 0){
				return new Result(ResultEnum.Error);
			}
		}
		return new Result(ResultEnum.Success);
	}

	@Override
	public CommonFormUserDTO queryCommonFormInfo(Integer formId, Integer userId) {
		CommonForm commonForm = commonFormMapper.queryById(formId);
		if(commonForm == null){
			return null;
		}
		CommonFormUserDTO commonFormUserDTO = new CommonFormUserDTO();
		BeanUtils.copyProperties(commonForm, commonFormUserDTO);
		CommonFormUser commonFormUser = new CommonFormUser();
		commonFormUser.setFormId(formId);
		commonFormUser.setUserId(userId);
		List<CommonFormUser> commonFormUserList = commonFormUserMapper.queryByParam(commonFormUser);
		if(!CollectionUtils.isEmpty(commonFormUserList)){
			commonFormUserDTO.setCommonFormUserList(commonFormUserList);
		}
		return commonFormUserDTO;
	}

}
