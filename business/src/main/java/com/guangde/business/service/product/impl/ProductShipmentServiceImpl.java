package com.guangde.business.service.product.impl;

import com.guangde.business.dao.ProductShipmentMapper;
import com.guangde.business.entry.ProductShipment;
import com.guangde.business.enums.ResultEnum;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.product.ProductShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ZHL on 2018/11/29.
 */
@Service
public class ProductShipmentServiceImpl implements ProductShipmentService{

	@Autowired
	private ProductShipmentMapper productShipmentMapper;
	
	
	@Override
	public Result shipmentSure(Integer userId,Integer state, Integer shipmentId) {
		ProductShipment productShipment = productShipmentMapper.queryById(shipmentId);
        if(productShipment == null){
            return new Result(ResultEnum.ShipmentError);
        }

        if(!productShipment.getUserId().equals(userId)){
        	return new Result(ResultEnum.ShipmentError);
        }
        productShipment.setState(state);
        productShipment.setUpdateTime(new Date());
        productShipmentMapper.update(productShipment);
        return new Result(ResultEnum.Success);
	}
}
