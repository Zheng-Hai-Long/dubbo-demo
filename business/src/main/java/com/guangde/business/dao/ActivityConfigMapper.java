package com.guangde.business.dao;

import com.guangde.business.entry.ActivityConfig;

import java.util.List;

public interface ActivityConfigMapper extends BaseMapper<ActivityConfig>
{
    List<ActivityConfig> queryCommonFormUserPageByParam(Integer userId);
}
