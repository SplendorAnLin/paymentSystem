package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.enums.BankBillType;
import com.yl.pay.pos.enums.BankConnectType;
import com.yl.pay.pos.enums.Status;

import java.util.List;

/**
 * Title: BankChannel Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface BankChannelDao {

	//根据ID查询
	public BankChannel findById(Long id);

	//创建
	public BankChannel create(BankChannel bankChannel);
	
	//更新
	public BankChannel update(BankChannel bankChannel);
	
	//根据编号查找
	public BankChannel findByCode(String code);
	
	//根据条件查找
	public List<BankChannel> findBankChannel(String issuer, List<String> rateAssortCodeList, String cardType, Status status, Boolean testFlag, String code);

	//根据条件获取可用通道
	public List<BankChannel> findBankChannel(String code, String bankInterface, BankConnectType bankConnectType, BankBillType bankBillType, String mccCategory);

	public List<BankChannel> findBankChannel(String code);

	//获取所有状态为可用的通道
	public List<BankChannel> findAllActive();
	//根据连接类型、账单类型获取所有可用通道
	public List<BankChannel> findAllActiveByConnectTypeAndBillType(BankConnectType bankConnectType, BankBillType bankBillType);
	//根据连接类型获取所有可用通道
	public List<BankChannel> findAllActiveByConnectType(BankConnectType bankConnectType);
	//根据账单类型获取所有可用通道
	public List<BankChannel> findAllActiveByBillType(BankBillType bankBillType);
	/**
	 * 根据银行接口编号、账单类型、MCC类别查找可用通道
	 */
	public List<BankChannel> findActiveByBankInterfaceAndBillTypeAndMccCategory(String bankInterface, BankBillType bankBillType, String mccCategory);
	
}
