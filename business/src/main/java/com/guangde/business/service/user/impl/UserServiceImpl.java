package com.guangde.business.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.guangde.business.constants.FrontUserConstants;
import com.guangde.business.constants.UrlConstants;
import com.guangde.business.dao.*;
import com.guangde.business.entry.*;
import com.guangde.business.enums.ResultEnum;
import com.guangde.business.exception.BaseException;
import com.guangde.business.pojo.Result;
import com.guangde.business.service.BaseService;
import com.guangde.business.service.user.IUserService;
import com.guangde.business.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl extends BaseService implements IUserService
{
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private FrontUserMapper frontUserMapper;
    
    @Autowired
    private ThirdUserMapper thirdUserMapper;
    @Autowired
    private BFileMapper bFileMapper;
    
    @Autowired
    private AuditStaffMapper auditStaffMapper;
    
    @Autowired
    private ProjectMapper projectMapper;
    
    @Autowired
    private ConfigMapper configMapper;
    
    @Autowired
    private DataStatisticsMapper dataStatisticsMapper;

    @Autowired
    private TogetherConfigMapper togetherConfigMapper;

    @Autowired
    private FrontUserAddressMapper frontUserAddressMapper;

    @Override
    public FrontUser queryByMobile(String mobileNum) {
        return frontUserMapper.queryByMobile(mobileNum);
    }

    @Override
    public List<FrontUser> queryByParam(FrontUser model) {
        return frontUserMapper.queryUserByParam(model);
    }

    @Override
    public List<ThirdUser> queryThirdUserByParam(String opneId) throws BaseException {
        ThirdUser thirdUser = new ThirdUser();
        thirdUser.setAccountNum(opneId);
        List<ThirdUser> thirdUsers = thirdUserMapper.queryThirdUserByParam(thirdUser);
        return thirdUsers;
    }

    @Override
    public Result login(FrontUser fUser, ThirdUser tUser)
    {
        Result result = new Result();
        if (null == fUser && null == tUser)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        if (null != fUser && null != tUser)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        // 本地登陆
        if (null != fUser)
        {
            if (StringUtils.isEmpty(fUser.getUserName()) || StringUtils.isEmpty(fUser.getUserPass()))
            {
                result.setResultCode(ResultCode.ParameterError);
                return result;
            }
            
            FrontUser frontUser = frontUserMapper.getUserByUserName(fUser.getUserName());
            if (null == frontUser)
            {
                result.setResultCode(ResultCode.UserNotExist);
                return result;
            }
            
            String userPass = MD5.encodeString(fUser.getUserPass());
            if (!frontUser.getUserPass().equals(userPass))
            {
                result.setResultCode(ResultCode.WrongPassword);
                return result;
            }

            
            String covIds = frontUser.getCoverImageId() + "";
            String resUrl = ConfigLoader.getResPictrueURL();
            if (StrUtil.isNotEmpty(covIds) && !"null".equals(covIds))
            {
                List<Integer> idList = StrUtil.convertIdList(covIds, Constant.data_separator);
                
                List<BFile> list = bFileMapper.queryByIdList(idList);
                
                if (!CollectionUtils.isEmpty(list))
                {
                    
                    for (BFile bFile : list)
                    {
                        resUrl = resUrl + bFile.getUrl();
                    }
                    
                    frontUser.setCoverImageUrl(resUrl);
                }
            }
            
            result.setData(String.valueOf(frontUser.getId()));
            result.setResultCode(ResultCode.Success);
            //result.setMessage(frontUser.getUserType());
            //result.setObject((Object)frontUser);
            return result;
        }
        else
        {
            // 第三方登陆
            // 1、调用第三方接品，验证 2、验证通过，判断此号码在系统中是否存在，存在更新登陆时间，不存在，保存此帐号
            
            return result;
        }
        
    }
    
    /**
     * 微信用户注册
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public Result thirdUserRegistered(FrontUser user,ThirdUser tuser) throws BaseException
    {
    	logger.info(" thirdUserRegistered   FrontUser = : " + user + ", ThirdUser = " + tuser);
        user.setUserType(FrontUserConstants.FRONTUSER_TYPE_INDIVIDUAL);
        user.setBalance(0.0);
        user.setAvailableBalance(0.0);
        user.setTotalAmount(0.0);
        user.setDonateNum(0);
        user.setIsVolunteer(0);//默认不是志愿者
        user.setRegistrTime(new Date());
        user.setLastLoginTime(new Date());
        user.setMobileState(State.submitAudit);

        //将微信头像保存到服务器
        String headImageUrl = "";
        String realPath = UrlConstants.REAL_PATH;
        try
        {
            String fileName = String.valueOf(System.currentTimeMillis()) + "_" + (int)(Math.random() * 1000.0D) + ".jpg";
            headImageUrl =  UrlConstants.HEADIAMGE_TXT + File.separator + DateUtil.format(new Date(),
                    DateUtil.C_DATA_PATTON_YYYYMMDD);
            FileUtil.createFolder(realPath + UrlConstants.SAVE_PICTURE_FILE + headImageUrl);
            headImageUrl = headImageUrl + File.separator + fileName;
            Image2Binary.toBDFile(user.getCoverImageUrl(), realPath + UrlConstants.SAVE_PICTURE_FILE + headImageUrl);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            headImageUrl = user.getCoverImageUrl();
        }
        BFile bFile = new BFile();
        int res = 0;
        if (StringUtils.isNotEmpty(headImageUrl))
        {
            bFile.setUrl(headImageUrl);
            bFile.setMiddleUrl(headImageUrl);
            bFile.setLitterUrl(headImageUrl);
            bFile.setFileType("picture");
            bFile.setCategory("weixinImage");
            bFile.setIsHide(Integer.valueOf(0));
            bFile.setDescription("微信头像");
            res = bFileMapper.save(bFile);
            if(res <= 0){
                throw new BaseException(ResultEnum.Error.getCode(), "添加用户头像error");
            }
        }

        user.setCoverImageId(bFile.getId());
        user.setUserName("用户" + StringUtil.uniqueCode());
        // 注册用户
        frontUserMapper.saveFrontUser(user);
        if(res <= 0){
            throw new BaseException(ResultEnum.Error.getCode(), "添加用户error");
        }
        List<ThirdUser> tList = thirdUserMapper.queryThirdUserByParam(tuser);
        if(tList.size() > 0 ){
            throw new BaseException(ResultEnum.ThirdUserExist);
        }
        tuser.setUserId(user.getId());
        // 注册第三方用户
        tuser.setType("weixin");
        thirdUserMapper.saveThirdUser(tuser);
        return new Result(ResultEnum.Success, user.getId());
    }

    @Override
    public Result registeredByGoodPassWord(FrontUser fUser) {
        return null;
    }



    @Override
    public int register(FrontUser model) throws BaseException {
        try {
            //密码=md5(用户名+密码）
            if(StringUtils.isNotBlank(model.getUserPass())) {
                String pass = MD5.encodeString(model.getUserName() + model.getUserPass());
                model.setUserPass(pass);
            }

            model.setIsVolunteer(0);
            model.setTotalAmount(0.0);
            model.setDonateNum(0);
            model.setBalance(0.0);
            model.setAvailableBalance(0.0);
            model.setMobileState(FrontUserConstants.MOBILESTATE_TG);
            model.setRegistrTime(new Date());
            model.setLastLoginTime(new Date());
            model.setUserType(FrontUserConstants.FRONTUSER_TYPE_INDIVIDUAL);

            int res = frontUserMapper.saveFrontUser(model);

            if(res > 0 && org.apache.commons.lang.StringUtils.isBlank(model.getUserName())){
                FrontUser user = new FrontUser();
                user.setId(model.getId());
                user.setUserName("A" + StrUtil.addZeroForNum(model.getId()+"",7));
                res = frontUserMapper.update(user);
                if(res <= 0){
                    throw new BaseException(ResultEnum.Error);
                }
            }
            return model.getId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public Result userExistence(String userName)
    {
        
        Result result = new Result();
        if (StringUtils.isEmpty(userName))
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        FrontUser frontUser = frontUserMapper.getUserByUserName(userName);
        if (null != frontUser)
        {
            result.setResultCode(ResultCode.UserExist);
            return result;
        }
        
        result.setResultCode(ResultCode.Success);
        return result;
    }
    
    @Override
    public Result realName(AuditStaff auditStaff, FrontUser fu)
    {
        Result result = new Result();
        if (null == auditStaff)
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        if (null == auditStaff.getUserId() || StringUtils.isEmpty(auditStaff.getFileId()) || StringUtils.isEmpty(auditStaff.getPersonType()))
        {
            result.setResultCode(ResultCode.ParameterError);
            return result;
        }
        
        FrontUser frontUser = frontUserMapper.queryById(auditStaff.getUserId());
        if (null == frontUser)
        {
            logger.info("userId:" + auditStaff.getUserId());
            result.setResultCode(ResultCode.UserNotExist);
            return result;
        }
        
        String[] bfileIds = auditStaff.getFileId().split(",");
        for (String bfiledId : bfileIds)
        {
            if (!StrUtil.isInt(bfiledId))
            {
                logger.info("filedId:" + bfileIds);
                result.setResultCode(ResultCode.ParameterError);
                return result;
            }
            
            BFile bfile = bFileMapper.queryById(Integer.parseInt(bfiledId));
            if (null == bfile)
            {
                result.setResultCode(ResultCode.FileNotExist);
                return result;
            }
        }
        
        if (null != fu)
        {
            if (StringUtils.isNotEmpty(fu.getIdCard()))
            {
                frontUser.setIdCard(fu.getIdCard());
            }
            
            if (StringUtils.isNotEmpty(fu.getRealName()))
            {
                frontUser.setRealName(fu.getRealName());
            }
            
            if (StringUtils.isNotEmpty(fu.getMobileNum()))
            {
                frontUser.setMobileNum(fu.getMobileNum());
            }
            
            frontUser.setLastAutoTime(new Date());
            

            frontUserMapper.update(frontUser);
        }
        
        auditStaff.setState(State.submitAudit);
        auditStaffMapper.save(auditStaff);
        
        result.setResultCode(ResultCode.Success);
        
        return result;
    }
    
    @Override
    public FrontUser queryById(Integer id)
    {
        FrontUser user = frontUserMapper.queryById(id);
        if(StringUtils.isNotBlank(user.getCoverImageUrl())) {
            String resUrl = ConfigLoader.getResPictrueURL();
            user.setCoverImageUrl(resUrl + user.getCoverImageUrl());
        }
        return user;
    }
    
    @Override
    public FrontUser queryPersonCenter(FrontUser user)
    {
        FrontUser f = frontUserMapper.queryPersonCenter(user);
        if (f.getTotalAmount() == null || f.getTotalAmount() == 0)
        {
            f = frontUserMapper.queryDaysById(user.getId());
        }
        return f;
    }
    
    @Override
    public void updateFrontUser(FrontUser frontUser)
        throws BaseException
    {
        logger.info(" updateFrontUser  param : " + frontUser);
        
        try
        {
            FrontUser newFrontUser = new FrontUser();
            newFrontUser.setId(frontUser.getId());

            int ret = frontUserMapper.update(newFrontUser);
            
            if (ret > 0)
            {
                String description = frontUser.getDescription() == null ? "" : frontUser.getDescription();
                description = description.replaceAll("\r", "");
                description = description.replaceAll("\n", "");
                String fieldId ="";
                
                AuditStaff auditStaff = new AuditStaff();
                auditStaff.setUserId(frontUser.getId());
                auditStaff.setState(State.submitAudit);
                auditStaff.setFileId(fieldId);
                auditStaff.setPersonType(FrontUserConstants.PERSONTYPE_LOVE);
                auditStaffMapper.save(auditStaff);
            }
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public List<FrontUser> queryUserByParam(FrontUser user, Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        return frontUserMapper.queryUserByParam(user);
    }
    
    @Override
    public List<ThirdUser> queryThirdUserByParam(ThirdUser tser, Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        return thirdUserMapper.queryThirdUserByParam(tser);
    }
    
    @Override
    public int updateThirdUserById(ThirdUser tser)
    {
        return thirdUserMapper.updateThirdUserById(tser);
    }
    
    @Override
    public void resetFrontUser(FrontUser user)
        throws BaseException
    {
        try
        {
            if (user.getUserPass() != null)
            {
                
                user.setUserPass(MD5.encodeString(user.getUserPass()));
            }
            frontUserMapper.resetFrontUser(user);
        }
        catch (Exception e)
        {
            logger.error(e);
            throw new BaseException(ResultCode.Error);
        }
    }
    
    @Override
    public List<FrontUser> queryCompanyUserList(FrontUser user, Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        
        return frontUserMapper.queryCompanyUserList(user);
    }
    
    @Override
    public void updateUser(FrontUser frontUser)
        throws BaseException
    {
        frontUserMapper.update(frontUser);
    }
    
    @Override
    public Double countDonateAmount(boolean isUseConfig)
    {
        logger.info("countDonateAmount  param  isUseConfig = " + isUseConfig);
        Double totalAmount = frontUserMapper.countDonateAmount();
        if (totalAmount == null)
        {
            totalAmount = 0.0;
        }
        try
        {
            
            if (isUseConfig)
            {
                Config c = new Config();
                c.setConfigKey("totalAmount");
                List<Config> list = configMapper.queryByParam(c);
                if (list != null && list.size() > 0)
                {
                    c = list.get(0);
                    String lw = c.getConfigValue();
                    if (StringUtils.isEmpty(lw))
                    {
                        lw = "0";
                    }
                    Double amount = Double.parseDouble(lw);
                    totalAmount = totalAmount + amount;
                }
                
            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return totalAmount;
    }
    
    @Override
    public int countDonatePeople(boolean isUseConfig)
    {
        logger.info("countDonatePeople  param  isUseConfig = " + isUseConfig);
        int donatePeople = projectMapper.countDonatePeople();

        try
        {
            
            if (isUseConfig)
            {
                Config c = new Config();
                c.setConfigKey("totalPeople");
                List<Config> list = configMapper.queryByParam(c);
                if (list != null && list.size() > 0)
                {
                    c = list.get(0);
                    String lw = c.getConfigValue();
                    if (StringUtils.isEmpty(lw))
                    {
                        lw = "0";
                    }
                    Integer people = Integer.valueOf(lw);
                    donatePeople = donatePeople + people;
                }
                
            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return donatePeople;
    }
    
    @Override
    public int countDonateProject(boolean isUseConfig,String field,Integer isHide)
    {
        logger.info("countDonateProject  param  isUseConfig = " + isUseConfig);
        int donateProject = projectMapper.countDonateProject(field,isHide);

        try
        {
            
            if (isUseConfig)
            {
                Config c = new Config();
                c.setConfigKey("totalProject");
                List<Config> list = configMapper.queryByParam(c);
                if (list != null && list.size() > 0)
                {
                    c = list.get(0);
                    String lw = c.getConfigValue();
                    if (StringUtils.isEmpty(lw))
                    {
                        lw = "0";
                    }
                    Integer people = Integer.valueOf(lw);
                    donateProject = donateProject + people;
                }
                
            }
        }
        catch (Exception e)
        {
            logger.error(e);
        }
        return donateProject;
    }

	@Override
	public List<FrontUser> queryByIdList(Integer libraryId)
	{
		List<FrontUser> userlist = frontUserMapper.queryByIdList(libraryId);
		if(userlist != null && userlist.size() > 0){
			for(FrontUser f:userlist){
				if(f.getCoverImageId() != null){
					String resUrl = ConfigLoader.getResPictrueURL();
					BFile bf = bFileMapper.queryById(f.getCoverImageId());
					if(bf != null){
						if (!bf.getUrl().contains("http://")) {
	                		resUrl = resUrl + bf.getUrl();
	                	}else{
	                		resUrl = bf.getUrl();
	                	}
						f.setCoverImageUrl(resUrl);
					}
				}
			}
		}
		return userlist;
		
	}
	
	@Override
	public List<DataStatistics> queryDataStatisticsList(DataStatistics dataStatistics,Integer pageNum,Integer pageSize)
	throws Exception
	{
		PageHelper.startPage(pageNum, pageSize);
		List<DataStatistics> list = dataStatisticsMapper.queryByParam(dataStatistics);
		return list;
	}


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveTeam(Integer userId, Integer projectId, Integer coverImgId, String launchName, Integer state) throws BaseException {
        try {
           //创建用户
            FrontUser user = new FrontUser();
            user.setUserName("团队" + DateUtil.parseToFormatDateString(new Date(), DateUtil.LOG_DATE_TIME));
            user.setUserType("touristUsers");
            user.setBalance(0.0);
            user.setAvailableBalance(0.0);
            user.setRegistrTime(new Date());
            user.setLastLoginTime(new Date());
            user.setNickName(user.getUserName());
            user.setUserPass("a123456");
            user.setUserPass(MD5.encodeString(user.getUserPass()));
            user.setMobileState(State.submitAudit);
            frontUserMapper.saveFrontUser(user);

            TogetherConfig togetherConfig = new TogetherConfig();
            togetherConfig.setUserId(user.getId());
            togetherConfig.setProjectId(projectId);
            togetherConfig.setCoverImageId(coverImgId);
            togetherConfig.setTeamState(100);
            togetherConfig.setClick(0);
            togetherConfig.setLaunchName(launchName);
            togetherConfig.setCreateTime(new Date());
            togetherConfig.setDonateMoney(BigDecimal.ZERO);
            togetherConfig.setTotalMoney(BigDecimal.valueOf(-1));
            togetherConfig.setType("group");
            togetherConfig.setTeamPlayNumLimit(3);
            togetherConfig.setDonateNum(0);
            togetherConfig.setTeamPlayNum(1);
            togetherConfig.setTotalDonateMoney(BigDecimal.ZERO);
            togetherConfig.setTotalDonateNum(0);
            togetherConfig.setIsHide(state);
            togetherConfigMapper.saveTogether(togetherConfig);

            Integer teamId = togetherConfig.getId();
            //判断用户是否存在记录
            TogetherConfig config = new TogetherConfig();
            config.setProjectId(projectId);
            config.setUserId(userId);
            config = togetherConfigMapper.selectByParam(config);
            if(config != null){
                config.setTeamState(200);
                config.setType("personal");
                config.setTotalMoney(BigDecimal.valueOf(-1));
                config.setUpdateTime(new Date());
                config.setTeamId(teamId);
                config.setTeamPlayNum(0);
                config.setTeamPlayNumLimit(0);
                togetherConfigMapper.update(config);
            }else{
                user = frontUserMapper.queryById(userId);
                togetherConfig = new TogetherConfig();
                togetherConfig.setUserId(user.getId());
                togetherConfig.setProjectId(projectId);
                togetherConfig.setCoverImageId(user.getCoverImageId());
                togetherConfig.setTeamState(200);
                togetherConfig.setClick(0);
                togetherConfig.setLaunchName(user.getNickName());
                togetherConfig.setCreateTime(new Date());
                togetherConfig.setDonateMoney(BigDecimal.ZERO);
                togetherConfig.setTotalMoney(BigDecimal.valueOf(-1));
                togetherConfig.setType("personal");
                togetherConfig.setTeamId(teamId);
                togetherConfig.setTeamPlayNum(0);
                togetherConfig.setDonateNum(0);
                togetherConfig.setTeamPlayNumLimit(0);
                togetherConfig.setTotalDonateMoney(BigDecimal.ZERO);
                togetherConfig.setTotalDonateNum(0);
                togetherConfigMapper.saveTogether(togetherConfig);
            }


        } catch (Exception e) {
            logger.error("crate team fail", e);
            throw new BaseException(ResultCode.Error);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addTeamPeopleAndUpdateTeam(Integer userId, Integer projectId, Integer teamId) throws BaseException {
        try {
            TogetherConfig config = new TogetherConfig();
            config.setProjectId(projectId);
            config.setUserId(userId);
            config = togetherConfigMapper.selectByParam(config);
            if(config != null){
                config.setTeamState(300);
                config.setType("personal");
                config.setTotalMoney(BigDecimal.valueOf(-1));
                config.setUpdateTime(new Date());
                config.setTeamId(teamId);
                config.setTeamPlayNum(0);
                config.setTeamPlayNumLimit(0);
                togetherConfigMapper.update(config);
            }else{
                //增加团队成员
                FrontUser user = frontUserMapper.queryById(userId);
                TogetherConfig togetherConfig = new TogetherConfig();
                togetherConfig.setUserId(user.getId());
                togetherConfig.setProjectId(projectId);
                togetherConfig.setTeamState(300);
                togetherConfig.setClick(0);
                togetherConfig.setLaunchName(user.getNickName());
                togetherConfig.setCreateTime(new Date());
                togetherConfig.setDonateMoney(BigDecimal.ZERO);
                togetherConfig.setTotalMoney(BigDecimal.valueOf(-1));
                togetherConfig.setType("personal");
                togetherConfig.setTeamId(teamId);
                togetherConfig.setTeamPlayNum(0);
                togetherConfig.setDonateNum(0);
                togetherConfig.setTeamPlayNumLimit(0);
                togetherConfig.setCoverImageId(user.getCoverImageId());
                togetherConfig.setTotalDonateMoney(BigDecimal.ZERO);
                togetherConfig.setTotalDonateNum(0);
                togetherConfigMapper.saveTogether(togetherConfig);
            }

            //团队人数满员，则团队自动显示
            TogetherConfig res = togetherConfigMapper.queryById(teamId);
            if(res.getIsHide() == 1 && res.getTeamPlayNum() >= res.getTeamPlayNumLimit()-1){
                res = new TogetherConfig();
                res.setId(teamId);
                res.setIsHide(0);
                togetherConfigMapper.update(res);
            }
            //更新团队人数
            togetherConfigMapper.updateTogetherTeamPeopleNum(teamId);


        } catch (Exception e) {
            e.printStackTrace();
            logger.error("add team people fail", e);
            throw new BaseException(ResultCode.Error);
        }
    }

    @Override
    public List<FrontUser> queryLoveData(String userType, String donateTimeStr, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FrontUser> list = frontUserMapper.queryLoveData(userType, donateTimeStr);
        String resUrl = ConfigLoader.getResPictrueURL();
        if (list != null && list.size() > 0) {
            for (FrontUser user : list) {
                if (user.getCoverImageId() != null) {
                    BFile bFile = bFileMapper.queryById(user.getCoverImageId());
                    if (bFile != null && bFile.getUrl() != null) {
                        if (!bFile.getUrl().contains("http://")) {
                            user.setCoverImageUrl(resUrl + bFile.getUrl());
                        } else {
                            user.setCoverImageUrl(bFile.getUrl());
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<FrontUser> queryEnterpriseLoveData(String donateTimeStr, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FrontUser> list = frontUserMapper.queryEnterpriseLoveData(donateTimeStr);
        String resUrl = ConfigLoader.getResPictrueURL();
        if (list != null && list.size() > 0) {
            for (FrontUser user : list) {
                if (user.getCoverImageId() != null) {
                    BFile bFile = bFileMapper.queryById(user.getCoverImageId());
                    if (bFile != null && bFile.getUrl() != null) {
                        if (!bFile.getUrl().contains("http://")) {
                            user.setCoverImageUrl(resUrl + bFile.getUrl());
                        } else {
                            user.setCoverImageUrl(bFile.getUrl());
                        }
                    }
                }
            }
        }

        return list;
    }

    @Override
    public int countLoveData(String userType) {
        return frontUserMapper.countLoveData(userType);
    }

    @Override
    public int countEnterpriseLoveData() {
        return frontUserMapper.countEnterpriseLoveData();
    }

}
