package com.guangde.business.service;

import com.guangde.business.entry.AuctionProject;

import java.util.List;

public interface AuctionProjectService
{
    
    /**
     * 查询项目列表
     * @param project
     * @param pageSize 
     * @param pageNum 
     * @return list
     */
    List<AuctionProject> queryProjectList(AuctionProject project, int pageNum, int pageSize);
    
       
    /**
     * 查询项目详情
     * @param id
     * @return Project
     */
    AuctionProject queryProjectDetail(Integer id);

  
    
}
