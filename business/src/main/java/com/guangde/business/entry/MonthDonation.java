package com.guangde.business.entry;

import java.io.Serializable;
import java.util.Date;


public class MonthDonation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -60857103793667751L;
	private Integer id;
	private Integer donors;
	private String donorType;
	private Date donorStartTime;
	private Date donorEndTime;
	private String donorRecord;
	private String projectFilld;
	private Date operatorTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDonors() {
		return donors;
	}
	public void setDonors(Integer donors) {
		this.donors = donors;
	}
	public String getDonorType() {
		return donorType;
	}
	public void setDonorType(String donorType) {
		this.donorType = donorType;
	}
	public Date getDonorStartTime() {
		return donorStartTime;
	}
	public void setDonorStartTime(Date donorStartTime) {
		this.donorStartTime = donorStartTime;
	}
	public Date getDonorEndTime() {
		return donorEndTime;
	}
	public void setDonorEndTime(Date donorEndTime) {
		this.donorEndTime = donorEndTime;
	}
	public String getDonorRecord() {
		return donorRecord;
	}
	public void setDonorRecord(String donorRecord) {
		this.donorRecord = donorRecord;
	}
	public String getProjectFilld() {
		return projectFilld;
	}
	public void setProjectFilld(String projectFilld) {
		this.projectFilld = projectFilld;
	}
	public Date getOperatorTime() {
		return operatorTime;
	}
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	@Override
	public String toString() {
		return "MonthDonation [id=" + id + ", donors=" + donors
				+ ", donorType=" + donorType + ", donorStartTime="
				+ donorStartTime + ", donorEndTime=" + donorEndTime
				+ ", donorRecord=" + donorRecord + ", projectFilld="
				+ projectFilld + ", operatorTime=" + operatorTime + "]";
	}
	
	
	
}
