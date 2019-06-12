package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.CustomerFeeHistory;

/**
 * 商户费率历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface CustomerFeeHistoryDao {
	
	/**
	 * 创建商户费率历史
	 * @param customerFeeHistory
	 */
	public void create(CustomerFeeHistory customerFeeHistory);
	
	/**
	 * 根据商编查询
	 * @param custNo
	 * @return
	 */
	public List<CustomerFeeHistory> findByCustNo(String custNo);

}
