package com.guangde.business.dao;

import java.util.List;

import com.guangde.business.entry.DonateTime;

public interface DonateTimeMapper extends BaseMapper<DonateTime>{

	List<DonateTime> queryByUserId(Integer userId);
	List<DonateTime> queryDonateTimeByParam(DonateTime param);
}
