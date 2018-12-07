package com.guangde.business.service.product;

import java.util.List;

import com.guangde.business.entry.ProductOrder;

/**
 * Created by ZHL on 2018/11/29.
 */
public interface ProductOrderService {
	
	List<ProductOrder> queryProductOrderParam(ProductOrder param, int pageNum, int pageSize);
}
