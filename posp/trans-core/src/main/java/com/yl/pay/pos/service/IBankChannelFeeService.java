package com.yl.pay.pos.service;

import com.yl.pay.pos.bean.BankChannelFeeReturnBean;
import com.yl.pay.pos.entity.BankChannel;


/**
 * Title: 银行通道费率服务 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface IBankChannelFeeService {
	
	//获取银行成本
	public BankChannelFeeReturnBean getBankChannelCost(BankChannel bankChannel, double trxAmount);
	
}

