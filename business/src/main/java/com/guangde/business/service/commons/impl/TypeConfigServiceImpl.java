package com.guangde.business.service.commons.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guangde.business.dao.ProjectTagMapper;
import com.guangde.business.dao.TypeConfigMapper;
import com.guangde.business.entry.ProjectTag;
import com.guangde.business.entry.TypeConfig;
import com.guangde.business.service.commons.TypeConfigService;

@Service("typeConfigService")
public class TypeConfigServiceImpl implements TypeConfigService
{
    @Autowired
    private TypeConfigMapper typeConfigMapper;
    @Autowired
    private ProjectTagMapper projectTagMapper;
    @Override
    public List<TypeConfig> queryList()
    {
        return typeConfigMapper.queryByParam(null);
    	
    }
    
    @Override
    public TypeConfig queryTypeConfig(TypeConfig typeConfig)
    {
        TypeConfig tc = typeConfigMapper.queryTypeConfig(typeConfig);
        
        return tc;
    }

	@Override
	public List<String> queryTypeConfigAndProjectTag(Integer id) {
		List<String> list = new ArrayList<String>();
		TypeConfig typeConfig = typeConfigMapper.queryById(id);
		if(typeConfig == null){
			return list = null;
		}
		String tagIds = typeConfig.getTagName();
		if(tagIds != null && !"".equals(tagIds)){
			String[] tags = tagIds.split(",");
			for(int j=0;j<tags.length;j++){
				Integer tag = Integer.valueOf(tags[j]);
				ProjectTag projectTag = projectTagMapper.queryById(tag);
				list.add(projectTag.getName());
    		}
		}
		return list;
	}
    
}
