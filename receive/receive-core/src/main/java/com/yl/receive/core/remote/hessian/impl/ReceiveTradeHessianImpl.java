package com.yl.receive.core.remote.hessian.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.receive.core.service.ReceiveTrade;
import com.yl.receive.hessian.ReceiveTradeHessian;
import com.yl.receive.hessian.bean.ReceiveRequestBean;
import com.yl.receive.hessian.bean.ResponseBean;

/**
 * 代收交易远程实现
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月7日
 * @version V1.0.0
 */
@Service("receiveTradeHessian")
public class ReceiveTradeHessianImpl implements ReceiveTradeHessian {
	
	@Resource
	private ReceiveTrade receiveTrade;

	@Override
	public ResponseBean singleTrade(ReceiveRequestBean receiveRequestBean) {
		return receiveTrade.trade(receiveRequestBean);
	}

}
