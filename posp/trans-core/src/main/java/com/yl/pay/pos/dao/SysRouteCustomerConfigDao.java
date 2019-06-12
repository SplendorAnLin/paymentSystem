package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.SysRouteCustomerConfig;
import com.yl.pay.pos.enums.Status;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public interface SysRouteCustomerConfigDao {
	
	/**
	 * 
	 * @param bankInterface
	 * @param bankCustomerNo
	 * @param status
	 * @return
	 */
	public SysRouteCustomerConfig findByBankInterfaceAndBankCustNoAndStatus(String bankInterface, String bankCustomerNo, Status status);

	/**
	 * @param sysRouteCustomerConfig
	 */
	public SysRouteCustomerConfig create(SysRouteCustomerConfig sysRouteCustomerConfig);
	
	/**
	 * 
	 * @param config
	 * @return
	 */
	public SysRouteCustomerConfig update(SysRouteCustomerConfig config);
	
	public SysRouteCustomerConfig findById(Long id);
	
}
