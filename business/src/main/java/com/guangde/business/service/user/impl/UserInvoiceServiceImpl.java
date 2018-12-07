package com.guangde.business.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.dao.DonateRecordMapper;
import com.guangde.business.dao.FrontUserAddressMapper;
import com.guangde.business.dao.FrontUserInvoiceMapper;
import com.guangde.business.dao.FrontUserMapper;
import com.guangde.business.entry.DonateRecord;
import com.guangde.business.entry.FrontUser;
import com.guangde.business.entry.FrontUser_address;
import com.guangde.business.entry.FrontUser_invoice;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.user.IUserInvoiceService;
import com.guangde.business.util.ConfigLoader;
import com.guangde.business.util.MathUtil;
import com.guangde.business.util.ResultCode;
import com.guangde.business.util.State;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userInvoiceService")
public class UserInvoiceServiceImpl extends BaseService implements IUserInvoiceService
{
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private FrontUserInvoiceMapper frontUserInvoiceMapper ;
    
    @Autowired
    private FrontUserAddressMapper frontUserAddressMapper;
    
    @Autowired
    private FrontUserMapper frontUserMapper;
    
    @Autowired
    private DonateRecordMapper donateRecordMapper;
    
	@Override
	public FrontUser_invoice queryById(Integer id) {
		
		return frontUserInvoiceMapper.queryById(id);
	}

	@Override
	public List<FrontUser_invoice> queryUserInvoiceParam(
			FrontUser_invoice userInvoice,Integer pageNum,Integer pageSize) {
		if(pageSize > 50){
			PageHelper.startPage(pageNum, pageSize);
		}
		return frontUserInvoiceMapper.queryByParam(userInvoice);
	}

	@Override
	public void saveUserInvoice(FrontUser_invoice userInvoice)
			throws BaseException {
		try
		{
			FrontUser_invoice  invoice = new FrontUser_invoice();
			invoice.setInfo(userInvoice.getInfo());
			List<FrontUser_invoice> list = frontUserInvoiceMapper.queryByParam(invoice);
			if(list.size()<=0)
			{
				frontUserInvoiceMapper.save(userInvoice);
			}
		}
		catch(Exception e)
		{
			logger.error("saveUserInvoice>> ",e);
			throw  new BaseException(ResultCode.Error);
		}
		
	}

	@Override
	public void updateUserInvoice(FrontUser_invoice userInvoice)
			throws BaseException {
		try
		{
			frontUserInvoiceMapper.update(userInvoice);
		}
		catch(Exception e)
		{
			logger.error("updateUserInvoice>> ",e);
			throw  new BaseException(ResultCode.Error);
		}
		
	}

	@Override
	public Integer countQueryUserInvoiceParam(FrontUser_invoice userInvoice) {
		
		Integer num = frontUserInvoiceMapper.countQueryByParam(userInvoice);
		if(num == null)
		{
			num = 0 ;
		}
		return num;
	}

	@Override
	public FrontUser_invoice queryByInvoiceIndex(Integer userId) {
		FrontUser_invoice frontUser_invoice = new FrontUser_invoice();
		frontUser_invoice.setUserId(userId);
		String address = null;
		Integer addressId = null;
		List<FrontUser_address> list = frontUserAddressMapper.queryByUserId(userId);
		if(list != null && list.size()>0){
			address = list.get(0).getProvince() + list.get(0).getCity() + list.get(0).getArea() + list.get(0).getDetailAddress();
			addressId = list.get(0).getId();
		}
		frontUser_invoice.setDetailAddress(address);
		frontUser_invoice.setAddressId(addressId);
		FrontUser frontUser = frontUserMapper.queryById(userId);
		if(frontUser == null){
			try {
				throw  new BaseException(ResultCode.Error);
			} catch (BaseException e) {
				e.printStackTrace();
			}
		}
		frontUser_invoice.setNickName(frontUser.getNickName());
		frontUser_invoice.setMobile(frontUser.getMobileNum());
		String resUrl = ConfigLoader.getResPictrueURL();
		if(StringUtils.isNotBlank(frontUser.getCoverImageUrl())){
			frontUser_invoice.setCoverImageUrl(resUrl+frontUser.getCoverImageUrl());
		}

		FrontUser_invoice f1 = new FrontUser_invoice();
		f1.setState(300);
		f1.setUserId(userId);
		Integer noNum = frontUserInvoiceMapper.countQueryByParam(f1);
		frontUser_invoice.setNoStateNum(noNum == null?0:noNum);
		
		FrontUser_invoice f2 = new FrontUser_invoice();
		f2.setState(302);
		f2.setUserId(userId);
		Integer yesNum = frontUserInvoiceMapper.countQueryByParam(f2);
		frontUser_invoice.setYesStateNum(yesNum == null?0:yesNum);
		
		DonateRecord donateRecord = new DonateRecord();
		FrontUser_invoice fVo = new FrontUser_invoice();
		//fVo.setState(302);
		fVo.setUserId(userId);
		List<Integer> list3 = new ArrayList<Integer>();
		List<FrontUser_invoice> list2 = frontUserInvoiceMapper.queryByParam(fVo);
		if(list2 != null && list2.size() > 0){
			for (FrontUser_invoice f : list2) {
				String[] list4 =f.getInfo().split("_");
				for (String string : list4) {
					list3.add(Integer.valueOf(string));
				}
			}
			donateRecord.setDonateIds(list3);
			
		}
		
		donateRecord.setUserId(userId);
		Double money = frontUserInvoiceMapper.sumDonatAmount(donateRecord);
		frontUser_invoice.setOpenMoney(new BigDecimal(money==null?0.0:money));
		
		return frontUser_invoice;
	}

	@Override
	public void saveInvoice(Integer userId, String invoiceList, Integer addressId, String invoiceHead)
			throws BaseException {
		try
		{
			DonateRecord donateRecord = new DonateRecord();
			donateRecord.setUserId(userId);
			List<Integer> list3 = new ArrayList<Integer>();
			String[] list = invoiceList.split(",");
			for (String string : list) {
				list3.add(Integer.valueOf(string));
			}
			donateRecord.setDonateIds(list3);
			List<DonateRecord> list2 = donateRecordMapper.selectDonateRecordForInvoice(donateRecord);
			if(list2 == null || list2.size() ==0){
				throw  new BaseException(ResultCode.Error);
			}
			Double donatAmount = 0.0;
			String info = "";
			for (DonateRecord d : list2) {
				donatAmount = MathUtil.add(donatAmount, d.getDonatAmount());
				info = info + "_" + d.getId();
			}
			info = info.substring(1,info.length());
			FrontUser_invoice frontUser_invoice = new FrontUser_invoice();
			frontUser_invoice.setUserId(userId);
			frontUser_invoice.setAddressId(addressId);
			frontUser_invoice.setState(State.notPay);
			frontUser_invoice.setCreateTime(new Date());
			frontUser_invoice.setInfo(info);
			frontUser_invoice.setInvoiceAmount(donatAmount);
			frontUser_invoice.setInvoiceHead(invoiceHead);
			frontUserInvoiceMapper.save(frontUser_invoice);
		}
		catch(Exception e)
		{
			logger.error("saveUserInvoice>> ",e);
			throw  new BaseException(ResultCode.Error);
		}
	}
   

}
