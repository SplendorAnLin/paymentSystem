package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.BankChannelFunction;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.TransType;


/**
 * Title: 银行通道权限DAO
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface BankChannelFunctionDao {
	
	//创建
	public BankChannelFunction create(BankChannelFunction bankChannelFunction);
	
	//根据分类码、交易类型、状态查找
	public BankChannelFunction findBySortCodeAndTransTypeAndStatus(String sortCode, TransType transType, Status status);
	
}
