package com.yl.recon.core.service;


import java.util.Date;

/**
 * 订单与账户
 * @author 邱健
 *
 */
public interface OrderAccountHandleService {


	/**
	 * 数据库对账
	 * @param date
	 */
	void executeByDb(Date date);



	/**
	 * 对账：账户与交易
	 * @param reconDate
	 */
	void reconAccountAndOnline(Date reconDate);

	/**
	 * 对账：账户与代付
	 * @param reconDate
	 */
	void reconAccountAndRemit(Date reconDate);

	/**
	 * 对账：账户与实名认证
	 * @param reconDate
	 */
	void reconAccountAndRealAuth(Date reconDate);

}