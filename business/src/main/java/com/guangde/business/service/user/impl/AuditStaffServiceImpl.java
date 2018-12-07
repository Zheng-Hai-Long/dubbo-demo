package com.guangde.business.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guangde.business.dao.AuditStaffMapper;
import com.guangde.business.entry.AuditStaff;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.user.IAuditStaffService;

@Service("auditStaffService")
public class AuditStaffServiceImpl extends BaseService implements IAuditStaffService

{
    @Autowired
    private AuditStaffMapper auditStaffMapper;
    
    @Override
    public List<AuditStaff> queryAuditStaffByParam(AuditStaff auditStaff)
    {
        logger.info("queryAuditStaffByParam param: " + auditStaff);
        
        return auditStaffMapper.queryAuditStaffByParam(auditStaff);
    }

	@Override
	public int save(AuditStaff auditStaff) {
		return auditStaffMapper.save(auditStaff);
		
	}

	@Override
	public void updateAuditStaff(AuditStaff auditStaff) {
		auditStaffMapper.updateAuditStaff(auditStaff);
	}
    
}
