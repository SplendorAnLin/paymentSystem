package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.CustomerHistory;

/**
 * 商户历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface CustomerHistoryDao {
	
	/**
	 * 创建商户历史
	 * @param customerHistory
	 */
	public void create(CustomerHistory customerHistory);
	
	/**
	 * 根据商编查询
	 * @param custNo
	 * @return
	 */
	public List<CustomerHistory> findByCustNo(String custNo);
	/**
	 * 
	 * @Description 根据商编和操作员 查询商户历史记录
	 * @param custNo
	 * @param oper
	 * @return
	 * @date 2017年9月24日
	 */
	public List<CustomerHistory> findByCustNoAndOper(String custNo, String oper);
	/**
	 * 获取最近一次拒绝信息
	 */
	String getLastInfo(String customerNo);
	
	/**
	 * 再次提交清空上次拒绝原因
	 */
	void deleteLastInfo(String customerNo);
}
