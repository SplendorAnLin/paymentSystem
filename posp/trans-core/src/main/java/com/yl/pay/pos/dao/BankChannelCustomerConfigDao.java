package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankChannelCustomerConfig;
import com.yl.pay.pos.enums.BankCustomerLevel;

import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface BankChannelCustomerConfigDao {
	
	//创建
	public BankChannelCustomerConfig create(BankChannelCustomerConfig bankChannelCustomerConfig);
	
	//根据银行接口编号、银行通道编号、银行商户编号查找
	public List<BankChannelCustomerConfig> findByBankInterfaceAndBankChannelCodeAndBankCustomerNo(String bankInterfaceCode, String bankChannelCode, String bankCustomerNo);

	//根据银行接口编号、银行通道编号、商户编号查找
	public List<BankChannelCustomerConfig> findByBankInterfaceAndBankChannelCodeAndCustomerNo(String bankInterfaceCode, String bankChannelCode, String customerNo, BankCustomerLevel customerLevel);

	public List<BankChannelCustomerConfig> findByBankInterfaceAndChannelAndCustomerNo(String bankInterfaceCode, String bankChannelCode, String bankCustomerNo, String customerNo);
	
}
