package com.guangde.business.entry;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project extends BaseBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -3737314003232993609L;

	private Integer id;

	/**
	 * 领域
	 */
	private String field;

	private String fieldChinese;

	private List<String> fieldList;

	/**
	 * 项目类型
	 */
	private String type;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 所在地
	 */
	private String location;

	/**
	 * 详细地址
	 */
	private String detailAddress;

	/**
	 * 求救金额
	 */
	private Double cryMoney;


	/**
	 * 求救人信息
	 */
	private String information;


	/**
	 * 开户人姓名
	 */
	private String accountName;

	/**
	 * 已募捐金额
	 */
	private Double donatAmount;

	/**
	 * 已打款金额
	 */
	private Double panyAmount;

	/**
	 * 状态
	 */
	private Integer state;

	private List<Integer> states;

	/**
	 * 注册时间
	 */
	private Date registrTime;


	/**
	 * 最后一次更新时间
	 */
	private Date lastUpdateTime;

	/**
	 * 排序
	 */
	private Integer sort;


	/**
	 * 捐款笔数
	 */
	private Integer donationNum;

	/**
	 * 项目截止时间
	 */
	private Date deadline;

	/**
	 * 副标题
	 */
	private String subTitle;

	/**
	 * 项目标题图
	 */
	private String coverImageUrl;

	/**
	 * 项目标题图 h5
	 */
	private String coverImageUrlMiddle;

	/**
	 * 项目标题图 h5 小图
	 */
	private String coverImageUrlLitter;

	/**
	 * 图片描述
	 */
	private String coverImageDecription;

	/**
	 * 内容图片
	 */
	private List<BFile> bfileList = new ArrayList<BFile>();

	private Integer coverImageId;

	/**
	 * 内容图片ID，多个ID以逗号分隔
	 */
	private String contentImageId;

	private Integer userId;

	/**
	 * 是否隐藏  0 显示   1 隐藏
	 */
	private Integer isHide;

	/**
	 * 发起人姓名
	 */
	private String  uname ;
	/**
	 * 发起人头像地址
	 */
	private String  headImageUrl ;
	/**
	 * 发起人头像id
	 */
	private Integer headImageId ;

	/**
	 * 是否推荐
	 */
	private Integer isRecommend ;

	/**
	 * 项目最新反馈时间
	 */
	private Date lastFeedbackTime;
	/**
	 * 求救人姓名
	 */
	private String realName ;
	/**
	 * 求救人年龄
	 */
	private Integer age ;
	/**
	 * 求救人性别
	 */
	private String sex ;
	/**
	 * 求救人地址
	 */
	private String familyAddress;
	/**
	 * 已捐百分比
	 */
	private String donatePercent ;
	/**
	 * 还需金额
	 */
	private Double leaveCryMoney ;


	/**
	 * 关键字
	 */
	private String keyword ;

	/**
	 * 项目相关人员类型
	 */
	private Integer personType;

	/**
	 * 需要捐款的项目列表
	 */
	private List<Integer> pList;
	/**
	 * 发布时间
	 */
	private Date issueTime;
	/**
	 * 项目标题
	 */
	private String tag;
	private List<String> taglist;

	/**0：未捐，1：已捐*/
	private Integer donateState;
	/**项目ids*/
	private List<Integer> ids;
	/**经度*/
	private Double longitude;
	/**纬度*/
	private Double latitude;
	/**认领人id*/
	private Integer claimUserId;

	/**
	 * 超募金额
	 */
	private BigDecimal overMoney;

	/**
	 * 是否超募：1是；0否
	 */
	private Integer isOverMoney;

	/**省*/
	private String province;
	/**市*/
	private String city;
	/**区*/
	private String area;
	/**分享项目次数*/
	private Integer shareCount;

	/**
	 * 视频封面图ID
	 */
	private Integer videoCoverImageId;

	/**
	 * 视频封面原图
	 */
	private String videoCoverImageUrl;

	/**
	 * 视频封面中图
	 */
	private String videoCoverImageMiddleUrl;

	/**
	 * 视频封面小图
	 */
	private String videoCoverImageLittleUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldChinese() {
		return fieldChinese;
	}

	public void setFieldChinese(String fieldChinese) {
		this.fieldChinese = fieldChinese;
	}

	public List<String> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<String> fieldList) {
		this.fieldList = fieldList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public Double getCryMoney() {
		return cryMoney;
	}

	public void setCryMoney(Double cryMoney) {
		this.cryMoney = cryMoney;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Double getDonatAmount() {
		return donatAmount;
	}

	public void setDonatAmount(Double donatAmount) {
		this.donatAmount = donatAmount;
	}

	public Double getPanyAmount() {
		return panyAmount;
	}

	public void setPanyAmount(Double panyAmount) {
		this.panyAmount = panyAmount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<Integer> getStates() {
		return states;
	}

	public void setStates(List<Integer> states) {
		this.states = states;
	}

	public Date getRegistrTime() {
		return registrTime;
	}

	public void setRegistrTime(Date registrTime) {
		this.registrTime = registrTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getDonationNum() {
		return donationNum;
	}

	public void setDonationNum(Integer donationNum) {
		this.donationNum = donationNum;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getCoverImageUrlMiddle() {
		return coverImageUrlMiddle;
	}

	public void setCoverImageUrlMiddle(String coverImageUrlMiddle) {
		this.coverImageUrlMiddle = coverImageUrlMiddle;
	}

	public String getCoverImageUrlLitter() {
		return coverImageUrlLitter;
	}

	public void setCoverImageUrlLitter(String coverImageUrlLitter) {
		this.coverImageUrlLitter = coverImageUrlLitter;
	}

	public String getCoverImageDecription() {
		return coverImageDecription;
	}

	public void setCoverImageDecription(String coverImageDecription) {
		this.coverImageDecription = coverImageDecription;
	}

	public List<BFile> getBfileList() {
		return bfileList;
	}

	public void setBfileList(List<BFile> bfileList) {
		this.bfileList = bfileList;
	}

	public Integer getCoverImageId() {
		return coverImageId;
	}

	public void setCoverImageId(Integer coverImageId) {
		this.coverImageId = coverImageId;
	}

	public String getContentImageId() {
		return contentImageId;
	}

	public void setContentImageId(String contentImageId) {
		this.contentImageId = contentImageId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsHide() {
		return isHide;
	}

	public void setIsHide(Integer isHide) {
		this.isHide = isHide;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public Integer getHeadImageId() {
		return headImageId;
	}

	public void setHeadImageId(Integer headImageId) {
		this.headImageId = headImageId;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Date getLastFeedbackTime() {
		return lastFeedbackTime;
	}

	public void setLastFeedbackTime(Date lastFeedbackTime) {
		this.lastFeedbackTime = lastFeedbackTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getDonatePercent() {
		return donatePercent;
	}

	public void setDonatePercent(String donatePercent) {
		this.donatePercent = donatePercent;
	}

	public Double getLeaveCryMoney() {
		return leaveCryMoney;
	}

	public void setLeaveCryMoney(Double leaveCryMoney) {
		this.leaveCryMoney = leaveCryMoney;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public List<Integer> getpList() {
		return pList;
	}

	public void setpList(List<Integer> pList) {
		this.pList = pList;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<String> getTaglist() {
		return taglist;
	}

	public void setTaglist(List<String> taglist) {
		this.taglist = taglist;
	}

	public Integer getDonateState() {
		return donateState;
	}

	public void setDonateState(Integer donateState) {
		this.donateState = donateState;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getClaimUserId() {
		return claimUserId;
	}

	public void setClaimUserId(Integer claimUserId) {
		this.claimUserId = claimUserId;
	}

	public BigDecimal getOverMoney() {
		return overMoney;
	}

	public void setOverMoney(BigDecimal overMoney) {
		this.overMoney = overMoney;
	}

	public Integer getIsOverMoney() {
		return isOverMoney;
	}

	public void setIsOverMoney(Integer isOverMoney) {
		this.isOverMoney = isOverMoney;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getVideoCoverImageId() {
		return videoCoverImageId;
	}

	public void setVideoCoverImageId(Integer videoCoverImageId) {
		this.videoCoverImageId = videoCoverImageId;
	}

	public String getVideoCoverImageUrl() {
		return videoCoverImageUrl;
	}

	public void setVideoCoverImageUrl(String videoCoverImageUrl) {
		this.videoCoverImageUrl = videoCoverImageUrl;
	}

	public String getVideoCoverImageMiddleUrl() {
		return videoCoverImageMiddleUrl;
	}

	public void setVideoCoverImageMiddleUrl(String videoCoverImageMiddleUrl) {
		this.videoCoverImageMiddleUrl = videoCoverImageMiddleUrl;
	}

	public String getVideoCoverImageLittleUrl() {
		return videoCoverImageLittleUrl;
	}

	public void setVideoCoverImageLittleUrl(String videoCoverImageLittleUrl) {
		this.videoCoverImageLittleUrl = videoCoverImageLittleUrl;
	}

	@Override
	public String toString() {
		return "ApiProject{" +
				"id=" + id +
				", field='" + field + '\'' +
				", fieldChinese='" + fieldChinese + '\'' +
				", fieldList=" + fieldList +
				", type='" + type + '\'' +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", location='" + location + '\'' +
				", detailAddress='" + detailAddress + '\'' +
				", cryMoney=" + cryMoney +
				", information='" + information + '\'' +
				", accountName='" + accountName + '\'' +
				", donatAmount=" + donatAmount +
				", panyAmount=" + panyAmount +
				", state=" + state +
				", states=" + states +
				", registrTime=" + registrTime +
				", lastUpdateTime=" + lastUpdateTime +
				", sort=" + sort +
				", donationNum=" + donationNum +
				", deadline=" + deadline +
				", subTitle='" + subTitle + '\'' +
				", coverImageUrl='" + coverImageUrl + '\'' +
				", coverImageUrlMiddle='" + coverImageUrlMiddle + '\'' +
				", coverImageUrlLitter='" + coverImageUrlLitter + '\'' +
				", coverImageDecription='" + coverImageDecription + '\'' +
				", bfileList=" + bfileList +
				", coverImageId=" + coverImageId +
				", contentImageId='" + contentImageId + '\'' +
				", userId=" + userId +
				", isHide=" + isHide +
				", uname='" + uname + '\'' +
				", headImageUrl='" + headImageUrl + '\'' +
				", headImageId=" + headImageId +
				", isRecommend=" + isRecommend +
				", lastFeedbackTime=" + lastFeedbackTime +
				", realName='" + realName + '\'' +
				", age=" + age +
				", sex='" + sex + '\'' +
				", familyAddress='" + familyAddress + '\'' +
				", donatePercent='" + donatePercent + '\'' +
				", leaveCryMoney=" + leaveCryMoney +
				", keyword='" + keyword + '\'' +
				", personType=" + personType +
				", pList=" + pList +
				", issueTime=" + issueTime +
				", tag='" + tag + '\'' +
				", taglist=" + taglist +
				", donateState=" + donateState +
				", ids=" + ids +
				", longitude=" + longitude +
				", latitude=" + latitude +
				", claimUserId=" + claimUserId +
				", overMoney=" + overMoney +
				", isOverMoney=" + isOverMoney +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", area='" + area + '\'' +
				", shareCount=" + shareCount +
				", videoCoverImageId=" + videoCoverImageId +
				", videoCoverImageUrl='" + videoCoverImageUrl + '\'' +
				", videoCoverImageMiddleUrl='" + videoCoverImageMiddleUrl + '\'' +
				", videoCoverImageLittleUrl='" + videoCoverImageLittleUrl + '\'' +
				'}';
	}
    
}
