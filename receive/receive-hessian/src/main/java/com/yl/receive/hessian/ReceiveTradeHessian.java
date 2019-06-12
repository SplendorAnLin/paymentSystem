package com.yl.receive.hessian;

import com.yl.receive.hessian.bean.ReceiveRequestBean;
import com.yl.receive.hessian.bean.ResponseBean;

/**
 * 代收交易远程
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月7日
 * @version V1.0.0
 */
public interface ReceiveTradeHessian {
	
	/**
	 * 单笔交易
	 * @param receiveRequestBean
	 * @return
	 */
	ResponseBean singleTrade(ReceiveRequestBean receiveRequestBean);

}
