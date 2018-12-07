package com.guangde.api.homepage;

import com.guangde.entry.ApiCustomerService;
import com.guangde.entry.ApiServiceLeaveWord;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

public interface ICustomerServiceFacade {
	/**
	 * 保存咨询、建议、投诉
	 * @param apiCustomerService
	 * @return
	 */
	ApiResult saveCustomerService(ApiCustomerService apiCustomerService);
	/**
	 * 查询咨询、建议、投诉列表
	 * @param apiCustomerService
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ApiPage<ApiCustomerService> queryCustomerServiceList(ApiCustomerService apiCustomerService,int pageNum,int pageSize);
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	ApiCustomerService queryById(Integer id);
	/**
	 * 保存客服中心回复留言
	 * @param serviceLeaveWord
	 */
	ApiResult saveServiceLeaveWord(ApiServiceLeaveWord serviceLeaveWord);
	/**
	 * 查询客服中心回复留言列表
	 * @param serviceLeaveWord
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	ApiPage<ApiServiceLeaveWord> queryServiceLeaveWordlist(ApiServiceLeaveWord serviceLeaveWord,int pageNum,int pageSize);
	/**
	 * 更新咨询、建议、投诉
	 * @param apiCustomerService
	 * @return
	 */
	ApiResult updateCustomerService(ApiCustomerService apiCustomerService);


}
