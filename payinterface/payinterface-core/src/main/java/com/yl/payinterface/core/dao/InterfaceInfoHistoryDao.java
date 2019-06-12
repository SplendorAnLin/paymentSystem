package com.yl.payinterface.core.dao;

import java.util.List;

import com.yl.payinterface.core.model.InterfaceInfoHistory;

/**
 * 接口信息历史数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月17日
 * @version V1.0.0
 */
public interface InterfaceInfoHistoryDao {
	
	/**
	 * 支付接口历史信息新增
	 * @param infoHistory
	 */
	public void create(InterfaceInfoHistory infoHistory);
	
	/**
	 * 根据接口编号查询支付接口历史信息
	 */
	public List<InterfaceInfoHistory> findByInterfaceCode(String interfaceCode);

}
