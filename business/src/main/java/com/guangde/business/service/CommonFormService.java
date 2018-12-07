package com.guangde.business.service;

import com.guangde.business.dto.CommonFormUserDTO;
import com.guangde.business.entry.CommonForm;
import com.guangde.business.entry.CommonFormConfig;
import com.guangde.business.entry.CommonFormUser;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;

import java.util.List;

public interface CommonFormService {

	void save(CommonFormConfig commonFormConfig) throws BaseException;

	/**
	 * 根据id查询表单配置
	 * @param id
	 * @return
     */
	CommonForm queryConformById(Integer id);

	/**
	 * 提交表单
	 * @param param
	 * @param userId
	 * @throws BaseException
     */
	Result submitForm(List<CommonFormUser> param, Integer userId) throws BaseException;

	/**
	 * 查询报名详情
	 * @param formId
	 * @param userId
     * @return
     */
	CommonFormUserDTO queryCommonFormInfo(Integer formId, Integer userId);

}
