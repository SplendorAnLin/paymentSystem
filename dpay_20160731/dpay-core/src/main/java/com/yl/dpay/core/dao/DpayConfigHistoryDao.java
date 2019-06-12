package com.yl.dpay.core.dao;

import com.yl.dpay.core.entity.DpayConfigHistory;

/**
 * 代付配置历史记录数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface DpayConfigHistoryDao {
	
	/**
	 * @param dpayConfigHistory
	 */
	public void insert(DpayConfigHistory dpayConfigHistory);

}
