package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.ServiceLeaveWord;
import com.guangde.business.exception.BaseException;

public interface ServiceLeaveWordService {
	/**
	 * 保存客服中心回复留言
	 * @param serviceLeaveWord
	 */
	void saveServiceLeaveWord(ServiceLeaveWord serviceLeaveWord) throws BaseException;
	/**
	 * 查询客服中心回复留言列表
	 * @param serviceLeaveWord
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<ServiceLeaveWord> queryServiceLeaveWordlist(ServiceLeaveWord serviceLeaveWord,int pageNum,int pageSize);

}
