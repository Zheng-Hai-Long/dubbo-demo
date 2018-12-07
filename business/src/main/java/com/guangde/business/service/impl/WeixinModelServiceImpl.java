package com.guangde.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guangde.business.dao.WeixinModelMapper;
import com.guangde.business.entry.WeixinModel;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.WeixinModelService;
import com.guangde.business.util.ResultCode;


@Service("weixinModelService")
public class WeixinModelServiceImpl extends BaseService implements WeixinModelService
{
	@Autowired
	private WeixinModelMapper weixinModelMapper;
	
	@Override
	public void save(WeixinModel param) throws BaseException {
		try
        {
			weixinModelMapper.save(param);
        }
        catch (Exception e)
        {
            logger.error("update WeixinModel error", e);
            throw new BaseException(ResultCode.Error);
        }
	}
}
