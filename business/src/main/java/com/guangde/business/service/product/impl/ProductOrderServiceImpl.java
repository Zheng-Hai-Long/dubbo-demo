package com.guangde.business.service.product.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.ProductOrderMapper;
import com.guangde.business.entry.ProductOrder;
import com.guangde.business.service.product.ProductOrderService;
import com.guangde.business.util.ConfigLoader;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZHL on 2018/11/29.
 */
@Service
public class ProductOrderServiceImpl implements ProductOrderService{
	
	@Autowired
	ProductOrderMapper productOrderMapper;

	@Override
	public List<ProductOrder> queryProductOrderParam(ProductOrder param,
			int pageNum, int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);

        List<ProductOrder> list = productOrderMapper.queryByParam(param);
        String resUrl = ConfigLoader.getResPictrueURL();
        for (ProductOrder productOrder : list) {
            if (productOrder != null && StringUtils.isNotBlank(productOrder.getCoverImageUrlMiddle())) {
            	productOrder.setCoverImageUrlMiddle(resUrl + productOrder.getCoverImageUrlMiddle());
            }
        }
        return list;
	}
}
