package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankChannelFee;

import java.util.List;


/**
 * Title: 银行通道费率规则DAO
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface BankChannelFeeDao {
	
	public BankChannelFee create(BankChannelFee bankChannelFee);
	
	public BankChannelFee findByBankChannelCode(String bankChannelCode);
	
	/**
	 * 根据通道编号查找可用通道费率规则
	 */
	public List<BankChannelFee> findByChannelCode(String bankChannelCode);
	
}
