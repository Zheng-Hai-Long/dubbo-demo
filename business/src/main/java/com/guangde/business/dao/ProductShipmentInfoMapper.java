package com.guangde.business.dao;

import com.guangde.business.entry.ProductShipmentInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductShipmentInfoMapper extends BaseMapper<ProductShipmentInfo>
{
	List<ProductShipmentInfo> queryByShipmentDetail(Integer shipmentId);
}
