package com.guangde.business.service;

import java.util.List;

import com.guangde.business.entry.DonateTime;
import com.guangde.business.exception.BaseException;

public interface DonateTimeService {
	/**
	 * 日捐月捐入库
	 * @param dt
	 * @return
	 * @throws BaseException
	 */
	public void saveDonateTime(DonateTime dt) throws BaseException;
	/**
	 * 日捐月捐入库(message返回入库id)
	 * @param dt
	 * @return
	 * @throws BaseException
	 */
	public int saveDonateTimeReturnId(DonateTime dt) throws BaseException;
	/**
	 * 通过userId查询月捐计划
	 * @param userId
	 * @return
	 */
	public List<DonateTime> queryByUserId(Integer userId,int pageNum, int pageSize);
	/**
	 * 通过userId查询月捐计划
	 * @param userId
	 * @return
	 */
	public List<DonateTime> queryDonateTimeByParam(DonateTime param,int pageNum, int pageSize);
	/**
	 * 更新日捐月捐
	 * @param dt
	 * @throws BaseException
	 */
	public void updateDonateTime(DonateTime dt) throws BaseException;

}
