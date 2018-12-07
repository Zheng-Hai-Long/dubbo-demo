package com.guangde.api.homepage;

import com.guangde.dto.ProductDTO;
import com.guangde.entry.ApiProduct;
import com.guangde.entry.ApiProductOrder;
import com.guangde.entry.ApiProductSpecification;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

/**
 * Created by ZHL on 2018/11/29.
 */
public interface IProductFacade {

    /**
     * 根据条件查询商品分页
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    ApiPage<ProductDTO> queryProductPageByParam(ApiProduct param, int pageNum, int pageSize);
    
    /**
     * 随机4个商品
     * @return
     */
    ApiPage<ProductDTO> queryProductMaybePageByParam(int pageNum, int pageSize);

    /**
     * 根据商品id查询相片详情
     * @param productId
     * @return
     */
    ApiResult queryProductDetailById(Integer productId);

    /**
     * 查询所有商品分类
     * @return
     */
    ApiResult queryProductCategoryAll();

    /**
     * 查询商品规格
     * @param productId
     * @return
     */
    ApiResult queryProductSpecification(Integer productId);

    ApiProductSpecification queryProductSpecificationById(Integer productId, Integer id);

    ApiProductOrder queryProductOrder(String transNum, Integer userId);

    ApiResult queryProductOrderDetail(String transNum, Integer userId);

    ApiPage<ApiProductOrder> queryUserBuyList(ApiProductOrder param, int pageNum, int pageSize);
    
    ApiResult shipmentSure(Integer userId,Integer state, Integer shipmentId);
}
