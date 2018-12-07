package com.guangde.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.constants.FrontUserConstants;
import com.guangde.business.constants.ProjectConstants;
import com.guangde.business.dao.*;
import com.guangde.business.entry.*;
import com.guangde.business.enums.ResultEnum;
import com.guangde.business.exception.BaseException;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.ProjectService;
import com.guangde.business.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Service("projectService")
public class ProjectServiceImpl extends BaseService implements ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private ProjectFeedbackMapper projectFeedbackMapper;
	@Autowired
	private DonateRecordMapper donateRecordMapper;
	@Autowired
	private CapitalinoutMapper capitalinoutMapper;
	@Autowired
	private BFileMapper bFileMapper;
	@Autowired
	private ProjectScheduleMapper projectScheduleMapper;
	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private FrontUserMapper frontUserMapper;
	@Autowired
	private ActionUserMapper actionUserMapper;
	@Autowired
	private SystemNotifyMapper systemNotifyMapper;
	@Autowired
	private AuctionProjectMapper auctionProjectMapper;
	@Autowired
	private AnnounceMapper announceMapper;
	@Autowired
	private NewLeaveWordMapper newLeaveWordMapper;
	@Autowired
	private FrontUserMapper userMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductShipmentMapper productShipmentMapper;

	@Autowired
	private ProductShipmentInfoMapper productShipmentInfoMapper;

	@Autowired
	private ProductOrderMapper productOrderMapper;

	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;

	@Override
	public List<Project> queryDonation(Integer hot, String code, int count) {
		if (null == hot || count < 1) {
			return null;
		}

		// 热门
		if (hot == 1) {
			// 1、查询配置表中热门数据
			String hotItem = ConfigCache.getSingle().getConfigValue(
					Constant.ConfigParam.hotItem);

			List<Project> list = new ArrayList<Project>();
			String[] hotItems = hotItem.split("\\,");
			String covIds = "";
			String resUrl = ConfigLoader.getResPictrueURL();

			if (!StringUtils.isEmpty(hotItem)) {
				for (int i = 0; i < hotItems.length; i++) {
					if (StrUtil.isInt(hotItems[i])) {
						Project project = queryById(Integer
								.parseInt(hotItems[i]));
						if (null != project) {
							covIds = project.getCoverImageId() + "";
							if (StrUtil.isNotEmpty(covIds)
									&& !"null".equals(covIds)) {
								List<Integer> idList = StrUtil.convertIdList(
										covIds, Constant.data_separator);

								List<BFile> listb = bFileMapper
										.queryByIdList(idList);

								if (!CollectionUtils.isEmpty(listb)) {

									for (BFile bFile : listb) {
										project.setCoverImageUrl(resUrl
												+ bFile.getUrl());

									}

								}
							}
							list.add(project);
						}
					}

				}

			}

			// 查询点击量最高的
			if (hotItems.length < count) {
				if (hotItems.length == 0) {
					hotItem = "0";
				}
				List<Project> projects = queryProectByHits(hotItem, count
						- hotItems.length);
				if (null != projects && !projects.isEmpty()) {
					for (Project project : projects) {
						covIds = project.getCoverImageId() + "";
						if (StrUtil.isNotEmpty(covIds)
								&& !"null".equals(covIds)) {
							List<Integer> idList = StrUtil.convertIdList(
									covIds, Constant.data_separator);

							List<BFile> listb = bFileMapper
									.queryByIdList(idList);

							if (!CollectionUtils.isEmpty(listb)) {

								for (BFile bFile : listb) {
									project.setCoverImageUrl(resUrl
											+ bFile.getUrl());
								}

							}
						}
						list.add(project);
					}
				}
			}

			return list;
		}
		// 所有
		else if (hot == -1) {
			if (StringUtils.isEmpty(code)) {
				return null;
			}

			return queryHomeDonation(code, count);
		}
		// 参数错误
		else {
			return null;
		}

	}

	@Override
	public Project queryById(Integer id) {
		if (null == id) {
			return null;
		}

		return projectMapper.queryById(id);
	}

	@Override
	public List<Project> queryProjectListByInterest(Integer userId, Integer size) {
		return null;
	}

	@Override
	public List<Project> queryProectByHits(String ids, int count) {
		logger.info("queryProectByHits param : ids >> " + ids + " count >> "
				+ count);

		List<Project> list = projectMapper.queryProectByHits(ids, count);
		if (!CollectionUtils.isEmpty(list)) {
			String resUrl = ConfigLoader.getResPictrueURL();
			String covIds = "";
			for (Project temp : list) {
				if (temp.getCoverImageId() == null) {
					continue;
				}
				covIds = temp.getCoverImageId() + "";
				if (StrUtil.isNotEmpty(covIds) && !"null".equals(covIds)) {
					List<Integer> idList = StrUtil.convertIdList(covIds,
							Constant.data_separator);

					List<BFile> listb = bFileMapper.queryByIdList(idList);

					if (!CollectionUtils.isEmpty(listb)) {

						for (BFile bFile : listb) {
							resUrl = resUrl + bFile.getUrl();
						}

						temp.setCoverImageUrl(resUrl);
					}
				}
			}
		}

		return list;
	}

	@Override
	public List<Project> queryHomeDonation(String field, int count) {
		logger.info("queryHomeDonation param : field >> " + field
				+ " count >> " + count);

		List<Project> list = projectMapper.queryHomeDonation(field, count);

		if (!CollectionUtils.isEmpty(list)) {
			String resUrl = ConfigLoader.getResPictrueURL();
			String covIds = "";
			for (Project temp : list) {
				covIds = temp.getCoverImageId() + "";
				if (StrUtil.isNotEmpty(covIds) && !"null".equals(covIds)) {
					List<Integer> idList = StrUtil.convertIdList(covIds,
							Constant.data_separator);

					List<BFile> listb = bFileMapper.queryByIdList(idList);

					if (!CollectionUtils.isEmpty(listb)) {

						for (BFile bFile : listb) {
							temp.setCoverImageUrl(resUrl + bFile.getUrl());
						}

					}
				}
			}
		}

		return list;
	}

	@Override
	public List<Project> queryProjectList(Project project, int pageNum,
			int pageSize) {
		logger.info("query param:" + project);

		PageHelper.startPage(pageNum, pageSize);

		List<Project> list = null;
		try {
			list = projectMapper.queryProjectList(project);

			if (list != null && list.size() > 0) {
				String resUrl = ConfigLoader.getResPictrueURL();
				for (Project temp : list) {
					if (temp != null && temp.getCoverImageUrl() != null) {
						temp.setCoverImageUrlMiddle(resUrl + temp.getCoverImageUrl());
					}
					Double leaveCryMoney = MathUtil.sub(temp.getCryMoney(), temp.getDonatAmount());
					temp.setLeaveCryMoney(leaveCryMoney < 0 ? 0 : leaveCryMoney);
					if(temp.getDonatAmount().compareTo(0.0) > 0) {
						Double pro = MathUtil.div(temp.getCryMoney(), temp.getDonatAmount(), 2);
						temp.setDonatePercent((pro >= 1.0 ? 1.0 : pro) + "");
					}else{
						temp.setDonatePercent("0");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Project queryProjectDetail(Integer id) {
		logger.info("query param id:" + id);

		Project p = projectMapper.queryById(id);
		if (p == null) {
			return null;
		}

		if( p.getDonatAmount().compareTo(0.0) > 0) {
			Double pro = MathUtil.div(p.getCryMoney(), p.getDonatAmount(), 2);
			p.setDonatePercent((pro >= 1.0 ? 1.0 : pro) + "");
		}else{
			p.setDonatePercent("0");
		}
		String ids = p.getContentImageId();
		String resUrl = ConfigLoader.getResPictrueURL();
		if (StrUtil.isNotEmpty(ids)) {
			List<Integer> idList = StrUtil.convertIdList(ids,
					Constant.data_separator);

			List<BFile> list = bFileMapper.queryByIdList(idList);
			if (!CollectionUtils.isEmpty(list)) {

				for (BFile bFile : list) {
					bFile.setUrl(resUrl + bFile.getUrl());
					if (!StringUtils.isEmpty(bFile.getMiddleUrl())) {
						bFile.setMiddleUrl(resUrl + bFile.getMiddleUrl());
					}
					if (!StringUtils.isEmpty(bFile.getLitterUrl())) {
						bFile.setLitterUrl(resUrl + bFile.getLitterUrl());
					}
				}

				p.setBfileList(list);
			}
		}
		if (p.getCoverImageUrl() != null) {
			p.setCoverImageUrl(resUrl + p.getCoverImageUrl());
		}
		if (p.getCoverImageUrlMiddle() != null) {
			p.setCoverImageUrlMiddle(resUrl + p.getCoverImageUrlMiddle());
		}
		if (p.getCoverImageUrlLitter() != null) {
			p.setCoverImageUrlLitter(resUrl + p.getCoverImageUrlLitter());
		}

		if (p.getVideoCoverImageId() != null) {
			BFile bFile = bFileMapper.queryById(p.getVideoCoverImageId());
			if(bFile != null){
				if(!StringUtils.isEmpty(bFile.getUrl())){
					p.setVideoCoverImageUrl(resUrl + bFile.getUrl());
				}
				if(!StringUtils.isEmpty(bFile.getMiddleUrl())){
					p.setVideoCoverImageMiddleUrl(resUrl + bFile.getMiddleUrl());
				}
				if(!StringUtils.isEmpty(bFile.getLitterUrl())){
					p.setVideoCoverImageLittleUrl(resUrl + bFile.getLitterUrl());
				}
			}
		}

		return p;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void updateDonate(DonateRecord donate, String payType, FrontUser user, String slogans)
			throws BaseException {
		logger.info("updateDonate receive param>>" + donate + " payType >> "
				+ payType);
		//更新用户信息
		try{
			if(user != null && user.getId() != null && (!StringUtils.isBlank(user.getRealName()) || !StringUtils.isBlank(user.getMobileNum()))){
				userMapper.update(user);
			}
		}catch (Exception e){

		}

		//更新一对一

		Integer projectId = donate.getProjectId();

		Project p = projectMapper.queryByLock(projectId);

		if (p == null) {
			throw new BaseException(ResultCode.ParameterError);
		}

		if (p.getState() == ProjectConstants.STATE_JS) {
			throw new BaseException(ResultCode.project_is_end);
		}

		double donateAmount = p.getDonatAmount() + donate.getDonatAmount();


		try {
			// 更新项目表
			Project np = new Project();
			np.setId(p.getId());
			int updateRet = projectMapper.update(np);

			if (updateRet == 1) {
				// 资金进出
				Capitalinout capitalinout = new Capitalinout();
				capitalinout.setUserId(donate.getUserId());
				capitalinout.setType(Constant.CAPITAL_IN);
				capitalinout.setInType(Constant.DONATE);
				capitalinout.setMoney(donate.getDonatAmount());
				capitalinout.setPayType(payType);
				capitalinout.setPayState(Constant.PAY_STATE_300);
				capitalinout.setTranNum(donate.getTranNum());

				capitalinoutMapper.save(capitalinout);

				// 捐款记录表
				donate.setCapitalinoutId(capitalinout.getId());
				FrontUser fu = frontUserMapper
						.queryById(donate.getUserId() == null ? 0 : donate
								.getUserId());
				String donorType = "";
				if (fu == null) {
					donorType = "externalPers";
				} else {
					donorType = UserUtil.getDonorType(fu.getUserType());
				}

				donate.setState(Constant.PAY_STATE_300);
				donate.setDonatTime(DateUtil.getDate());
				if(donate.getNameOpen() == null){
					donate.setNameOpen(0);//默认不匿名
				}
				donateRecordMapper.save(donate);

			}

		}

		catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void updateDonate(DonateRecord donate, String payType, FrontUser user, String slogans, Integer redPacketId)
			throws BaseException {
		logger.info("updateDonate receive param>>" + donate + " payType >> "
				+ payType + " redPacketId >>" + redPacketId + " slogans >>" + slogans);
		if(payType.equals("aliRedType")){
			payType = "alipay";
		}
		if(payType.equals("tenRedType")){
			payType = "tenpay";
		}





		Integer projectId = donate.getProjectId();

		Project p = projectMapper.queryByLock(projectId);

		if (p == null) {
			throw new BaseException(ResultCode.ParameterError);
		}

		if (p.getState() == ProjectConstants.STATE_JS) {
			throw new BaseException(ResultCode.project_is_end);
		}

		try {
			// 更新项目表
			Project np = new Project();
			np.setId(p.getId());
			int updateRet = projectMapper.update(np);
			if (updateRet == 1) {

				// 资金进出
				Capitalinout capitalinout = new Capitalinout();
				capitalinout.setUserId(donate.getUserId());
				capitalinout.setType(Constant.CAPITAL_IN);
				capitalinout.setInType(Constant.DONATE);
				capitalinout.setMoney(donate.getDonatAmount());
				capitalinout.setPayType(payType);
				capitalinout.setPayState(Constant.PAY_STATE_300);
				capitalinout.setTranNum(donate.getTranNum());

				capitalinoutMapper.save(capitalinout);

				// 捐款记录表
				donate.setCapitalinoutId(capitalinout.getId());
				FrontUser fu = frontUserMapper
						.queryById(donate.getUserId() == null ? 0 : donate
								.getUserId());

				donate.setState(Constant.PAY_STATE_300);
				donate.setDonatTime(DateUtil.getDate());
				if(donate.getNameOpen() == null){
					donate.setNameOpen(0);//默认不匿名
				}
				donateRecordMapper.save(donate);
			}

		}

		catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public void updateDonate(DonateRecord donate, Capitalinout capitalinout, String payType) throws BaseException {

	}


	@Override
	public int updateProject(Project project) {
		project.setLastUpdateTime(DateUtil.getDate());
		return projectMapper.update(project);
	}

	@Override
	public void saveProject(Project project) throws BaseException {
		try {
			project.setLastUpdateTime(DateUtil.getDate());
			project.setIsHide(0);
			project.setIsRecommend(0);
			project.setContent(StringUtil.filterEmoji(project.getContent()));
			project.setTitle(StringUtil.filterEmoji(project.getTitle()));
			project.setSubTitle(StringUtil.filterEmoji(project.getSubTitle()));
			projectMapper.save(project);

		} catch (Exception e) {
			logger.error("save database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDonateResult(String tranNum, String payNum,
			boolean isPaySuccess, String errorMsg) throws BaseException {
		logger.info("updateDonateResult>> tranNum = " + tranNum + " payNum = "
				+ payNum + " isPaySuccess = " + isPaySuccess + " errorMsg = "
				+ errorMsg);

		Capitalinout param = new Capitalinout();
		param.setTranNum(tranNum);

		List<Capitalinout> list = capitalinoutMapper.queryByParam(param);

		if (CollectionUtil.isEmpty(list)) {
			logger.info("update DonateResult error, tranNum:" + tranNum);
			throw new BaseException(ResultCode.buy_pay_num_error);
		}

		Capitalinout c = list.get(0);
		if (c.getPayState() == Constant.PAY_STATE_302) {
			logger.info("回调已成功 ， 多次发起回调 capitalinoutId = " + c.getId()
					+ " tranNum = " + tranNum);
			return;
		}
		DonateRecord dparam = new DonateRecord();
		dparam.setCapitalinoutId(c.getId());
		List<DonateRecord> dlist = donateRecordMapper.queryByParam(dparam);

		if (CollectionUtil.isEmpty(dlist)) {
			logger.info("update DonateResult error, capitalinoutId:"
					+ c.getId());
			throw new BaseException(ResultCode.buy_donate_not_found);
		}

		try {
			Capitalinout newCapitalinout = new Capitalinout();
			newCapitalinout.setId(c.getId());
			newCapitalinout.setPayState(isPaySuccess ? Constant.PAY_STATE_302
					: Constant.PAY_STATE_301);
			newCapitalinout.setTranNum(tranNum);
			newCapitalinout.setPayNum(payNum);
			capitalinoutMapper.update(newCapitalinout);

			DonateRecord newDonateRecord = new DonateRecord();
			newDonateRecord.setId(dlist.get(0).getId());
			newDonateRecord.setState(isPaySuccess ? Constant.PAY_STATE_302
					: Constant.PAY_STATE_301);
			donateRecordMapper.update(newDonateRecord);

			Project project = projectMapper.queryById(dlist.get(0)
					.getProjectId());
			if (null == project) {
				logger.info("updateDonateResult  project is null  projectId>> "
						+ dlist.get(0).getProjectId());
			} else
			// 更新项目已募捐金额、用户捐款总额
			{
				if (isPaySuccess) {
					// 本次捐款金额
					double currentAmount = dlist.get(0).getDonatAmount();

					double donatAmount = project.getDonatAmount();
					donatAmount=MathUtil.add(donatAmount, currentAmount);
					DonateRecord dRecord= new DonateRecord();
					dRecord.setProjectId(dlist.get(0).getProjectId());
					dRecord.setState(302);
					dRecord=donateRecordMapper.countByParam(dRecord);
					
					Project p = new Project();
					p.setId(dlist.get(0).getProjectId());
					p.setDonatAmount(donatAmount);

					// 超募金额判断
					if ((project.getIsOverMoney() == null || project.getIsOverMoney() == 0) && p.getDonatAmount() >= project.getCryMoney()) {//无超募
						if(project.getDeadline()==null)//判断项目截止时间；1.有则不作处理；2.无则更新截止时间
							p.setDeadline(DateUtil.getDate());
						p.setState(ProjectConstants.STATE_JS);
					} else if (project.getIsOverMoney() == 1 && project.getOverMoney() != BigDecimal.ZERO
							&& p.getDonatAmount() >= project.getOverMoney().doubleValue()) {//允许超募,超募指定金额
						if(project.getDeadline()==null)//判断项目截止时间；1.有则不作处理；2.无则更新截止时间
							p.setDeadline(DateUtil.getDate());
						p.setState(ProjectConstants.STATE_JS);
					} else if (project.getIsOverMoney() == 1 && project.getOverMoney() == BigDecimal.ZERO) {//允许超募，且可以无限大

					}
					p.setLastUpdateTime(DateUtil.getDate());

					// 捐款笔数
					Integer donationNum = project.getDonationNum() + 1;
					p.setDonationNum(donationNum);

					int ret = projectMapper.update(p);
					if (ret > 0) {
						// 更新用户捐款总额
						FrontUser user = frontUserMapper.queryById(dlist.get(0)
								.getUserId());
						

						double totalAmount = user.getTotalAmount();
						totalAmount += currentAmount;

						user = new FrontUser();
						user.setId(dlist.get(0).getUserId());
						user.setTotalAmount(totalAmount);

						frontUserMapper.update(user);
						// 更新获捐记录的消息记录
						SystemNotify systemNotify = new SystemNotify();
						systemNotify.setUserId(project.getUserId());
						systemNotify.setSender("获捐消息");
						systemNotify.setSubject("捐款记录");
						systemNotify.setContent("爱心用户:" + user.getUserName()
								+ "给你的" + project.getTitle() + "捐赠了"
								+ currentAmount + "元");
						systemNotify.setState(0);
						systemNotify.setIsShow(0);
						systemNotifyMapper.save(systemNotify);
						
						//竞拍回调修改>>>>>>>>>>>
						AuctionProject ap = new AuctionProject();
						ap.setProjectId(p.getId());
						List<AuctionProject> atList= auctionProjectMapper.queryByCache(ap);
						if(atList.size() > 0){
							ap = atList.get(0);
							ap.setState(203);//已支付
							ap.setBelong(user.getId());
							auctionProjectMapper.update(ap);
							p.setCryMoney(p.getDonatAmount());
							p.setState(ProjectConstants.STATE_JS);
							p.setDeadline(DateUtil.getDate());
							projectMapper.update(p);
						}
					}
				}

			}

		} catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateDonateResult(String tranNum, String payNum, String payType) throws BaseException {
		logger.info("updateDonateResult>> tranNum = " + tranNum + " payNum = "
				+ payNum  + " payType = "
				+ payType);

		Capitalinout param = new Capitalinout();
		param.setTranNum(tranNum);

		List<Capitalinout> list = capitalinoutMapper.queryByParam(param);

		if (CollectionUtil.isEmpty(list)) {
			logger.info("update DonateResult error, tranNum:" + tranNum);
			throw new BaseException(ResultCode.buy_pay_num_error);
		}

		Capitalinout c = list.get(0);
		if (c.getPayState() == Constant.PAY_STATE_302) {
			logger.info("回调已成功 ， 多次发起回调 capitalinoutId = " + c.getId()
					+ " tranNum = " + tranNum);
			return;
		}

		c.setPayState(302);
		c.setUpdateTime(new Date());
		c.setPayNum(payNum);
		int res = capitalinoutMapper.update(c);
		if(res <= 0){
			throw new BaseException(ResultEnum.Error);
		}

		if(payType.equals("project")){
			DonateRecord d = donateRecordMapper.queryWeixinPayNoticeByTranNum(c.getTranNum());

			if (d == null) {
				logger.info("update DonateResult error, capitalinoutId:"
						+ c.getId());
				throw new BaseException(ResultCode.buy_donate_not_found);
			}
			/*if(dlist.size() != 1){
				throw new BaseException(ResultCode.Error);
			}
			DonateRecord d = dlist.get(0);*/
			if(d.getState() != 300){
				throw new BaseException(ResultCode.Error);
			}

			d.setState(302);
			res = donateRecordMapper.update(d);
			if(res <= 0){
				throw new BaseException(ResultEnum.Error);
			}

			Project p = projectMapper.queryById(d.getProjectId());
			if(p == null){
				throw new BaseException(ResultEnum.Error);
			}
			p.setDonationNum(p.getDonationNum() + 1);
			p.setDonatAmount(MathUtil.add(p.getDonatAmount(), c.getMoney()));

			res = projectMapper.update(p);
			if(res <= 0){
				throw new BaseException(ResultEnum.Error);
			}

			FrontUser user = userMapper.queryById(c.getUserId());
			user.setDonateNum(user.getDonateNum() + 1);
			user.setTotalAmount(MathUtil.add(user.getTotalAmount(), c.getMoney()));
			res = userMapper.update(user);
			if(res <= 0){
				throw new BaseException(ResultEnum.Error);
			}

		}else if(payType.equals("product")){
			ProductOrder orderVO = new ProductOrder();
			orderVO.setTranNum(tranNum);
			List<ProductOrder> productOrders = productOrderMapper.queryByParam(orderVO);
			if(CollectionUtils.isEmpty(productOrders) || productOrders.size() != 1){
				throw new BaseException(ResultEnum.Error);
			}

			orderVO = productOrders.get(0);
			if(orderVO.getState() != 101){
				throw new BaseException(ResultEnum.Error);
			}

			//更新商品销售额、销售数量、库存
			Product product = productMapper.queryById(orderVO.getProductId());
			product.setSalesMoney(product.getSalesMoney().add(new BigDecimal(c.getMoney())));
			product.setSalesNum(product.getSalesNum()+orderVO.getShipmentNum());
			product.setStockNum(product.getStockNum() - orderVO.getShipmentNum());
			product.setUpdateTime(new Date());
			res = productMapper.update(product);
			if(res <= 0){
				throw new BaseException(ResultEnum.Error);
			}


			// 更新商品订单详情状态
			ProductShipment productShipment = new ProductShipment();
			productShipment.setTranNum(tranNum);
			List<ProductShipment> productShipments = productShipmentMapper.queryByParam(productShipment);
			if(CollectionUtils.isEmpty(productShipments) || productShipments.size() != 1){
				throw new BaseException(ResultEnum.Error);
			}
			productShipment = productShipments.get(0);
			productShipment.setState(103);
			productShipment.setUpdateTime(new Date());
			res = productShipmentMapper.update(productShipment);
			if(res <= 0){
				throw new BaseException(ResultEnum.Error);
			}

			//更新商品规格
			ProductSpecification productSpecification = productSpecificationMapper.queryById(orderVO.getProductSpecificationId());
			productSpecification.setStock(productSpecification.getStock() - orderVO.getShipmentNum());
			productSpecification.setUpdateTime(new Date());
			res = productSpecificationMapper.update(productSpecification);
			if(res <= 0){
				throw new BaseException(ResultEnum.Error);
			}

			//更新sha商品订单状态
			orderVO.setState(103);
			orderVO.setStockNum(productSpecification.getStock());
			res = productOrderMapper.update(orderVO);
			if(res <= 0){
				throw new BaseException(ResultEnum.Error);
			}
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCapitalinoutResult(String tranNum, String payNum,
			boolean isPaySuccess, String errorMsg) throws BaseException {
		logger.info("updateCapitalinoutResult>> tranNum = " + tranNum + " payNum = "
				+ payNum + " isPaySuccess = " + isPaySuccess + " errorMsg = "
				+ errorMsg);

		Capitalinout param = new Capitalinout();
		param.setTranNum(tranNum);

		List<Capitalinout> list = capitalinoutMapper.queryByParam(param);

		if (CollectionUtil.isEmpty(list)) {
			logger.info("update DonateResult error, tranNum:" + tranNum);
			throw new BaseException(ResultCode.buy_pay_num_error);
		}

		Capitalinout c = list.get(0);
		if (c.getPayState() == Constant.PAY_STATE_302) {
			logger.info("回调已成功 ， 多次发起回调 capitalinoutId = " + c.getId()
					+ " tranNum = " + tranNum);
			return;
		}
		try {
			Capitalinout newCapitalinout = new Capitalinout();
			newCapitalinout.setId(c.getId());
			newCapitalinout.setPayState(isPaySuccess ? Constant.PAY_STATE_302
					: Constant.PAY_STATE_301);
			newCapitalinout.setTranNum(tranNum);
			newCapitalinout.setPayNum(payNum);
			capitalinoutMapper.update(newCapitalinout);
		} catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public void updateDonate(DonateRecord donate) throws BaseException {

		Integer projectId = donate.getProjectId();

		Project p = projectMapper.queryByLock(projectId);

		if (p == null) {
			throw new BaseException(ResultCode.ParameterError);
		}



		try {
			Project np = new Project();
			np.setId(p.getId());

			int updateRet = projectMapper.update(np);

			if (updateRet == 1) {

				// 捐款记录设置状态：失败
				DonateRecord newDonateRecord = new DonateRecord();
				newDonateRecord.setId(donate.getId());
				newDonateRecord.setState(Constant.PAY_STATE_301);
				donateRecordMapper.update(newDonateRecord);

				// 资金进出设置状态：失败
				Capitalinout newCapitalinout = new Capitalinout();
				newCapitalinout.setId(donate.getCapitalinoutId());
				newCapitalinout.setPayState(Constant.PAY_STATE_301);

				capitalinoutMapper.update(newCapitalinout);
			}

		} catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}

	}


	@Override
	public void deleteProject(Integer id) throws BaseException {

		try {
			projectMapper.delete(id);
		} catch (Exception e) {
			logger.error("delete database error" + e);
			throw new BaseException(ResultCode.Error);
		}

	}

	@Override
	public List<ProjectSchedule> queryProjectScheduleList(
			ProjectSchedule projectSchedule, int pageNum, int pageSize) {
		logger.info("queryProjectScheduleList param : " + projectSchedule);

		PageHelper.startPage(pageNum, pageSize);
		if (projectSchedule.getOperatorTime() == null) {
			projectSchedule.setOperatorTime(new Date());
		}
		List<ProjectSchedule> list = projectScheduleMapper
				.queryByParam(projectSchedule);

		return list;
	}

	@Override
	public void saveReport(Report report) throws BaseException {
		try {
			reportMapper.save(report);

			if (report.getType() != 3 && report.getType() != 5) {

				ProjectFeedback p = new ProjectFeedback();
				p.setAudit(report.getId());
				p.setAuditState(203);
				p.setContent(report.getContent());
				p.setContentImageId(report.getContentImageId());
				p.setFeedbackTime(DateUtil.getDate());
				p.setFeedbackPeople(report.getReportPeople());
				p.setProjectId(report.getProjectId());
				p.setSource("home");
				projectFeedbackMapper.save(p);

				Project project = new Project();
				project.setId(report.getProjectId());
				project.setLastFeedbackTime(DateUtil.getDate());

				projectMapper.update(project);
			}

		} catch (Exception e) {
			logger.error("save database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public List<Report> queryReportList(Report report, int pageNum, int pageSize) {
		logger.info("query reportList param : " + report);

		PageHelper.startPage(pageNum, pageSize);
		List<Report> list = reportMapper.queryByParam(report);
		String ids = "";
		String resUrl = ConfigLoader.getResPictrueURL();

		FrontUser user = null;
		ActionUser auser = null;
		for (Report r : list) {
			ids = r.getContentImageId();
			if (StrUtil.isNotEmpty(ids)) {
				List<Integer> idList = StrUtil.convertIdList(ids,
						Constant.data_separator);

				List<BFile> blist = bFileMapper.queryByIdList(idList);

				if (!CollectionUtils.isEmpty(list)) {

					List<String> imageList = new ArrayList<String>();

					for (BFile bFile : blist) {
						imageList.add(resUrl + bFile.getUrl());
					}

					r.setContentImageUrl(imageList);
				}
			}
			if (r.getType() == 6) {
				auser = actionUserMapper.queryById(r.getReportPeople());
				if (null != auser) {
					String username = auser.getRealName();
					r.setReportPeopleName(username);
				}
			} else {
				user = frontUserMapper.queryById(r.getReportPeople());
				if (null != user) {
					r.setReportPeopleName(user.getUserName());
				}

			}
		}
		return list;
	}


	@Override
	public List<Project> countKeeperProjectByState(Project project) {
		logger.info("query params : " + project);

		List<Project> list = projectMapper.countKeeperProjectByState(project);

		return list;
	}

	
	@Override
	public void updateWeRunDonate(DonateRecord donate, Capitalinout capital,
			String payType) throws BaseException {
		logger.info("updateWeRunDonate receive param>>" + donate + " payType = "
				+ payType + " capital = " + capital);

		Integer projectId = donate.getProjectId();
		logger.info("projectId>>>>>>"+projectId);
		Project p = projectMapper.queryByLock(projectId);
		if (p == null) {
			logger.info("project is null error>>>>>");
			throw new BaseException(ResultCode.ParameterError);
		}

		if (p.getState() == ProjectConstants.STATE_JS) {
			logger.info("project is end error>>>>>");
			throw new BaseException(ResultCode.project_is_end);
		}

		FrontUser fu = frontUserMapper.queryByLock(donate.getUserId() == null ? 0 : donate.getUserId());



		try {
			// 更新项目表
			Project np = new Project();
			np.setId(p.getId());

			int updateRet = projectMapper.update(np);

			if (updateRet == 1) {
				int ret = 0;
				//TODO 更新企业捐赠金额 (donate.extensionpeople add companyUserId)

				/*
				else{
					logger.info("企业捐步信息表出错donate.getExtensionPeople()="+donate.getExtensionPeople()+","+companies.size()+"个相同企业存在");
				}
				*/
				if (ret > 0) {
					// 资金进出
					Capitalinout capitalinout = new Capitalinout();
					capitalinout.setUserId(donate.getUserId());
					capitalinout.setType(Constant.CAPITAL_IN);
					capitalinout.setInType(Constant.DONATE);
					capitalinout.setMoney(donate.getDonatAmount());
					capitalinout.setPayType("freezType");

					capitalinout.setPayState(Constant.PAY_STATE_302);
					capitalinout.setTranNum(donate.getTranNum());

					capitalinoutMapper.save(capitalinout);
					

					
					
					// 捐款记录表
					donate.setCapitalinoutId(capitalinout.getId());


					donate.setState(Constant.PAY_STATE_302);
					donate.setDonatTime(DateUtil.getDate());
					
					donateRecordMapper.save(donate);

					// 更新项目已募捐金额
					double donatAmount =MathUtil.add(p.getDonatAmount(), donate.getDonatAmount());
					DonateRecord dRecord= new DonateRecord();
					dRecord.setProjectId(p.getId());
					dRecord.setState(302);
					dRecord=donateRecordMapper.countByParam(dRecord);

					Project pro = new Project();
					pro.setId(p.getId());
					pro.setDonatAmount(donatAmount);
					pro.setLastUpdateTime(new Date());

					//TODO 超募金额判断
						if ((p.getIsOverMoney() == null || p.getIsOverMoney() == 0) && donatAmount >= p.getCryMoney()) {//无超募
							if(p.getDeadline()==null)//判断项目截止时间；1.有则不作处理；2.无则更新截止时间
								pro.setDeadline(DateUtil.getDate());
							pro.setState(ProjectConstants.STATE_JS);
						} else if (p.getIsOverMoney() == 1 && p.getOverMoney() != BigDecimal.ZERO
								&& donatAmount >= p.getOverMoney().doubleValue()) {//允许超募,超募指定金额
							pro.setState(ProjectConstants.STATE_JS);
							if(p.getDeadline()==null)//判断项目截止时间；1.有则不作处理；2.无则更新截止时间
								pro.setDeadline(DateUtil.getDate());
						} else if (p.getIsOverMoney() == 1 && p.getOverMoney() == BigDecimal.ZERO) {//允许超募，且可以无限大

						}

					pro.setLastUpdateTime(DateUtil.getDate());
					
					// 捐款笔数
					Integer donationNum = p.getDonationNum() + 1;
					pro.setDonationNum(donationNum);
					projectMapper.update(pro);
				}
			}
		}
		catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void updateDonate2(DonateRecord donate,Capitalinout capitalinout, String payType)
			throws BaseException {
		logger.info("updateDonate receive param>>" + donate + " payType >> "
				+ payType);

		Integer projectId = donate.getProjectId();

		Project p = projectMapper.queryByLock(projectId);

		if (p == null) {
			throw new BaseException(ResultCode.ParameterError);
		}

		if (p.getState() == ProjectConstants.STATE_JS) {
			throw new BaseException(ResultCode.project_is_end);
		}


		double donateAmount = p.getDonatAmount() + donate.getDonatAmount();



		try {
			// 更新项目表
			Project np = new Project();
			np.setId(p.getId());


			int updateRet = projectMapper.update(np);

			if (updateRet == 1) {
				// 资金进出
				capitalinout.setUserId(donate.getUserId());
				capitalinout.setType(Constant.CAPITAL_IN);
				capitalinout.setInType(Constant.DONATE);
				capitalinout.setMoney(donate.getDonatAmount());
				capitalinout.setPayType(payType);
				capitalinout.setPayState(Constant.PAY_STATE_300);
				capitalinout.setTranNum(donate.getTranNum());

				capitalinoutMapper.save(capitalinout);

				// 捐款记录表
				donate.setCapitalinoutId(capitalinout.getId());
				FrontUser fu = frontUserMapper
						.queryById(donate.getUserId() == null ? 0 : donate
								.getUserId());

				donate.setState(Constant.PAY_STATE_300);
				donate.setDonatTime(DateUtil.getDate());
				if(donate.getNameOpen() == null){
					donate.setNameOpen(0);//默认不匿名
				}
				donateRecordMapper.save(donate);

			}

		}

		catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public void updateDonate(DonateRecord donate, Capitalinout capitalinout, String payType, Object object, Object  userRedpackets, Object o) throws BaseException {

	}


	@Override
	public void updateDonateResult(String tranNum, String payNum,
			boolean isPaySuccess, String errorMsg, String payType)
			throws BaseException {
		logger.info("updateDonateResult>> tranNum = " + tranNum + " payNum = "
				+ payNum + " isPaySuccess = " + isPaySuccess + " errorMsg = "
				+ errorMsg + " payType = " + payType);

		Capitalinout param = new Capitalinout();
		param.setTranNum(tranNum);

		List<Capitalinout> list = capitalinoutMapper.queryByParam(param);

		if (CollectionUtil.isEmpty(list)) {
			logger.info("update DonateResult error, tranNum:" + tranNum);
			throw new BaseException(ResultCode.buy_pay_num_error);
		}

		Capitalinout c = list.get(0);
		if (c.getPayState() == Constant.PAY_STATE_302) {
			logger.info("回调已成功 ， 多次发起回调 capitalinoutId = " + c.getId()
					+ " tranNum = " + tranNum);
			return;
		}

		DonateRecord dparam = new DonateRecord();
		dparam.setCapitalinoutId(c.getId());
		List<DonateRecord> dlist = donateRecordMapper.queryByParam(dparam);

		if (CollectionUtil.isEmpty(dlist)) {
			logger.info("update DonateResult error, capitalinoutId:"
					+ c.getId());
			throw new BaseException(ResultCode.buy_donate_not_found);
		}

		try {
			Integer payState = isPaySuccess ? Constant.PAY_STATE_302
					: Constant.PAY_STATE_301;
			FrontUser fu = frontUserMapper.queryByLock(c.getUserId());
			Double balance = c.getMoney() + fu.getAvailableBalance();
			Capitalinout newCapitalinout = new Capitalinout();
			newCapitalinout.setId(c.getId());
			newCapitalinout.setPayState(payState);
			newCapitalinout.setTranNum(tranNum);
			newCapitalinout.setPayNum(payNum);
			newCapitalinout.setBalance(balance);
			int ret = capitalinoutMapper.update(newCapitalinout);

			if (ret > 0) {

				FrontUser newfu = new FrontUser();
				newfu.setId(fu.getId());
				newfu.setAvailableBalance(balance);
				newfu.setBalance(balance);
				frontUserMapper.update(newfu);

				fu = frontUserMapper.queryByLock(fu.getId());

				DonateRecord donateRecord = donateRecordMapper
						.queryByDonateId(dlist.get(0).getId());

				if (fu.getAvailableBalance() < donateRecord.getDonatAmount()) {
					payState = Constant.PAY_STATE_301;
					logger.info("availableBalance = "
							+ fu.getAvailableBalance() + " donatAmount = "
							+ donateRecord.getDonatAmount());
					throw new BaseException(ResultCode.buy_not_enough_amount);
				} else {
					balance = fu.getAvailableBalance()
							- donateRecord.getDonatAmount();
					newfu = new FrontUser();
					newfu.setId(fu.getId());
					newfu.setAvailableBalance(balance);
					newfu.setBalance(balance);
					frontUserMapper.update(newfu);

					newCapitalinout = new Capitalinout();
					newCapitalinout.setUserId(fu.getId());
					newCapitalinout.setType(Constant.CAPITAL_IN);
					newCapitalinout.setInType(Constant.DONATE);
					newCapitalinout.setMoney(donateRecord.getDonatAmount());
					newCapitalinout.setBalance(balance);
					newCapitalinout.setPayState(payState);
					newCapitalinout.setTranNum(StringUtil.uniqueCode());
					newCapitalinout.setPayNum(tranNum);
					newCapitalinout.setPayType("freezType");
					newCapitalinout.setSource(c.getSource());

					capitalinoutMapper.save(newCapitalinout);

					DonateRecord newDonateRecord = new DonateRecord();
					newDonateRecord.setId(donateRecord.getId());
					newDonateRecord.setState(payState);
					newDonateRecord.setCapitalinoutId(newCapitalinout.getId());
					donateRecordMapper.update(newDonateRecord);
				}
			}

			Project project = projectMapper.queryById(dlist.get(0)
					.getProjectId());
			if (null == project) {
				logger.info("updateDonateResult  project is null  projectId>> "
						+ dlist.get(0).getProjectId());
			} else
			// 更新项目已募捐金额、用户捐款总额
			{
				if (isPaySuccess) {
					// 本次捐款金额
					double currentAmount = dlist.get(0).getDonatAmount();

					double donatAmount = project.getDonatAmount();
					donatAmount = MathUtil.add(donatAmount,currentAmount);
					DonateRecord dRecord= new DonateRecord();
					dRecord.setProjectId(dlist.get(0).getProjectId());
					dRecord.setState(302);
					dRecord=donateRecordMapper.countByParam(dRecord);

					
					Project p = new Project();
					p.setId(dlist.get(0).getProjectId());
					p.setDonatAmount(donatAmount);
					p.setLastUpdateTime(new Date());
					//超募金额判断
						if ((project.getIsOverMoney() == null || project.getIsOverMoney() == 0) && donatAmount >= project.getCryMoney()) {//无超募
							p.setState(ProjectConstants.STATE_JS);
							if(p.getDeadline()==null)//判断项目截止时间；1.有则不作处理；2.无则更新截止时间
								p.setDeadline(DateUtil.getDate());
						} else if (project.getIsOverMoney() == 1 && project.getOverMoney() != BigDecimal.ZERO
								&& donatAmount >= project.getOverMoney().doubleValue()) {//允许超募,超募指定金额
							p.setState(ProjectConstants.STATE_JS);
							if(p.getDeadline()==null)//判断项目截止时间；1.有则不作处理；2.无则更新截止时间
								p.setDeadline(DateUtil.getDate());
						} else if (project.getIsOverMoney() == 1 && project.getOverMoney() == BigDecimal.ZERO) {//允许超募，且可以无限大

						}

					p.setLastUpdateTime(DateUtil.getDate());

					// 捐款笔数
					Integer donationNum = project.getDonationNum() + 1;
					p.setDonationNum(donationNum);

					ret = projectMapper.update(p);
					if (ret > 0) {
						// 更新用户捐款总额
						FrontUser user = frontUserMapper.queryById(dlist.get(0)
								.getUserId());
						

						
						double totalAmount = user.getTotalAmount();
						totalAmount += currentAmount;
						user = new FrontUser();
						user.setId(dlist.get(0).getUserId());
						user.setTotalAmount(totalAmount);

						frontUserMapper.update(user);
					}
				}

			}

		} catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public List<ProjectFeedback> queryCareProjectFeedbackList(
			ProjectFeedback projectFeedback, int pageNum, int pageSize) {

		logger.info("query param:" + projectFeedback);

		PageHelper.startPage(pageNum, pageSize);

		List<ProjectFeedback> list = projectFeedbackMapper
				.queryCareProjectFeedbackList(projectFeedback);

		String ids = "";
		String resUrl = ConfigLoader.getResPictrueURL();
		for (ProjectFeedback p : list) {

			if (p.getHeadImageUrl() != null) {
				p.setHeadImageUrl(resUrl + p.getHeadImageUrl());
			}

			ids = p.getContentImageId();
			if (StrUtil.isNotEmpty(ids)) {
				List<Integer> idList = StrUtil.convertIdList(ids,
						Constant.data_separator);

				List<BFile> blist = bFileMapper.queryByIdList(idList);

				if (!CollectionUtils.isEmpty(list)) {

					List<String> imageList = new ArrayList<String>();

					for (BFile bFile : blist) {
						imageList.add(resUrl + bFile.getUrl());
					}

					p.setContentImageUrl(imageList);
				}
			}
		}

		return list;
	}

	@Override
	public List<ProjectFeedback> queryH5ProjectFeedbackList(
			ProjectFeedback projectFeedback, int pageNum, int pageSize) {

		logger.info("query param:" + projectFeedback);

		PageHelper.startPage(pageNum, pageSize);

		List<ProjectFeedback> list = projectFeedbackMapper
				.queryH5ProjectFeedbackList(projectFeedback);

		String ids = "";
		String resUrl = ConfigLoader.getResPictrueURL();
		if(list != null && list.size() > 0){
		for (ProjectFeedback p : list) {

			if (p.getHeadImageUrl() != null) {
				if(!p.getHeadImageUrl().contains("http://")){
					p.setHeadImageUrl(resUrl + p.getHeadImageUrl());
				}else{
					p.setHeadImageUrl(p.getHeadImageUrl());
				}
			}

			ids = p.getContentImageId();
			if (StrUtil.isNotEmpty(ids)) {
				List<Integer> idList = StrUtil.convertIdList(ids,
						Constant.data_separator);

				List<BFile> blist = bFileMapper.queryByIdList(idList);

				if (!CollectionUtils.isEmpty(list)) {

					List<String> imageList = new ArrayList<String>();

					for (BFile bFile : blist) {
						imageList.add(resUrl + bFile.getUrl());
					}

					p.setContentImageUrl(imageList);
				}
			}
		}
		}
		return list;
	}

	@Override
	public Integer countCareProjectFeedbackList(ProjectFeedback projectFeedback) {

		Integer count = projectFeedbackMapper
				.countCareProjectFeedbackList(projectFeedback);

		return count;
	}

	@Override
	public List<Project> queryProjectListNew(Project project, int pageNum,
			int pageSize,int param1,int param2) {
		List<Project> list = null;
	    DonateRecord param;
	    try
	    {
	      if(project.getIds()==null){
	      String projectIds = null;
	      FrontUser user = (FrontUser)this.frontUserMapper.queryById(project.getUserId().intValue());
	      if (user != null)
	      {
	        param = new DonateRecord();
	        param.setUserId(project.getUserId());
	        param.setState(Integer.valueOf(302));
	        List<DonateRecord> records = this.donateRecordMapper.queryByParamUserId(param);
	        for (int i = 0; i < records.size(); i++) {
	          if (i == 0) {
	            projectIds = ((DonateRecord)records.get(i)).getProjectId().toString();
	          } else {
	            projectIds = projectIds + "," + ((DonateRecord)records.get(i)).getProjectId().toString();
	          }
	        }
	        System.out.println(projectIds);
	        if(projectIds==null){
	        	projectIds="0,-1";
	        }
	        List<Integer> idList = StrUtil.convertIdList(projectIds, ",");
	        project.setIds(idList);
	      }}
	      PageHelper.startPage(pageNum, pageSize);
	      if(project.getUserId()!=0)
	    	 project.setUserId(null);
	      list = this.projectMapper.queryByParamNew(project);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    if (!CollectionUtils.isEmpty(list))
	    {
	      String resUrl = ConfigLoader.getResPictrueURL();
	      for (Project temp : list) {
	        if (temp.getCoverImageUrl() != null) {
	          temp.setCoverImageUrl(resUrl + temp.getCoverImageUrl());
	        }
	      }
	    }
	    return list;
		
	}
	@Override
	public List<Project> queryProjectListNew(Project project, int pageNum,
			int pageSize) {
		logger.info("query param:" + project);

		PageHelper.startPage(pageNum, pageSize);

		List<Project> list = null;
		try {
			list = projectMapper.queryByParamNew(project);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!CollectionUtils.isEmpty(list)) {
			String resUrl = ConfigLoader.getResPictrueURL();

			for (Project temp : list) {
				if (temp.getCoverImageUrl() != null) {
					temp.setCoverImageUrl(resUrl + temp.getCoverImageUrl());
				}
			}
		}

		return list;
	}
	
	@Override
	public List<Project> queryShareProject(Project project, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Project> list = projectMapper.queryShareProject(project);
		for (Project p : list) {
			String resUrl = ConfigLoader.getResPictrueURL();
			if (p.getCoverImageUrl() != null) {
				p.setCoverImageUrl(resUrl + p.getCoverImageUrl());
			}

		}
		return list;
	}

	@Override
	public Integer countShareProjectList(Project project) {

		return projectMapper.countQueryShareProject(project);
	}

	@Override
	public Integer countShareProjectListUser(Project project) {
		
		return projectMapper.countQueryShareProjectUser(project);
	}

	@Override
	public Double sumShareProjectListUser(Project project) {

		return projectMapper.sumQueryShareProject(project);
	}

	@Override
	public Integer countDonatePeopleByUser(Project project) {

		return projectMapper.countDonatePeopleByUser(project);
	}

	@Override
	public Project countDonatePeopleByUserId(Project project) {

		return projectMapper.countDonatePeopleByUserId(project);
	}
	
	@Override
	public List<Project> queryOneLevel(Project project) {
		
		return projectMapper.queryOneLevel(project);
	}

	@Override
	public List<Project> queryTwoLevel(Project project) {
		return projectMapper.queryTwoLevel(project);
	}
	
	@Override
	public void updateBatchDonate(DonateRecord donate, Capitalinout capital,
			String payType) throws BaseException {
		logger.info("updateBatchDonate receive param>>" + donate + " payType = "
				+ payType + " capital = " + capital);


		FrontUser fu = frontUserMapper
				.queryByLock(donate.getUserId() == null ? 0 : donate
						.getUserId());
		/*if (capital == null) {
			if (fu.getAvailableBalance() < donate.getDonatAmount()) {
				logger.info("企业用户余额募捐余额不足   availableBalance "
						+ fu.getAvailableBalance() + " < donatAmount "
						+ fu.getAvailableBalance());
				throw new BaseException(ResultCode.buy_not_enough_amount);
			}
		}*/

		try {
				if (payType.equals(Constant.FREEZ_PAY)) {
					// 企业账户余额足够支付
					if (capital == null) {
						FrontUser newfu = new FrontUser();
						newfu.setId(fu.getId());
						newfu.setAvailableBalance(fu.getAvailableBalance()
								- donate.getDonatAmount());
						newfu.setBalance(fu.getAvailableBalance()
								- donate.getDonatAmount());
						newfu.setTotalAmount(fu.getTotalAmount()
								+ donate.getDonatAmount());

						int ret = frontUserMapper.update(newfu);
						if (ret > 0) {
							// 资金进出
							Capitalinout capitalinout = new Capitalinout();
							capitalinout.setUserId(donate.getUserId());
							capitalinout.setType(Constant.CAPITAL_IN);
							capitalinout.setInType(Constant.BATCH_DONATE_INT);
							capitalinout.setMoney(donate.getDonatAmount());
							capitalinout.setPayType(payType);

							capitalinout.setPayState(Constant.PAY_STATE_302);
							capitalinout.setTranNum(donate.getTranNum());

							capitalinout.setBalance(fu.getAvailableBalance()
									- donate.getDonatAmount());
							capitalinoutMapper.save(capitalinout);

							// 捐款记录表
							donate.setCapitalinoutId(capitalinout.getId());
							String donorType = "";
							donorType = UserUtil.getDonorType(fu
									.getUserType());

							donate.setState(Constant.PAY_STATE_305);
							donate.setDonatTime(DateUtil.getDate());

							donateRecordMapper.save(donate);

						}
					}
					// 账户批量捐余额不足，先充值
					else {
						// 资金进出
						capital.setUserId(donate.getUserId());
						capital.setType(Constant.CAPITAL_IN);
						capital.setPayState(Constant.PAY_STATE_300);
						capital.setInType(Constant.BATCH_DONATE_INT);
						capital.setBalance(fu.getBalance() == null ? 0 : fu
								.getBalance());
						capitalinoutMapper.save(capital);

						// 捐款记录表
						donate.setCapitalinoutId(capital.getId());

						donate.setState(Constant.PAY_STATE_300);
						donate.setDonatTime(DateUtil.getDate());

						donateRecordMapper.save(donate);
					}
				}else {
					// 资金进出
					Capitalinout capitalinout = new Capitalinout();
					capitalinout.setUserId(donate.getUserId());
					capitalinout.setType(Constant.CAPITAL_IN);
					capitalinout.setInType(Constant.BATCH_DONATE_INT);
					capitalinout.setMoney(donate.getDonatAmount());
					capitalinout.setPayType(payType);
					capitalinout.setPayState(Constant.PAY_STATE_300);
					capitalinout.setTranNum(donate.getTranNum());

					capitalinoutMapper.save(capitalinout);

					// 捐款记录表
					donate.setCapitalinoutId(capitalinout.getId());


					donate.setState(Constant.PAY_STATE_300);
					donate.setDonatTime(DateUtil.getDate());

					donateRecordMapper.save(donate);
				}
		}catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}
	
	@Override
	public void updateBatchBuyDonateResult(String tranNum, String payNum,
			boolean isPaySuccess, String errorMsg, String payType)
			throws BaseException {
		logger.info("batchBuyDonateResult>> tranNum = " + tranNum + " payNum = "
				+ payNum + " isPaySuccess = " + isPaySuccess + " errorMsg = "
				+ errorMsg + " payType = " + payType);

		Capitalinout param = new Capitalinout();
		param.setTranNum(tranNum);

		List<Capitalinout> list = capitalinoutMapper.queryByParam(param);

		if (CollectionUtil.isEmpty(list)) {
			logger.info("update DonateResult error, tranNum:" + tranNum);
			throw new BaseException(ResultCode.buy_pay_num_error);
		}

		Capitalinout c = list.get(0);
		if (c.getPayState() == Constant.PAY_STATE_302) {
			logger.info("回调已成功 ， 多次发起回调 capitalinoutId = " + c.getId()
					+ " tranNum = " + tranNum);
			return;
		}

		DonateRecord dparam = new DonateRecord();
		dparam.setCapitalinoutId(c.getId());
		dparam.setOrderBy("coverImageId");
		List<DonateRecord> dlist = donateRecordMapper.queryByParam(dparam);

		if (CollectionUtil.isEmpty(dlist)) {
			logger.info("update DonateResult error, capitalinoutId:"
					+ c.getId());
			throw new BaseException(ResultCode.buy_donate_not_found);
		}

		try {
			Integer payState = isPaySuccess ? Constant.PAY_STATE_302
					: Constant.PAY_STATE_301;
			FrontUser fu = frontUserMapper.queryById(c.getUserId());
			Double balance = c.getMoney() + fu.getAvailableBalance();
			Capitalinout newCapitalinout = new Capitalinout();
			newCapitalinout.setId(c.getId());
			newCapitalinout.setPayState(Constant.PAY_STATE_302);
			newCapitalinout.setTranNum(tranNum);
			newCapitalinout.setPayNum(payNum);
			newCapitalinout.setBalance(balance);
			int ret = capitalinoutMapper.update(newCapitalinout);

			if (ret > 0) {

				if(payType.equals(Constant.FREEZ_PAY)){
					fu.setAvailableBalance(balance);
					fu.setBalance(balance);
					frontUserMapper.update(fu);
					DonateRecord donateRecord = donateRecordMapper
							.queryByDonateId(dlist.get(0).getId());

					if (fu.getAvailableBalance() < donateRecord.getDonatAmount()) {
						payState = Constant.PAY_STATE_301;
						logger.info("availableBalance = "
								+ fu.getAvailableBalance() + " donatAmount = "
								+ donateRecord.getDonatAmount());
						throw new BaseException(ResultCode.buy_not_enough_amount);
					} else {
						balance = fu.getAvailableBalance()
								- donateRecord.getDonatAmount();
						fu.setId(fu.getId());
						fu.setAvailableBalance(balance);
						fu.setBalance(balance);
						frontUserMapper.update(fu);

						newCapitalinout = new Capitalinout();
						newCapitalinout.setUserId(fu.getId());
						newCapitalinout.setType(Constant.CAPITAL_IN);
						newCapitalinout.setInType(Constant.BATCH_DONATE_INT);
						newCapitalinout.setMoney(donateRecord.getDonatAmount());
						newCapitalinout.setBalance(balance);
						newCapitalinout.setPayState(payState);
						newCapitalinout.setTranNum(StringUtil.uniqueCode());
						newCapitalinout.setPayNum(tranNum);
						newCapitalinout.setPayType("freezType");
						newCapitalinout.setSource(c.getSource());
						capitalinoutMapper.save(newCapitalinout);

						DonateRecord newDonateRecord = new DonateRecord();
						newDonateRecord.setId(donateRecord.getId());
						newDonateRecord.setState(Constant.PAY_STATE_305);
						newDonateRecord.setCapitalinoutId(newCapitalinout.getId());
						donateRecordMapper.update(newDonateRecord);
					}
				}else {

					DonateRecord newDonateRecord = new DonateRecord();
					newDonateRecord.setId(dlist.get(0).getId());
					newDonateRecord.setState(isPaySuccess ? Constant.PAY_STATE_305
							: Constant.PAY_STATE_301);
					donateRecordMapper.update(newDonateRecord);
				}
			}
				
				// 更新项目已募捐金额、用户捐款总额
				if (isPaySuccess) {
					// 本次捐款金额
					double currentAmount = dlist.get(0).getDonatAmount();
					// 更新用户捐款总额
					FrontUser user = frontUserMapper.queryById(dlist.get(0)
							.getUserId());
					double totalAmount = user.getTotalAmount();
					totalAmount += currentAmount;
					user = new FrontUser();
					user.setId(dlist.get(0).getUserId());
					user.setTotalAmount(totalAmount);
					frontUserMapper.update(user);
				}
		} catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public Integer countProjectNumByTags(Project project) {
		
		return projectMapper.countProjectNumByTags(project);
	}
	
	@Override
	public Integer countUserDonateNum(Project project,int userId,int state){
		List<Project> list = null;
	    try
	    {
	      List<Integer> projectIds = new ArrayList();
	      FrontUser user = (FrontUser)this.frontUserMapper.queryById(userId);
	      if (user != null)
	      {
	        DonateRecord param = new DonateRecord();
	        param.setUserId(Integer.valueOf(userId));
	        param.setState(Integer.valueOf(302));
	        List<DonateRecord> records = this.donateRecordMapper.queryByParamUserId(param);
	        if (records.size() > 0) {
	          for (DonateRecord record : records) {
	            projectIds.add(record.getProjectId());
	          }
	        }
	        if(projectIds.size()==0)
	        	project.setIds(null);
	        else
	        	project.setIds(projectIds);
	        if (state == 0) {//未捐
	          project.setDonateState(Integer.valueOf(0));
	        } else if (state == 1) {//已捐
	          project.setDonateState(Integer.valueOf(1));
	        }
	      }
	      project.setUserId(null);
	      list = this.projectMapper.queryByParamNew(project);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return Integer.valueOf(list.size());
		
	}

	@Override
	public List<Project> queryUCenterProjectlist(Project project,
			Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
		List<Project> list = null;
		try {
			list = projectMapper.queryUCenterProjectlist(project);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!CollectionUtils.isEmpty(list)) {
			String resUrl = ConfigLoader.getResPictrueURL();

			for (Project temp : list) {
				if (temp.getCoverImageUrl() != null) {
					temp.setCoverImageUrl(resUrl + temp.getCoverImageUrl());
				}
			}
		}
		return list;
	}



	@Override
	public Integer countLeaveWordNum(NewLeaveWord newLeaveWord){
		logger.info("query params : " + newLeaveWord);
		return newLeaveWordMapper.countLeaveWordNum(newLeaveWord);
	}
	
	@Override
	public List<Project> queryProjectByIds(Project project,Integer pageNum,Integer pageSize){
		logger.info("query param:" + project);
		PageHelper.startPage(pageNum, pageSize);
		List<Project> list = null;
		try {
			list = projectMapper.queryProjectByIds(project);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!CollectionUtils.isEmpty(list)) {
			String resUrl = ConfigLoader.getResPictrueURL();

			for (Project temp : list) {
				if (temp.getCoverImageUrl() != null) {
					temp.setCoverImageUrl(resUrl + temp.getCoverImageUrl());
				}
			}
		}

		return list;
	}
	
	@Override
	public List<Project> queryRandomProjectList(Project project,Integer pageNum,Integer pageSize){
		logger.info("query param:" + project);
		PageHelper.startPage(pageNum, pageSize);
		List<Project> list = null;
		try {
			list = projectMapper.queryRandomProjectList(project);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!CollectionUtils.isEmpty(list)) {
			String resUrl = ConfigLoader.getResPictrueURL();

			for (Project temp : list) {
				if (temp.getCoverImageUrl() != null) {
					temp.setCoverImageUrl(resUrl + temp.getCoverImageUrl());
				}
			}
		}

		return list;
	}
	
	@Override
	public List<Project> queryProjectAndUserInfo(Project project){
		logger.info("query param:" + project);
		List<Project> list = null;
		try {
			list = projectMapper.queryProjectAndUserInfo(project);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*if (!CollectionUtils.isEmpty(list)) {
			String resUrl = ConfigLoader.getResPictrueURL();

			for (Project temp : list) {
				if (temp.getCoverImageUrl() != null) {
					temp.setCoverImageUrl(resUrl + temp.getCoverImageUrl());
				}
			}
		}*/

		return list;
	}

	@Override
	public List<Integer> queryProjectIds(Integer userId) {
		List<Integer> list = projectMapper.queryProjectIds(userId);
		return list;
	}
	
	@Override
	public List<Project> queryProjectUserIds(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
		List<Project> list = null;
		try {
			list = projectMapper.queryProjectUserIds();
			String resUrl = ConfigLoader.getResPictrueURL();
			if(list != null && list.size() > 0){
			for (Project p : list) {
					if(p.getCoverImageId()!=null){
						BFile bFile = bFileMapper.queryById(p.getCoverImageId());
						if(bFile!=null && bFile.getUrl()!=null){
							if(!bFile.getUrl().contains("http://")){
								p.setCoverImageUrl(resUrl + bFile.getUrl());
							}else{
								p.setCoverImageUrl(bFile.getUrl());
							}
					}
				}
			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int queryOneAidHelpByprojectId(Integer projectId) {
		int count=projectMapper.queryOneAidHelpByprojectId(projectId);
		return count;
	}

	@Override
	public List<Project> queryProjectBy30(int pageNum,
											 int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Project> list = null;
		try {
			list = projectMapper.queryProjectBy30();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!CollectionUtils.isEmpty(list)) {
			String resUrl = ConfigLoader.getResPictrueURL();

			for (Project temp : list) {
				if (temp.getCoverImageUrl() != null) {
					temp.setCoverImageUrl(resUrl + temp.getCoverImageUrl());
				}
			}
		}

		return list;
	}

	@Override
	public List<ProjectFeedback> queryByUserIdSelectList(
			Integer userId, int pageNum, int pageSize) {
		logger.info("query param:" + userId);

		PageHelper.startPage(pageNum, pageSize);
		List<ProjectFeedback> list = null;
		try {
			list = projectFeedbackMapper.queryH5ProjectFeedbackByUserList(userId);
			String ids = "";
			String resUrl = ConfigLoader.getResPictrueURL();
			if(list != null && list.size() > 0) {
				for (ProjectFeedback p : list) {

					Integer uids = p.getHeadImageId();
					if (uids == null) {
						p.setHeadImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
					} else {
						BFile b1 = bFileMapper.queryById(uids);
						if (b1 == null || b1.getUrl() == null || "".equals(b1.getUrl())) {
							p.setHeadImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
						} else if (b1.getUrl().contains("http")) {
							p.setHeadImageUrl(b1.getUrl());
						} else {
							p.setHeadImageUrl(resUrl + b1.getUrl());
						}

					}

					Integer pids = p.getProjectImageId();
					if (pids == null) {
						p.setProjectImageUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
						p.setProjectImageMiddelUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
						p.setProjectImageLittleUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
					} else {
						BFile b2 = bFileMapper.queryById(pids);
						if (b2 != null) {
							if (!"".equals(b2.getUrl()) && b2.getUrl() != null) {
								p.setProjectImageUrl(resUrl + b2.getUrl());
							} else {
								p.setProjectImageUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
							}
							if (!"".equals(b2.getMiddleUrl()) && b2.getMiddleUrl() != null) {
								p.setProjectImageMiddelUrl(resUrl + b2.getMiddleUrl());
							}
							if (!"".equals(b2.getLitterUrl()) && b2.getLitterUrl() != null) {
								p.setProjectImageLittleUrl(resUrl + b2.getLitterUrl());
							} 
						}
					}

					ids = p.getContentImageId();
					if (StrUtil.isNotEmpty(ids)) {
						List<Integer> idList = StrUtil.convertIdList(ids,
								Constant.data_separator);

						List<BFile> blist = bFileMapper.queryByIdList(idList);

						if (!CollectionUtils.isEmpty(blist)) {

							List<List<String>> imageList = new ArrayList<List<String>>();

							for (BFile bFile : blist) {
								List<String> imageMiddleList = new ArrayList<String>();
								if (bFile != null) {
									if (!"".equals(bFile.getUrl()) && bFile.getUrl() != null) {
										imageMiddleList.add(resUrl + bFile.getUrl());
									} else {
										imageMiddleList.add("");
									}
									if (!"".equals(bFile.getMiddleUrl()) && bFile.getMiddleUrl() != null) {
										imageMiddleList.add(resUrl + bFile.getMiddleUrl());
									} else {
										imageMiddleList.add("");
									}
									if (!"".equals(bFile.getLitterUrl()) && bFile.getLitterUrl() != null) {
										imageMiddleList.add(resUrl + bFile.getLitterUrl());
									} else {
										imageMiddleList.add("");
									}
								}
								imageList.add(imageMiddleList);
							}
							p.setContentImageUrls(imageList);
						}
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Project> queryByIndexProjectList(Project project, int pageNum,
			int pageSize) {
		logger.info("query param:" + project);

		PageHelper.startPage(pageNum, pageSize);

		List<Project> list = null;
		try {
			list = projectMapper.queryByIndexProjectList(project);

			if (list != null && list.size() > 0) {
				String resUrl = ConfigLoader.getResPictrueURL();
				for (Project p : list) {
					Integer uids = p.getHeadImageId();
					if(uids == null){
						p.setHeadImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
					}else{
						BFile b1 = bFileMapper.queryById(uids);
						if(b1 != null && !"".equals(b1.getUrl()) && b1.getUrl() != null){
							p.setHeadImageUrl(resUrl+b1.getUrl());
						}else{
							p.setHeadImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
						}
					}
					Integer pids = p.getCoverImageId();
					if(pids == null){
						p.setCoverImageUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
						p.setCoverImageUrlMiddle(ProjectConstants.DEFAULT_PROJECT_IMG);
						p.setCoverImageUrlLitter(ProjectConstants.DEFAULT_PROJECT_IMG);
					}else{
						BFile b2 = bFileMapper.queryById(pids);
						if(b2 != null){
							if(!"".equals(b2.getUrl()) && b2.getUrl() != null){
								p.setCoverImageUrl(resUrl+b2.getUrl());
							}else{
								p.setCoverImageUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
							}
							if(!"".equals(b2.getMiddleUrl()) && b2.getMiddleUrl() != null){
								p.setCoverImageUrlMiddle(resUrl+b2.getMiddleUrl());
							}
							if(!"".equals(b2.getLitterUrl()) && b2.getLitterUrl() != null){
								p.setCoverImageUrlLitter(resUrl+b2.getLitterUrl());
							}
						}
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Project> queryByNewProjectList(Project project, int pageNum,
			int pageSize) {
		logger.info("query param:" + project);

		PageHelper.startPage(pageNum, pageSize);

		List<Project> list = null;
		try {
			list = projectMapper.queryByNewProjectList(project);

			if (list != null && list.size() > 0) {
				String resUrl = ConfigLoader.getResPictrueURL();
				for (Project p : list) {
					Integer uids = p.getHeadImageId();
					if(uids == null){
						p.setHeadImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
					}else{
						BFile b1 = bFileMapper.queryById(uids);
						if(b1 == null || b1.getUrl() == null || "".equals(b1.getUrl())){
							p.setHeadImageUrl(FrontUserConstants.DEFAULT_HEADIMG);
						}else if(b1.getUrl().contains("http")){
							p.setHeadImageUrl(b1.getUrl());
						}else{
							p.setHeadImageUrl(resUrl+b1.getUrl());
						}
					}
					Integer pids = p.getCoverImageId();
					if(pids == null){
						p.setCoverImageUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
						p.setCoverImageUrlMiddle(ProjectConstants.DEFAULT_PROJECT_IMG);
						p.setCoverImageUrlLitter(ProjectConstants.DEFAULT_PROJECT_IMG);
					}else{
						BFile b2 = bFileMapper.queryById(pids);
						if(b2 != null){
							if(!"".equals(b2.getUrl()) && b2.getUrl() != null){
								p.setCoverImageUrl(resUrl+b2.getUrl());
							}else{
								p.setCoverImageUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
							}
							if(!"".equals(b2.getMiddleUrl()) && b2.getMiddleUrl() != null){
								p.setCoverImageUrlMiddle(resUrl+b2.getMiddleUrl());
							}
							if(!"".equals(b2.getLitterUrl()) && b2.getLitterUrl() != null){
								p.setCoverImageUrlLitter(resUrl+b2.getLitterUrl());
							}
						}
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Project> queryByFieldAndArea(String field,
			Integer pageNum, Integer pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		List<Project> list = null;
		try {
				list = projectMapper.queryByFieldOrArea(field);
				if(list != null && list.size() > 0){
					String resUrl = ConfigLoader.getResPictrueURL();
					for (Project p : list) {
						Integer pids = p.getCoverImageId();
						if(pids == null){
							p.setCoverImageUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
						}else{
							BFile b2 = bFileMapper.queryById(pids);
							if(b2 != null){
								if(!"".equals(b2.getUrl()) && b2.getUrl() != null){
									p.setCoverImageUrl(resUrl+b2.getUrl());
								}else{
									p.setCoverImageUrl(ProjectConstants.DEFAULT_PROJECT_IMG);
								}
								if(!"".equals(b2.getMiddleUrl()) && b2.getMiddleUrl() != null){
									p.setCoverImageUrlMiddle(resUrl+b2.getMiddleUrl());
								}
								if(!"".equals(b2.getLitterUrl()) && b2.getLitterUrl() != null){
									p.setCoverImageUrlLitter(resUrl+b2.getLitterUrl());
								}
							}
						}
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateProjectShareCount(Integer projectId) throws BaseException {
		try {
				projectMapper.updateProjectShareCount(projectId);
		} catch (Exception e) {
			logger.error("update database error", e);
			throw new BaseException(ResultCode.Error);
		}
	}

	@Override
	public List<Project> queryProjectMaybeList(int pageNum, int pageSize) {
		logger.info("query queryProjectMaybeList:");


		List<Project> list = null;
		try {
			PageHelper.startPage(pageNum, pageSize);
			list = projectMapper.queryProjectMaybeList();

			if (list != null && list.size() > 0) {
				String resUrl = ConfigLoader.getResPictrueURL();
				for (Project temp : list) {
					if (temp != null && temp.getCoverImageUrl() != null) {
						temp.setCoverImageUrl(resUrl + temp.getCoverImageUrl());
					}
					Double leaveCryMoney = MathUtil.sub(temp.getCryMoney(), temp.getDonatAmount());
					temp.setLeaveCryMoney(leaveCryMoney < 0 ? 0 : leaveCryMoney);
					if(temp.getDonatAmount().compareTo(0.0) > 0) {
						Double pro = MathUtil.div(temp.getCryMoney(), temp.getDonatAmount(), 2);
						temp.setDonatePercent((pro >= 1.0 ? 1.0 : pro) + "");
					}else{
						temp.setDonatePercent("0");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
