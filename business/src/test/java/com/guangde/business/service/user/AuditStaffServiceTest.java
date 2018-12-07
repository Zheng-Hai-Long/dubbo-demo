package com.guangde.business.service.user;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.guangde.business.BaseTest;
import com.guangde.business.entry.AuditStaff;

public class AuditStaffServiceTest extends BaseTest
{
    @Autowired
    private IAuditStaffService auditStaffService;
    
    @Test
    public void TestQuery()
    {
        AuditStaff auditStaff = new AuditStaff();
        auditStaff.setUserId(11);
        // auditStaff.setPersonType("loveGroupMent");
        if (auditStaff != null)
        {
            try
            {
                // auditStaff = null;
                List<AuditStaff> list = auditStaffService.queryAuditStaffByParam(auditStaff);
                if (list != null)
                {
                    System.out.println(list.get(0).getReviewContent());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }
}
