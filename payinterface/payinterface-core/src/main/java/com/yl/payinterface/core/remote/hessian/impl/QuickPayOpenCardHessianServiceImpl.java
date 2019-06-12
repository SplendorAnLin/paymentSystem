package com.yl.payinterface.core.remote.hessian.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.hessian.exception.BusinessRuntimeException;
import com.yl.payinterface.core.bean.QuickPayOpenCardRequestBean;
import com.yl.payinterface.core.bean.QuickPayOpenCardResponseBean;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.handler.QuickPayOpenCardHandler;
import com.yl.payinterface.core.hessian.QuickPayOpenCardHessianService;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.service.InterfaceInfoService;

/** 
 * @ClassName QuickPayOpenCardHessianServiceImpl 
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @author Administrator 
 * @date 2017年11月20日 上午11:23:51  
 */
@Service("quickPayOpenCardHessianService")
public class QuickPayOpenCardHessianServiceImpl implements QuickPayOpenCardHessianService {
	private static Logger logger = LoggerFactory.getLogger(QuickPayOpenCardHessianServiceImpl.class);
	
	@Resource
	private Map<String, QuickPayOpenCardHandler> quickPayOpenCardHandlers;
	@Resource
	private InterfaceInfoService interfaceInfoService;

	@Override
	public QuickPayOpenCardResponseBean sendOpenCardVerifyCode(QuickPayOpenCardRequestBean requestBean) {
		
		QuickPayOpenCardHandler quickPayOpenCardHandler = quickPayOpenCardHandlers.get(requestBean.getInterfaceInfoCode());
		if (quickPayOpenCardHandler == null) {
			throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		}
		
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(requestBean.getInterfaceInfoCode());
		
		Map<String, String> map = new HashMap<>();
		map.put("realName", requestBean.getName());
		map.put("cardNo", requestBean.getCardNo());
		map.put("phone", requestBean.getPhone());
		map.put("customerNo", requestBean.getCustomerNo());
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		map.put("cvn", requestBean.getCvn());
		map.put("expireDate", requestBean.getExpireDate());
		return quickPayOpenCardHandler.sendOpenCardVerifyCode(map);
	}

	@Override
	public QuickPayOpenCardResponseBean openCardInfo(QuickPayOpenCardRequestBean requestBean) {
		QuickPayOpenCardHandler quickPayOpenCardHandler = quickPayOpenCardHandlers.get(requestBean.getInterfaceInfoCode());
		if (quickPayOpenCardHandler == null) {
			throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		}
		
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(requestBean.getInterfaceInfoCode());
		
		Map<String, String> map = new HashMap<>();
		map.put("realName", requestBean.getName());
		map.put("cardNo", requestBean.getCardNo());
		map.put("phone", requestBean.getPhone());
		map.put("expireDate", requestBean.getExpireDate());
		map.put("cvn", requestBean.getCvn());
		map.put("smsCode", requestBean.getSmsCode());
		map.put("customerNo", requestBean.getCustomerNo());
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		
		return quickPayOpenCardHandler.openCardInfo(map);
	}

}
