package com.guangde.home.vo.project;

/**
 * 
 * 受捐项目
 *
 */
public class ProjectSimple {

	private Integer id;

	private String title;

	private String coverImageUrl;

	private String realName;

	private Integer userId;

	private String mobile;


	/**
	 * 是否有红包
	 */
	private Integer isHaveRedPacket = 0;

	/**
	 * 是否有善库
	 */
	private  Integer isHaveGoodLibrary = 0;

	/**
	 * 是否有积分
	 */
	private Integer isUseSource = 0;

	/**
	 * 是否有企业配捐
	 */
	private Integer isHaveEnterpriseDonate = 0;

	/**
	 * 是否配置过项目捐赠金额
	 */
	private Integer isProjectMoneyConfig = 0;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsProjectMoneyConfig() {
		return isProjectMoneyConfig;
	}

	public void setIsProjectMoneyConfig(Integer isProjectMoneyConfig) {
		this.isProjectMoneyConfig = isProjectMoneyConfig;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsHaveRedPacket() {
		return isHaveRedPacket;
	}

	public void setIsHaveRedPacket(Integer isHaveRedPacket) {
		this.isHaveRedPacket = isHaveRedPacket;
	}

	public Integer getIsHaveGoodLibrary() {
		return isHaveGoodLibrary;
	}

	public void setIsHaveGoodLibrary(Integer isHaveGoodLibrary) {
		this.isHaveGoodLibrary = isHaveGoodLibrary;
	}

	public Integer getIsUseSource() {
		return isUseSource;
	}

	public void setIsUseSource(Integer isUseSource) {
		this.isUseSource = isUseSource;
	}

	public Integer getIsHaveEnterpriseDonate() {
		return isHaveEnterpriseDonate;
	}

	public void setIsHaveEnterpriseDonate(Integer isHaveEnterpriseDonate) {
		this.isHaveEnterpriseDonate = isHaveEnterpriseDonate;
	}
}