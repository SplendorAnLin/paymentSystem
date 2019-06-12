package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.SysRouteCustomerConfigDetail;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public interface ISysRouteCustomerConfigDetailService {
	/**
	 * 状态还原，变为未领取状态
	 * @param detail
	 */
	public void createRecover(SysRouteCustomerConfigDetail detail);
	
}
