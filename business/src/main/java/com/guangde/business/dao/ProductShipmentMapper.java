package com.guangde.business.dao;

import com.guangde.business.entry.ProductShipment;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductShipmentMapper extends BaseMapper<ProductShipment>
{
	List<ProductShipment> queryByParam(ProductShipment vo);
}
