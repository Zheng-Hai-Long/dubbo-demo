package com.guangde.business.service;

import com.guangde.business.entry.WeixinModel;
import com.guangde.business.exception.BaseException;


public interface WeixinModelService
{
	void save(WeixinModel param) throws BaseException;
}
