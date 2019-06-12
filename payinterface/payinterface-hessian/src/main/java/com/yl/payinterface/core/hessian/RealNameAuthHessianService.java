package com.yl.payinterface.core.hessian;

import com.yl.payinterface.core.bean.AuthRequestBean;
import com.yl.payinterface.core.bean.AuthRequestResponseBean;

public interface RealNameAuthHessianService {

	/**
	 * 实名认证交易处理
	 * @param authRequestBean 请求参数
	 * @return
	 */
	Object trade(AuthRequestBean authRequestBean);

	/**
	 * 实名认证交易查询处理
	 * @param authRequestQueryBean 交易查询接口公用Bean
	 * @return 返回值
	 */
	// Map<String, String> query(AuthRequestQueryBean authRequestQueryBean);

	/**
	 * 交易完成处理
	 * @param authRequestResponseBean 资金通道响应结果Bean
	 * @return 返回值
	 */
	Object complete(AuthRequestResponseBean authRequestResponseBean);

}
