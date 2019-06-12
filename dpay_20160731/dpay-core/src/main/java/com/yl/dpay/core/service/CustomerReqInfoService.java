package com.yl.dpay.core.service;

import com.yl.dpay.core.entity.CustomerReqInfo;

/**
 * 商户请求信息服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface CustomerReqInfoService {

	/**
	 * 查询商户请求记录
	 * @param customerNo
	 * @param customerOrderCode
	 * @param requestType
	 * @return
	 */
	public CustomerReqInfo findByCutomerOrderCode(String customerNo, String customerOrderCode, String requestType);
}
