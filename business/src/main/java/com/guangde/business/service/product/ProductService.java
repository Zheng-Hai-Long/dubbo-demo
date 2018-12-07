package com.guangde.business.service.product;

import com.guangde.business.entry.Product;
import com.guangde.business.entry.ProductCategory;
import com.guangde.business.entry.ProductOrder;
import com.guangde.business.entry.ProductSpecification;
import com.guangde.business.pojo.Result;

import java.util.List;

/**
 * Created by ZHL on 2018/11/29.
 */
public interface ProductService {

    /**
     * 根据条件查询商品列表
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Product> queryProductPageByParam(Product param, int pageNum, int pageSize);
    
    /**
     * 随机查询4件商品记录
     * @return
     */
    List<Product> queryProductMaybePageByParam(int pageNum, int pageSize);

    /**
     * 根据商品id查询相片详情
     * @param productId
     * @return
     */
    Product queryProductDetailById(Integer productId);

    /**
     * 查询所有商品分类
     * @return
     */
    List<ProductCategory> queryProductCategoryAll();

    /**
     * 查询商品规格
     * @param productId
     * @return
     */
    List<ProductSpecification> queryProductSpecification(Integer productId);

    ProductSpecification queryProductSpecificationById(Integer productId, Integer id);

    ProductOrder queryProductOrder(String transNum, Integer userId);

    Result queryProductOrderDetail(String transNum, Integer userId);
}
