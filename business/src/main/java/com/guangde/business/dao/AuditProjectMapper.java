package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.AuditProject;

public interface AuditProjectMapper extends BaseMapper<AuditProject>{
	/**
	 * 根据param查询，返回单个对象
	 * @param param
	 * @return
	 */
	AuditProject selectByParam(AuditProject param);
	
	/**
	 * 查询总数
	 * @param param
	 * @return
	 */
	int selectProjectCount(AuditProject param);
	
	List<AuditProject> selectAuditProjectList(AuditProject param) throws Exception;
}
