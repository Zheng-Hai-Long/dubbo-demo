package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.CustomerService;
import com.guangde.business.exception.BaseException;

public interface CustomerServiceService{
	/**
	 * 保存咨询、建议、投诉
	 * @param customerService
	 * @throws BaseException
	 */
	void saveCustomerService(CustomerService customerService ) throws BaseException;
	/**
	 * 查询咨询、建议、投诉列表
	 * @param customerService
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<CustomerService> queryCustomerServiceList(CustomerService customerService,int pageNum,int pageSize);
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	CustomerService queryById(Integer id);
	/**
	 * 更新信息
	 * @param customerService
	 * @return
	 */
	int updateCustomerService(CustomerService customerService) throws BaseException;
	

}
