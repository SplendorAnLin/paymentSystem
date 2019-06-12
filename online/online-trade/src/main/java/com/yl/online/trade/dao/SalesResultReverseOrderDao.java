package com.yl.online.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.online.model.model.SalesResultReverseOrder;

/**
 * 消费结果补单数据处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface SalesResultReverseOrderDao {
	
	/**
	 * 记录消费结果补单信息
	 * @param salesResultReverseOrder 消费结果补单实体Bean
	 */
	void createSalesResultReverseOrder(SalesResultReverseOrder salesResultReverseOrder);

	/**
	 * 根据交易订单号查询消费结果补单实体
	 * @param businessOrderID 交易订单号
	 * @return SalesResultReverseOrder
	 */
	SalesResultReverseOrder querySalesResultReverseOrder(@Param("businessOrderID")String businessOrderID);

	/**
	 * 根据补单次数查询需要补单的消费结果信息
	 * @param maxCount 最大补单次数
	 * @param maxNums 一批最大补单量
	 * @return List<SalesResultReverseOrder>
	 */
	List<SalesResultReverseOrder> querySalesResultReverseOrderBy(@Param("maxCount")int maxCount, @Param("maxNums")int maxNums);

	/**
	 * 更新消费结果补单信息
	 * @param salesResultReverseOrder 消费结果补单实体Bean
	 */
	void updateSalesResultReverseOrder(@Param("salesResultReverseOrder")SalesResultReverseOrder salesResultReverseOrder);

	/**
	 * 更新消费结果补单次数
	 * @param salesResultReverseOrder 消费结果补单实体Bean
	 */
	void updateSalesResultReverseOrderCount(@Param("salesResultReverseOrder")SalesResultReverseOrder salesResultReverseOrder);

	/**
	 * 更新补单记录表数据
	 * @param salesResultReverseOrder
	 */
	void updateSalesResultReverse(@Param("salesResultReverseOrder")SalesResultReverseOrder salesResultReverseOrder);
}