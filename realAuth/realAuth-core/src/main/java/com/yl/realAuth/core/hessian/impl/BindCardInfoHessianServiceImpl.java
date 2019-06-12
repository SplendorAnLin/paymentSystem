package com.yl.realAuth.core.hessian.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.core.service.BindCardInfoService;
import com.yl.realAuth.hessian.BindCardInfoHessianService;
@Service("bindCardInfoHessianService")
public class BindCardInfoHessianServiceImpl implements BindCardInfoHessianService {
	private static final Logger logger=LoggerFactory.getLogger(BindCardInfoHessianServiceImpl.class);
	@Resource
	private BindCardInfoService bindCardInfoService;
	@Override
	public Page findAlldynamicBindCardInfoBean(Map<String, Object> params,Page page) {
		return bindCardInfoService.findAlldynamicBindCardInfoBean(params,page);
	}

}
