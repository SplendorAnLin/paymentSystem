package com.yl.receive.core.service;

import com.yl.receive.hessian.bean.ReceiveRequestBean;
import com.yl.receive.hessian.bean.ResponseBean;

/**
 * 代收交易服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月4日
 * @version V1.0.0
 */
public interface ReceiveTrade {

	public ResponseBean trade(ReceiveRequestBean receiveRequestBean);
}
