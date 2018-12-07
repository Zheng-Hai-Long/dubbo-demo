package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.Config;

public interface ConfigService {
	
	void update(Config config);
	List<Config> queryAll();
    //取数据库的系统参数表数据
    List<Config> queryList(Config config);
	
}
