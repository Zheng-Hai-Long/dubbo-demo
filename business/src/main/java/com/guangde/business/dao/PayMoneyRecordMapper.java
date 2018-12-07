package com.guangde.business.dao;

import com.guangde.business.entry.PayMoneyRecord;

import java.util.List;

public interface PayMoneyRecordMapper extends BaseMapper<PayMoneyRecord>
{
	 public List<PayMoneyRecord> queryFollowPayMoneyList(PayMoneyRecord payMoneyRecord);
	 public List<PayMoneyRecord> queryPayMoneyListGo(PayMoneyRecord payMoneyRecord);

	public PayMoneyRecord sumPayMoney();
}
