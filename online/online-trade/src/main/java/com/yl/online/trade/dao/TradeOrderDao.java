package com.yl.online.trade.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.Order;

/**
 * 交易订单数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface TradeOrderDao {
	
	
	/**
	 * 更新支付方式
	 * @param orderCode
	 * @param payType
	 */
	void updatePay(@Param("orderCode")String orderCode,@Param("payType")String payType);
	
	/**
	 * 根据时间  商户号 支付方式查询交易金额
	 * @param orderTime
	 * @param receiver
	 * @param payType
	 * @return
	 */
	Map<String, Double> orderAmountSum(@Param("timeStart")String timeStart, @Param("timeEnd")String timeEnd,@Param("receiver")String receiver,@Param("payType")String payType);

	/**
	 * 创建交易订单
	 * @param order 交易订单实体
	 * @return int
	 */
	void insert(Order order);

	/**
	 * 根据交易订单号查询交易订单
	 * @param code 交易订单号
	 * @return 交易订单
	 */
	Order findByCode(@Param("code")String code);

	/**
	 * 根据收款方编号和业务订单号查询交易订单
	 * @param receiver 收款方
	 * @param requestCode 业务订单号
	 * @return 交易订单
	 */
	Order findByRequestCode(@Param("receiver")String receiver, @Param("requestCode")String requestCode);

	/**
	 * 更新交易订单信息
	 * @param order 交易订单实体
	 */
	void update(@Param("order")Order order);

	/**
	 * 分页查询交易订单实体
	 * @param page 分页实体
	 * @return List<TradeOrder>
	 */
	List<Order> findBy(@Param("page")Page page);

	/**
	 * 按批更新交易订单(已超时)
	 * @param order 交易订单信息
	 */
	void closeTimeoutOrder(@Param("order")Order order);

	/**
	 * 分页查询待关闭交易订单信息
	 * @param maxNums 一次关闭订单数
	 * @return List<Order>
	 */
	List<Order> queryWaitCloseOrderBy(@Param("maxNums")int maxNums);

	/**
	 * 入账成功更新交易清算状态
	 * @param order
	 */
	void modifyOrderClearingStatus(@Param("order")Order order);
	
	/**
	 * 分页查询订单信息和手续费
	 * @param page
	 * @param params
	 * @return
	 */
	public List<Order> findAllTradeOrderAndFee(@Param("page")Page page, @Param("params")Map<String, Object> params,@Param("list")List<String> list);

	/**
	 * 订单费用合计
	 * @param params
	 * @return
	 */
	public Map<String, Object> orderFeeSum(@Param("params")Map<String, Object> params,@Param("list")List<String> list);

	/**
	 * 订单信息导出
	 * @param params
	 * @return
	 */
	public List<Order> exportTradeOrderInfo(@Param("params") Map<String, Object> params,@Param("list")List<String> list);

	/**
	 * 根据合作方编号或者交易日期分组查询
	 * @param params 查询参数
	 * @param orderTimeEnd 交易起始时间
	 * @param orderTimeStart 交易终止时间
	 * @return
	 */
	public List<Order> payOrderGroupSum(@Param("params")Map<String, String> params, @Param("orderTimeStart")Date orderTimeStart, 
			@Param("orderTimeEnd")Date orderTimeEnd);
	
	/**
	 * 按天统计成功交易金额
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 */
	public List<Map<String,String>> orderSumByDay(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd,@Param("receiver")String receiver);
	
	/**
	 * 根据日期和订单状态统计交易订单数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param status
	 * @return
	 */
	public List<Map<String, String>> orderSum(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd,@Param("status")String status,@Param("list")List<String> receiver);
	
	/**
	 * 根据日期查询成功订单笔数和金额
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param receiver
	 * @return
	 */
	public List<Map<String, String>> orderAmountSumByDay(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd,@Param("receiver")String receiver);
	
	/**
	 * 根据日期查询成功订单笔数 金额 支付方式
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param receiver
	 * @return
	 */
	public List<Map<String, String>> orderAmountSumByPayType(@Param("orderTimeStart")String orderTimeStart, 
			@Param("orderTimeEnd")String orderTimeEnd,@Param("receiver")String receiver);
	
	/**
	 * 查询支付订单
	 */
	public List<Map<String, Object>> selectTradeOrder(@Param("params")Map<String, Object> params);
	/**
	 * 查询支付订单合计
	 */
	public List<Map<String, Object>> selectTradeOrderSum(@Param("params")Map<String, Object> params);
	/**
	 * 查询支付订单详细
	 */
	public List<Map<String, Object>> selectTradeOrderDetailed(@Param("params")Map<String, Object> params);
	/**
	 * 根据customerNo/angetNo 查询条数
	 */
	public Map<String, Object>  selectOrderCount(@Param("params")Map<String, Object> params);
	
	public List<Order> findAllTradeOrder(@Param("page")Page page, @Param("params")Map<String, Object> params);
	
	public Order findTradeOrderDetail( @Param("params")Map<String, Object> params);
	/**
	 * 统计7日每天数据合计
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> orderWeekSumByDay(@Param("params")Map<String, Object> params);
	/**
	 * App订单前4条
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findOrderApp(@Param("params")Map<String, Object> params);

	
	/**
	 * 定时任务 - 成功订单
	 * @param params
	 * @return
	 */
	List<Order> findOrderByJob(@Param("params")Map<String, Object> params);
	
	/**
	 * 定时任务 - 成功订单合计
	 * @param params
	 * @return
	 */
	Map<String, Object> findTotalByJob(@Param("params")Map<String, Object> params);

	
	/**
	 * 商户对账单生产
	 */
	public List<Order> customerRecon(@Param("params")Map<String, Object> params);
}