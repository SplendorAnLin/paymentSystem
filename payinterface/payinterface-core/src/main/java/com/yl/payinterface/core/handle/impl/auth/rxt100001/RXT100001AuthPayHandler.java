package com.yl.payinterface.core.handle.impl.auth.rxt100001;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.payinterface.core.handler.AuthPayHandler;

/**
 * 融信通快捷支付接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月16日
 * @version V1.0.0
 */
public class RXT100001AuthPayHandler implements AuthPayHandler {
	
	private static Logger logger = LoggerFactory.getLogger(RXT100001AuthPayHandler.class);

	@Override
	public Map<String, String> sendVerifyCode(Map<String, String> params) {
		return null;
	}

	@Override
	public Map<String, String> authPay(Map<String, String> map) {
		Map<String, String> resParams = null;
		try {
			Properties prop = JsonUtils.toObject(map.get("tradeConfigs"), new TypeReference<Properties>(){});
			
			//基础信息
			String merchantCode = prop.getProperty("merchantCode");
			String orderNo = map.get("interfaceRequestID");
			String frontUrl = prop.getProperty("frontUrl");
			String backUrl = prop.getProperty("backUrl");
			String payAmount = map.get("amount");
			String remark = map.get("interfaceCode");
			String payerAcctNo = map.get("bankCardNo");
			
			//构建src
			String src = new StringBuilder()
			.append(merchantCode)
			.append(orderNo)
			.append(frontUrl)
			.append(backUrl)
			.append(payAmount)
			.append(remark)
			.append(payerAcctNo)
			.append(prop.get("key"))
			.toString();
			
			//构建sign
			String sign = MD5Util.md5(src);
			
			//构建请求参数VO
			PayReqVO payVO = new PayReqVO();
			payVO.setMerchantCode(merchantCode);
			payVO.setOrderNo(orderNo);
			payVO.setFrontUrl(frontUrl);
			payVO.setBackUrl(backUrl);
			payVO.setPayAmount(payAmount);
			payVO.setRemark(remark);
			payVO.setBankCardNo(payerAcctNo);
			payVO.setSign(sign);
			
			//请求报文
			String reqContent = JsonUtils.toJsonString(payVO);
			logger.info(reqContent);
			//HTTP请求
			String respContent = HttpClientUtils.send(Method.POST, String.valueOf(prop.get("payUrl")), reqContent, false, "");
			logger.info(respContent);
			//解析响应报文
			PayRespVO respVO = JsonUtils.toObject(respContent, new TypeReference<PayRespVO>() {});
			
			//判断是否成功
			if(!"1".equals(respVO.getInvokeStatus())){
				throw new RuntimeException("channel response error");
			}
			
			resParams = new HashMap<String, String>();
			resParams.put("url", respVO.getRedirectUrl());
			resParams.put("gateway", "authPaySubmit");
			resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
			return resParams;
		} catch (Exception e) {
			throw new RuntimeException("authPay payinterfaceRequestID:"+map.get("interfaceRequestID")+" pay error ",e);
		}
	}

	@Override
	public Map<String, String> sale(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> query(Map<String, String> map) {
		String reqContent = null;
		Map<String, String> reqMap;
		Map<String, String> resMap;
		Map<String, String> resParams;
		
		try {
			Properties prop = JsonUtils.toObject(map.get("tradeConfigs"), new TypeReference<Properties>(){});
			reqMap = new HashMap<>();
			reqMap.put("merchantCode", prop.getProperty("merchantCode"));
			reqMap.put("orderNo", map.get("interfaceRequestID"));
			String respContent = HttpClientUtils.send(Method.POST, String.valueOf(prop.get("queryUrl")), reqContent, false, "");
			resMap = JsonUtils.toObject(respContent, new TypeReference<Map<String, String>>() {});
			
			resParams = new HashMap<>();
			if("1".equals(resMap.get("invokeStatus")) && "1".equals(resMap.get("result"))){
				resParams.put("tranStat", "SUCCESS");
				resParams.put("interfaceRequestID", resMap.get("orderNo"));
				resParams.put("amount", resMap.get("payAmount"));
			} else {
				resParams.put("tranStat", "UNKNOWN");
			}
		} catch (IOException e) {
			throw new RuntimeException("authPay payinterfaceRequestID:"+map.get("interfaceRequestID")+" query error ",e);
		}
		return null;
	}

}
