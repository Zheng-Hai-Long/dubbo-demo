package com.guangde.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guangde.business.dao.ProjectMoneyConfigMapper;
import com.guangde.business.entry.ProjectMoneyConfig;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ProjectMoneyConfigService;

@Service("projectMoneyConfigService")
public class ProjectMoneyConfigServiceImpl extends BaseService implements ProjectMoneyConfigService {
	@Autowired
	private ProjectMoneyConfigMapper projectMoneyConfigMapper;
	
	@Override
	public List<ProjectMoneyConfig> queryMoneyConfigParam(ProjectMoneyConfig param) {
		List<ProjectMoneyConfig> list = null;
		try {
			list = projectMoneyConfigMapper.queryByParam(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
