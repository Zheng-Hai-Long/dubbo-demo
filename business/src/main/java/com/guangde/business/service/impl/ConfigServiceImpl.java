package com.guangde.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guangde.business.dao.ConfigMapper;
import com.guangde.business.entry.Config;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ConfigService;

@Service("configService")
public class ConfigServiceImpl extends BaseService implements ConfigService{
	
	@Autowired
	private ConfigMapper configMapper;
	
	@Override
	public void update(Config config) {
		if(null != config)
		{
			configMapper.update(config);
		}
		
	}

	@Override
	public List<Config> queryAll() {
		return configMapper.queryAll();
	}
	
	@Override
	public List<Config> queryList(Config config) {
		return configMapper.queryByParam(config);
	}
}
