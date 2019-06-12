package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.entity.BankCustomerConfig;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public interface BankCustomerConfigDao {
	
	//根据ID查找
	public BankCustomerConfig findById(Long id);
	//根据ID查找
	public BankCustomerConfig findByBankCustomerNo(String bankCustomerNo);
	//根据接口编号、通道编号、商户号查找，默认取状态为“可用”的
	public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndBankCustomerNo(String bankInterface, BankChannel bankChannel, String bankCustomerNo);
	//
	public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndCustomerNo(String bankInterface, BankChannel bankChannel, String customerNo);
	
	public List<BankCustomerConfig> findByBankInterfaceAndBankChannelAndCustomerNoAndPosCati(String bankInterface, BankChannel bankChannel, String customerNo,String posCati);

	public BankCustomerConfig create(BankCustomerConfig config);

	public List<BankCustomerConfig> findByBankInterfaceAndBankCustomerNoAndCustomerNo(String bankInterface, String bankCustomerNo, String customerNo);
	
}
