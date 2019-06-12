package com.yl.recon.core.service;

import com.yl.recon.core.entity.ReconOrder;

import java.util.Map;

/**
 * 对账订单访问接口
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月11日
 */
public interface ReconOrderService {


	/**
	 * 初始化对账结果(对账订单)
	 *
	 * @param params
	 * @return
	 */
	ReconOrder initReconOrder(Map <String, Object> params);

}