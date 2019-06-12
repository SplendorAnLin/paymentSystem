package com.yl.recon.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.recon.api.core.bean.ReconOrderDataQueryBean;
import com.yl.recon.core.entity.order.TradeOrder;

/**
 * 交易订单访问接口
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月11日
 */
public interface TradeOrderService {


	/**
	 * 单个插入
	 *
	 * @param tradeOrder
	 * @return
	 */
	int insert(TradeOrder tradeOrder);

	/**
	 * 插入
	 *
	 * @param tradeOrder
	 * @return
	 */
	int insertSelective(TradeOrder tradeOrder);

	/**
	 * 批量插入
	 *
	 * @param tradeOrders
	 * @return
	 */
	int insertList(List <TradeOrder> tradeOrders);

	/**
	 * 修改交易订单记录
	 */
	int update(TradeOrder tradeOrder);


	/**
	 * 分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @param page
	 * @return
	 */
	List <com.yl.recon.api.core.bean.order.TradeOrder> findAllTradeOrders(ReconOrderDataQueryBean reconOrderDataQueryBean, Page page);

	/**
	 * 不分页查询
	 *
	 * @param reconOrderDataQueryBean
	 * @return
	 */
	List <com.yl.recon.api.core.bean.order.TradeOrder> findTradeOrders(ReconOrderDataQueryBean reconOrderDataQueryBean);

 }