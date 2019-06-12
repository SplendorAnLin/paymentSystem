package com.yl.realAuth.front.dao;

import java.util.Date;
import java.util.List;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.model.AuthResultNotifyOrder;

public interface AuthSalesResultNotifyOrderDao {
	/**
	 * 根据订单号查询补单记录
	 * @param code 订单号
	 * @return 补单记录
	 */
	AuthResultNotifyOrder queryOrderByCode(String code);

	/**
	 * 记录失败补单信息
	 * @param authResultNotifyOrder
	 */
	void recordFaildOrder(AuthResultNotifyOrder authResultNotifyOrder);

	/**
	 * 根据最大补单次数和补单量查询补单记录
	 * @param maxCount 最大补单次数
	 * @param page 补单量
	 * @param before 当前时间前三个小时
	 * @param before 当前时间
	 * @return 补单记录集合
	 */
	List<AuthResultNotifyOrder> queryBy(int maxCount, Page page, Date before, Date now);

	/**
	 * 更新商户消费结果实体信息
	 * @param merchantSalesResultNotifyOrder 商户消费结果的订单实体
	 */
	void updateAuthSalesResultNotifyOrder(AuthResultNotifyOrder authResultNotifyOrder);

	/**
	 * 更新商户消费结果通知次数
	 * @param merchantSalesResultNotifyOrder 商户消费结果的订单实体
	 */
	void updateNotifyCount(AuthResultNotifyOrder authResultNotifyOrder);
}
