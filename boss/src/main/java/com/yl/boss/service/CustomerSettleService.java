package com.yl.boss.service;

import java.util.List;

import com.yl.boss.entity.CustomerSettle;
import com.yl.boss.entity.CustomerSettleHistory;

/**
 * 商户结算卡服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface CustomerSettleService {

	/**
	 * 更新商户结算卡
	 * @param customerSettle
	 * @param oper
	 */
	public void update(CustomerSettle customerSettle, String oper);
	
	/**
	 * 根据商编查询
	 * @param custNo
	 * @return
	 */
	public CustomerSettle findByCustNo(String custNo);
	
	/**
	 * 根据商户编号查询历史
	 * @param custNo
	 * @return List<CustomerSettleHistory>
	 */
	public List<CustomerSettleHistory> findHistByCustNo(String custNo);
	
}
