package com.yl.payinterface.core.remote.hessian.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 提供方接口请求记录远程服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
@Service("interfaceRequestHessianService")
public class InterfaceRequestHessianServiceImpl implements InterfaceRequestHessianService {

	@Resource
	private InterfaceRequestService interfaceRequestService;
	
	@Override
	public Page queryAll(Page page ,Map<String, Object> map) {
		page  = interfaceRequestService.findAllInterfaceRequest(page, map);
		page.setObject(JsonUtils.toJsonString(page.getObject()));
		return page;
	}

	@Override
	public String queryAllSum(Map<String, Object> map) {
		Map<String, Object> mapResult = interfaceRequestService.queryAllSum(map);
		return JsonUtils.toJsonString(mapResult);
	}

	@Override
	public InterfaceRequestQueryBean findRequestByInterfaceReqId(String interfaceReqId) {
		InterfaceRequestQueryBean bean = new InterfaceRequestQueryBean();
		InterfaceRequest request = interfaceRequestService.queryByInterfaceRequestID(interfaceReqId);
		bean.setAmount(request.getAmount());
		bean.setBussinessFlowID(request.getBussinessFlowID());
		bean.setBussinessOrderID(request.getBussinessOrderID());
		bean.setCardType(request.getCardType());
		bean.setClientIP(request.getClientIP());
		bean.setClientRefer(request.getClientRefer());
		bean.setCompleteTime(request.getCompleteTime());
		bean.setResponseCode(request.getResponseCode());
		bean.setResponseMessage(request.getResponseMessage());
		bean.setInterfaceInfoCode(request.getInterfaceInfoCode());
		return bean;
	}

	@Override
	public String findProviderNameByProvideCode(String providerCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findAllInterfaceInfoCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findInterfaceNameByInterfaceCode(String interfaceInfoCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InterfaceRequestQueryBean findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> queryInterfaceRequestQuery(Map<String, Object> map) {
		return interfaceRequestService.queryInterfaceRequestQuery(map);
	}

    @Override
    public InterfaceRequestQueryBean findByInterfaceOrderId(String interfaceOrderId) {
        InterfaceRequestQueryBean bean = new InterfaceRequestQueryBean();
        InterfaceRequest request = interfaceRequestService.findByInterfaceOrderId(interfaceOrderId);
        bean.setAmount(request.getAmount());
        bean.setBussinessFlowID(request.getBussinessFlowID());
        bean.setBussinessOrderID(request.getBussinessOrderID());
        bean.setCardType(request.getCardType());
        bean.setClientIP(request.getClientIP());
        bean.setClientRefer(request.getClientRefer());
        bean.setCompleteTime(request.getCompleteTime());
        bean.setResponseCode(request.getResponseCode());
        bean.setResponseMessage(request.getResponseMessage());
        bean.setInterfaceInfoCode(request.getInterfaceInfoCode());
        bean.setInterfaceRequestID(request.getInterfaceRequestID());
        return bean;
    }
}