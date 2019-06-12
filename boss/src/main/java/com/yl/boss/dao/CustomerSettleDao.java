package com.yl.boss.dao;

import com.yl.boss.entity.CustomerSettle;

/**
 * 商户结算卡数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface CustomerSettleDao {
	
	/**
	 * 创建商户结算卡
	 * @param customerSettle
	 */
	public void create(CustomerSettle customerSettle);
	
	/**
	 * 更新商户结算卡
	 * @param customerSettle
	 */
	public void update(CustomerSettle customerSettle);
	
	/**
	 * 根据商编查询
	 * @param custNo
	 * @return
	 */
	public CustomerSettle findByCustNo(String custNo);

}
