package com.yl.dpay.front.service;

import com.yl.dpay.front.model.CustomerReqInfo;

/**
 * 商户请求信息服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月24日
 * @version V1.0.0
 */
public interface CustomerReqInfoService {

	/**
	 * 保存商户请求参数
	 * @param customerReqInfo
	 */
	public void create(CustomerReqInfo customerReqInfo);
}
