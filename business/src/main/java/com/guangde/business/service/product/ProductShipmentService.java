package com.guangde.business.service.product;

import com.guangde.business.pojo.Result;

/**
 * Created by ZHL on 2018/11/29.
 */
public interface ProductShipmentService{
	
	Result shipmentSure(Integer userId,Integer state, Integer shipmentId);
}
