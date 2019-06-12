package com.yl.recon.core.service;


import com.yl.recon.core.entity.order.*;

import java.util.Date;
import java.util.List;

/**
 * 订单与账户
 *
 * @author 邱健
 */
public interface InsertFileToDbService {


	/**
	 * 数据库对账
	 *
	 * @param reconDate
	 */
	void executeByDb(Date reconDate);


	/**
	 * 批量插入账户数据
	 *
	 * @param accountOrder
	 * @return
	 */
	void insertListAccountOrder(List <AccountOrder> accountOrder);


	/**
	 * 批量插入交易订单数据
	 *
	 * @param tradeOrder
	 * @return
	 */
	void insertListTradeOrder(List <TradeOrder> tradeOrder);

	/**
	 * 批量插入代付订单数据
	 *
	 * @param remitOrders
	 * @return
	 */
	void insertListRemitOrder(List <RemitOrder> remitOrders);

	/**
	 * 批量插入实名认证订单数据
	 * @param realAuthOrders
	 */
	void insertListRealAuthOrder(List <RealAuthOrder> realAuthOrders);

	/**
	 * 批量插入接口数据
	 *
	 * @param PayinterfaceOrder
	 * @return
	 */
	void insertListPayinterfaceOrder(List <PayinterfaceOrder> PayinterfaceOrder);

	/**
	 * 批量插入
	 * @param baseBankChannelOrders
	 * @return
	 */
	void insertListBaseBankChannelOrder(List <BaseBankChannelOrder> baseBankChannelOrders);

}