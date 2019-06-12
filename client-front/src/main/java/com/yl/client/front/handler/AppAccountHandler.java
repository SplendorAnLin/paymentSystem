package com.yl.client.front.handler;

import java.util.Map;

import com.yl.client.front.common.AppRuntimeException;

/**
 * App账户处理
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public interface AppAccountHandler extends AppHandler{
	
	 Map<String,Object> findBalance(Map<String,Object> param) throws AppRuntimeException;
	 
	 Map<String, Object> findAccountChange(Map<String, Object> queryParams) throws AppRuntimeException;
	 
}
