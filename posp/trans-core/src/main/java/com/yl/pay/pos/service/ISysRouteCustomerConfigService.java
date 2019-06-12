package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.SysRouteCustomerConfig;
import com.yl.pay.pos.entity.SysRouteCustomerConfigDetail;
import com.yl.pay.pos.enums.Status;

import java.util.List;

/**
 * Title: 系统路由商编配置服务
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public interface ISysRouteCustomerConfigService {
	
	/**
	 * 根据银行接口编号、银行商户号、交易金额 获取系统路由商编
	 * @param bankInterface
	 * @param bankCustomerNo
	 * @param transAmount
	 * @return
	 */
	public SysRouteCustomerConfigDetail getSysRouteBankCustomerNo(String bankInterface, String bankCustomerNo, double transAmount);

	/**
	 * 完成商编的使用，更新状态及交易金额
	 * @param sysRouteCustomerConfigDetail
	 * @param transAmount
	 */
	public void complete(SysRouteCustomerConfigDetail sysRouteCustomerConfigDetail, double transAmount);
	/**
	 * 创建分流配置接口
	 * @param bankInterface
	 * @param bankCustomerNo
	 * @param bankCustomerName
	 */
	public SysRouteCustomerConfigDetail createSysRouteCustomerConfig(String bankInterface, String bankCustomerNo, String bankCustomerName);
	/**
	 * 根据商户好和接口查找分流配置
	 * @param bankInterface
	 * @param bankCustomerNo
	 * @return
	 */
	public SysRouteCustomerConfig findByBankCustomerNo(String bankInterface, String bankCustomerNo);
	/**
	 * 根据分流配置查询银联商户号明细
	 * @param sysRouteCustomerConfig
	 * @return
	 */
	public List<SysRouteCustomerConfigDetail> findBySysRouteCustConf(SysRouteCustomerConfig sysRouteCustomerConfig);
	/**
	 * 更新
	 * @param configDetail
	 * @return
	 */
	public SysRouteCustomerConfigDetail update(SysRouteCustomerConfigDetail configDetail);
	/**
	 * 根据银行接口，银行商户号，状态查找分流商户号
	 * @param bankInterface
	 * @param bankCustomerNo
	 * @param status
	 * @return
	 */
	public SysRouteCustomerConfigDetail findByBankInterfaceAndBankCustomerNoAndStatus(String bankInterface, String bankCustomerNo, Status status);
	
	
	public SysRouteCustomerConfig update(SysRouteCustomerConfig config);
	
	public SysRouteCustomerConfig findById(Long id);
}
