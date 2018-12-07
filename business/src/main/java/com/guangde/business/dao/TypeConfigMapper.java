package com.guangde.business.dao;

import com.guangde.business.entry.TypeConfig;

public interface TypeConfigMapper extends BaseMapper<TypeConfig>
{
    TypeConfig queryTypeConfig(TypeConfig typeConfig);
}
