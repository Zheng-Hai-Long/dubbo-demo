package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.Config;

public interface ConfigMapper
{
    void update(Config config);
    
    List<Config> queryAll();
    
    List<Config> queryByIdList(List<Integer> list);
    
    List<Config> queryByParam(Config config);
}
