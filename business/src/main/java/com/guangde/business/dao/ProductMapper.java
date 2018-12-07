package com.guangde.business.dao;

import com.guangde.business.entry.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends BaseMapper<Product>
{
	List<Product> queryByParam(Product vo);
	
	List<Product> queryProductMaybePageByParam();
}
