package com.guangde.api.user;

import com.guangde.entry.ApiFrontUser_address;
import com.guangde.entry.ApiFrontUser_invoice;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

import java.util.List;

public interface IUserRelationInfoFacade
{
	/**
	 * 用户地址入库
	 * @param userAddress
	 * @return
	 */
	ApiResult saveUserAddress(ApiFrontUser_address userAddress);
    
	/**
	 * 用户地址更新
	 * @param userAddress
	 * @return
	 */
	ApiResult updateUserAddress(ApiFrontUser_address userAddress);
    
    /**
     * 删除用户地址，支持多条
     */
	ApiResult deleteUserAddress(List<String> ids, Integer userId);

	/**
	 * 默认用户地址
	 * @param id
	 * @param userId
     * @return
     */
	ApiResult defaultUserAddress(Integer id, Integer userId);

	/**
	 * 查询用户地址分页
	 * @param userId
	 * @param pageNum
	 * @param pageSize
     * @return
     */
	ApiResult queryUserAddressPageByUserId(Integer userId, int pageNum, int pageSize);
    
    /**
     * 根据id查找用户地址
     * @param id
     * @return
     */
	ApiResult queryUserAddress(Integer id, Integer userId);

	ApiFrontUser_address queryDefaultAddress(Integer userId);
    
    
	/**
	 * 用户发票信息入库
	 * @return
	 */
    public ApiResult saveUserInvoice(ApiFrontUser_invoice userInvoice);
    
	/**
	 * 用户发票信息更新
	 * @return
	 */
    public ApiResult updateUserInvoice(ApiFrontUser_invoice userInvoice);
    
    /**
     * 根据id查找用户发票信息
     * @param id
     * @return
     */
    public ApiFrontUser_invoice queryInvoiceById(Integer id);
    
    /**
     * 根据条件查询用户发票信息
     * @param apiInvoice
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ApiFrontUser_invoice> queryInvoiceByCodition(ApiFrontUser_invoice apiInvoice, Integer pageNum, Integer pageSize);
    
    /**
     * 条件统计用户发票信息数量
     * @param apiInvoice
     * @return
     */
    Integer countQueryInvoiceByCodition(ApiFrontUser_invoice apiInvoice);

    
}
