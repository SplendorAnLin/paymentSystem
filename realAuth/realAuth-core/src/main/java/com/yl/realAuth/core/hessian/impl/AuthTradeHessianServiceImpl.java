package com.yl.realAuth.core.hessian.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.core.service.AuthOrderService;
import com.yl.realAuth.core.service.AuthTradeService;
import com.yl.realAuth.hessian.AuthTradeHessianService;
import com.yl.realAuth.hessian.bean.AuthOrderQueryBean;
import com.yl.realAuth.hessian.bean.AuthResponseResult;
import com.yl.realAuth.hessian.bean.CreateOrderBean;
import com.yl.realAuth.hessian.exception.BusinessException;
import com.yl.realAuth.model.RealNameAuthOrder;

/**
 * 实名认证交易服务管理
 * @author congxiang.bai
 * @since 2015年6月3日
 */
@Service("authTradeHessianService")
public class AuthTradeHessianServiceImpl implements AuthTradeHessianService {
	@Resource
	private AuthTradeService authTradeService;
	@Resource
	private AuthOrderService authOrderService;

	@Override
	public Page authOrderQuery(Page page, AuthOrderQueryBean authOrderQueryBean) {
		return authOrderService.authOrderQuery(page, authOrderQueryBean);
	}

	@Override
	public RealNameAuthOrder queryAuthOrderByCode(String orderCode) {
		return authOrderService.queryAuthOrderByCode(orderCode);
	}

	public AuthResponseResult createOrder(CreateOrderBean createOrderBean) {
		AuthResponseResult authResponseResult = null;
		try {
			authResponseResult = authTradeService.createOrder(createOrderBean);
		} catch (BusinessException e) {
			throw new RuntimeException(e);
		}
		return authResponseResult;
	}

	@Override
	public String authOrderTotal(AuthOrderQueryBean authOrderQueryBean) {
		return authOrderService.authOrderTotal(authOrderQueryBean);
	}

	@Override
	public String authOrderExport(AuthOrderQueryBean authOrderQueryBean) {
		return authOrderService.authOrderExport(authOrderQueryBean);
	}

//	@Override
//	public QueryAuthResponseResult queryOrder(String partnerCode, String outOrderId) {
//		return authTradeService.queryOrder(partnerCode, outOrderId);
//	}

}
