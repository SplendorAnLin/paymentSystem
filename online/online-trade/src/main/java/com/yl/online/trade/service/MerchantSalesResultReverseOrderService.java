package com.yl.online.trade.service;

import java.util.List;

import com.yl.online.model.model.MerchantSalesResultReverseOrder;

/**
 * 商户消费结果补单业务处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface MerchantSalesResultReverseOrderService {

	/**
	 * 记录商户消费结果补单信息
	 * @param merchantSalesResultReverseOrder 商户消费结果补单实体
	 */
	public void createMerchantSalesResultReverseOrder(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder);
	
	/**
	 * 根据交易订单编号查询消费结果补单信息
	 * @param orderCode 交易订单编号
	 * @return MerchantSalesResultReverseOrder
	 */
	public MerchantSalesResultReverseOrder queryBy(String orderCode);

	/**
	 * 根据补单次数查询需要补单的商户消费结果信息
	 * @param maxCount 最大补单次数
	 * @param maxNums 一批最大补单量
	 * @return List<MerchantSalesResultReverseOrder>
	 */
	public List<MerchantSalesResultReverseOrder> queryBy(int maxCount, int maxNums);

	/**
	 * 更新商户消费补单次数
	 * @param merchantSalesResultReverseOrder 商户消费结果补单实体
	 */
	public void updateMerchantSalesResultReverseOrderCount(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder);

	/**
	 * 更新商户消费补单信息
	 * @param merchantSalesResultReverseOrder 商户消费结果补单实体
	 */
	public void updateMerchantSalesResultReverseOrder(MerchantSalesResultReverseOrder merchantSalesResultReverseOrder);

}
