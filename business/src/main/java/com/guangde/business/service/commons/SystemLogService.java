package com.guangde.business.service.commons;

import com.guangde.business.entry.SystemLog;
import com.guangde.business.exception.BaseException;

public interface SystemLogService
{
    void save(SystemLog systemLog) throws BaseException;
}
