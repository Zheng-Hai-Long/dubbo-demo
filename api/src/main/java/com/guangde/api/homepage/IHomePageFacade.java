package com.guangde.api.homepage;

import com.guangde.dto.ActivityDTO;
import com.guangde.dto.ActivityDetailDTO;
import com.guangde.entry.ApiActivityConfig;
import com.guangde.entry.ApiCommonFormUser;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

import java.util.List;

public interface IHomePageFacade {
	
	/**
	 * 根据id查询活动
	 * @return
	 */
	ActivityDetailDTO queryById(Integer id);

	/**
	 * 根据条件查询活动
	 * @param param
	 * @param pageNum
	 * @param pageSize
     * @return
     */
	ApiPage<ActivityDTO> queryActivityPageByParam(ApiActivityConfig param, int pageNum, int pageSize);

	ApiPage<ApiActivityConfig> queryCommonFormUserPageByParam(Integer userId, int pageNum, int pageSize);

	/**
	 * 根据表单id查询表单配置
	 * @param id
	 * @return
     */
	ApiResult queryCommonFormById(Integer id);

	/**
	 * 提交表单
	 * @param param
	 * @param userId
     * @return
     */
	ApiResult submitForm(List<ApiCommonFormUser> param, Integer userId);

	/**
	 * 查询报名详情
	 * @param formId
	 * @param userId
     * @return
     */
	ApiResult queryCommonFormInfo(Integer formId, Integer userId);

}
