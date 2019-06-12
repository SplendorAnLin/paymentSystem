package com.yl.realAuth.core.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.core.dao.RealNameAuthOrderDao;
import com.yl.realAuth.core.service.RealNameAuthOrderService;
import com.yl.realAuth.model.RealNameAuthOrder;
@Service("realNameAuthOrderService")
public class RealNameAuthOrderServiceImpl implements RealNameAuthOrderService {
	@Resource
	private RealNameAuthOrderDao realNameAuthOrderDao;
	@Override
	public Page findAlldynamicRealNameAuthOrder(Map<String, Object> params,Page page) {
		List<RealNameAuthOrder> list = realNameAuthOrderDao.findAlldynamicRealNameAuthOrder(params,page);
		page.setObject(list);
		return page;
	}

}
