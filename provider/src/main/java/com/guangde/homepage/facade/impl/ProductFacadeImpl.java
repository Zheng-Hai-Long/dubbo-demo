package com.guangde.homepage.facade.impl;

import com.github.pagehelper.Page;
import com.guangde.api.homepage.IProductFacade;
import com.guangde.business.entry.Product;
import com.guangde.business.entry.ProductCategory;
import com.guangde.business.entry.ProductOrder;
import com.guangde.business.entry.ProductSpecification;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.product.ProductOrderService;
import com.guangde.business.service.product.ProductService;
import com.guangde.business.service.product.ProductShipmentService;
import com.guangde.dto.ProductDTO;
import com.guangde.entry.ApiProduct;
import com.guangde.entry.ApiProductCategory;
import com.guangde.entry.ApiProductOrder;
import com.guangde.entry.ApiProductSpecification;
import com.guangde.enums.ResultEnum;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;
import com.guangde.util.BeanUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by ZHL on 2018/11/29.
 */
@Service("productFacade")
public class ProductFacadeImpl implements IProductFacade {


    private static final Logger logger = LoggerFactory.getLogger(ProductFacadeImpl.class);

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductOrderService productOrderService;
    
    @Autowired
    private ProductShipmentService productShipmentService;
    
    @Override
    public ApiPage<ProductDTO> queryProductPageByParam(ApiProduct param, int pageNum, int pageSize) {
        logger.info("receive queryProductPageByParam ApiProduct:" + param);

        Product p = BeanUtil.copy(param, Product.class);

        if (p == null)
        {
            new ApiPage<ApiProduct>(pageNum, pageSize);
        }

        List<Product> list = productService.queryProductPageByParam(p, pageNum, pageSize);
        ApiPage<ProductDTO> ret = BeanUtil.copyPage((Page<Product>)list, ProductDTO.class);
        return ret;
    }

    @Override
    public ApiResult queryProductDetailById(Integer productId) {
        logger.info("receive queryProductDetailById productId:" + productId);

        Product product = productService.queryProductDetailById(productId);
        ApiProduct apiProduct = new ApiProduct();
        BeanUtils.copyProperties(product, apiProduct);

        if(apiProduct == null){
            return new ApiResult(ResultEnum.EmptyData);
        }
        return new ApiResult(ResultEnum.Success, apiProduct);
    }

    @Override
    public ApiResult queryProductCategoryAll() {
        List<ProductCategory> productCategoryList = productService.queryProductCategoryAll();

        List<ApiProductCategory> list = BeanUtil.copyList((List<ProductCategory>)productCategoryList, ApiProductCategory.class);

        if(CollectionUtils.isEmpty(list)){
            return new ApiResult(ResultEnum.EmptyData);
        }
        return new ApiResult(ResultEnum.Success, list);
    }

    @Override
    public ApiResult queryProductSpecification(Integer productId) {
        logger.info("receive queryProductSpecification productId:" + productId);

        List<ProductSpecification> productSpecificationList = productService.queryProductSpecification(productId);

        List<ApiProductSpecification> list = BeanUtil.copyList((List<ProductSpecification>)productSpecificationList, ApiProductSpecification.class);

        if(CollectionUtils.isEmpty(list)){
            return new ApiResult(ResultEnum.EmptyData);
        }
        return new ApiResult(ResultEnum.Success, list);
    }

    @Override
    public ApiProductSpecification queryProductSpecificationById(Integer productId, Integer id) {
        logger.info("receive queryProductSpecificationById id:" + id + "productId: " + productId);
        ProductSpecification productSpecification = productService.queryProductSpecificationById(productId, id);
        ApiProductSpecification apiProductSpecification = new ApiProductSpecification();
        BeanUtils.copyProperties(productSpecification, apiProductSpecification);
        return apiProductSpecification;
    }

    @Override
    public ApiProductOrder queryProductOrder(String transNum, Integer userId) {
        logger.info("receive queryProductOrder transNum:" + transNum + "，userId:" + userId);
        ProductOrder productOrder = productService.queryProductOrder(transNum, userId);
        ApiProductOrder apiProductOrder = new ApiProductOrder();
        BeanUtils.copyProperties(productOrder, apiProductOrder);
        return apiProductOrder;
    }

    @Override
    public ApiResult queryProductOrderDetail(String transNum, Integer userId) {
        logger.info("receive queryProductOrder transNum:" + transNum + "，userId:" + userId);
        Result result = productService.queryProductOrderDetail(transNum, userId);
        ApiResult apiResult = new ApiResult();
        BeanUtils.copyProperties(result, apiResult);
        return apiResult;
    }

    @Override
    public ApiPage<ApiProductOrder> queryUserBuyList(ApiProductOrder param, int pageNum, int pageSize) {
    	logger.info("receive queryUserBuyList ApiProductOrder:" + param);
    	ProductOrder productOrder = BeanUtil.copy(param, ProductOrder.class);
    	if(productOrder == null){
    		new ApiPage<ApiProductOrder>(pageNum,pageSize);
    	}
    	List<ProductOrder> list = productOrderService.queryProductOrderParam(productOrder,pageNum, pageSize);
    	ApiPage<ApiProductOrder> ret = BeanUtil.copyPage((Page<ProductOrder>)list, ApiProductOrder.class);

        return ret;
    }

	@Override
	public ApiResult shipmentSure(Integer userId,Integer state, Integer shipmentId) {
		logger.info("receive shipmentSure state:" + state + ",shipmentId" + shipmentId + ",userId" + userId);
		Result result = productShipmentService.shipmentSure(userId,state, shipmentId);
		ApiResult apiResult = new ApiResult();
        BeanUtils.copyProperties(result, apiResult);
		return apiResult;
	}

	@Override
	public ApiPage<ProductDTO> queryProductMaybePageByParam(int pageNum, int pageSize) {
		logger.info("receive queryProductMaybePageByParam");

        List<Product> list = productService.queryProductMaybePageByParam(pageNum, pageSize);
        ApiPage<ProductDTO> ret = BeanUtil.copyPage((Page<Product>)list, ProductDTO.class);
        return ret;
	}
}
