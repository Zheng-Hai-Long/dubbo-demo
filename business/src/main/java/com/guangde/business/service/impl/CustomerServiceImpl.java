package com.guangde.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.CustomerServiceMapper;
import com.guangde.business.entry.CustomerService;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.CustomerServiceService;
import com.guangde.business.util.ConfigLoader;
import com.guangde.business.util.DateUtil;
import com.guangde.business.util.ResultCode;
import com.guangde.business.util.UserUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("customerService")
public class CustomerServiceImpl extends BaseService implements CustomerServiceService{

	@Autowired
	private CustomerServiceMapper customerServiceMapper;
	
	@Override
	public void saveCustomerService(CustomerService customerService) 
			throws BaseException{
		
		try {
			customerService.setState(300);//初始入库状态:未回复
			customerService.setCreateTime(DateUtil.getDate());
			customerService.setLastUpdateTime(DateUtil.getDate());
			customerService.setIsHide(0);
			customerServiceMapper.save(customerService);
		} catch (Exception e) {
			logger.error("save database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}
	
	@Override
	public List<CustomerService> queryCustomerServiceList(
			CustomerService customerService, int pageNum, int pageSize) {
		logger.info("query param is " + customerService);
		PageHelper.startPage(pageNum, pageSize);
		List<CustomerService> list = null;
		try {
			
			list = customerServiceMapper.queryByParam(customerService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		    
		return list;
	}

	@Override
	public CustomerService queryById(Integer id) {
		logger.info("query param is "+ id);
		if(id == null){
			return null;
		}
		CustomerService customerService = customerServiceMapper.queryById(id);
		String safeName = "";
		if(customerService.getUserNickName() != null){
			safeName = UserUtil.getSafeName(customerService.getUserNickName());
			customerService.setUserNickName(safeName);
		}
		if(StringUtils.isNotEmpty(customerService.getHeadUrl())){
			if(!customerService.getHeadUrl().contains("http://")){
				String resUrl = ConfigLoader.getResPictrueURL();
				customerService.setHeadUrl(resUrl+customerService.getHeadUrl());
			}
		}
		return customerService;
	}

	@Override
	public int updateCustomerService(CustomerService customerService) {
		customerService.setLastUpdateTime(DateUtil.getDate());
		return customerServiceMapper.update(customerService);
	}
}
