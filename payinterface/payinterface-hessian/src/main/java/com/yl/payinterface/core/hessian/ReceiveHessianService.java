package com.yl.payinterface.core.hessian;

import java.util.Map;

import com.yl.payinterface.core.bean.ReceiveQueryBean;
import com.yl.payinterface.core.bean.ReceiveResponseBean;
import com.yl.payinterface.core.bean.ReceiveTradeBean;

/**
 * 代收接口系统业务处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
public interface ReceiveHessianService {

	/**
	 * 交易处理
	 * @param receiveTradeBean 代收接口公用BEAN
	 */

	Map<String,String> trade(ReceiveTradeBean receiveTradeBean);

	/**
	 * 交易完成处理
	 * @param receiveResponseBean 代收接口响应信息
	 */
	public void complete(ReceiveResponseBean receiveResponseBean);

	/**
	 * 查询处理
	 * @param receiveTradeBean 代收接口公用BEAN
	 */
	Map<String, String> query(ReceiveQueryBean receiveQueryBean);

}
