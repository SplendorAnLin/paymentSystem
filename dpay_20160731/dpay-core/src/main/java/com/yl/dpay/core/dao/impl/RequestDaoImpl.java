package com.yl.dpay.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.lefu.commons.utils.Page;
import org.springframework.stereotype.Repository;

import com.yl.dpay.core.dao.RequestDao;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.enums.RequestStatus;
import com.yl.dpay.core.mybatis.mapper.RequestMapper;

/**
 * 代付申请数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
@Repository("requestDao")
public class RequestDaoImpl implements RequestDao {

	@Resource
	private RequestMapper requestMapper;
	
	@Override
	public List<Map<String, Object>> findAllrequest(Map<String, Object> params, Page page) {
		return requestMapper.findAllrequest(params,page);
	}

    
	
	@Override
	public void insert(Request t) {
		requestMapper.insert(t);
	}

	@Override
	public Request findById(Long id) {
		return requestMapper.findById(id);
	}

	@Override
	public void update(Request request) {
		requestMapper.update(request);
	}

	@Override
	public Request findByFlowNo(String flowNo) {
		return requestMapper.findByFlowNo(flowNo);
	}

	@Override
	public Request find(String requestNo, String ownerId) {
		return requestMapper.find(requestNo, ownerId);
	}

	@Override
	public Request findByRequestNo(String requestNo,String ownerId) {
		return requestMapper.findByRequestNo(requestNo,ownerId);
	}

	@Override
	public List<Request> findByStatus(RequestStatus status) {
		return requestMapper.findByStatus(status);
	}

	@Override
	public List<Request> findWaitNotifyRequest(int count, int nums) {
		return requestMapper.findWaitNotifyRequest(count, nums);
	}

	@Override
	public Request findByAccNameAmtDay(String accountName, String accountNo, double amount,
			String startDate, String endDate) {
		return requestMapper.findByAccNameAmtDay(accountName, accountNo, amount, startDate, endDate);
	}

	@Override
	public List<Map<String, String>> orderSumByDay(String orderTimeStart, String orderTimeEnd,String owner) {
		return requestMapper.orderSumByDay(orderTimeStart, orderTimeEnd,owner);
	}
	
	@Override
	public List<Map<String, String>> orderAllSumByDay(String orderTimeStart, String orderTimeEnd,String owner){
		return requestMapper.orderAllSumByDay(orderTimeStart, orderTimeEnd,owner);
	}
	
	@Override
	public List<Map<String, String>> dfSuccess(String orderTimeStart, String orderTimeEnd, List owner) {
		return requestMapper.dfSuccess(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public List<Map<String, String>> dfSuccessCount(String orderTimeStart, String orderTimeEnd, String owner) {
		return requestMapper.dfSuccessCount(orderTimeStart, orderTimeEnd, owner);
	}

	@Override
	public List<Map<String, Object>> selectRequest(Map<String, Object> params) {
		return requestMapper.selectRequest(params);
	}

	@Override
	public List<Map<String, Object>> selectRequestDetailed(Map<String, Object> params) {
		return requestMapper.selectRequestDetailed(params);
	}

	@Override
	public Map<String, Object> selectRequestsum(Map<String, Object> params) {
		return requestMapper.selectRequestsum(params);
	}

	@Override
	public Map<String, Object> findrequestDetail(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return requestMapper.findrequestDetail(params);
	}

	@Override
	public List<Request> findByParams(Map<String, Object> params) {
		return requestMapper.findByParams(params);
	}

	@Override
	public List<Request> findByCreateTime(Map<String, Object> params) {
		return requestMapper.findByCreateTime(params);
	}

	@Override
	public Map<String, Object> findSumCountAndFee(Map<String, Object> params) {
		return requestMapper.findSumCountAndFee(params);
	}


	@Override
	public Map<String, Object> customerReconHead(Map<String, Object> params) {
		return requestMapper.customerReconHead(params);
	}
}