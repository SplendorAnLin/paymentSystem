package com.yl.realAuth.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.model.RealNameAuthOrder;

/**
 * 实名认证订单管理
 */
public interface AuthOrderDao {

	/**
	 * 查询订单信息
	 * @param page 分页
	 * @param params 查询参数
	 * @return
	 */
	List<RealNameAuthOrder> authOrderQuery(Page<?> page, Map<String, Object> params);

	/**
	 * 实名认证订单合计
	 * @param params
	 * @return
	 */
	Map<String, Object> authOrderTotal(Map<String, Object> params);

	/**
	 * 实名认证订单导出
	 * @param params
	 * @return
	 */
	List<RealNameAuthOrder> authOrderExport(Map<String, Object> params);

	/**
	 * 实名认证订单新增
	 * @param order 订单实体
	 */
	void insertAuthOrder(RealNameAuthOrder order);

	/**
	 * 根据商户编号和商户订单号查询实名认证记录
	 * @param customerNo 商户编号
	 * @param requestCode 商户订单号
	 * @return
	 */
	RealNameAuthOrder queryOrderByRequestCode(@Param("customerNo")String customerNo, @Param("requestCode")String requestCode);

	/**
	 * 订单超时关闭查询
	 * @param page 批量执行500条
	 * @param dBefore 当前时间前6小时
	 * @param dqBefore 当前时间前12小时
	 * @return
	 */
	List<RealNameAuthOrder> queryWaitCloseOrder(Page<?> page, Date dBefore, Date dqBefore);

	/**
	 * 订单超时关闭
	 * @param RealNameAuthOrder 单笔订单
	 */
	void closeTimeoutOrder(RealNameAuthOrder RealNameAuthOrder);

	/**
	 * 根据订单号查询实名认证实体信息
	 * @param orderCode 订单号
	 * @return 请求实体
	 */
	RealNameAuthOrder queryAuthOrderByCode(@Param("orderCode")String orderCode);

	/**
	 * 实名认证订单信息更新
	 * @param RealNameAuthOrder
	 */
	void updateOrderStatus(RealNameAuthOrder RealNameAuthOrder);

	/**
	 * 订单初始化,处理中的订单关闭
	 * @param page
	 * @param dBefore
	 * @param dqBefore
	 * @return
	 */
	List<RealNameAuthOrder> queryWaitProcessOrder(Page<?> page, Date dBefore, Date dqBefore);

	/**
	 * 根据日期查询成功的订单
	 * @param customer
	 * @param authOrderStatus
	 * @param startOrderTime
	 * @param endOrderTime
	 * @return
	 */
	List<RealNameAuthOrder> findSuccessOrderBy(Date startOrderTime, Date endOrderTime);

	/**
	 * 根据商户编号和商户订单号查询实名认证结果
	 * @param outOrderId商户订单号
	 * @param partnerCode 商户编号
	 * @return
	 */
	RealNameAuthOrder findRequestBy(String partnerCode, String outOrderId);
}
