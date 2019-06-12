package com.yl.online.trade.service;

import java.util.List;

import com.yl.online.model.model.SalesResultReverseOrder;

/**
 * 消费结果补单业务处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface SalesResultReverseOrderService {
	
	/**
	 * 记录消费结果补单信息
	 * @param salesResultReverseOrder 消费结果补单实体Bean
	 */
	public void recordSalesResultReverseOrder(SalesResultReverseOrder salesResultReverseOrder);

	/**
	 * 根据交易订单号查询消费结果补单实体
	 * @param businessOrderID 交易订单号
	 * @return SalesResultReverseOrder
	 */
	public SalesResultReverseOrder querySalesResultReverseOrder(String businessOrderID);

	/**
	 * 根据补单次数查询需要补单的消费结果信息
	 * @param maxCount 最大补单次数
	 * @param maxNums 一批最大补单量
	 * @return List<SalesResultReverseOrder>
	 */
	public List<SalesResultReverseOrder> querySalesResultReverseOrderBy(int maxCount, int maxNums);

	/**
	 * 更新消费结果补单信息
	 * @param salesResultReverseOrder 消费结果补单实体Bean
	 */
	public void updateSalesResultReverseOrder(SalesResultReverseOrder salesResultReverseOrder);

	/**
	 * 更新消费结果补单次数
	 * @param salesResultReverseOrder 消费结果补单实体Bean
	 */
	public void updateSalesResultReverseOrderCount(SalesResultReverseOrder salesResultReverseOrder);
	
	/**
	 * 更新补单记录表数据
	 * @param salesResultReverseOrder
	 */
	public void updateSalesResultReverse(SalesResultReverseOrder salesResultReverseOrder);
}