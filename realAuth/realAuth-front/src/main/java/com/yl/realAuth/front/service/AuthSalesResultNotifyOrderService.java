package com.yl.realAuth.front.service;

import java.util.Date;
import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.model.AuthResultNotifyOrder;

public interface AuthSalesResultNotifyOrderService {
	/**
	 * 根据订单号查询补单记录
	 * @param code 订单号
	 * @return 补发通知记录
	 */
	AuthResultNotifyOrder queryByOrderCode(String code);

	/**
	 * 通知失败信息记录
	 * @param authResultNotifyOrder 通知失败信息
	 */
	void recordFaildOrder(AuthResultNotifyOrder authResultNotifyOrder);

	/**
	 * 根据最大补单次数和一次补单量查询补单记录
	 * @param maxCount 最大补单次数
	 * @param page 一次补单量
	 * @param dBefore 起始查询时间
	 * @param date 当前时间
	 * @return
	 */
	List<AuthResultNotifyOrder> queryBy(int maxCount, Page page, Date date, Date dBefore);

	/**
	 * 更新实名认证通知记录
	 * @param authResultNotifyOrder 实名认证通知记录
	 */
	void updateAuthSalesResultNotifyOrder(AuthResultNotifyOrder authResultNotifyOrder);

	/**
	 * 更新实名认证通知次数
	 * @param authResultNotifyOrder 实名认证通知记录
	 */
	void updateNotifyCount(AuthResultNotifyOrder authResultNotifyOrder);
}
