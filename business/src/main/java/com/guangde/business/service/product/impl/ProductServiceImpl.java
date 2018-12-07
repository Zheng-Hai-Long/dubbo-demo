package com.guangde.business.service.product.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.*;
import com.guangde.business.entry.*;
import com.guangde.business.enums.ResultEnum;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.product.ProductService;
import com.guangde.business.util.ConfigLoader;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by ZHL on 2018/11/29.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductSpecificationMapper productSpecificationMapper;

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private ProductShipmentMapper productShipmentMapper;

    @Override
    public List<Product> queryProductPageByParam(Product param, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<Product> productList = productMapper.queryByParam(param);
        String resUrl = ConfigLoader.getResPictrueURL();
        for (Product product : productList) {
            if (product != null && StringUtils.isNotBlank(product.getCoverImageUrl())) {
                product.setCoverImageUrl(resUrl + product.getCoverImageUrl());
            }
        }
        return productList;
    }

    @Override
    public Product queryProductDetailById(Integer productId) {
        Product product = productMapper.queryById(productId);
        String resUrl = ConfigLoader.getResPictrueURL();
        if (product != null && StringUtils.isNotBlank(product.getCoverImageUrl())) {
            product.setCoverImageUrl(resUrl + product.getCoverImageUrl());
        }
        return product;
    }

    @Override
    public List<ProductCategory> queryProductCategoryAll() {
        return productCategoryMapper.queryAll();
    }

    @Override
    public List<ProductSpecification> queryProductSpecification(Integer productId) {
        ProductSpecification productSpecification = new ProductSpecification();
        productSpecification.setProductId(productId);
        List<ProductSpecification> productSpecificationList = productSpecificationMapper.queryByParam(productSpecification);
        return productSpecificationList;
    }

    @Override
    public ProductSpecification queryProductSpecificationById(Integer productId, Integer id) {
        ProductSpecification productSpecification = new ProductSpecification();
        productSpecification.setId(id);
        productSpecification.setProductId(productId);
        List<ProductSpecification> productSpecificationList = productSpecificationMapper.queryByParam(productSpecification);
        if(!CollectionUtils.isEmpty(productSpecificationList)){
            return productSpecificationList.get(0);
        }
        return null;
    }

    @Override
    public ProductOrder queryProductOrder(String transNum, Integer userId) {
        return productOrderMapper.queryProductOrder(transNum, userId);
    }

    @Override
    public Result queryProductOrderDetail(String transNum, Integer userId) {

        ProductOrder productOrder = productOrderMapper.queryProductOrder(transNum, userId);
        if(productOrder == null){
            return new Result(ResultEnum.OrderError);
        }

        Product product = productMapper.queryById(productOrder.getProductId());
        String resUrl = ConfigLoader.getResPictrueURL();
        productOrder.setCoverImageUrl(resUrl + product.getCoverImageUrl());

        ProductShipment shipment = new ProductShipment();
        shipment.setTranNum(transNum);
        List<ProductShipment> productShipments = productShipmentMapper.queryByParam(shipment);
        if(!CollectionUtils.isEmpty(productShipments) && productShipments.size() == 1){
            productOrder.setShipment(productShipments.get(0));
        }
        else{
            return new Result(ResultEnum.OrderError);
        }
        return new Result(ResultEnum.Success, productOrder);
    }

	@Override
	public List<Product> queryProductMaybePageByParam(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.queryProductMaybePageByParam();
        String resUrl = ConfigLoader.getResPictrueURL();
        for (Product product : productList) {
            if (product != null && StringUtils.isNotBlank(product.getCoverImageUrl())) {
                product.setCoverImageUrl(resUrl + product.getCoverImageUrl());
            }
        }
        return productList;
	}
}
