package com.guangde.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.AuditProjectMapper;
import com.guangde.business.dao.BFileMapper;
import com.guangde.business.entry.AuditProject;
import com.guangde.business.entry.BFile;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.AuditProjectService;
import com.guangde.business.service.BaseService;
import com.guangde.business.util.ResultCode;

@Service("auditProjectService")
public class AuditProjectServiceImpl extends BaseService implements AuditProjectService {
	@Autowired
	private AuditProjectMapper auditProjectMapper;
	
	@Autowired
	private  BFileMapper bFileMapper;
	@Override
	public AuditProject selectByParam(AuditProject param)throws Exception{
		
		return auditProjectMapper.selectByParam(param);
	}

	@Override
	public int selectProjectCount(AuditProject param) throws Exception {
		
		return auditProjectMapper.selectProjectCount(param);
	}
	
	@Override
	public void save(AuditProject param) throws BaseException{
		try {
			auditProjectMapper.save(param);
		} catch (Exception e) {
			logger.error(e);
            throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public List<AuditProject> selectAuditProjectList(AuditProject param,Integer pageNum,Integer pageSize)
			throws Exception {
		PageHelper.startPage(pageNum, pageSize);
		List<AuditProject>  list=auditProjectMapper.selectAuditProjectList(param);
		for (AuditProject auditProject:list) {
			if(auditProject.getCoverImageId()!=null){
				 BFile bFile=bFileMapper.queryById(auditProject.getCoverImageId());
				 auditProject.setHeadUrl(bFile.getUrl());
				}
		}
		return list;
	}

	
}
