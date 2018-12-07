package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.ProjectTag;

public interface ProjectTagService {

	/**
	 * 根据id查询标签信息
	 * @param id 
	 * @return
	 */
	ProjectTag queryProjectTagById(Integer id);
	
	List<ProjectTag> queryProjectTagByParam(ProjectTag projectTag);
}
