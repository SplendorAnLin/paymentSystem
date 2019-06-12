package com.yl.online.trade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.online.model.model.MerchantSalesResultReverseOrder;

/**
 * 商户消费结果补单数据处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface MerchantSalesResultReverseOrderDao {

	/**
	 * 记录商户消费结果补单信息
	 * @param merchantSalesResultReverseOrder 商户消费结果补单实体
	 */
	void createReverseOrder(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder);

	/**
	 * 根据交易订单编号查询消费结果补单信息
	 * @param orderCode 交易订单编号
	 * @return MerchantSalesResultReverseOrder
	 */
	MerchantSalesResultReverseOrder queryByOrderCode(@Param("orderCode")String orderCode);

	/**
	 * 根据补单次数查询需要补单的商户消费结果信息
	 * @param maxCount 最大补单次数
	 * @param maxNums 一批最大补单量
	 * @return List<MerchantSalesResultReverseOrder>
	 */
	List<MerchantSalesResultReverseOrder> queryBy(@Param("maxCount")int maxCount, @Param("maxNums")int maxNums);

	/**
	 * 更新商户消费补单次数
	 * @param merchantSalesResultReverseOrder 商户消费结果补单实体
	 */
	void updateMerchantSalesResultReverseOrderCount(@Param("merchantSalesResultReverseOrder")MerchantSalesResultReverseOrder merchantSalesResultReverseOrder);

	/**
	 * 更新商户消费补单信息
	 * @param merchantSalesResultReverseOrder 商户消费结果补单实体
	 */
	void updateMerchantSalesResultReverseOrder(@Param("merchantSalesResultReverseOrder")MerchantSalesResultReverseOrder merchantSalesResultReverseOrder);

}
