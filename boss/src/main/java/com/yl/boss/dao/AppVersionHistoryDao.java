package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.AppVersionHistory;

/**
 * oem版客户端历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AppVersionHistoryDao {
	
	/**
	 * 创建oem版客户端历史信息
	 * @param customerCertHistorty
	 */
	public void create(AppVersionHistory appVersionHistory);
	
	/**
	 * 根据商户编号查询
	 * @param custNo
	 * @return
	 */
	public List<AppVersionHistory> findByAgentNo(String agentNo);

}
