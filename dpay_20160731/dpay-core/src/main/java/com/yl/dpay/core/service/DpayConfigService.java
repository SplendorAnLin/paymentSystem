package com.yl.dpay.core.service;

import com.yl.dpay.core.entity.DpayConfig;
import com.yl.dpay.core.entity.Request;

/**
 * 代付出款配置服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface DpayConfigService {
	
	/**
	 * @param dpayConfig
	 * @param oper
	 */
	public void insert(DpayConfig dpayConfig,String oper);
	
	/**
	 * @param id
	 * @return
	 */
	public DpayConfig findById(Long id);
	
	/**
	 * @param dpayConfig
	 * @param oper
	 */
	public void update(DpayConfig dpayConfig,String oper);
	
	/**
	 * 代付发起校验
	 * @param request
	 */
	public void checkStart(Request request);
	
	/**
	 * 代付审核校验
	 * @param request
	 */
	public void checkAudit(Request request);
	
	/**
	 * 获取代付默认配置
	 */
	public DpayConfig findDpayConfig();

}
