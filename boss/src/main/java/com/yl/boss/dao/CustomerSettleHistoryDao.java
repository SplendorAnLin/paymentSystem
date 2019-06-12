package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.CustomerSettleHistory;

/**
 * 商户结算卡历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface CustomerSettleHistoryDao {
	
	/**
	 * 创建商户结算卡历史
	 * @param customerSettleHistory
	 */
	public void create(CustomerSettleHistory customerSettleHistory);
	
	/**
	 * 根据商编查询
	 * @param custNo
	 * @return
	 */
	public List<CustomerSettleHistory> findByCustNo(String custNo);

}
