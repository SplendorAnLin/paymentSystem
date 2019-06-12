package com.yl.online.trade.hessian;

import java.util.Date;
import java.util.List;
import java.util.Map;


import com.lefu.commons.utils.Page;
import com.yl.online.model.enums.RefundStatus;
import com.yl.online.model.model.Order;

/**
 * 在线交易查詢服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
public interface OnlineTradeQueryHessianService {
	/**
	 * 交易查询page分页
	 * @param page
	 * @param params
	 * @return
	 */
	public Page findTradeOrder(Page page, Map<String, Object> params);
	
	/**
	 * 订单信息查询
	 * @param page 分页
	 * @param params 参数列表
	 * @return Object
	 */
	Object findAllTradeOrderAndFee(Page page, Map<String, Object> params);

	/**
	 * 根据交易订单号查询订单详细
	 * @param tradeOrderCode 交易订单号
	 * @return Object
	 */
	Object findOrderByCode(String tradeOrderCode);

	/**
	 * 订单费用合计
	 * @param params
	 * @return String
	 */
	String orderFeeSum(Map<String, Object> params);

	/**
	 * 分页查询退款记录
	 * @param page 分页
	 * @param params 参数列表
	 * @return Object
	 */
	Object findRefundInfo(Page page, Map<String, Object> params);

	/**
	 * 订单信息导出
	 * @param params 参数列表
	 * @return String
	 */
	String orderInfoExport(Map<String, Object> params);

	/**
	 * 根据合作方编号和订单号查询订单信息
	 * @param partner 合作方编码
	 * @param outOrderId 合作方订单号
	 * @return
	 */
	String findOrderBy(String partner, String outOrderId);

	/**
	 * 根据合作方编号或者交易日期分组合计
	 * @param params 查询参数
	 * @return 合计结果
	 */
	Map<Object, Object> payOrderGroupSum(Map<String, String> params);
	
	/**
	 * 查询payment
	 * @param orderCode
	 * @return
	 */
	Object findPaymentByOrderCode(String orderCode);
	
	/**
	 * 根据日期统计成功交易金额
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @return
	 */
	List<Map<String,String>> orderSumByDay(Date orderTimeStart, Date orderTimeEnd,String receiver);
	
	/**
	 * 根据日期和订单状态统计交易订单数
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @return
	 */
	List<Map<String,String>> orderSum(Date orderTimeStart, Date orderTimeEnd, String status, List receiver);
	
	/**
	 * 根据日期查询成功订单笔数和金额
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param receiver
	 * @return
	 */
	List<Map<String,String>> orderAmountSumByDay(String orderTimeStart, String orderTimeEnd, String receiver);
	
	/**
	 * 根据日期查询成功订单笔数 金额 支付方式
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param receiver
	 * @return
	 */
	List<Map<String,String>> orderAmountSumByPayType(Date orderTimeStart, Date orderTimeEnd, String receiver);
	/**
	 * app调用订单查询接口
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectTradeOrder(Map<String, Object> params);
	/**
	 * app调用订单查询合计接口
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectTradeOrderSum(Map<String, Object> params);

	/**
	 * app调用订单查询详细
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> selectTradeOrderDetailed(Map<String, Object> params);
	
	/**
	 * 根据customerNo/angetNo 查询条数
	 * @param params
	 * @return
	 */
	public Map<String, Object> selectOrderCount(Map<String, Object> params);
	/**
	 * 统计7日每天数据合计
	 * @param params
	 * @returnz
	 */
	public List<Map<String, Object>> orderWeekSumByDay(Map<String, Object> params);
	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findOrderApp(Map<String, Object> params);
	
	public Order findTradeOrderDetail( Map<String, Object> params);
	
	/**
	 * 商户对账单生产
	 */
	List<Order> customerRecon(Map<String, Object> params);
}