package com.yl.realAuth.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.model.RealNameAuthOrder;

public interface RealNameAuthOrderDao {
	/**
	 *动态查询实名认证订单信息
	 */
	public List<RealNameAuthOrder> findAlldynamicRealNameAuthOrder(@Param("params")Map<String, Object> params,@Param("page")Page page);
}
