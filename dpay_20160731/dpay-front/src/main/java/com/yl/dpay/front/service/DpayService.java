package com.yl.dpay.front.service;

import com.yl.dpay.front.model.DpayInfoBean;
import com.yl.dpay.front.model.DpayQueryResBean;
import com.yl.dpay.front.model.DpayTradeResBean;

/**
 * 代付服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月24日
 * @version V1.0.0
 */
public interface DpayService {

	/**
	 * 单笔代付
	 * @param dpayInfoBean
	 * @param customerNo
	 * @return
	 */
	public DpayTradeResBean singleRequest(DpayInfoBean dpayInfoBean, String customerNo);

	/**
	 * 根据商户编号、商户订单号查询
	 * @param customerNo
	 * @param customerOrderId
	 * @return
	 */
	public DpayQueryResBean findByCustOrderId(String customerNo, String customerOrderId);
	
	/**
	 * 提现
	 * @param customerNo
	 * @param amount
	 * @return
	 */
	public DpayTradeResBean drawCash(String customerNo, double amount);

}
