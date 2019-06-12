package com.yl.boss.dao;

import com.yl.boss.entity.OperateLog;

/**
 * 操作日志DAO接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
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
