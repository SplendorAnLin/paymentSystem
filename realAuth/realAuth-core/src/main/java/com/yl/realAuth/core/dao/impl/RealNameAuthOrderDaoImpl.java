package com.yl.realAuth.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.core.dao.RealNameAuthOrderDao;
import com.yl.realAuth.core.dao.mapper.RealNameAuthOrderMapper;
import com.yl.realAuth.model.RealNameAuthOrder;
@Repository("realNameAuthOrderDao")
public class RealNameAuthOrderDaoImpl implements RealNameAuthOrderDao {
	@Resource
	RealNameAuthOrderMapper realNameAuthOrderMapper;
	@Override
	public List<RealNameAuthOrder> findAlldynamicRealNameAuthOrder(Map<String, Object> params,Page page) {
		// TODO Auto-generated method stub
		return realNameAuthOrderMapper.findAlldynamicRealNameAuthOrder(params,page);
	}

}
