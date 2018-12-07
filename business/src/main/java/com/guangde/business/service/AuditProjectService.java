package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.AuditProject;
import com.guangde.business.exception.BaseException;


public interface AuditProjectService
{
    /**
     * 根据param查询用户证实信息
     * @param param
     * @return
     */
    AuditProject selectByParam(AuditProject param)throws Exception;
    
    /**
     * 查询总数
     * @param param
     * @return
     * @throws Exception
     */
   int  selectProjectCount(AuditProject param) throws Exception;
   
   /**
    * 添加用户证实信息
    * @param param
    * @throws Exception
    */
   void save(AuditProject param) throws BaseException;
   /**
    * 返回证实人列表
    * @param param
    * @return
    * @throws Exception
    */
   List<AuditProject> selectAuditProjectList(AuditProject param,Integer pageNum,Integer pageSize) throws Exception;
}
