package com.yl.recon.core.service;


import java.util.Date;

/**
 * 订单与接口
 * @author 邱健
 *
 */
public interface OrderInterfaceHandleService {


	/**
	 * 数据库对账
	 * @param reconDate
	 */
	void executeByDb(Date reconDate);

	/**
	 * 对账：接口与交易
	 * @param reconDate
	 */
	void reconInterfaceAndOnline(Date reconDate);

	/**
	 * 对账：接口与代付
	 * @param reconDate
	 */
	void reconInterfaceAndRemit(Date reconDate);

	/**
	 * 对账：接口与实名认证
	 * @param reconDate
	 */
	void reconInterfaceAndRealAuth(Date reconDate);
	 
}