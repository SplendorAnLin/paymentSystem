package com.yl.online.trade.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.online.model.bean.TradeContext;
import com.yl.online.model.enums.RefundStatus;
import com.yl.online.model.model.Order;

/**
 * 交易订单业务处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface TradeOrderService {
	
	
	
	/**
	 * 交易查询page分页
	 * @param page
	 * @param params
	 * @return
	 */
	public List<Order> findTradeOrder(Page page, Map<String, Object> params);
	
	/**
	 * 统计金额
	 * @param orderTime
	 * @param receiver
	 * @param payType
	 * @return
	 */
	Double orderAmountSum(Date timeStart, String receiver,String payType);

	void create(Order order);

	/**
	 * 根据交易订单号查询交易订单
	 * @param code 交易订单号
	 * @return 交易订单
	 */
	Order queryByCode(String code);

	/**
	 * 根据收款方编号和业务订单号查询交易订单
	 * @param receiver 收款方
	 * @param requestCode 业务订单号
	 * @return 交易订单
	 */
	Order queryByRequestCode(String receiver, String requestCode);

	/**
	 * 交易完成
	 * @param tradeContext 完整的交易实体
	 */
	void complete(TradeContext tradeContext);

	/**
	 * 分页查询交易订单（交易状态为成功）
	 * @param page 分页实体
	 * @return List<TradeOrder>
	 */
	List<Order> queryBy(Page page);

	/**
	 * 根据支付接口流水查询交易订单实体
	 * @param interfaceOrderID 支付接口流水
	 * @return Order
	 */
	Order queryByInterfaceRequestID(String interfaceRequestID);

	/**
	 * 按批更新交易订单(已超时)
	 * @param order 交易订单信息
	 */
	void closeTimeoutOrder(Order order);

	/**
	 * 根据交易记录流水号查询支付订单
	 * @param businessOrderID 交易记录流水号
	 * @return Order
	 */
	Order queryByPaymentCode(String businessOrderID);

	/**
	 * 分页查询待关闭交易订单信息
	 * @param maxNums 一次关闭订单数
	 * @return List<Order>
	 */
	List<Order> queryWaitCloseOrderBy(int maxNums);

	/**
	 * 更新交易订单信息
	 * @param order 交易订单信息
	 */
	void modifyOrder(Order order);

	/**
	 * 更新订单清算状态
	 * @param order
	 */
	void modifyOrderClearingStatus(Order order);
	
	/**
	 * 分页查询支付记录数和支付手续费
	 * @param showCount 每页显示条数
	 * @param currentPage 当前页数
	 * @return
	 */
	public Object findAllTradeOrderAndFee(Page page, Map<String, Object> params);

	/**
	 * 根据订单编号查询订单信息
	 * @param orderCode 支付订单号
	 * @return
	 */
	public String queryTradeOrderByCode(String orderCode);

	/**
	 * 订单信息合计
	 * @param param 参数
	 * @return
	 */
	public String orderFeeSum(Map<String, Object> params);

	/**
	 * 订单信息导出
	 * @param param 参数
	 * @return
	 */
	public String orderInfoExport(Map<String, Object> params);

	/**
	 * 查询订单支付交易参数
	 * @param params 参数列表
	 * @return OrderPayBean
	 */
	public Order findOrderPayTradeInfo(Map<String, Object> params);

	/**
	 * 根据合作方编号和订单号查询交易订单信息
	 * @param partner 合作方编号
	 * @param outOrderId 合作方订单号
	 * @return
	 */
	public String findOrderBy(String partner, String outOrderId);

	/**
	 * 根据商户编号或者交易日期分组合计
	 * @param params 查询参数
	 * @return 合计结果
	 */
	public Map<Object, Object> payOrderGroupSum(Map<String, String> params);
	
	/**
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @return
	 */
	public List<Map<String, String>> orderSumByDay(Date orderTimeStart, Date orderTimeEnd,String receiver);
	
	/**
	 * 根据日期和订单状态统计交易订单数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param status
	 * @return
	 */
	public List<Map<String, String>> orderSum(Date orderTimeStart, Date orderTimeEnd, String status, List receiver);
	
	/**
	 * 根据日期查询成功订单笔数和金额
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param receiver
	 * @return
	 */
	public List<Map<String,String>> orderAmountSumByDay(String orderTimeStart, String orderTimeEnd, String receiver);
	
	/**
	 * 根据日期查询成功订单笔数 金额 支付方式
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param receiver
	 * @return
	 */
	public List<Map<String,String>> orderAmountSumByPayType(Date orderTimeStart, Date orderTimeEnd, String receiver);
	/**
	 * 查询支付订单
	 */
	public List<Map<String, Object>> selectTradeOrder(Map<String, Object> params);
	/**
	 * 查询支付订单合计
	 */
	public List<Map<String, Object>> selectTradeOrderSum(Map<String, Object> params);
	/**
	 * 查询支付订单详细
	 */
	public List<Map<String, Object>> selectTradeOrderDetailed(Map<String, Object> params);
	
	/**
	 * 根据customerNo/angetNo查询条数
	 */
	public Map<String, Object> selectOrderCount(Map<String, Object> params);
	
	/**
	 * 统计7日每天数据合计
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> orderWeekSumByDay(Map<String, Object> params);

	List<Map<String, Object>> findOrderApp(Map<String, Object> params);
	
	public Order findTradeOrderDetail(Map<String, Object> params);
	
		/**
	 * 定时任务 - 成功订单
	 * @param params
	 * @return
	 */
	List<Order> findOrderByJob(Map<String, Object> params);
	
	/**
	 * 定时任务 - 成功订单合计
	 * @param params
	 * @return
	 */
	Map<String, Object> findTotalByJob(Map<String, Object> params);
}