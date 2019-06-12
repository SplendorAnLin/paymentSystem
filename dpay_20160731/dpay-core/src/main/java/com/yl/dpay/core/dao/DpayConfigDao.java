package com.yl.dpay.core.dao;

import com.yl.dpay.core.entity.DpayConfig;

/**
 * 代付配置数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface DpayConfigDao extends BaseDao<DpayConfig>{
	
	/**
	 * @return
	 */
	public DpayConfig findDpayConfig();

}
