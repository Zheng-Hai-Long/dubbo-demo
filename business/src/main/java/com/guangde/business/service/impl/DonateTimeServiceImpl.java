package com.guangde.business.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.DonateTimeMapper;
import com.guangde.business.dao.FrontUserMapper;
import com.guangde.business.entry.DonateTime;
import com.guangde.business.entry.FrontUser;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.DonateTimeService;
import com.guangde.business.util.DateUtil;
import com.guangde.business.util.ResultCode;
@Service("donateTimeService")
public class DonateTimeServiceImpl extends BaseService implements DonateTimeService{
	
	@Autowired
	private DonateTimeMapper donateTimeMapper;
	@Autowired
	private FrontUserMapper frontUserMapper;

	@Override
	public void saveDonateTime(DonateTime dt) throws BaseException {
		
		try {
			dt.setCreatetime(DateUtil.getDate());
			dt.setNumber(0);
			//dt.setState(201);
			int flag = donateTimeMapper.save(dt);
			if (flag > 0 && StringUtils.isNotEmpty(dt.getMobileNum())) {
				FrontUser user = new FrontUser();
				user.setId(dt.getUserId());
				user.setMobileNum(dt.getMobileNum());
				frontUserMapper.update(user);
			}
			
		} catch (Exception e) {
			logger.error("save database error", e);
			throw new BaseException(ResultCode.Error);
		}
		
	}
	
	@Override
	public int saveDonateTimeReturnId(DonateTime dt) throws BaseException {
		
		try {
			dt.setCreatetime(DateUtil.getDate());
			dt.setNumber(0);
			//dt.setState(201);
			int flag = donateTimeMapper.save(dt);
			if (flag > 0 && StringUtils.isNotEmpty(dt.getMobileNum())) {
				FrontUser user = new FrontUser();
				user.setId(dt.getUserId());
				user.setMobileNum(dt.getMobileNum());
				frontUserMapper.update(user);
			}
			return dt.getId();
			
		} catch (Exception e) {
			logger.error("save database error", e);
			throw new BaseException(ResultCode.Error);
		}
		
	}

	@Override
	public List<DonateTime> queryByUserId(Integer userId,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return donateTimeMapper.queryByUserId(userId);
	}
	
	@Override
	public List<DonateTime> queryDonateTimeByParam(DonateTime param,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return donateTimeMapper.queryDonateTimeByParam(param);
	}

	@Override
	public void updateDonateTime(DonateTime dt) throws BaseException {
		
		try {
			donateTimeMapper.update(dt);
		} catch (Exception e) {
			logger.error("update database error",e);
			throw new BaseException(ResultCode.Error);
		}
		 
		
	}

	

}
