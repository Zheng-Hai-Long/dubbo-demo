package com.guangde.business.service.user;

import com.guangde.business.entry.FrontUser_address;
import com.guangde.business.exception.BaseException;

import java.util.List;



public interface IUserAddressService
{
	/**
	 * 用户地址入库
	 * @param userAddress
	 * @return
	 * @throws BaseException 
	 */
	int saveUserAddress(FrontUser_address userAddress) throws BaseException;

	/**
	 * 用户地址更新
	 * @param userAddress
	 * @throws BaseException 
	 */
	void updateUserAddress(FrontUser_address userAddress) throws BaseException;

	/**
	 * 删除地址
	 * @param ids
	 * @throws BaseException
	 */
	void deleteUserAddress(List<String> ids,Integer userId) throws BaseException;

	/**
	 * 默认地址
	 * @param id
	 * @throws BaseException
	 */
	void defaultUserAddress(Integer id,Integer userId) throws BaseException;

	/**
	 * 根据Id查找用户地址
	 * @param id
	 * @return
	 */
	FrontUser_address queryUserAddress(Integer id, Integer userId);

	/**
	 * 条件查找用户地址列表
	 * @param userAddress
	 * @return
	 */
	List<FrontUser_address> queryByParam(FrontUser_address userAddress, int pageNum, int pageSize);

	int countByUserId(Integer userId);
    
}
