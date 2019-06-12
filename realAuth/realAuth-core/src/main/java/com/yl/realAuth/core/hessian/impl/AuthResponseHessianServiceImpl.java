package com.yl.realAuth.core.hessian.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.StringUtils;
import com.yl.realAuth.core.ExceptionMessages;
import com.yl.realAuth.core.InnerResponseCode;
import com.yl.realAuth.core.InnerResponseConvertHandle;
import com.yl.realAuth.core.service.AccountPlusOperateService;
import com.yl.realAuth.core.service.AuthInfoManageService;
import com.yl.realAuth.core.service.AuthOrderService;
import com.yl.realAuth.enums.AuthOrderStatus;
import com.yl.realAuth.enums.AuthResult;
import com.yl.realAuth.hessian.AuthResponseHessianService;
import com.yl.realAuth.hessian.bean.AuthResponseBean;
import com.yl.realAuth.hessian.exception.BusinessException;
import com.yl.realAuth.model.RealNameAuthOrder;

/**
 * 实名认证支付接口回调结果处理
 * @author congxiang.bai
 * @since 2015年6月3日
 */
@Service("authResponseHessianService")
public class AuthResponseHessianServiceImpl implements AuthResponseHessianService {

	private static Logger logger = LoggerFactory.getLogger(AuthResponseHessianServiceImpl.class);
	@Resource
	private AuthOrderService authOrderService;
	// @Resource
	// private AuthTradeProducer authTradeProducer;
	@Resource
	private AuthInfoManageService authInfoManageService;
	//@Resource
	//private AccountPlusOperateService accountPlusOperateService;

	private static Properties prop = new Properties();
	static {
		try {
			prop.load(AuthResponseHessianServiceImpl.class.getClassLoader().getResourceAsStream("serverHost.properties"));
		} catch (IOException e) {
			logger.error("{}", e);
		}
	}

	@Override
	public void callBack(AuthResponseBean authResponseBean) throws BusinessException {
		logger.info("实名认证接口返回信息为：{}", authResponseBean);
		// 交易状态
		String interfaceStatus = authResponseBean.getAuthOrderStatus().name();
		// 业务订单号
		String businessOrderID = authResponseBean.getOrderCode();

		// 参数校验
		if (StringUtils.isBlank(interfaceStatus)) throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", "transStatus")
				.toString());
		if (StringUtils.isBlank(businessOrderID)) throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", "businessOrderID")
				.toString());

		// 查询支付订单记录
		RealNameAuthOrder order = authOrderService.queryAuthOrderByCode(businessOrderID);

		logger.info("订单号为【" + businessOrderID + "】的订单详细信息：{}", order);
		// 认证信息存储（只有认证成功才存储）
		if (authResponseBean.getAuthResult() != null && AuthResult.AUTH_SUCCESS.name().equals(authResponseBean.getAuthResult().name())) {
			authInfoManageService.saveAuthInfo(order);
		}
		if (!(order.getAuthOrderStatus().equals(AuthOrderStatus.WAIT) || order.getAuthOrderStatus().equals(AuthOrderStatus.PROCESSING))) {
			throw new BusinessException(ExceptionMessages.TRADE_ORDER_AREADY_SUCCESS);
		}

		// 更新支付订单信息
		order.setCompleteTime(new Date());
		order.setCost(authResponseBean.getCost());
		order.setAuthOrderStatus(AuthOrderStatus.valueOf(interfaceStatus));
		order.setResponseMsg(authResponseBean.getResponseMsg());
		if (authResponseBean.getAuthResult() != null) {
			order.setAuthResult(AuthResult.valueOf(authResponseBean.getAuthResult().name()));
		}
		order.setInterfaceRequestId(authResponseBean.getInterfaceRequestId());

		logger.info("实名认证返回订单详细信息：{}", order.toString());

		// 接口失败,返回内部错误码与内部错误描述
		if ((null != authResponseBean.getAuthOrderStatus() && AuthOrderStatus.FAILED.name().equals(authResponseBean.getAuthOrderStatus().toString()))
				|| (null != authResponseBean.getAuthResult() && AuthResult.AUTH_FAILED.name().equals(authResponseBean.getAuthResult().toString()))) {
			if (null != authResponseBean.getResponseCode()) {
				String errorCode = InnerResponseConvertHandle.getInnerResponseCode(authResponseBean.getResponseCode());
				InnerResponseCode innerResponseCode = InnerResponseCode.getHandlerResultCode(errorCode);
				order.setInnerErrorCode(innerResponseCode.getErrorCode());
				order.setInnerErrorMsg(innerResponseCode.getErrorMsg());

				logger.info("接口失败,返回订单详细信息：{}", order.toString());
			}
		}
		order.setClearTime(new Date());
		// 失败手续费退回处理
		if (AuthOrderStatus.FAILED.name().equals(interfaceStatus) && "SUBTRACT_FEE_SUCCESS".equals(order.getBusinessFlag1())) {
			/** 调用账务退回手续费流程 */
			
		}
		// 发送完成消息到消息中间件
		// authTradeProducer.producer(businessOrderID, Constant.NOTIFY_TOPIC);
		authOrderService.modifyOrderStatus(order);
	}
}
