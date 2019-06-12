package com.yl.pay.pos.service;

import com.yl.pay.pos.bean.BankChannelRouteBean;
import com.yl.pay.pos.bean.BankChannelRouteReturnBean;

import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface IBankChannelRouteService {
	/**
	 * 获取可用银行通道信息 
	 */
	public List<BankChannelRouteReturnBean> fetchAvailableBankChannel(BankChannelRouteBean bankChannelRouteBean);
	
}
