package com.yl.realAuth.core.service;

import com.yl.realAuth.hessian.bean.AuthResponseResult;
import com.yl.realAuth.hessian.bean.CreateOrderBean;
import com.yl.realAuth.hessian.exception.BusinessException;


/**
 * 实名认证服务管理
 * @author Shark
 * @since 2015年6月6日
 */
public interface AuthTradeService {
	/**
	 * 生成订单记录
	 * @param createOrderBean 订单创建实体Bean
	 * @return
	 */
	AuthResponseResult createOrder(CreateOrderBean createOrderBean) throws BusinessException;

	/**
	 * 根据商户编号和商户订单号查询实名认证结果
	 * @param partnerCode 商户编号
	 * @return ReaNameAuthOrder
	 */
	// QueryAuthResponseResult queryOrder(String partnerCode, String outOrderId);

}
