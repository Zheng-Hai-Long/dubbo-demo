package com.guangde.business.service.user;

import java.util.List;

import com.guangde.business.entry.FrontUser_invoice;
import com.guangde.business.exception.BaseException;



public interface IUserInvoiceService
{
	/**
	 * 发票信息入库
	 * @param userInvoice
	 * @throws BaseException
	 */
	public void saveUserInvoice(FrontUser_invoice userInvoice) throws BaseException;
	/**
	 * 修改发票信息
	 * @param userInvoice
	 * @throws BaseException
	 */
	public void updateUserInvoice(FrontUser_invoice userInvoice) throws BaseException;
	/**
	 * 根据id查找发票信息
	 * @param id
	 * @return
	 */
	public FrontUser_invoice queryById(Integer id);
	/**
	 * 条件查找用户发票信息列表
	 * @param userInvoice
	 * @return
	 */
	public List<FrontUser_invoice> queryUserInvoiceParam(FrontUser_invoice userInvoice,Integer pageNum,Integer pageSize);
	
	/**
	 * 条件统计用户发票信息数量
	 * @return
	 */
	public Integer countQueryUserInvoiceParam(FrontUser_invoice userInvoice);
	
	public FrontUser_invoice queryByInvoiceIndex(Integer userId);
	
	void saveInvoice(Integer userId, String invoiceList, Integer addressId, String invoiceHead) throws BaseException;
    
}
