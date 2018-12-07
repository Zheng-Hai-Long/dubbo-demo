package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.ProjectMoneyConfig;


public interface ProjectMoneyConfigService
{
	/**
	 * 根据条件查询项目捐款配置列表
	 * @param param
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<ProjectMoneyConfig> queryMoneyConfigParam(ProjectMoneyConfig param);
	
}
