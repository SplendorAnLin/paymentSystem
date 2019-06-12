package com.yl.realAuth.core.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.core.dao.AuthOrderDao;
import com.yl.realAuth.core.dao.mapper.AuthOrderMapper;
import com.yl.realAuth.core.utils.CodeBuilder;
import com.yl.realAuth.model.RealNameAuthOrder;

@Repository("authOrderDao")
public class AuthOrderDaoImpl implements AuthOrderDao {

	@Resource
	private AuthOrderMapper authOrderMapper;
	
	@Override
	public List<RealNameAuthOrder> authOrderQuery(Page<?> page, Map<String, Object> params) {
		return authOrderMapper.authOrderQuery(page, params);
	}

	@Override
	public List<RealNameAuthOrder> authOrderExport(Map<String, Object> params) {
		return authOrderMapper.authOrderExport(params);
	}

	@Override
	public void insertAuthOrder(RealNameAuthOrder order) {
		order.setCode(CodeBuilder.build("AUTH", "yyyyMMdd", 6));
		order.setCreateTime(new Date());
		order.setVersion(System.currentTimeMillis());
		authOrderMapper.insertAuthOrder(order);
	}

	@Override
	public RealNameAuthOrder queryOrderByRequestCode(String customerNo, String requestCode) {
		return authOrderMapper.queryOrderByRequestCode(customerNo, requestCode);
	}

	@Override
	public List<RealNameAuthOrder> queryWaitCloseOrder(Page<?> page, Date dBefore, Date dqBefore) {
		return authOrderMapper.queryWaitCloseOrder(page, dBefore, dqBefore);
	}

	@Override
	public void closeTimeoutOrder(RealNameAuthOrder RealNameAuthOrder) {
		authOrderMapper.closeTimeoutOrder(RealNameAuthOrder);

	}

	@Override
	public RealNameAuthOrder queryAuthOrderByCode(String orderCode) {
		return authOrderMapper.queryAuthOrderByCode(orderCode);
	}

	@Override
	public void updateOrderStatus(RealNameAuthOrder RealNameAuthOrder) {
		RealNameAuthOrder.setVersion(System.currentTimeMillis());
		authOrderMapper.updateOrderStatus(RealNameAuthOrder);
	}

	@Override
	public List<RealNameAuthOrder> queryWaitProcessOrder(Page<?> page, Date dBefore, Date dqBefore) {
		return authOrderMapper.queryWaitProcessOrder(page, dBefore, dqBefore);
	}

	@Override
	public List<RealNameAuthOrder> findSuccessOrderBy(Date startOrderTime, Date endOrderTime) {
		return authOrderMapper.findSuccessOrderBy(startOrderTime, endOrderTime);
	}

	@Override
	public RealNameAuthOrder findRequestBy(String partnerCode, String outOrderId) {
		return authOrderMapper.findRequestBy(partnerCode, outOrderId);
	}

	@Override
	public Map<String, Object> authOrderTotal(Map<String, Object> params) {
		return authOrderMapper.authOrderTotal(params);
	}

}
