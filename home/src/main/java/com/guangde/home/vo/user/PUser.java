package com.guangde.home.vo.user;

public class PUser {
	
	public static String USER_TYPE_P = "individualUsers";//个人
	public static String USER_TYPE_E = "enterpriseUsers";//企业用户
	//info
	private int id;//用户id
	private String name;//用户名
	private String nickName; //用户昵称
	private String passWord;//登入密码
	private String newpassWord;//新密码
	private String mobileNum;//手机号
	//
	private String code;//验证码
	private String phoneCode;//手机验证码

	private String imageId;//图片id字符串
	private String realname;	//真实姓名
	private String idCard;	//身份证号
	
	private String vocation; //职业
	private String workUnit; //工作单位
	private String coverImageId;//头像

	private String openid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getNewpassWord() {
		return newpassWord;
	}

	public void setNewpassWord(String newpassWord) {
		this.newpassWord = newpassWord;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getVocation() {
		return vocation;
	}

	public void setVocation(String vocation) {
		this.vocation = vocation;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getCoverImageId() {
		return coverImageId;
	}

	public void setCoverImageId(String coverImageId) {
		this.coverImageId = coverImageId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Override
	public String toString() {
		return "PUser{" +
				"id=" + id +
				", name='" + name + '\'' +
				", nickName='" + nickName + '\'' +
				", passWord='" + passWord + '\'' +
				", newpassWord='" + newpassWord + '\'' +
				", mobileNum='" + mobileNum + '\'' +
				", code='" + code + '\'' +
				", phoneCode='" + phoneCode + '\'' +
				", imageId='" + imageId + '\'' +
				", realname='" + realname + '\'' +
				", idCard='" + idCard + '\'' +
				", vocation='" + vocation + '\'' +
				", workUnit='" + workUnit + '\'' +
				", coverImageId='" + coverImageId + '\'' +
				", openid='" + openid + '\'' +
				'}';
	}
}
