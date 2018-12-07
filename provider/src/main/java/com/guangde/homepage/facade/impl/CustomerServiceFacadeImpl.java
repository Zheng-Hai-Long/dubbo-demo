package com.guangde.homepage.facade.impl;

import com.github.pagehelper.Page;
import com.guangde.api.homepage.ICustomerServiceFacade;
import com.guangde.business.entry.CustomerService;
import com.guangde.business.entry.ServiceLeaveWord;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.CustomerServiceService;
import com.guangde.business.service.ServiceLeaveWordService;
import com.guangde.business.util.DateUtil;
import com.guangde.entry.ApiCustomerService;
import com.guangde.entry.ApiServiceLeaveWord;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;
import com.sun.istack.internal.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("customerServiceFacade")
public class CustomerServiceFacadeImpl implements ICustomerServiceFacade{
	
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private CustomerServiceService customerServiceService;
	@Autowired
	private ServiceLeaveWordService serviceLeaveWordService;

	@Override
	public ApiResult saveCustomerService(ApiCustomerService apiCustomerService) {
		
		logger.info("receive save apiCustomerService param : " + apiCustomerService);
		
		CustomerService customerService = BeanUtil.copy(apiCustomerService, CustomerService.class);
		
		if(!valParam(customerService)){
			return ApiResultUtil.getParameterError();
		}
		try {
			customerServiceService.saveCustomerService(customerService);
			ApiResult apiResult = ApiResultUtil.SUCCESS;
			//apiResult.setMessage(customerService.getId()+"");
			return apiResult;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	private boolean valParam(CustomerService customerService) {
		 boolean ret = true;
		 if(customerService == null){
			 logger.info("param is null !");
			 ret = false;
		 }else if(customerService.getState() != null && customerService.getState() ==300){
			if (StringUtils.isEmpty(customerService.getContent())) {
				logger.info("content id null");
				ret = false;
			}
		}
		return ret;
	}

	@Override
	public ApiPage<ApiCustomerService> queryCustomerServiceList(
			ApiCustomerService apiCustomerService, int pageNum, int pageSize) {
		
		logger.info("receive query param:" + apiCustomerService);
		
		CustomerService cs = BeanUtil.copy(apiCustomerService, CustomerService.class);
		
		if( cs == null){
			new ApiPage<CustomerService>(pageNum,pageSize);
		}
		
		List<CustomerService> list = customerServiceService.queryCustomerServiceList(cs, pageNum, pageSize);
		ApiPage<ApiCustomerService> ret = BeanUtil.copyPage((Page<CustomerService>)list, ApiCustomerService.class);
		return ret;
	}

	@Override
	public ApiCustomerService queryById(Integer id) {
		if(null == id){
			return null;
		}
		CustomerService cs = customerServiceService.queryById(id);
		ApiCustomerService acs = BeanUtil.copy(cs, ApiCustomerService.class);
		return acs;
	}
	
	@Override
	public ApiResult saveServiceLeaveWord(ApiServiceLeaveWord apiServiceLeaveWord) {
		logger.info("receive save apiServiceLeaveWord param"+apiServiceLeaveWord);
		ServiceLeaveWord serviceLeaveWord = BeanUtil.copy(apiServiceLeaveWord, ServiceLeaveWord.class);
		if(!valParamS(serviceLeaveWord)){
			return ApiResultUtil.getParameterError();
		}
		try {
			serviceLeaveWordService.saveServiceLeaveWord(serviceLeaveWord);
			ApiResult apiResult = ApiResultUtil.SUCCESS;
			//apiResult.setMessage(serviceLeaveWord.getId()+"");
			return apiResult;
		} catch (BaseException e) {
			
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	private boolean valParamS(ServiceLeaveWord serviceLeaveWord) {
		boolean ret = true;
		if(serviceLeaveWord == null){
			logger.info("param is null !");
			ret = false;
		}else {
			if(serviceLeaveWord.getServiceId() == null){
				logger.info("serviceId is null !");
				ret = false;
			}
		}
		return ret;
	}

	@Override
	public ApiPage<ApiServiceLeaveWord> queryServiceLeaveWordlist(
			ApiServiceLeaveWord apiServiceLeaveWord, int pageNum, int pageSize) {
		logger.info("receive query param:"+apiServiceLeaveWord);
		
		ServiceLeaveWord slw = BeanUtil.copy(apiServiceLeaveWord, ServiceLeaveWord.class);
		if( slw == null){
			new ApiPage<ServiceLeaveWord>(pageNum,pageSize);
		}
		
		List<ServiceLeaveWord> list = serviceLeaveWordService.queryServiceLeaveWordlist(slw, pageNum, pageSize);
		ApiPage<ApiServiceLeaveWord> ret = BeanUtil.copyPage((Page<ServiceLeaveWord>)list, ApiServiceLeaveWord.class);
		
		return ret;
	}

	@Override
	public ApiResult updateCustomerService(ApiCustomerService apiCustomerService) {
		logger.info("update param is "+apiCustomerService);
		CustomerService cService = BeanUtil.copy(apiCustomerService, CustomerService.class);
		if(!valUpdParam(cService)){
			return ApiResultUtil.getParameterError();
		}else {
			if(cService.getLastUpdateTime() == null){
				cService.setLastUpdateTime(DateUtil.getDate());
			}
		}
		try {
			customerServiceService.updateCustomerService(cService);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			 
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	private boolean valUpdParam(CustomerService cService) {
		boolean ret = true;
		if(cService == null){
			logger.info("param is null");
			ret = false;
		}
		return ret;
	}

}
