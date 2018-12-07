package com.guangde.business.dao;

import com.guangde.business.entry.ProductOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderMapper extends BaseMapper<ProductOrder>
{
	List<ProductOrder> queryByParam(ProductOrder vo);

	ProductOrder queryProductOrder(@Param("transNum") String transNum, @Param("userId") Integer userId);
}
