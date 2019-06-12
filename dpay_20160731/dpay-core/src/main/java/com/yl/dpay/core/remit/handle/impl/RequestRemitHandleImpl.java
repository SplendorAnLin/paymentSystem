package com.yl.dpay.core.remit.handle.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.payinterface.core.bean.InterfaceRemitBillHessian;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.dpay.core.entity.Request;
import com.yl.dpay.core.remit.handle.RequestRemitHandle;
import com.yl.dpay.core.service.RouteConfigService;
import com.yl.dpay.hessian.beans.DpayRuntimeException;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;

/**
 * 付款处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月16日
 * @version V1.0.0
 */
@Service("requestRemitHandle")
public class RequestRemitHandleImpl implements RequestRemitHandle{
	
	private static Logger logger = LoggerFactory.getLogger(RequestRemitHandleImpl.class);
	@Resource
	private RemitHessianService remitHessianService;
	@Resource
	private RouteConfigService routeConfigService;

	@Override
	public Map<String, String> remit(Request request) {
		Map<String, String> resMap;
		InterfaceRemitBillHessian interfaceRemitBillHessian = initRemitBean(request);
		try {
			resMap = remitHessianService.remit(interfaceRemitBillHessian);
		} catch (Exception e) {
			logger.error("代付单:{} 调用接口:{} 付款出现异常:{}", request.getFlowNo(),interfaceRemitBillHessian.getInterfaceCode(),e);
			resMap = new HashMap<String, String>();
			if(e instanceof DpayRuntimeException){
				resMap.put("tranStat", "FAILED");
				resMap.put("resCode", ((DpayRuntimeException) e).getCode());
				resMap.put("resMsg", DpayExceptionEnum.getMessage(((DpayRuntimeException) e).getCode()));
			}else{
				resMap.put("tranStat", "FAILED");
				resMap.put("resCode", DpayExceptionEnum.SYSERR.getCode());
				resMap.put("resMsg", DpayExceptionEnum.getMessage(DpayExceptionEnum.SYSERR.getCode()));
			}
		}
		resMap.put("interfaceCode", interfaceRemitBillHessian.getInterfaceCode());
		return resMap;
	}

	@Override
	public Map<String, String> query(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private InterfaceRemitBillHessian initRemitBean(Request request){
		
		InterfaceRemitBillHessian interfaceRemitBillHessian = new InterfaceRemitBillHessian();
		if (StringUtils.isBlank(request.getInterfaceInfoCode())) {
			interfaceRemitBillHessian.setInterfaceCode(getInterfaceCode(request));
		} else {
			interfaceRemitBillHessian.setInterfaceCode(request.getInterfaceInfoCode());
		}
		interfaceRemitBillHessian.setAccountName(request.getAccountName());
		interfaceRemitBillHessian.setAccountNo(request.getAccountNo());
		interfaceRemitBillHessian.setAccountType(request.getAccountType()==null?null:request.getAccountType().toString());
		interfaceRemitBillHessian.setAmount(request.getAmount());
		interfaceRemitBillHessian.setBankCode(request.getBankCode());
		interfaceRemitBillHessian.setBankName(request.getBankName());
		interfaceRemitBillHessian.setBillCode(request.getFlowNo());
		interfaceRemitBillHessian.setCardType(request.getCardType()==null?null:request.getCardType().toString());
		interfaceRemitBillHessian.setCerNo(request.getCerNo());
		interfaceRemitBillHessian.setCerType(request.getCerType()==null?null:request.getCerType().toString());
		interfaceRemitBillHessian.setCvv(request.getCvv());
		interfaceRemitBillHessian.setOwnerId(request.getOwnerId());
		interfaceRemitBillHessian.setRemark(request.getDescription());
		interfaceRemitBillHessian.setValidity(request.getValidity());
		interfaceRemitBillHessian.setOrderPayCode(request.getRequestNo());
		
		return interfaceRemitBillHessian;
	}
	
	private String getInterfaceCode(Request request){
		String interfaceCode = null;
		if(StringUtils.notBlank(request.getBankCode()) && request.getAccountType() != null && request.getCardType() != null
				&& request.getCerType() != null){
			interfaceCode = routeConfigService.compRoute(request.getBankCode(), request.getAccountType(), request.getCardType(), request.getCerType());
			if(StringUtils.notBlank(interfaceCode)){
				return interfaceCode;
			}
		}
		
		if(StringUtils.notBlank(request.getBankCode()) && request.getAccountType() != null && request.getCardType() != null){
			interfaceCode = routeConfigService.compRoute(request.getBankCode(), request.getAccountType(), request.getCardType());
			if(StringUtils.notBlank(interfaceCode)){
				return interfaceCode;
			}
		}
		
		if(StringUtils.notBlank(request.getBankCode()) && request.getAccountType() != null){
			interfaceCode = routeConfigService.compRoute(request.getBankCode(),request.getAccountType());
			if(StringUtils.notBlank(interfaceCode)){
				return interfaceCode;
			}
		}
		
		if(StringUtils.notBlank(request.getBankCode())){
			interfaceCode = routeConfigService.compRoute(request.getBankCode());
			if(StringUtils.notBlank(interfaceCode)){
				return interfaceCode;
			}
		}
		
		if(request.getAccountType() != null){
			interfaceCode = routeConfigService.compRoute(request.getAccountType());
			if(StringUtils.notBlank(interfaceCode)){
				return interfaceCode;
			}
		}
		
		throw new DpayRuntimeException(DpayExceptionEnum.NO_AVAILABLE_CHANNEL.getCode(), DpayExceptionEnum.NO_AVAILABLE_CHANNEL.getMessage());
	}

}
