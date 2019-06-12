package com.yl.realAuth.hessian;

import java.util.Map;

import com.lefu.commons.utils.Page;

public interface BindCardInfoHessianService {
	/**
	 * 动态查询绑定卡信息
	 * @param params
	 * @return
	 */
	public Page findAlldynamicBindCardInfoBean(Map<String, Object> params,Page page);
}
