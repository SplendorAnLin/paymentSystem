package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.CustomerCertHistory;

/**
 * 商户证件历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface CustomerCertHistoryDao {
	
	/**
	 * 创建商户证件历史信息
	 * @param customerCertHistorty
	 */
	public void create(CustomerCertHistory customerCertHistorty);
	
	/**
	 * 根据商户编号查询
	 * @param custNo
	 * @return
	 */
	public List<CustomerCertHistory> findByCustNo(String custNo);

}
