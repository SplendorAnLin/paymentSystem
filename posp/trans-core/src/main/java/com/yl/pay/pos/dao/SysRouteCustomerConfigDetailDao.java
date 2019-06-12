package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.SysRouteCustomerConfig;
import com.yl.pay.pos.entity.SysRouteCustomerConfigDetail;
import com.yl.pay.pos.enums.Status;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public interface SysRouteCustomerConfigDetailDao {
	/**
	 * 
	 * @param sysRouteCustomerConfigDetail
	 * @return
	 */
	public SysRouteCustomerConfigDetail create(SysRouteCustomerConfigDetail sysRouteCustomerConfigDetail);
	/**
	 * 
	 * @param sysRouteCustomerConfigDetail
	 * @return
	 */
	public SysRouteCustomerConfigDetail update(SysRouteCustomerConfigDetail sysRouteCustomerConfigDetail);
	
	/**
	 * 默认获取状态为可用且未被领取的
	 * @param sysRouteCustomerConfig
	 * @return
	 */
	public List<SysRouteCustomerConfigDetail> findBySysRouteCustConf(SysRouteCustomerConfig sysRouteCustomerConfig);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public SysRouteCustomerConfigDetail findById(Long id);
	
	public SysRouteCustomerConfigDetail findByBankInterfaceAndBankCustomerNoAndStatus(String bankInterface, String bankCustomerNo, Status status);
	
}
