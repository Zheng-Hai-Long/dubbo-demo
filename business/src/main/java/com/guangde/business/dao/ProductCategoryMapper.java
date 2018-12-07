package com.guangde.business.dao;


import com.guangde.business.entry.ProductCategory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductCategoryMapper extends BaseMapper<ProductCategory>
{
	List<ProductCategory> queryByParam(ProductCategory param);
}
