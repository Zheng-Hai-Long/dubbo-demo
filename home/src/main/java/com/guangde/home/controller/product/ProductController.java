package com.guangde.home.controller.product;

import com.guangde.api.homepage.IProductFacade;
import com.guangde.api.user.IUserFacade;
import com.guangde.api.user.IUserRelationInfoFacade;
import com.guangde.dto.OrderSureDTO;
import com.guangde.dto.ProductDTO;
import com.guangde.entry.ApiFrontUser_address;
import com.guangde.entry.ApiProduct;
import com.guangde.entry.ApiProductOrder;
import com.guangde.entry.ApiProductSpecification;
import com.guangde.enums.ResultEnum;
import com.guangde.home.constant.UrlConstants;
import com.guangde.home.utils.CommonUtils;
import com.guangde.home.utils.ResultUtil;
import com.guangde.pojo.ApiPage;
import com.guangde.pojo.ApiResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

/**
 * Created by ZHL on 2018/11/29.
 */

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductFacade productFacade;

    @Autowired
    private IUserFacade userFacade;

    @Autowired
    private IUserRelationInfoFacade userRelationInfoFacade;


    /**
     * 商品列表
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @param productCategoryId
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult productList(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                                 @RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "productCategoryId", required = false) Integer productCategoryId){
        ApiProduct param = new ApiProduct();
        param.setOrderBy(" updateTime ");
        param.setOrderDirection(" desc ");
        param.setProductName(keyword);
        param.setProductCategoryId(productCategoryId);
        ApiPage<ProductDTO> page = productFacade.queryProductPageByParam(param, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }


    /**
     * 商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult productDetail(@RequestParam("id") Integer id){
        return productFacade.queryProductDetailById(id);
    }

    /**
     * 商品规格详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/specification", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult productSpecification(@RequestParam("id") Integer id){
        return productFacade.queryProductSpecification(id);
    }

    /**
     * 商品分类
     * @return
     */
    @RequestMapping(value = "/categoryName", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult productCategoryName(){
        return productFacade.queryProductCategoryAll();
    }


    //订单确认
    @RequestMapping(value = "/order/sure", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult orderSure(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("productId") Integer productId,
                               @RequestParam("specificationId") Integer specificationId,
                               @RequestParam("productNum") Integer productNum) {
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());

        ApiResult<ApiProduct> apiResult = productFacade.queryProductDetailById(productId);
        if(apiResult.getCode() != 1){
            return apiResult;
        }
        ApiProduct product = apiResult.getData();

        OrderSureDTO dto = new OrderSureDTO();
        dto.setCoverImageUrl(product.getCoverImageUrl());
        dto.setProductName(product.getProductName());
        dto.setProductId(productId);


        ApiFrontUser_address apiFrontUser_address = userRelationInfoFacade.queryDefaultAddress(userId);
        dto.setAddressState(0);
        if(apiFrontUser_address != null){
            dto.setAddressState(1);
            dto.setAddressId(apiFrontUser_address.getId());
            dto.setAddresseeAddress(apiFrontUser_address.getProvince()+apiFrontUser_address.getCity()+apiFrontUser_address.getArea()+apiFrontUser_address.getDetailAddress());
            dto.setReceiverName(apiFrontUser_address.getName());
            dto.setReceiverPhone(apiFrontUser_address.getMobile());
        }

        dto.setSpecificationId(specificationId);
        dto.setProductNum(productNum);
        ApiProductSpecification apiProductSpecification = productFacade.queryProductSpecificationById(productId, specificationId);
        if(apiProductSpecification != null){
            dto.setPrice(apiProductSpecification.getPrice());
            dto.setAmountMoney(apiProductSpecification.getPrice().multiply(new BigDecimal(productNum)));
            dto.setStock(apiProductSpecification.getStock());
            dto.setSpecificationName(apiProductSpecification.getProductSpecification());
        }
        return new ApiResult(ResultEnum.Success, dto);
    }



    //订单详情
    @RequestMapping(value = "/order/detail", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult orderDetail(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("transNum") String transNum){
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());
        return productFacade.queryProductOrderDetail(transNum, userId);
    }

    //购买记录
    @RequestMapping(value = "/buy/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult buyList(HttpServletRequest request, HttpServletResponse response,
    		                 @RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                             @RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
                             String productName, Integer state){
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }
        Integer userId = Integer.valueOf(result.getData().toString());
        ApiProductOrder productOrder = new ApiProductOrder();
        productOrder.setUserId(userId);
        productOrder.setState(state);
        productOrder.setProductName(productName);
        ApiPage<ApiProductOrder> page  = productFacade.queryUserBuyList(productOrder, pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }
    
    //确认收货
    @RequestMapping(value = "/shipment/sure", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult shipmentSure(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("state") Integer state,
                               @RequestParam("shipmentId") Integer shipmentId) {
        response.setHeader("Access-Control-Allow-Origin", UrlConstants.URL);
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ApiResult result = CommonUtils.valUserLogin(request);
        if(result.getCode() != 1){
            return result;
        }

        Integer userId = Integer.valueOf(result.getData().toString());

        productFacade.shipmentSure(userId,state ,shipmentId);
        return new ApiResult(ResultEnum.Success);
    }
    
    //可能会感兴趣的商品
    @RequestMapping(value = "/interest/list", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult productMaybeList( @RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
                                       @RequestParam(value="pageSize",required=false,defaultValue="4")Integer pageSize){
        ApiPage<ProductDTO> page = productFacade.queryProductMaybePageByParam(pageNum, pageSize);
        if(page != null && page.valPage(page, pageNum)){
            return ResultUtil.SUCCESS(page);
        }
        if(pageNum > 1){
            return new ApiResult(ResultEnum.NotMoreData);
        }
        return new ApiResult(ResultEnum.EmptyData);
    }

}
