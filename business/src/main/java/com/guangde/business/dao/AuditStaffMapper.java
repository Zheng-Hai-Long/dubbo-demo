package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.AuditStaff;

public interface AuditStaffMapper
{
    
    int save(AuditStaff auditStaff);
    
    void updateAuditStaff(AuditStaff auditStaff);
    
    /**
     * 根据关键字查询审核信息
     * @param auditStaff
     * @return
     */
    List<AuditStaff> queryAuditStaffByParam(AuditStaff auditStaff);
}
