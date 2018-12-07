package com.guangde.business.service.impl;

import java.util.List;

import com.guangde.business.dao.*;
import com.guangde.business.entry.*;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ProjectTagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("projectTagService")
public class ProjectTagServiceImpl extends BaseService implements ProjectTagService{

	@Autowired
	private ProjectTagMapper projectTagMapper;

	@Override
	public ProjectTag queryProjectTagById(Integer id) {
		return projectTagMapper.queryById(id);
	}

	@Override
	public List<ProjectTag> queryProjectTagByParam(ProjectTag projectTag) {
		return projectTagMapper.queryByParam(projectTag);
	}
	
	
}
