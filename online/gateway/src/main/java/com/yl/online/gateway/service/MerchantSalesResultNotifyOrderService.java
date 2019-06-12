package com.yl.online.gateway.service;

import java.util.List;

import com.yl.online.model.model.MerchantSalesResultNotifyOrder;

/**
 * 商户消费结果通知订单业务处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public interface MerchantSalesResultNotifyOrderService {

	/**
	 * 记录失败的商户消费结果通知订单
	 * @param merchantSalesResultNotifyOrder 商户消费结果的订单实体
	 */
	void recordFaildOrder(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder);

	/**
	 * 根据交易订单编号查询
	 * @param code 交易订单编号
	 * @return MerchantSalesResultNotifyOrder
	 */
	MerchantSalesResultNotifyOrder queryByOrderCode(String code);

	/**
	 * 批量查询商户消费结果订单
	 * @param maxCount 最大补单次数
	 * @param maxNums
	 * @return List<MerchantSalesResultNotifyOrder>
	 */
	List<MerchantSalesResultNotifyOrder> queryBy(int maxCount, int maxNums);

	/**
	 * 更新商户消费结果实体信息
	 * @param merchantSalesResultNotifyOrder 商户消费结果的订单实体
	 */
	void updateMerchantSalesResultNotifyOrder(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder);

	/**
	 * 更新商户消费结果通知次数
	 * @param merchantSalesResultNotifyOrder 商户消费结果的订单实体
	 */
	void updateNotifyCount(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder);

}
