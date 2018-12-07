package com.guangde.user.facade.impl;

import com.github.pagehelper.Page;
import com.guangde.api.user.IUserRelationInfoFacade;
import com.guangde.business.entry.*;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.user.*;
import com.guangde.entry.*;
import com.guangde.enums.ResultEnum;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.ApiResultUtil;
import com.guangde.util.BeanUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("userRelationInfoFacade")
public class UserRelationInfoFacadeImpl implements IUserRelationInfoFacade
{
    private static final Logger logger = LoggerFactory.getLogger(UserRelationInfoFacadeImpl.class);
    
    @Autowired
    public IUserAddressService userAddressService;
    
    @Autowired
    public IUserInvoiceService userInvoiceService;
    

	@Override
	public ApiResult queryUserAddress(Integer id, Integer userId) {
		logger.info("queryById id = {}，userId = {}", id, userId);
		FrontUser_address userAddress = userAddressService.queryUserAddress(id, userId);
		ApiFrontUser_address apiUserAddress = BeanUtil.copy(userAddress, ApiFrontUser_address.class);
		if(apiUserAddress != null){
			return new ApiResult(ResultEnum.Success, apiUserAddress);
		}
		return new ApiResult(ResultEnum.EmptyData);
	}

	@Override
	public ApiFrontUser_address queryDefaultAddress(Integer userId) {

		logger.info("queryDefaultAddress userId = " + userId);
		FrontUser_address param = new FrontUser_address();
		param.setIsSelected(1);
		param.setUserId(userId);
		List<FrontUser_address> frontUser_addresses = userAddressService.queryByParam(param, 1, 1);
		if(CollectionUtils.isEmpty(frontUser_addresses)){
			param.setIsSelected(null);
			frontUser_addresses = userAddressService.queryByParam(param, 1, 1);
			if(!CollectionUtils.isEmpty(frontUser_addresses)){
				FrontUser_address address = frontUser_addresses.get(0);
				ApiFrontUser_address address1 = new ApiFrontUser_address();
				BeanUtils.copyProperties(address, address1);
				return address1;
			}
		}else{
			FrontUser_address address = frontUser_addresses.get(0);
			ApiFrontUser_address address1 = new ApiFrontUser_address();
			BeanUtils.copyProperties(address, address1);
			return address1;
		}
		return null;
	}

	@Override
	public ApiFrontUser_invoice queryInvoiceById(Integer id) {
		
		FrontUser_invoice userInvoice = userInvoiceService.queryById(id);
		
		ApiFrontUser_invoice apiUserInvoice  = BeanUtil.copy(userInvoice, ApiFrontUser_invoice.class);
		
		return apiUserInvoice;
	}

	@Override
	public ApiResult saveUserAddress(ApiFrontUser_address userAddress) {
		logger.info("saveUserAddress ApiFrontUser_address = " + userAddress);
		if(userAddress ==  null)
		{
			logger.info("ApiFrontUser_address is null");
			return ApiResultUtil.getParameterError();
		}
		else if(userAddress.getUserId() == null){
			logger.info("userId is null");
			return ApiResultUtil.getParameterError();
		}
		FrontUser_address uAddress = BeanUtil.copy(userAddress, FrontUser_address.class);
		try {
			int flag = userAddressService.saveUserAddress(uAddress);
			return new ApiResult(ResultEnum.Success, flag);
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	@Override
	public ApiResult saveUserInvoice(ApiFrontUser_invoice userInvoice) {
		if(userInvoice ==  null)
		{
			logger.info("ApiFrontUser_invoice is null");
			return ApiResultUtil.getParameterError();
		}
		else if(userInvoice.getUserId() == null){
			logger.info("userId is null");
			return ApiResultUtil.getParameterError();
		}
		FrontUser_invoice uInvoice = BeanUtil.copy(userInvoice, FrontUser_invoice.class);
		try {
			userInvoiceService.saveUserInvoice(uInvoice);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	@Override
	public ApiResult updateUserAddress(ApiFrontUser_address userAddress) {
		logger.info("updateUserAddress ApiFrontUser_address = " + userAddress);
		if(userAddress ==  null)
		{
			logger.info("ApiFrontUser_address is null");
			return ApiResultUtil.getParameterError();
		}
		if(userAddress.getId() == null){
			logger.info("addresId is null");
			return ApiResultUtil.getParameterError();
		}
		if(userAddress.getUserId() == null){
			logger.info("userId is null");
			return ApiResultUtil.getParameterError();
		}

		FrontUser_address uAddress = BeanUtil.copy(userAddress, FrontUser_address.class);
		try {
			userAddressService.updateUserAddress(uAddress);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	
	}

	@Override
	public ApiResult updateUserInvoice(ApiFrontUser_invoice userInvoice) {
		if(userInvoice ==  null)
		{
			logger.info("ApiFrontUser_invoice is null");
			return ApiResultUtil.getParameterError();
		}
		else if(userInvoice.getId() == null){
			logger.info("userId is null");
			return ApiResultUtil.getParameterError();
		}
		FrontUser_invoice uInvoice = BeanUtil.copy(userInvoice, FrontUser_invoice.class);
		try {
			userInvoiceService.updateUserInvoice(uInvoice);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

    @Override
    public ApiPage<ApiFrontUser_invoice> queryInvoiceByCodition(ApiFrontUser_invoice apiInvoice, Integer pageNum, Integer pageSize)
    {
    	FrontUser_invoice userInvoice = BeanUtil.copy(apiInvoice, FrontUser_invoice.class);
        
        if (userInvoice == null)
        {
            return new ApiPage<ApiFrontUser_invoice>(pageNum, pageSize);
        }
        
        List<FrontUser_invoice> list = userInvoiceService.queryUserInvoiceParam(userInvoice, pageNum, pageSize);
        
        ApiPage<ApiFrontUser_invoice> apiPage = BeanUtil.copyPage((Page<FrontUser_invoice>)list, ApiFrontUser_invoice.class);
        
        return apiPage;
    }



	@Override
	public Integer countQueryInvoiceByCodition(ApiFrontUser_invoice apiInvoice) {
		
		FrontUser_invoice userInvoice = BeanUtil.copy(apiInvoice, FrontUser_invoice.class);
		   if(userInvoice == null)
		   {
			   return  0 ;
		   }
		return userInvoiceService.countQueryUserInvoiceParam(userInvoice);
	}


	@Override
	public ApiResult deleteUserAddress(List<String> ids,Integer userId) {
		logger.info("deleteUserAddress ids = " + ids + "，userId = " + userId);
		try {
			userAddressService.deleteUserAddress(ids,userId);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	@Override
	public ApiResult defaultUserAddress(Integer id, Integer userId) {
		logger.info("deleteUserAddress id = " + id + "，userId = " + userId);
		try {
			userAddressService.defaultUserAddress(id, userId);
			return ApiResultUtil.SUCCESS;
		} catch (BaseException e) {
			return ApiResultUtil.getApiResult(e.getResultCode());
		}
	}

	@Override
	public ApiResult queryUserAddressPageByUserId(Integer userId, int pageNum, int pageSize) {
		logger.info("queryUserAddressPageByUserId userId = " + userId);

		FrontUser_address param = new FrontUser_address();
		param.setUserId(userId);
		List<FrontUser_address> list = userAddressService.queryByParam(param, pageNum, pageSize);

		ApiPage<ApiFrontUser_address> apiPage = BeanUtil.copyPage((Page<FrontUser_address>)list, ApiFrontUser_address.class);
		if(apiPage != null && apiPage.valPage(apiPage, pageNum)){
			return new ApiResult(ResultEnum.Success, apiPage);
		}

		if(pageNum == 1){
			return new ApiResult(ResultEnum.EmptyData);
		}
		return new ApiResult(ResultEnum.NotMoreData);
	}
}
