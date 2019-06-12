package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.enums.BankBillType;
import com.yl.pay.pos.enums.CardType;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.enums.YesNo;

import java.util.List;


/**
 * Title: 银行通道服务
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface IBankChannelService {
	
	//根据通道编号查询通道
	public BankChannel findByBankChannelCode(String channelCode);
	//判断指定通道是否可用
	public BankChannel isBankChannelUseable(String issuer, String bankChannelCode, CardType cardType, TransType transType, YesNo isIC);

	//获取所有可用银行通道
	public List<BankChannel> findAvailableBankChannel(String issuer, CardType cardType, TransType transType, YesNo isIC);

	//根据连接类型、账单类型获取可用银行通道
	public List<BankChannel> findAvailableBankChannel(String issuer, CardType cardType, TransType transType,
                                                      String bankConnectType, String bankBillType, YesNo isIc);

	/**
	 *
	 */
	public List<BankChannel> findAvailableBankChannel(String bankInterface, BankBillType bankBillType, String mccCategory,
                                                      String issuer, CardType cardType, TransType transType, YesNo isIC);
	
}

