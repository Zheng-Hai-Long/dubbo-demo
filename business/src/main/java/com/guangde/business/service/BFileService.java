package com.guangde.business.service;

import java.util.List;

import com.guangde.business.pojo.FocusMap;

public interface BFileService
{
    
    /**
     * 查询焦点图
     * @return 焦点图列表
     */
    List<FocusMap> queryFocusMap();
    
}
