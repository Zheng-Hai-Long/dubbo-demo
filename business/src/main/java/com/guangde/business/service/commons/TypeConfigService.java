package com.guangde.business.service.commons;

import java.util.List;

import com.guangde.business.entry.TypeConfig;

public interface TypeConfigService
{
    List<TypeConfig> queryList();
    
    TypeConfig queryTypeConfig(TypeConfig typeConfig);
    
    List<String> queryTypeConfigAndProjectTag(Integer id);
}
