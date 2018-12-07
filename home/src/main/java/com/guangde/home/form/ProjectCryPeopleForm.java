package com.guangde.home.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目求助者
 * @author Administrator
 *
 */
public class ProjectCryPeopleForm implements Serializable
{
    private Integer id;

	@NotBlank(message = "姓名不能为空")
    private String realName;

	@NotNull(message = "性别不能为空")
    private Integer sex;

	@NotBlank(message = "地址不能为空")
    private String address;

	@NotBlank(message = "电话不能为空")
    private String mobile;

	@NotNull(message = "身份证照片不能为空")
    private Integer idCardImageId;

	@NotBlank(message = "求助原因不能为空")
    private String helpCause;

    private String remark;
  
    private Date createTime;

	private String idCardImageUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIdCardImageId() {
		return idCardImageId;
	}

	public void setIdCardImageId(Integer idCardImageId) {
		this.idCardImageId = idCardImageId;
	}

	public String getHelpCause() {
		return helpCause;
	}

	public void setHelpCause(String helpCause) {
		this.helpCause = helpCause;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIdCardImageUrl() {
		return idCardImageUrl;
	}

	public void setIdCardImageUrl(String idCardImageUrl) {
		this.idCardImageUrl = idCardImageUrl;
	}

	@Override
	public String toString() {
		return "ApiProjectCryPeople{" +
				"id=" + id +
				", realName='" + realName + '\'' +
				", sex=" + sex +
				", address='" + address + '\'' +
				", mobile='" + mobile + '\'' +
				", idCardImageId=" + idCardImageId +
				", helpCause='" + helpCause + '\'' +
				", remark='" + remark + '\'' +
				", createTime=" + createTime +
				", idCardImageUrl='" + idCardImageUrl + '\'' +
				'}';
	}
}
