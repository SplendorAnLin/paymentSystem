package com.yl.realAuth.core.service;

import java.util.List;
import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.model.RealNameAuthOrder;

public interface RealNameAuthOrderService {
	/**
	 * 动态查询绑定卡信息
	 * @param params
	 * @return
	 */
	public Page findAlldynamicRealNameAuthOrder(Map<String, Object> params,Page page);
}
