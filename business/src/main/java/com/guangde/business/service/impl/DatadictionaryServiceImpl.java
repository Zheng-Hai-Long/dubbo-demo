package com.guangde.business.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guangde.business.dao.DatadictionaryMapper;
import com.guangde.business.entry.Datadictionary;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.DatadictionaryService;

@Service("datadictionaryService")
public class DatadictionaryServiceImpl extends BaseService implements DatadictionaryService{
	
	@Autowired
	private DatadictionaryMapper datadictionaryMapper;

	@Override
	public Datadictionary queryByCode(String code) {
		if(StringUtils.isEmpty(code))
		{
			return null;
		}
		
		return datadictionaryMapper.queryByCode(code);
	}

	@Override
	public List<Datadictionary> queryByTypeCode(String typeCode) {
		if(StringUtils.isEmpty(typeCode))
		{
			return null;
		}
		
		return datadictionaryMapper.queryByTypeCode(typeCode);
	}

}
