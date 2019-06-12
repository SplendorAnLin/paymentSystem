package com.yl.customer.dao;

import com.yl.customer.entity.OperateLog;

/**
 * 操作日志DAO
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public interface OperateLogDao {

	/**
	 * 添加日志
	 * @param operateLog
	 * @return
	 */
	public OperateLog create(OperateLog operateLog);
}
