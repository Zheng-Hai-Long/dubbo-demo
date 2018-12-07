package com.guangde.business.util;

public interface State {
	
	/**
	 * 启用
	 */
	int enable = 100;
	
	/**
	 * 停用
	 */
	int disable = 101;
	
	/**
	 * 暂停
	 */
	int pause = 102;
	
	/**
	 * 锁定
	 */
	int lock = 103;
	
	/**
	 * 保存未提交
	 */
	int uncommitted = 200;
	
	/**
	 * 提交未审核
	 */
	int submitAudit = 201;
	
	/**
	 * 审核未通过
	 */
	int notPass = 202;
	
	/**
	 * 审核通过
	 */
	int pass = 203;
	
	/**
	 * 募捐中
	 */
	int donations = 204;
	
	/**
	 * 募捐结束
	 */
	int fundraisers = 205;
	
	/**
	 * 执行中
	 */
	int  implementation  = 206;
	
	/**
	 * 结束
	 */
	int projectEnd = 207;
	
	/**
	 * 未捐款、未打款、未支付
	 */
	int notPay=  300;
	
	/**
	 * 失败
	 */
	int Failure = 301;
	
	/**
	 * 成功
	 */
	int success = 302;
			
}
