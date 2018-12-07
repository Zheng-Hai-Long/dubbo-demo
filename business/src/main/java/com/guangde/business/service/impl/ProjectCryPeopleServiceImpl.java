package com.guangde.business.service.impl;

import com.guangde.business.dao.FrontUserMapper;
import com.guangde.business.dao.ProjectCryPeopleMapper;
import com.guangde.business.entry.FrontUser;
import com.guangde.business.entry.ProjectCryPeople;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ProjectCryPeopleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("projectCryPerpleService")
public class ProjectCryPeopleServiceImpl extends BaseService implements ProjectCryPeopleService
{
	@Autowired
	private ProjectCryPeopleMapper projectCryPeopleMapper;

	@Autowired
	private FrontUserMapper frontUserMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void save(ProjectCryPeople crypeople) throws BaseException {

		FrontUser user = frontUserMapper.queryById(crypeople.getUserId());
		if(user != null){
			FrontUser user2 = new FrontUser();
			user2.setId(crypeople.getUserId());
			if(StringUtils.isBlank(user.getMobileNum())){
				user2.setMobileNum(crypeople.getMobile());
			}
			if(StringUtils.isBlank(user.getRealName())){
				user2.setRealName(crypeople.getRealName());
			}
			if(StringUtils.isBlank(user.getSex())){
				user2.setSex(crypeople.getSex()+"");
			}
			if(StringUtils.isBlank(user.getMobileNum()) || StringUtils.isBlank(user.getRealName()) || StringUtils.isBlank(user.getSex())){
				frontUserMapper.update(user2);
			}
		}
		crypeople.setState(0);
		crypeople.setCreateTime(new Date());
		crypeople.setUpdateTime(new Date());
		projectCryPeopleMapper.save(crypeople);
	}

}
