package com.guangde.business.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.constants.FrontUserConstants;
import com.guangde.business.dao.*;
import com.guangde.business.dto.BuyProductFormDTO;
import com.guangde.business.dto.DepositFormDTO;
import com.guangde.business.entry.*;
import com.guangde.business.enums.ResultEnum;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.user.IDonateRecordService;
import com.guangde.business.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("donateRecordService")
public class DonateRecordServiceImpl extends BaseService implements IDonateRecordService
{
    @Autowired
    private DonateRecordMapper donateRecordMapper;
    
    @Autowired
    private CapitalinoutMapper capitalinoutMapper;
    
    @Autowired
    private BFileMapper bFileMapper;
    
    @Autowired
    private FrontUserMapper userMapper;
    
    @Autowired
    private ProjectMapper projectMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;

	@Autowired
	private ProductOrderMapper productOrderMapper;

	@Autowired
	private ProductShipmentMapper productShipmentMapper;

	@Autowired
	private ProductShipmentInfoMapper productShipmentInfoMapper;
	
	@Autowired
	private FrontUserInvoiceMapper frontUserInvoiceMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public Result buyDonate(DepositFormDTO depositFormDTO) throws BaseException {

		if(depositFormDTO.getMoney() <= 0){
			return new Result(ResultEnum.OtherError.getCode(), "捐赠金额不合法");
		}

		//判断项目是否存在
		Project project = projectMapper.queryById(depositFormDTO.getProjectId());
		if(project == null ||  project.getState() != 240){
			return new Result(ResultEnum.OtherError.getCode(), "项目id：" + depositFormDTO.getProjectId() + "，项目异常");
		}
		//判断用户是否存在
		FrontUser user = userMapper.queryById(depositFormDTO.getUserId());
		if(user == null){
			return new Result(ResultEnum.OtherError.getCode(), "用户id：" + depositFormDTO.getUserId()+ ",用户不存在");
		}

		try {
			FrontUser newUser = new FrontUser();
			newUser.setId(user.getId());
			if(StringUtils.isBlank(user.getMobileNum()) && StringUtils.isNotBlank(depositFormDTO.getMobile())){
				newUser.setMobileState(203);
				newUser.setMobileNum(depositFormDTO.getMobile());
			}
			if(StringUtils.isBlank(user.getRealName()) && StringUtils.isNotBlank(depositFormDTO.getRealName())){
				newUser.setRealName(depositFormDTO.getRealName());
			}
			if(StringUtils.isNotBlank(newUser.getMobileNum()) || StringUtils.isNotBlank(newUser.getRealName())){
				userMapper.update(newUser);
			}
		}catch (Exception e){

		}


		//生成资金明细
		Capitalinout ca = new Capitalinout();
		ca.setUserId(user.getId());
		ca.setBalance(user.getBalance());
		ca.setMoney(depositFormDTO.getMoney());
		ca.setType(0);
		ca.setInType(0);
		ca.setTranNum(depositFormDTO.getTransNum());
		ca.setCreateTime(new Date());
		ca.setUpdateTime(new Date());
		ca.setPayState(300);
		ca.setPayType("tenpay");
		ca.setSource(depositFormDTO.getSource());
		int res = capitalinoutMapper.save(ca);
		if(res <= 0){
			throw new BaseException(ResultEnum.Error.getCode(), "生成资金error");
		}
		//生成捐赠记录
		DonateRecord donateRecord = new DonateRecord();
		donateRecord.setUserId(user.getId());
		donateRecord.setNameOpen(depositFormDTO.getNameOpen());
		donateRecord.setState(300);
		donateRecord.setProjectId(depositFormDTO.getProjectId());
		donateRecord.setProjectTitle(project.getTitle());
		donateRecord.setCapitalinoutId(ca.getId());
		donateRecord.setCoverImageId(user.getCoverImageId());
		donateRecord.setNickName(user.getNickName());
		donateRecord.setDonatAmount(depositFormDTO.getMoney());
		donateRecord.setDonatTime(new Date());
		donateRecord.setDonatType("IndividualDonation");
		donateRecord.setTranNum(depositFormDTO.getTransNum());
		donateRecord.setDonateSource(depositFormDTO.getSource());
		res = donateRecordMapper.save(donateRecord);
		if(res <= 0){
			throw new BaseException(ResultEnum.Error.getCode(), "生成捐赠记录error");
		}

		return new Result(ResultEnum.Success);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public Result buyProduct(BuyProductFormDTO depositFormDTO) throws BaseException {

		//判断商品是否存在
		Product product = productMapper.queryById(depositFormDTO.getProductId());
		if(product == null){
			return new Result(ResultEnum.OtherError.getCode(), "商品不存在");
		}
		//判断库存是否足够
		ProductSpecification specification= new ProductSpecification();
		specification.setProductId(depositFormDTO.getProductId());
		specification.setId(depositFormDTO.getSpecificationId());
		List<ProductSpecification> productSpecificationList = productSpecificationMapper.queryByParam(specification);
		if(CollectionUtils.isEmpty(productSpecificationList) || productSpecificationList.size() != 1){
			return new Result(ResultEnum.OtherError.getCode(), "商品异常");
		}

		//判断用户是否存在
		FrontUser user = userMapper.queryById(depositFormDTO.getUserId());
		if(user == null){
			return new Result(ResultEnum.OtherError.getCode(), "用户id：" + depositFormDTO.getUserId()+ ",用户不存在");
		}

		ProductSpecification productSpecification = productSpecificationList.get(0);
		if(productSpecification.getStock() < depositFormDTO.getShipmentNum()){
			return new Result(ResultEnum.OtherError.getCode(), "商品id：" + depositFormDTO.getProductId()+ ",库存不足");
		}

		Double money = MathUtil.mul(depositFormDTO.getShipmentNum(), productSpecification.getPrice().doubleValue());


		//生成资金明细
		Capitalinout ca = new Capitalinout();
		ca.setUserId(user.getId());
		ca.setBalance(user.getBalance());
		ca.setMoney(money);
		ca.setType(0);
		ca.setInType(17);//购买商品
		ca.setTranNum(depositFormDTO.getTransNum());
		ca.setCreateTime(new Date());
		ca.setUpdateTime(new Date());
		ca.setPayState(300);
		ca.setPayType("tenpay");
		ca.setSource(depositFormDTO.getSource());
		int res = capitalinoutMapper.save(ca);
		if(res <= 0){
			throw new BaseException(ResultEnum.Error.getCode(), "生成资金error");
		}

		//生成订单
		ProductOrder productOrder = new ProductOrder();
		productOrder.setProductId(depositFormDTO.getProductId());
		productOrder.setUpdateTime(new Date());
		productOrder.setCreateTime(new Date());
		productOrder.setUserId(depositFormDTO.getUserId());
		productOrder.setAmountMoney(BigDecimal.valueOf(money));
		productOrder.setTranNum(depositFormDTO.getTransNum());
		productOrder.setStockNum(productSpecification.getStock());
		productOrder.setProductName(product.getProductName());
		productOrder.setState(101);
		productOrder.setPrice(productSpecification.getPrice());
		productOrder.setShipmentNum(depositFormDTO.getShipmentNum());
		productOrder.setProductSpecificationName(productSpecification.getProductSpecification());
		productOrder.setProductSpecificationId(productSpecification.getId());
		res = productOrderMapper.save(productOrder);
		if(res <= 0){
			throw new BaseException(ResultEnum.Error.getCode(), "生成商品订单error");
		}

		//生成发货记录
		ProductShipment shipment = new ProductShipment();
		shipment.setProductName(product.getProductName());
		shipment.setState(101);
		shipment.setTranNum(depositFormDTO.getTransNum());
		shipment.setAddresseeAddress(depositFormDTO.getAddresseeAddress());
		shipment.setCreateTime(new Date());
		shipment.setUpdateTime(new Date());
		shipment.setProductId(depositFormDTO.getProductId());
		shipment.setReceiverName(depositFormDTO.getReceiverName());
		shipment.setReceiverPhone(depositFormDTO.getReceiverPhone());
		shipment.setUserId(depositFormDTO.getUserId());
		shipment.setPostage(BigDecimal.ZERO);
		res = productShipmentMapper.save(shipment);
		if(res <= 0){
			throw new BaseException(ResultEnum.Error.getCode(), "生成发货记录error");
		}

		//生成发货详情记录
		ProductShipmentInfo shipmentInfo = new ProductShipmentInfo();
		shipmentInfo.setProductId(depositFormDTO.getProductId());
		shipmentInfo.setCreateTime(new Date());
		shipmentInfo.setShipmentNum(depositFormDTO.getShipmentNum());
		shipmentInfo.setProductName(product.getProductName());
		shipmentInfo.setShipmentId(shipment.getId());
		shipmentInfo.setProductSpecificationName(productSpecification.getProductSpecification());
		shipmentInfo.setProductSpecificationId(productSpecification.getId());
		res = productShipmentInfoMapper.save(shipmentInfo);
		if(res <= 0){
			throw new BaseException(ResultEnum.Error.getCode(), "生成发货详情记录error");
		}
		return new Result(ResultEnum.Success);
	}

	@Override
    public List<DonateRecord> queryByCondition(DonateRecord donateRecord, int pageNum, int pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        
        List<DonateRecord> list = donateRecordMapper.queryByParam(donateRecord);

        for (DonateRecord item : list)
        {
			if(StringUtils.isNotBlank(item.getCoverImageUrl()) && !item.getCoverImageUrl().startsWith("http")){
				String resUrl = ConfigLoader.getResPictrueURL();
				item.setCoverImageUrl(resUrl + item.getCoverImageUrl());
			}
        }
        return list;
    }
    
    
    @Override
    public List<DonateRecord> queryByDonate(DonateRecord donateRecord, int pageNum, int pageSize)
    {
        
        PageHelper.startPage(pageNum, pageSize, true);
        
        List<DonateRecord> list = donateRecordMapper.queryByDonate(donateRecord);
        String safeName = "";
        if(list != null && list.size() > 0){
        for (DonateRecord item : list)
        {
            if (item.getNickName() != null)
            {
                safeName = UserUtil.getSafeName(item.getNickName());
                item.setNickName(safeName);
            }
            if(item.getCoverImageId()!=0){
    			BFile bFile = bFileMapper.queryById(item.getCoverImageId());
    			if(!bFile.getUrl().contains("http://")){
    				String resUrl = ConfigLoader.getResPictrueURL();
            		item.setCoverImageUrl(resUrl + bFile.getUrl());
    			}
    			else{
    				item.setCoverImageUrl(bFile.getUrl());
    			}
    			
    		}
        }
        }
        return list;
    }
    
    @Override
    public Integer countByCompanyGoodHelp(DonateRecord donateRecord)
    {
        return donateRecordMapper.countByCompanyGoodHelp(donateRecord);
    }
    
    @Override
    public DonateRecord countByParam(DonateRecord donateRecord)
    {
        return donateRecordMapper.countByParam(donateRecord);
    }
    
    @Override
    public Double countQueryByCondition(DonateRecord donateRecord)
    {
        
        return donateRecordMapper.countQueryByParam(donateRecord);
    }
    
    @Override
    public Double countQueryDonateRecordList(DonateRecord donateRecord)
    {
        
        return donateRecordMapper.countQueryDonateRecordList(donateRecord);
    }
    
    @Override
    public Integer queryProjectIdByCapitalinoutId(String tranNum)
    {
        
        return donateRecordMapper.queryProjectIdByCapitalinoutId(tranNum);
    }
    
    @Override
    public DonateRecord queryWeixinPayNoticeByTranNum(String tranNum)
    {
        
        return donateRecordMapper.queryWeixinPayNoticeByTranNum(tranNum);
    }
    
	@Override
	public List<DonateRecord> queryLatestDonateRecordList(
			DonateRecord donateRecord, int pageNum, int pageSize) {
		
		 PageHelper.startPage(pageNum, pageSize, false);
		 
        List<DonateRecord> list = donateRecordMapper.queryLatestDonateRecordList(donateRecord);
        String safeName = "";
        if(list != null && list.size() > 0){
        for (DonateRecord item : list)
        {
			if(item != null){
        	if(item.getNameOpen() == 1){//匿名
        		if (item.getNickName() != null)
                {
                    item.setNickName(FrontUserConstants.DEFAULT_DONATED_USER_NAME);
                    item.setCoverImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
                }
        	}else {//不匿名
        		if (item.getNickName() != null)
                {
                    safeName = UserUtil.getSafeName(item.getNickName());
                    item.setNickName(safeName);
                }
        		if(StringUtils.isNotEmpty(item.getCoverImageUrl())){
                	if (!item.getCoverImageUrl().contains("http://")) {
                		String resUrl = ConfigLoader.getResPictrueURL();
                		item.setCoverImageUrl(resUrl + item.getCoverImageUrl());
    				}
                }
        	}}
        }
        }
        return list;
	}
	
	@Override
	public List<DonateRecord> queryLatestDonateRecordIdList(
			DonateRecord donateRecord, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DonateRecord> list = donateRecordMapper.queryLatestDonateRecordIdList(donateRecord); 
        return list;
	}

	@Override
	public List<DonateRecord> queryLatestDonateRecordIdDetailList(
			DonateRecord donateRecord) {
        List<DonateRecord> list = donateRecordMapper.queryLatestDonateRecordIdDetailList(donateRecord);
        String safeName = "";
        if(list != null && list.size() > 0){
        for (DonateRecord item : list)
        {
        	if(item.getNameOpen() == 1){//匿名
        		if (item.getNickName() != null)
                {
                    item.setNickName(FrontUserConstants.DEFAULT_DONATED_USER_NAME);
                    item.setCoverImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
                }
        	}else{//不匿名
        		if (item.getNickName() != null)
                {
                    safeName = UserUtil.getSafeName(item.getNickName());
                    item.setNickName(safeName);
                }
        		if(StringUtils.isNotEmpty(item.getCoverImageUrl())){
                	if (!item.getCoverImageUrl().contains("http://")) {
                		String resUrl = ConfigLoader.getResPictrueURL();
                		item.setCoverImageUrl(resUrl + item.getCoverImageUrl());
    				}
                }
        	}
        }}
        return list;
	}

	@Override
	public List<DonateRecord> queryMyDonateRecordList(
			DonateRecord donateRecord, int pageNum, int pageSize) {
		
	     	PageHelper.startPage(pageNum, pageSize);
	        
	        List<DonateRecord> list = donateRecordMapper.queryMyDonate(donateRecord);
	        String safeName = "";
	        if(list != null && list.size() > 0){
	        for (DonateRecord item : list)
	        {

	            if(StringUtils.isNotEmpty(item.getCoverImageUrl())){
	            	if (!item.getCoverImageUrl().contains("http://")) {
	            		String resUrl = ConfigLoader.getResPictrueURL();
	            		item.setCoverImageUrl(resUrl + item.getCoverImageUrl());
					}
	            }
	        }
	        }
	        return list;
	}


	@Override
	public Integer countNumQueryByCondition(DonateRecord donateRecord) {
		
		return donateRecordMapper.countNumQueryByParam(donateRecord);
	}


	@Override
	public int updateDonatLeaveWord(DonateRecord donateRecord) throws BaseException {
		
		int result = 0 ;
		try
		{
			
			Capitalinout newc = new Capitalinout();
			newc.setTranNum(donateRecord.getTranNum());
			List<Capitalinout> list = capitalinoutMapper.queryByParam(newc);
			Capitalinout c = null;
			
			if(list != null && list.size()>0){
				c = list.get(0);
			}
			if(c != null){
				donateRecord.setCapitalinoutId(c.getId());
				result = donateRecordMapper.updateGoodLuck(donateRecord);
			}
		}
		catch(Exception e)
		{
			logger.error("updateDonatLeaveWord>>", e);
			throw new BaseException(ResultCode.Error);
		}
		return result;
	}


	@Override
	public List<DonateRecord> queryByIdList(DonateRecord donateRecord) {
		
		return donateRecordMapper.queryByIdList(donateRecord);
	}


	@Override
	public List<DonateRecord> queryDonateListByInvoice(
			DonateRecord donateRecord, Integer pageNum, Integer pageSize) {
		 PageHelper.startPage(pageNum, pageSize);
		return donateRecordMapper.queryDonateListByInvoice(donateRecord);
	}


	@Override
	public Double sumQueryDonateListByInvoice(DonateRecord donateRecord) {
		Double totalAmount = donateRecordMapper.sumQueryDonateListByInvoice(donateRecord);
		if(totalAmount == null)
		{
			totalAmount = 0.0 ;
		}
		return totalAmount;
	}


	@Override
	public DonateRecord queryPayNoticeByTranNum(String tranNum) {
		
		DonateRecord  record = donateRecordMapper.queryPayNoticeByTranNum(tranNum);
		
		if(null == record || record.getProjectId() == null)
		{
			record = donateRecordMapper.queryPayNoticeByPayNum(tranNum);
		}
		
		if(record!=null && record.getCoverImageId()!=0){
			BFile bFile = bFileMapper.queryById(record.getCoverImageId());
			 String resUrl = ConfigLoader.getResPictrueURL();
			if(bFile!=null && bFile.getUrl()!=null){
				if(!bFile.getUrl().contains("http://")){
					record.setCoverImageUrl(resUrl + bFile.getUrl());
				}else{
					record.setCoverImageUrl(bFile.getUrl());
				}
			}
		}
		
		return record;
	}
	
	@Override
	public Capitalinout queryCapitalinoutByParam(Capitalinout param){
		Capitalinout capitalinout = capitalinoutMapper.queryCapitalinoutByParam(param);
		return capitalinout;
	}


	@Override
	public List<DonateRecord> queryDonateHeadImgByProjectId(Integer projectId,
			int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        
        List<DonateRecord> list = donateRecordMapper.queryDonateHeadImgByProjectId(projectId);
        String safeName = "";
        String resUrl = ConfigLoader.getResPictrueURL();
        if(list != null && list.size() > 0){
        for (DonateRecord item : list)
        {

            if(item.getCoverImageId()!=0){
				BFile bFile = bFileMapper.queryById(item.getCoverImageId());
				if(bFile!=null && bFile.getUrl()!=null){
					if(!bFile.getUrl().contains("http://")){
						item.setCoverImageUrl(resUrl + bFile.getUrl());
					}else{
						item.setCoverImageUrl(bFile.getUrl());
					}
				}
			}
        }}
        return list;
	}



	@Override
	public int addDayDonate(Capitalinout ca, DonateRecord dr, Project project,
			FrontUser user) {
		int res = capitalinoutMapper.save(ca);
		if(res>0){
			dr.setCapitalinoutId(ca.getId());
			res = donateRecordMapper.save(dr);
			if(res>0){
				res = projectMapper.update(project);
				if(res>0){
					res = userMapper.update(user);
				}
			}
		}
		
		return res;
	}


	@Override
	public int countDonateRecord(Integer state, String title) {
		return donateRecordMapper.countDonateRecord(state, title);
	}


	@Override
	public DonateRecord queryByDonateId(Integer id) {
		return donateRecordMapper.queryByDonateId(id);
	}
	
	@Override
	public void update(DonateRecord param) throws BaseException{
		try {
			donateRecordMapper.update(param);
		} catch (Exception e) {
			throw new BaseException();
		}
	}


	@Override
	public List<DonateRecord> queryYiXingTeamDonate(
			TogetherConfig togetherConfig, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        List<DonateRecord> list = donateRecordMapper.queryYiXingTeamDonate(togetherConfig);
        String safeName = "";
        if(list != null && list.size() > 0){
        for (DonateRecord item : list)
        {
            /*if (item.getNickName() != null)
            {
                safeName = UserUtil.getSafeName(item.getNickName());
                item.setNickName(safeName);
            }*/
            
            if(item.getNameOpen() == 1){//匿名
        		if (item.getNickName() != null)
                {
                    item.setNickName(FrontUserConstants.DEFAULT_DONATED_USER_NAME);
                    item.setCoverImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
                }
        	}else{//不匿名
        		if (item.getNickName() != null)
                {
                    safeName = UserUtil.getSafeName(item.getNickName());
                    item.setNickName(safeName);
                }
        		if(item.getCoverImageId()!=0){
        			BFile bFile = bFileMapper.queryById(item.getCoverImageId());
        			if(bFile != null && !bFile.getUrl().contains("http://")){
        				String resUrl = ConfigLoader.getResPictrueURL();
                		item.setCoverImageUrl(resUrl + bFile.getUrl());
        			}
        			else{
        				item.setCoverImageUrl(bFile.getUrl());
        			}
        		}
        	}
        }
        }
        return list;
	}


	@Override
	public List<DonateRecord> queryByUserIdSelectLeaveWord(Integer userId,Integer isRead,
			int pageNum, int pageSize) {
		logger.info("query param:" + userId);

		PageHelper.startPage(pageNum, pageSize);

		List<DonateRecord> list = donateRecordMapper
				.queryByUserIdSelectLeaveWord(userId,isRead);

		String resUrl = ConfigLoader.getResPictrueURL();
		if(list != null && list.size() > 0){
		for (DonateRecord d : list) {

			Integer uids = d.getCoverImageId();
			if(uids == null||uids ==0){
				d.setCoverImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
			}else{
				BFile b1 = bFileMapper.queryById(uids);
				if(b1 == null || b1.getUrl() == null || "".equals(b1.getUrl())){
					d.setCoverImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
				}else if(b1.getUrl().contains("http")){
					d.setCoverImageUrl(b1.getUrl());
				}else{
					d.setCoverImageUrl(resUrl+b1.getUrl());
				}
			}
		}
		}
		return list;
	}



	@Override
	public List<DonateRecord> queryByUserIdForDonateTime(DonateRecord donateRecord,
			int pageNum, int pageSize) {
		logger.info("query param:" + donateRecord);

		PageHelper.startPage(pageNum, pageSize);
		List<DonateRecord> list = donateRecordMapper
				.queryByUserIdForDonateTime(donateRecord);
		return list;
	}


	@Override
	public List<DonateRecord> queryByDonateTimeForInfo(DonateRecord donateRecord) {
		return donateRecordMapper.queryByDonateTimeForInfo(donateRecord);
	}


	@Override
	public List<DonateRecord> queryByDonateTimeParam(DonateRecord donateRecord) {
		return donateRecordMapper.queryByDonateTimeParam(donateRecord);
	}
	


	@Override
	public int countByDonateRecordParam(DonateRecord donateRecord) {
		try {
			return donateRecordMapper.countByDonateRecordParam(donateRecord);
		}catch (Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<DonateRecord> queryDonateRecordByEp(DonateRecord donateRecord, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<DonateRecord> list = donateRecordMapper.queryDonateRecordByEp(donateRecord);
		String resUrl = ConfigLoader.getResPictrueURL();
		if(list != null && list.size() > 0){
			for (DonateRecord d : list) {
				Integer uid = d.getCoverImageId();
				if(uid == null||uid ==0){
					d.setCoverImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
				}else{
					BFile b1 = bFileMapper.queryById(uid);
					if(b1 == null || b1.getUrl() == null || "".equals(b1.getUrl())){
						d.setCoverImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
					}else if(b1.getUrl().contains("http")){
						d.setCoverImageUrl(b1.getUrl());
					}else{
						d.setCoverImageUrl(resUrl+b1.getUrl());
					}
				}
			}
		}

		return list;
	}

	@Override
	public List<DonateRecord> queryByInvoiceApplyList(Integer userId, int pageNum, int pageSize) {

		FrontUser_invoice fVo = new FrontUser_invoice();
		//fVo.setState(302);
		fVo.setUserId(userId);
		List<Integer> list3 = new ArrayList<Integer>();
		List<FrontUser_invoice> list2 = frontUserInvoiceMapper.queryByParam(fVo);
		List<DonateRecord> list = null;
		if(list2 != null && list2.size() > 0){
			for (FrontUser_invoice f : list2) {
				String[] list4 =f.getInfo().split("_");
				for (String string : list4) {
					list3.add(Integer.valueOf(string));
				}
			}
			DonateRecord donateRecord = new DonateRecord();
			donateRecord.setUserId(userId);
			donateRecord.setDonateIds(list3);
			PageHelper.startPage(pageNum, pageSize);
			list = donateRecordMapper.queryByParam(donateRecord);
		}else{
			DonateRecord donateRecord = new DonateRecord();
			donateRecord.setUserId(userId);
			PageHelper.startPage(pageNum, pageSize);
			list = donateRecordMapper.queryByParam(donateRecord);
		}
		return list;
	}

	@Override
	public DonateRecord queryDonateRecordSuccess(DonateRecord donateRecord) {
		donateRecord = donateRecordMapper.queryDonateRecordSuccess(donateRecord);
		String resUrl = ConfigLoader.getResPictrueURL();
		if(StringUtils.isNotBlank(donateRecord.getCoverImageUrl())){
			donateRecord.setCoverImageUrl(resUrl + donateRecord.getCoverImageUrl());
		}
		if(StringUtils.isNotBlank(donateRecord.getProjectImageUrl())){
			donateRecord.setProjectImageUrl(resUrl + donateRecord.getProjectImageUrl());
		}
		return donateRecord;
	}


}
