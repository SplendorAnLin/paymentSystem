package com.yl.payinterface.core.service;

import com.yl.payinterface.core.bean.AuthSaleBean;

/**
 * 认证持卡人信息服务
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public interface AuthSaleInfoService {

	/**
	 * 认证持卡人信息新增
	 * @param authSaleBean 持卡人信息
	 */
	void save(AuthSaleBean authSaleBean);
	
	/**
	 * 根据接口请求号查询认证持卡人信息
	 * @param interfaceRequestId 持卡人信息
	 */
	AuthSaleBean findByInterfaceRequestId(String interfaceRequestId);

}
