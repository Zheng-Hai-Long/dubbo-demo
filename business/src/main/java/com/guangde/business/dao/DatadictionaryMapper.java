package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.Datadictionary;

public interface DatadictionaryMapper {
	Datadictionary queryByCode(String code);
	List<Datadictionary>  queryByTypeCode(String typeCode);
}
