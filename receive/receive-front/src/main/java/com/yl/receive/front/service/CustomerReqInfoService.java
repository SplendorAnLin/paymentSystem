package com.yl.receive.front.service;

import com.yl.receive.front.model.CustomerReqInfo;

/**
 * 商户请求信息业务访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public interface CustomerReqInfoService {

	/**
	 * 保存商户请求参数
	 * @param customerReqInfo
	 */
	public void create(CustomerReqInfo customerReqInfo);
}
