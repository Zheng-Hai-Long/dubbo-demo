package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;
/**
 * 微信消息发送表
 * @author xj 2017-11-28
 *
 */
public class WeixinModel extends BaseBean implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7189489796047181106L;


	private Integer id;
	/**
	 * 模版类型，2：执行进度，3：筹款结束通知，5：审核通过，6：审核不通过，7：留言回复，8：一起捐
	 */
	private Integer model;
	/**
	 * 0：未发送， 1：已发送
	 */
    private Integer state;
    /**
	 * 用户
	 */
    private Integer userId;
    /**
	 * 微信唯一识别号
	 */
    private String openId;
    /**
	 * 项目Id
	 */
    private Integer projectId;
    /**
	 * 报告id
	 */
    private Integer reportId;
    /**
	 * 项目反馈id
	 */
    private Integer feedbackId;
    private Date createTime;
    //微信的信息，后台人员自己控制
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private String value6;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getReportId() {
		return reportId;
	}
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	public Integer getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public String getValue4() {
		return value4;
	}
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	public String getValue5() {
		return value5;
	}
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	public String getValue6() {
		return value6;
	}
	public void setValue6(String value6) {
		this.value6 = value6;
	}
	@Override
	public String toString() {
		return "WeixinModel [id=" + id + ", model=" + model + ", state="
				+ state + ", userId=" + userId + ", openId=" + openId
				+ ", projectId=" + projectId + ", reportId=" + reportId
				+ ", feedbackId=" + feedbackId + ", createTime=" + createTime
				+ ", value1=" + value1 + ", value2=" + value2 + ", value3="
				+ value3 + ", value4=" + value4 + ", value5=" + value5
				+ ", value6=" + value6 + "]";
	}
    
    
}
