package com.yl.payinterface.core.remote.hessian.impl;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.bean.AuthBean;
import com.lefu.hessian.exception.BusinessException;
import com.lefu.hessian.exception.BusinessRuntimeException;
import com.yl.payinterface.core.C;
import com.yl.payinterface.core.InterfaceResponseCodeChange;
import com.yl.payinterface.core.bean.AuthRequestBean;
import com.yl.payinterface.core.bean.AuthRequestResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.enums.ReverseSwitch;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.handler.RealNameAuthHandler;
import com.yl.payinterface.core.hessian.RealNameAuthHessianService;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.utils.FeeUtils;
import com.yl.realAuth.hessian.AuthResponseHessianService;
import com.yl.realAuth.hessian.bean.AuthResponseBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service("realNameAuthHessianService")
public class RealNameAuthHessianServiceImpl implements RealNameAuthHessianService {
	
	private Logger logger = LoggerFactory.getLogger(RealNameAuthHessianServiceImpl.class);

	@Resource
	private Validator validator;
	@Resource
	private Map<String, RealNameAuthHandler> realNameAuthHandlers;
	@Resource
	private InterfaceInfoService interfaceInfoService;
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private AuthResponseHessianService authResponseHessianService;
	
	@Override
	public Object trade(AuthRequestBean authRequestBean) {
		InterfaceRequest interfaceRequest = null;
		InterfaceInfo interfaceInfo = null;
		try {
			// 参数校验
			Set<ConstraintViolation<AuthRequestBean>> violations = validator.validate(authRequestBean);
			if (violations.size() > 0) {
				String originalMsg = violations.iterator().next().getPropertyPath().toString();
				String errorMsg = originalMsg.substring(originalMsg.lastIndexOf(".") + 1, originalMsg.length());
				if (violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType().isAssignableFrom(NotNull.class)) throw new BusinessException(
						StringUtils.concatToSB(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS, "-", errorMsg).toString());
				else throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS, "-", errorMsg).toString());
			}
			TradeContext tradeContext = new TradeContext();
			// XXX
			// authRequestBean.setInterfaceCode("REALAUTH_YLZF_110001");
			RealNameAuthHandler tradeHandler = realNameAuthHandlers.get(authRequestBean.getInterfaceCode());
			if (tradeHandler == null) throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
			interfaceInfo = interfaceInfoService.queryByCode(authRequestBean.getInterfaceCode());
			if (interfaceInfo != null) authRequestBean.setInterfaceProviderCode(interfaceInfo.getProvider());


			// 接口费用
			double interfaceFee = FeeUtils.computeFee(1, interfaceInfo.getFeeType(), interfaceInfo.getFee());
			authRequestBean.setInterfaceFee(interfaceFee);
			// 银行编码
			String bankCode = authRequestBean.getInterfaceProviderCode();

			// 记录支付接口请求
			interfaceRequest = interfaceRequestService.save(authRequestBean);
			logger.info("实名认证接口请求信息 ： {}", interfaceRequest.toString());

			// 异步发送消息
			// activeMQProducer.producer("reverseInterfaceRequestQueue", interfaceRequest);

			// 生成接口请求
			tradeContext.setInterfaceRequest(interfaceRequest);

			Map<String, String> map = new HashMap<String, String>();
			// 交易配置
			map.put("transConfig", interfaceInfo.getTradeConfigs());
			// 卡类型
			map.put("cardType", authRequestBean.getCardType());
			// 账户号
			map.put("accountNo", authRequestBean.getAccountNo());
			// 账户名
			map.put("accountName", authRequestBean.getAccountName());
			// 证件号码
			map.put("certificatesCode", authRequestBean.getCertificatesCode());
			// 省份
			map.put("province", authRequestBean.getProvince());
			// 手机号
			map.put("phone", authRequestBean.getPhoneNo());
			// 银行编码
			map.put("bankCode", bankCode);
			// 用途
			map.put("usage", authRequestBean.getRemark());
			tradeContext.setRequestParameters(map);

			// 交易处理生成交易报文
			Map<String, Object> tradeParams = tradeHandler.trade(tradeContext);
			if (tradeParams == null || tradeParams.isEmpty()) {
				throw new BusinessException("接口调用出现异常");
			}
			logger.info("实名认证请求订单号：" + interfaceRequest.getInterfaceRequestID() + "交易返回map：{}", tradeParams);

			tradeParams.put("interfaceInfoCode", interfaceInfo.getCode());
			tradeParams.put("businessCompleteType", BusinessCompleteType.NORMAL);
			tradeParams.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());

			if (tradeParams.get("requestResponseCode") != null) {
				AuthRequestResponseBean authRequestResponseBean = new AuthRequestResponseBean();
				authRequestResponseBean.setInterfaceCode(interfaceInfo.getCode());
				authRequestResponseBean.setAuthRequestResponseMsg(tradeParams);
				// 同步通知商户结果
				AuthResponseBean authResponseBean = new AuthResponseBean();
				authResponseBean = (AuthResponseBean) complete(authRequestResponseBean);
				tradeParams.put("authResponseBean", JsonUtils.toJsonString(authResponseBean));
				tradeParams.put("insideRespCode", authResponseBean.getResponseCode());
				tradeParams.put("insideRespDesc", authResponseBean.getResponseMsg());
			} else {
				Map<String, String> insideRespMap = InterfaceResponseCodeChange.responseCodeChange(interfaceInfo.getCode(), tradeParams.get("requestResponseCode")
						.toString());
				if (insideRespMap != null && insideRespMap.size() != 0) {
					tradeParams.put("insideRespCode", insideRespMap.get("insideRespCode"));
					tradeParams.put("insideRespDesc", insideRespMap.get("insideRespDesc"));
					logger.info("实名认证请求订单号：" + interfaceRequest.getInterfaceRequestID() + "交易返回实名认证系统map：{}", tradeParams);
				}
			}
			return tradeParams;
		} catch (Exception e) {
			if (null != interfaceInfo && null != interfaceInfo.getReverseSwitch() && ReverseSwitch.CLOSED.toString().equals(interfaceInfo.getReverseSwitch().name())) {
				if (interfaceRequest != null) {
					logger.info("接口超时，则把支付接口改成失败，支付接口订单号{}", interfaceRequest.getInterfaceRequestID());
					interfaceRequest.setStatus(InterfaceTradeStatus.FAILED);
					interfaceRequestService.modify(interfaceRequest);
				}
			}
			logger.info("实名认证交易异常：" + e.getMessage());
			logger.error("接口订单号为【" + interfaceRequest.getInterfaceRequestID() + "】的实名认证交易异常：{}", e);
			throw new BusinessRuntimeException(e.getMessage());
		}
	}
	
	@Override
	public Object complete(AuthRequestResponseBean authRequestResponseBean) {
		AuthResponseBean authResponseBean = new AuthResponseBean();
		Map<String, Object> completeParameters = authRequestResponseBean.getAuthRequestResponseMsg();
		try {
			// 实名认证业务处理
			RealNameAuthHandler completeHandler = realNameAuthHandlers.get(authRequestResponseBean.getInterfaceCode());
			if (completeHandler == null) throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);

			// 接口信息
			InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(authRequestResponseBean.getInterfaceCode());
			if (interfaceInfo == null) throw new BusinessRuntimeException(ExceptionMessages.INTERFACE_INFO_NOT_EXISTS);
			
			InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(completeParameters.get("interfaceRequestID").toString(), authRequestResponseBean.getInterfaceCode());
			if(interfaceRequest.getStatus() != InterfaceTradeStatus.UNKNOWN){
				logger.warn("接口订单:{} 已处理", interfaceRequest.getInterfaceRequestID());
				return authResponseBean;
			}
			
			// 资金通道数据验签
			// Map<String, Object> completeParams = completeHandler.signatureVerify(authRequestResponseBean, interfaceInfo.getTradeConfigs());

			try {
				interfaceRequest.setInterfaceOrderID(completeParameters.get("interfaceOrderID").toString());
				interfaceRequest.setStatus(InterfaceTradeStatus.valueOf(completeParameters.get("tranStat").toString()));
				// 响应码
				interfaceRequest.setResponseCode(completeParameters.get("requestResponseCode").toString());
				// 响应描述
				// interfaceRequest.setResponseMessage(completeParameters.get("responseMessage").toString());
				// 业务完成方式
				interfaceRequest.setBusinessCompleteType(BusinessCompleteType.valueOf(completeParameters.get("businessCompleteType").toString()));
				// 交易完成时间
				interfaceRequest.setCompleteTime(new Date());
				// 路由到具体资金通道处理
				// interfaceRequest = completeHandler.complete(completeParams);
				if (!InterfaceTradeStatus.UNKNOWN.equals(interfaceRequest.getStatus())) authResponseBean = callBackOnlineTradeResult(interfaceRequest, interfaceInfo);
			} catch (Exception e) {
				logger.error("{}", e);
				if (!e.getMessage().equals(ExceptionMessages.INTERFACE_REQUEST_NOT_INIT)) throw new BusinessException(e.getMessage());
			}
			return authResponseBean;
		} catch (Exception e) {
			logger.error("channel: {},interfaceRequestId:{},errorCode:{}", authRequestResponseBean.getInterfaceCode(), completeParameters.get("interfaceOrderID").toString(),
					e.getMessage());
			throw new BusinessRuntimeException(e.getMessage());
		}
	}

	/**
	 * 支付结果通知
	 * @param interfaceRequest 提供方接口请求记录
	 * @param interfaceInfo 接口信息
	 */
	public AuthResponseBean callBackOnlineTradeResult(InterfaceRequest interfaceRequest, InterfaceInfo interfaceInfo) throws BusinessException {

		Map<String, String> insideRespMap = InterfaceResponseCodeChange.responseCodeChange(interfaceInfo.getCode(), interfaceRequest.getResponseCode());
		String respCode = "";
		String respDesc = "";
		if (insideRespMap != null && insideRespMap.size() != 0) {
			respCode = insideRespMap.get("insideRespCode");
			respDesc = insideRespMap.get("insideRespDesc");
			logger.info("实名认证请求订单号：" + interfaceRequest.getInterfaceRequestID() + "内部响应码：{}，内部响应描述：{}", insideRespMap.get("insideRespCode"),
					insideRespMap.get("insideRespDesc"));
			interfaceRequest.setResponseCode(respCode);
			interfaceRequest.setResponseMessage(respDesc);
		}
		// 更新接口请求
		interfaceRequestService.modify(interfaceRequest);
		logger.info("LOG interfaceRequestService modify interfaceRequest ：{}", interfaceRequest);
		// 组装通知实名认证系统参数
		AuthResponseBean authResponseBean = new AuthResponseBean();
		authResponseBean.setResponseMsg(respCode);
		authResponseBean.setResponseCode(respDesc);
		authResponseBean.setCost(interfaceRequest.getFee());
		authResponseBean.setInterfaceRequestId(interfaceRequest.getInterfaceRequestID());
		authResponseBean.setOrderCode(interfaceRequest.getBussinessOrderID());
		authResponseBean.setAuthOrderStatus(com.yl.realAuth.hessian.enums.AuthOrderStatus.valueOf(interfaceRequest.getStatus().toString().equals("FAILED") ? "FAILED" : interfaceRequest
				.getStatus().toString()));

		if ("I0001".equals(respCode)) {
			authResponseBean.setAuthResult(com.yl.realAuth.hessian.enums.AuthResult.AUTH_SUCCESS);
		} else if ("I0002".equals(respCode)) {
			authResponseBean.setAuthResult(com.yl.realAuth.hessian.enums.AuthResult.AUTH_FAILED);
		}
		
		try {
			authResponseHessianService.callBack(authResponseBean);
		} catch (Exception e) {
			logger.info("更新实名认证订单失败：{}", e);
		}
		
		// rocketMQProducer.producer(C.AUTH_TRADE_COMPLETE_THEME, JsonUtils.toJsonString(authResponseBean));
		// activeMQProducer.producer("reverseInterfaceRequestCompleteQueue", interfaceRequest);
		return authResponseBean;
	}

}
