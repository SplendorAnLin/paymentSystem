package com.yl.payinterface.core.handle.impl.realAuth.mock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.ExceptionMessages;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.AuthRequestResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.handler.RealNameAuthHandler;
import com.yl.payinterface.core.model.InterfaceRequest;

@Service("REALAUTH_YLZF_110001")
public class AuthYLHandler  implements RealNameAuthHandler {

	private static Logger logger = LoggerFactory.getLogger(AuthYLHandler.class);
	@Resource
	private InterfaceRequestService interfaceRequestService;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> trade(TradeContext tradeContext) throws BusinessException {
		Map<String, Object> responseParams = new LinkedHashMap<String, Object>();
		// 请求参数
		Map<String, String> requestParameters = tradeContext.getRequestParameters();
		// 获取配置信息
		Properties tradeConfigs = JsonUtils.toObject(requestParameters.get("transConfig"), Properties.class);
		try {

			String timeout = tradeConfigs.getProperty("timeout");
			if ("true".equals(timeout)) {
				throw new Exception();
			} else {
				String result = tradeConfigs.getProperty("result");
				if ("AUTH_SUCCESS".equals(result)) {
					responseParams.put("requestResponseCode", "0");
					responseParams.put("status", "0");
					responseParams.put("value", "description result");
					responseParams.put("compStatus", "3");
					responseParams.put("businessCompleteType", BusinessCompleteType.NORMAL);
				} else if ("AUTH_FAIL".equals(result)) {
					responseParams.put("requestResponseCode", "0");
					responseParams.put("status", "0");
					responseParams.put("value", "description result");
					responseParams.put("compStatus", "1");
					responseParams.put("businessCompleteType", BusinessCompleteType.NORMAL);
				} else {
					responseParams.put("requestResponseCode", "0");
					responseParams.put("status", "0");
					responseParams.put("value", "description result");
					responseParams.put("compStatus", "07");
					responseParams.put("businessCompleteType", BusinessCompleteType.NORMAL);
				}

			}

		} catch (Exception e) {
			logger.error("{}", e);
		}
		return responseParams;
	}

	@Override
	public InterfaceRequest complete(Map<String, Object> completeParameters) throws BusinessException {
		// 查询接口请求信息
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(completeParameters.get("interfaceRequestID").toString()
				.replace(" ", ""));
		if (interfaceRequest == null) throw new BusinessException(ExceptionMessages.BUSINESS_HANDLER_ENTITY_NULL);
		// 检验交易完成是否已处理成功 成功跳过,否则继续
		if (!"".equals(interfaceRequest.getStatus()) && InterfaceTradeStatus.SUCCESS.equals(interfaceRequest.getStatus())) throw new BusinessException(
				ExceptionMessages.INTERFACE_REQUEST_NOT_INIT);

		// 获取实名认证响应码(0-处理成功;其他失败)
		String responseCode = (String) completeParameters.get("responseCode");
		logger.info("接口请求号为【" + interfaceRequest.getInterfaceRequestID() + "】的实名认证完成时订单响应码：{}", responseCode);

		// 实名认证交易状态 0 认证成功 其他 认证失败
		if ("0".equals(responseCode)) {
			String completeStatus = (String) completeParameters.get("authResultStatus");
			String completeResult = (String) completeParameters.get("authResultDescription");
			logger.info("接口请求号为【" + interfaceRequest.getInterfaceRequestID() + "】的实名认证完成时订单交易状态：{} 交易结果:{}", completeStatus, completeResult);
			if ("3".equals(completeStatus) || "2".equals(completeStatus) || "1".equals(completeStatus)) {
				interfaceRequest.setStatus(InterfaceTradeStatus.SUCCESS);// 认证成功
			} else if ("07".equals(completeStatus)) {
				interfaceRequest.setStatus(InterfaceTradeStatus.FAILED);// 结果失败
			} else {
				interfaceRequest.setStatus(InterfaceTradeStatus.UNKNOWN);// 结果未知
			}
			//interfaceRequest.setInternetbankHandleStatus(completeStatus);
		} else {
			interfaceRequest.setStatus(InterfaceTradeStatus.FAILED);
		}
		// 响应码
		interfaceRequest.setResponseCode(completeParameters.get("responseCode").toString());
		// 响应描述
		interfaceRequest.setResponseMessage(completeParameters.get("responseMessage").toString());
		// 响应日期
		//interfaceRequest.setInterfaceDate(completeParameters.get("interfaceDate").toString());
		// 响应时间
		//interfaceRequest.setInterfaceTime(completeParameters.get("interfaceTime").toString());
		// 业务完成方式
		interfaceRequest.setBusinessCompleteType(BusinessCompleteType.valueOf(completeParameters.get("businessCompleteType").toString()));
		// 交易完成时间
		interfaceRequest.setCompleteTime(new Date());
		return interfaceRequest;
	}

	@Override
	public Object[] query(TradeContext tradeContext) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> signatureVerify(AuthRequestResponseBean authRequestResponseBean, String tradeConfigs) throws BusinessException {
		// 请求响应Map
		Map<String, Object> responseData = null;
		responseData = authRequestResponseBean.getAuthRequestResponseMsg();

		logger.info("接口请求号为【" + responseData.get("reqSerialNo") + "】的同步响应参数为：{}", responseData);

		// 格式化Map
		responseData = paramFormat(responseData);

		// 组装完成参数
		Map<String, Object> params = new LinkedHashMap<String, Object>();

		// 订单状态码
		params.put("authResultStatus", responseData.get("authResultStatus"));
		// 订单结果描述
		params.put("authResultDescription", responseData.get("authResultDescription"));
		// 响应码
		params.put("responseCode", responseData.get("requestResponesStatus"));
		// 响应描述
		params.put("responseMessage", responseData.get("requestResponesResult"));
		// 日期格式化
		SimpleDateFormat interfaceResponseDate = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat interfaceResponseTime = new SimpleDateFormat("HHmmss");
		Date dataFormat = new Date();
		// 响应日期
		params.put("interfaceDate", interfaceResponseDate.format(dataFormat));
		// 响应时间
		params.put("interfaceTime", interfaceResponseTime.format(dataFormat));
		// 业务完成方式
		params.put("businessCompleteType", responseData.get("businessCompleteType"));
		// 接口请求订单号
		params.put("interfaceRequestID", responseData.get("interfaceRequestID"));

		return params;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> paramFormat(Map<String, Object> responseData) {
		// 一级节点
		Map<String, Object> requestResponseMessage = (Map) responseData.get("message");
		// Map policeCheckInfos = (Map) responseData.get("policeCheckInfos");

		// 二级节点
		String requestResponesStatus = (String) responseData.get("status");
		String requestResponesResult = (String) responseData.get("value");
		// Map policeCheckInfo = (Map) policeCheckInfos.get("policeCheckInfo");
		String authResultStatus = (String) responseData.get("compStatus");
		String authResultDescription = (String) responseData.get("compResult");
		// String identitycard = (String) policeCheckInfo.get("identitycard");
		// String name = (String) policeCheckInfo.get("name");
		// Map queryResultMessage = (Map) policeCheckInfo.get("message");

		// 三级节点
		// String queryResultStatus = (String) queryResultMessage.get("status");
		// String queryResultDescription = (String) queryResultMessage.get("value");

		// 返回Map
		Map<String, Object> responseMap = new HashMap<String, Object>();
		// 请求响应码
		responseMap.put("requestResponesStatus", requestResponesStatus);
		// 请求响应描述
		responseMap.put("requestResponesResult", requestResponesResult);
		// 订单结果状态码
		responseMap.put("authResultStatus", authResultStatus);
		// 订单结果描述
		responseMap.put("authResultDescription", authResultDescription);
		// // 身份证号
		// responseMap.put("identitycard", identitycard);
		// // 姓名
		// responseMap.put("name", name);
		// // 查询数据状态码
		// responseMap.put("queryResultStatus", queryResultStatus);
		// // 查询数据描述
		// responseMap.put("queryResultDescription", queryResultDescription);
		// 业务完成方式
		responseMap.put("businessCompleteType", responseData.get("businessCompleteType"));
		// 接口请求订单号
		responseMap.put("interfaceRequestID", responseData.get("interfaceRequestID"));

		return responseMap;
	}

}
