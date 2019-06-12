package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.SysRespCustomerNoConfig;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public interface SysRespCustomerNoConfigDao {
	/**
	 * 
	 * @param sysRespCustomerNoConfig
	 * @return
	 */
	public SysRespCustomerNoConfig create(SysRespCustomerNoConfig sysRespCustomerNoConfig);
	/**
	 * 根据商户编号查找，默认取状态为可用的
	 * @param customerNo
	 * @return
	 */
	public SysRespCustomerNoConfig findByCustomerNo(String customerNo);
	
}
