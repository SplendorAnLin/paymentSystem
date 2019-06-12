package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.BankChannelCustomerConfig;
import com.yl.pay.pos.enums.BankCustomerChooseType;

import java.util.List;


/**
 * Title: 银行通道商户配置服务
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface IBankChannelCustomerConfigService {
	
	//获取银行通道商户配置
	public List<BankChannelCustomerConfig>  getBankChannelCustomerConfig(String bankInterfaceCode, String bankChannelCode,
                                                                         BankCustomerChooseType bankCustomerChooseType, String customerNo, String customerOrg);
	
}
