package com.yl.payinterface.core.remote.hessian.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import org.springframework.stereotype.Service;

import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.hessian.ChannelReverseHessianService;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 通道补单远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
@Service("channelReverseHessianService")
public class ChannelReverseHessianServiceImpl implements
		ChannelReverseHessianService {
	
	@Resource
	private Map<String, ChannelReverseHandler> channelReverseHandlers;
	@Resource
	private InterfaceRequestService interfaceRequestService;

	@Override
	public Map<String, String> reverse(Map<String, String> params) {
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByBusinessOrderID(params.get("businessOrderID"));
		if(interfaceRequest == null){
			params.put("tranStat", "UNKNOWN");
			return params;
		}
		if(interfaceRequest.getStatus() != InterfaceTradeStatus.UNKNOWN){
			params.put("transStatus", String.valueOf(interfaceRequest.getStatus()));
			params.put("tranStat", String.valueOf(interfaceRequest.getStatus()));
			params.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
			params.put("responseCode", interfaceRequest.getResponseCode());
			params.put("responseMsg", interfaceRequest.getResponseMessage());
			params.put("amount", String.valueOf(interfaceRequest.getAmount()));
			params.put("interfaceFee", String.valueOf(interfaceRequest.getFee()));
			params.put("businessFlowID",interfaceRequest.getBussinessFlowID());
			return params;
		}
		ChannelReverseHandler channelReverseHandler = channelReverseHandlers.get(interfaceRequest.getInterfaceInfoCode());
		if (channelReverseHandler == null) {
			params.put("tranStat", "UNKNOWN");
			return params;
		}
		params.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		params.put("businessFlowID",interfaceRequest.getBussinessFlowID());
		return channelReverseHandler.reverse(params);
	}
}