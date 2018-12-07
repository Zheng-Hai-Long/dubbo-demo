package com.guangde.business.service.user;

import java.util.List;

import com.guangde.business.entry.AuditStaff;
import com.guangde.business.exception.BaseException;

public interface IAuditStaffService
{
    /**
     * 根据关键字查找 审核信息
     * @param auditStaff
     * @return
     */
    List<AuditStaff> queryAuditStaffByParam(AuditStaff auditStaff);
    
    /**
     * 保存审核信息
     * @param auditStaff
     * @throws BaseException
     */
    int save(AuditStaff auditStaff);
    
    /**
     * 更新审核信息
     * @param auditStaff
     * @throws BaseException
     */
    void updateAuditStaff(AuditStaff auditStaff);
}
