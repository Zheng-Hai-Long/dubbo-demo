package com.guangde.business.dao;

import com.guangde.business.entry.DonateRecord;
import com.guangde.business.entry.FrontUser_invoice;


public interface FrontUserInvoiceMapper extends BaseMapper<FrontUser_invoice>{
	Integer countQueryByParam(FrontUser_invoice invoice);
	Double sumDonatAmount(DonateRecord donateRecord);
}
