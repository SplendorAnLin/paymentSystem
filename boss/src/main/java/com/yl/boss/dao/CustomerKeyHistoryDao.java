package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.CustomerKeyHistory;

/**
 * 商户密钥历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface CustomerKeyHistoryDao {
	
	/**
	 * 创建商户密钥历史
	 * @param customerKeyHistory
	 */
	public void create(CustomerKeyHistory customerKeyHistory);
	
	/**
	 * 根据商户编号查询
	 * @param custNo
	 * @return
	 */
	public List<CustomerKeyHistory> findByCustNo(String custNo);

}
