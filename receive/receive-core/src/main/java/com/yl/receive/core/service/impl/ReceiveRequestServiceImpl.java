package com.yl.receive.core.service.impl;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.receive.core.dao.ReceiveRequestDao;
import com.yl.receive.core.entity.ReceiveRequest;
import com.yl.receive.core.service.ReceiveRequestService;
import com.yl.receive.hessian.bean.ReceiveRequestBean;
import com.yl.receive.hessian.enums.AccNoType;
import com.yl.receive.hessian.enums.AccType;
import com.yl.receive.hessian.enums.OwnerRole;
import com.yl.receive.hessian.enums.ReceiveStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代收信息服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
@Service("receiveRequestService")
public class ReceiveRequestServiceImpl implements ReceiveRequestService {

	@Resource
	private ReceiveRequestDao receiveRequestDao;

	@Override
	public List<Map<String, String>> orderSumByDay(Date orderTimeStart,
			Date orderTimeEnd, String owner) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return receiveRequestDao.orderSumByDay(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
	}
	
	@Override
	public ReceiveRequestBean findBy(String receiveId) {
		ReceiveRequest receiveRequest = receiveRequestDao.queryByReceiveId(receiveId);
		if (receiveRequest != null) {
			ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();
			receiveRequestBean.setAccNoType(AccNoType.valueOf(receiveRequest.getAccNoType().toString()));
			receiveRequestBean.setAccountName(receiveRequest.getAccountName());
			receiveRequestBean.setAccountNo(receiveRequest.getAccountNo());
			receiveRequestBean.setAccType(AccType.valueOf(receiveRequest.getAccType().toString()));
			receiveRequestBean.setAmount(receiveRequest.getAmount());
			receiveRequestBean.setCertificatesCode(receiveRequest.getCertificatesCode());
			receiveRequestBean.setCertificatesType(receiveRequest.getCertificatesType().toString());
			receiveRequestBean.setCity(receiveRequest.getCity());
			receiveRequestBean.setContractId(receiveRequest.getContractId());
			receiveRequestBean.setCost(receiveRequest.getCost());
			receiveRequestBean.setCreateTime(receiveRequest.getCreateTime());
			receiveRequestBean.setCustomerNo(receiveRequest.getOwnerId());
			receiveRequestBean.setCustomerOrderCode(receiveRequest.getSeqId());
			receiveRequestBean.setFee(receiveRequest.getFee());
			receiveRequestBean.setNotifyUrl(receiveRequest.getNotifyUrl());
			receiveRequestBean.setOpenBank(receiveRequest.getOpenBank());
			receiveRequestBean.setOwnerRole(OwnerRole.valueOf(receiveRequest.getOwnerRole().toString()));
			receiveRequestBean.setPayerBankNo(receiveRequest.getPayerBankNo());
			receiveRequestBean.setPhone(receiveRequest.getPhone());
			receiveRequestBean.setProvince(receiveRequest.getProvince());
			receiveRequestBean.setReceiveId(receiveRequest.getReceiveId());
			receiveRequestBean.setReceiveStatus(ReceiveStatus.valueOf(receiveRequest.getOrderStatus().toString()));
			receiveRequestBean.setRemark(receiveRequest.getRemark());
			return receiveRequestBean;
		}
		return null;
	}

	@Override
	public ReceiveRequestBean findBy(String ownerId, String customerOrderCode) {
		ReceiveRequest receiveRequest = receiveRequestDao.queryBySeqId(customerOrderCode, ownerId);
		ReceiveRequestBean receiveRequestBean = new ReceiveRequestBean();
		receiveRequestBean.setAccNoType(AccNoType.valueOf(receiveRequest.getAccNoType().toString()));
		receiveRequestBean.setAccountName(receiveRequest.getAccountName());
		receiveRequestBean.setAccountNo(receiveRequest.getAccountNo());
		receiveRequestBean.setAccType(AccType.valueOf(receiveRequest.getAccType().toString()));
		receiveRequestBean.setAmount(receiveRequest.getAmount());
		receiveRequestBean.setCertificatesCode(receiveRequest.getCertificatesCode());
		receiveRequestBean.setCertificatesType(receiveRequest.getCertificatesType().toString());
		receiveRequestBean.setCity(receiveRequest.getCity());
		receiveRequestBean.setContractId(receiveRequest.getContractId());
		receiveRequestBean.setCost(receiveRequest.getCost());
		receiveRequestBean.setCreateTime(receiveRequest.getCreateTime());
		receiveRequestBean.setCustomerNo(receiveRequest.getOwnerId());
		receiveRequestBean.setCustomerOrderCode(customerOrderCode);
		receiveRequestBean.setFee(receiveRequest.getFee());
		receiveRequestBean.setNotifyUrl(receiveRequest.getNotifyUrl());
		receiveRequestBean.setOpenBank(receiveRequest.getOpenBank());
		receiveRequestBean.setOwnerRole(OwnerRole.valueOf(receiveRequest.getOwnerRole().toString()));
		receiveRequestBean.setPayerBankNo(receiveRequest.getPayerBankNo());
		receiveRequestBean.setPhone(receiveRequest.getPhone());
		receiveRequestBean.setProvince(receiveRequest.getProvince());
		receiveRequestBean.setReceiveId(receiveRequest.getReceiveId());
		receiveRequestBean.setReceiveStatus(ReceiveStatus.valueOf(receiveRequest.getOrderStatus().toString()));
		receiveRequestBean.setRemark(receiveRequest.getRemark());
		return receiveRequestBean;
	}

	@Override
	public List<Map<String, String>> orderSum(Date orderTimeStart, Date orderTimeEnd, String owner) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return receiveRequestDao.orderSum(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
	}

	@Override
	public List<Map<String, String>> orderSumCount(Date orderTimeStart, Date orderTimeEnd, String owner) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return receiveRequestDao.orderSumCount(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), owner);
	}

	@Override
	public List<ReceiveRequest> findByReconJob(Map<String, Object> params) {
		return receiveRequestDao.findByReconJob(params);
	}

	@Override
	public Map<String, Object> findTotalByJob(Map<String, Object> params) {
		return receiveRequestDao.findTotalByJob(params);
	}


	@Override
	public List<ReceiveRequestBean> customerRecon(Map<String, Object> params) {
		List<ReceiveRequestBean> receiveRequestBeans = new ArrayList<>();
		List<ReceiveRequest> receiveRequests = receiveRequestDao.customerRecon(params);
		ReceiveRequestBean receiveRequestBean = null;
		ReceiveRequest receiveRequest = null;
		for (int i = 0; i < receiveRequests.size(); i++) {
			receiveRequest = new ReceiveRequest();
			receiveRequestBean = new ReceiveRequestBean();
			receiveRequest = receiveRequests.get(i);
			receiveRequestBean.setAmount(receiveRequest.getAmount());
			receiveRequestBean.setFee(receiveRequest.getFee());
			receiveRequestBean.setLastUpdateTime(receiveRequest.getLastUpdateTime());
			receiveRequestBean.setCreateTime(receiveRequest.getCreateTime());
			receiveRequestBean.setReceiveId(receiveRequest.getReceiveId());
			receiveRequestBean.setCustomerOrderCode(receiveRequest.getSeqId());
			receiveRequestBeans.add(receiveRequestBean);
		}
		return receiveRequestBeans;
	}

	@Override
	public String customerReconHead(Map<String, Object> params) {
		Map<String, Object> map = receiveRequestDao.customerReconHead(params);
		return JsonUtils.toJsonString(map);
	}

	@Override
	public String customerReconSum(Map<String, Object> params) {
		Map<String, Object> map = receiveRequestDao.customerReconSum(params);
		return JsonUtils.toJsonString(map);
	}
}