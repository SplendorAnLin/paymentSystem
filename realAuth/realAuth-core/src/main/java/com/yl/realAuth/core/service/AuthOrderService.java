package com.yl.realAuth.core.service;

import java.util.Date;
import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.hessian.bean.AuthOrderQueryBean;
import com.yl.realAuth.model.RealNameAuthOrder;

/**
 * 实名认证订单服务管理
 * @author Shark
 * @since 2015年6月5日
 */
public interface AuthOrderService {
	/**
	 * 根据商户编号和请求号查询实名认证订单记录
	 * @param partner 商户编号
	 * @param requestCode 商户请求号
	 * @return
	 */
	RealNameAuthOrder queryByRequestCode(String partner, String requestCode);

	/**
	 * 订单信息新增
	 * @param order 订单实体
	 */
	void insertOrder(RealNameAuthOrder order);

	/**
	 * 查询实名认证订单
	 * @param page
	 * @param authOrderQueryBean
	 * @return
	 */
	Page authOrderQuery(Page page, AuthOrderQueryBean authOrderQueryBean);

	/**
	 * 实名认证订单合计
	 * @param authOrderQueryBean
	 * @return
	 */
	String authOrderTotal(AuthOrderQueryBean authOrderQueryBean);

	/**
	 * 实名认证订单导出
	 * @param authOrderQueryBean
	 * @return
	 */
	String authOrderExport(AuthOrderQueryBean authOrderQueryBean);

	/**
	 * 订单超时关闭查询
	 * @param page 批量执行500条
	 * @param dBefore 当期时间前6小时
	 * @param dqBefore 当前时间前12小时
	 * @return
	 */
	List<RealNameAuthOrder> queryWaitCloseOrderBy(Page page, Date dBefore, Date dqBefore);

	/**
	 * 订单超时关闭查询2
	 * @param page 批量执行500条
	 * @param dBefore 当期时间前6小时
	 * @param dqBefore 当前时间前12小时
	 * @return
	 */
	List<RealNameAuthOrder> queryWaitOrProcessOrderBy(Page page, Date dBefore, Date dqBefore);

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
	RealNameAuthOrder queryAuthOrderByCode(String orderCode);

	/**
	 * 订单信息修改
	 * @param RealNameAuthOrder 订单记录
	 */
	void modifyOrderStatus(RealNameAuthOrder RealNameAuthOrder);

	/**
	 * 查询前一天所有成功的订单
	 * @param dBefore 前一天时间
	 * @return
	 */
	List<RealNameAuthOrder> querySuccessOrderBy(Date dBefore);

	/**
	 * 根据商户编号和商户订单号查询实名认证交易信息
	 * @param requestCode 商户订单号
	 * @param customerNo 商户编号
	 * @return
	 */
	RealNameAuthOrder findRequestBy(String partnerCode, String outOrderId);
}
