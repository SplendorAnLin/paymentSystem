package com.yl.payinterface.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.AuthRequestBean;
import com.yl.payinterface.core.bean.InternetbankSalesTradeBean;
import com.yl.payinterface.core.bean.ReceiveTradeBean;
import com.yl.payinterface.core.dao.InterfaceRequestDao;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceRequestType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 接口请求服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
@Service("interfaceRequestService")
public class InterfaceRequestServiceImpl implements InterfaceRequestService {

	@Resource
	private InterfaceRequestDao interfaceRequestDao;

	@Override
	public void modify(InterfaceRequest interfaceRequest) {
		interfaceRequestDao.update(interfaceRequest, System.currentTimeMillis());
	}

	@Override
	public InterfaceRequest queryByInterfaceRequestID(String interfaceRequestID) {
		return interfaceRequestDao.findByInterfaceRequestID(interfaceRequestID);
	}

	@Override
	public List<InterfaceRequest> queryNeedReverseOrderInterfaceRequest(int interfaceRequestCount, Page page) {
		return interfaceRequestDao.queryNeedReverseOrderInterfaceRequest(interfaceRequestCount, page);
	}

	@Override
	public InterfaceRequest save(InternetbankSalesTradeBean b2cTradeBean) throws BusinessException {
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setInterfaceProviderCode(b2cTradeBean.getInterfaceProviderCode());
		interfaceRequest.setInterfaceInfoCode(b2cTradeBean.getInterfaceCode());
		interfaceRequest.setBussinessOrderID(b2cTradeBean.getBusinessOrderID());
		interfaceRequest.setAmount(b2cTradeBean.getAmount());
		interfaceRequest.setClientIP(b2cTradeBean.getClientIp());
		interfaceRequest.setRequestTime(new Date());
		interfaceRequest.setClientRefer(b2cTradeBean.getReferer());
		interfaceRequest.setCardType(b2cTradeBean.getCardType());
		interfaceRequest.setOriginalInterfaceRequestID(b2cTradeBean.getOriginalBusinessOrderID());
		interfaceRequest.setPayInterfaceRequestType(InterfaceRequestType.valueOf(b2cTradeBean.getTradeType()));
		interfaceRequest.setBussinessFlowID(b2cTradeBean.getBussinessFlowID());
		interfaceRequest.setOwnerID(b2cTradeBean.getOwnerID());
		interfaceRequestDao.create(interfaceRequest);
		return interfaceRequest;
	}

	@Override
	public void modifyQueryInterfaceRequest(InterfaceRequest queryInterfaceRequest, Map<String, Object> resultMap) {
		Object respCode = resultMap.get("responseCode");
		Object respMsg = resultMap.get("responseMessage");
		Object queryStatus = resultMap.get("queryStatus");

		if (respCode == null)
			return;
		queryInterfaceRequest.setResponseCode(respCode.toString());
		queryInterfaceRequest.setResponseMessage(respMsg == null ? "" : respMsg.toString());
		if (queryStatus != null)
			queryInterfaceRequest.setStatus(InterfaceTradeStatus.valueOf(queryStatus.toString()));
		queryInterfaceRequest.setBusinessCompleteType(BusinessCompleteType.valueOf(String.valueOf(resultMap.get("businessCompleteType"))));
		interfaceRequestDao.updateQueryInterfaceRequest(queryInterfaceRequest);

	}

	@Override
	public InterfaceRequest save(ReceiveTradeBean receiveTradeBean) throws BusinessException {
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setInterfaceProviderCode(receiveTradeBean.getInterfaceProviderCode());
		interfaceRequest.setInterfaceInfoCode(receiveTradeBean.getInterfaceCode());
		interfaceRequest.setBussinessOrderID(receiveTradeBean.getBusinessOrderID());
		interfaceRequest.setAmount(receiveTradeBean.getAmount());
		interfaceRequest.setRequestTime(new Date());
		interfaceRequest.setPayInterfaceRequestType(InterfaceRequestType.PAY);
		interfaceRequestDao.create(interfaceRequest);
		return interfaceRequest;
	}

	@Override
	public void save(InterfaceRequest interfaceRequest) {
		interfaceRequestDao.create(interfaceRequest);
	}

	@Override
	public InterfaceRequest queryByBusinessOrderID(String businessOrderID) {
		return interfaceRequestDao.queryByBusinessOrderID(businessOrderID);
	}

	@Override
	public Page findAllInterfaceRequest(Page page, Map<String, Object> map) {
		List<InterfaceRequest> list = interfaceRequestDao.findAllInterfaceRequest(page, map);
		page.setObject(list);
		return page;
	}
	
	@Override
	public Map<String, Object> queryAllSum(Map<String, Object> map) {
		return interfaceRequestDao.queryAllSum(map);
	}
	
	@Override
	public InterfaceRequest save(AuthRequestBean authRequestBean) throws BusinessException {
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setInterfaceProviderCode(authRequestBean.getInterfaceProviderCode());
		interfaceRequest.setInterfaceInfoCode(authRequestBean.getInterfaceCode());
		interfaceRequest.setBussinessOrderID(authRequestBean.getBusinessOrderID());
		interfaceRequest.setRequestTime(new Date());
		interfaceRequest.setPayInterfaceRequestType(InterfaceRequestType.PAY);
		interfaceRequest.setCardType(authRequestBean.getCardType());
		interfaceRequest.setOwnerID(authRequestBean.getOwnerID());
		interfaceRequest.setFee(authRequestBean.getInterfaceFee());

		interfaceRequestDao.create(interfaceRequest);
		return interfaceRequest;
	}

	@Override
	public List<InterfaceRequest> allByDate(Map<String, Object> param) {
		return interfaceRequestDao.allByDate(param);
	}

	@Override
	public Map<String, Object> totalByJob(Map<String, Object> map) {
		return interfaceRequestDao.totalByJob(map);
	}

	@Override
	public List<Map<String, Object>> queryInterfaceRequestQuery(Map<String, Object> map) {
		return interfaceRequestDao.queryInterfaceRequestQuery(map);
	}

	@Override
	public InterfaceRequest queryOneInterfaceRequestByInterfaceCode(String code, InterfaceTradeStatus status) {
		return interfaceRequestDao.queryOneInterfaceRequestByInterfaceCode(code, status);
	}

	@Override
	public InterfaceRequest queryByInterfaceRequestID(String interfaceRequestID, String interfaceCode) {
		return interfaceRequestDao.queryByInterfaceCodeAndRequestId(interfaceRequestID, interfaceCode);
	}

    @Override
    public InterfaceRequest findByInterfaceOrderId(String interfaceOrderId) {
        return interfaceRequestDao.findByInterfaceOrderId(interfaceOrderId);
    }
}