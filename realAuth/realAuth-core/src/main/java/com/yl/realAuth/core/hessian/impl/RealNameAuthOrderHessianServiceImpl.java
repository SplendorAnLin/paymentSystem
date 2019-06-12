package com.yl.realAuth.core.hessian.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.core.service.RealNameAuthOrderService;
import com.yl.realAuth.hessian.RealNameAuthOrderHessianService;
@Service("realNameAuthOrderHessianService")
public class RealNameAuthOrderHessianServiceImpl implements RealNameAuthOrderHessianService {
	private static final Logger logger=LoggerFactory.getLogger(RealNameAuthOrderHessianServiceImpl.class);
	@Resource
	private RealNameAuthOrderService realNameAuthOrderService;
	@Override
	public Page findAlldynamicRealNameAuthOrder(Map<String, Object> params,Page page) {
		return realNameAuthOrderService.findAlldynamicRealNameAuthOrder(params,page);
	}

}
