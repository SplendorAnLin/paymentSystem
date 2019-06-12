package com.yl.recon.core.service;


import java.util.Date;

/**
 * 接口与银行通道
 * @author 邱健
 *
 */
public interface InterfaceBankHandleService {


	/**
	 * 接口与银行通道：数据库对账
	 * @param reconDate
	 */
	void executeByDb(Date reconDate);
	 
}