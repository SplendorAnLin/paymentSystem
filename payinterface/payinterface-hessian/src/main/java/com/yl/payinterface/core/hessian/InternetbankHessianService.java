package com.yl.payinterface.core.hessian;

import java.util.Map;

import com.lefu.hessian.bean.AuthBean;
import com.yl.payinterface.core.bean.InternetbankQueryBean;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.InternetbankSalesTradeBean;

/**
 * 支付接口服务网银支付Hessian接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public interface InternetbankHessianService {

	/**
	 * 交易处理
	 * @param internetbankSalesTradeBean 交易接口公用BEAN
	 * @return 返回值
	 */
	Object trade(InternetbankSalesTradeBean internetbankSalesTradeBean);
	
	/**
	 * 交易查询处理
	 * @param auth 认证Bean
	 * @param internetbankQueryBean 交易查询接口公用Bean
	 * @return 返回值
	 */
	Map<String, String> query(AuthBean auth, InternetbankQueryBean internetbankQueryBean);

	/**
	 * 交易完成处理
	 * @param auth 认证Bean
	 * @param internetbankResponseBean B2C资金通道响应结果Bean
	 * @return 返回值
	 */
	void complete(AuthBean auth, InternetbankSalesResponseBean internetbankResponseBean);

}
