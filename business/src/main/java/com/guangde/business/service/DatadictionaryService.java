package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.Datadictionary;

public interface DatadictionaryService {
	Datadictionary queryByCode(String code);
	List<Datadictionary>  queryByTypeCode(String typeCode);
}
