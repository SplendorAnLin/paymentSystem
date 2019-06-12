package com.yl.payinterface.core.handle.impl.wallet.cmbc584001;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.dpay.hessian.utils.Base64Utils;
import com.yl.payinterface.core.ExceptionMessages;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.handler.ChannelRecionHandler;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 民生微信、支付宝二维码接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年12月14日
 * @version V1.0.0
 */
@Service("walletCmbc584001Handler")
public class Cmbc584001HandlerImpl implements WalletPayHandler,ChannelRecionHandler{
	
	private static Logger logger = LoggerFactory.getLogger(Cmbc584001HandlerImpl.class);
	@Resource
	private InterfaceRequestService interfaceRequestService;
	
	private static int merchantIndex = 0;

	@Override
	public Map<String, String> pay(Map<String, String> map) {
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
		
		Map<String, String> reqParams = new HashMap<>();
		reqParams.put("amount", String.valueOf((int)AmountUtils.multiply(Double.valueOf(map.get("amount")), 100)));
		String[] merchantNos = properties.getProperty("merchantNo").split(",");
		if(merchantIndex >= merchantNos.length){
			merchantIndex=0;
		}
		reqParams.put("merchantNo", merchantNos[merchantIndex]);
		if(merchantIndex < merchantNos.length-1){
			merchantIndex++;
		}else{
			merchantIndex=0;
		}
		reqParams.put("merchantSeq", map.get("interfaceRequestID"));
		reqParams.put("notifyUrl", properties.getProperty("notify_url"));
		reqParams.put("orderInfo", map.get("productName"));
		reqParams.put("platformId", properties.getProperty("platformId"));
//		reqParams.put("remark", "");
		if(InterfaceType.ALIPAY.toString().equals(map.get("interfaceType"))){
			reqParams.put("selectTradeType", "API_ZFBQRCODE");
		}else if(InterfaceType.WXNATIVE.toString().equals(map.get("interfaceType"))){
			reqParams.put("selectTradeType", "API_WXQRCODE");
		}else{
			throw new RuntimeException("interfaceType is error!");
		}
		
		reqParams.put("transDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
		reqParams.put("transTime", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		
		
		Map<String, String> resMap;
		try {
			String context = JsonUtils.toJsonString(reqParams);
			logger.info("WalletCmbc584001 下单请求明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),context);
			String sign = SignEncryptDncryptSignChk.getSign(context);
			String reqStr = SignEncryptDncryptSignChk.encrypt(SignEncryptDncryptSignChk.sign(sign, context));
			
			Map<String, String> reqBusiParams = new HashMap<>();
			reqBusiParams.put("businessContext", reqStr);
			reqBusiParams.put("merchantNo", "");
			reqBusiParams.put("merchantSeq", "");
			reqBusiParams.put("reserve1", map.get("interfaceInfoCode"));
			reqBusiParams.put("reserve2", "");
			reqBusiParams.put("reserve3", "");
			reqBusiParams.put("reserve4", "");
			reqBusiParams.put("reserve5", "");
			reqBusiParams.put("reserveJson", "");
			reqBusiParams.put("securityType", "");
			reqBusiParams.put("sessionId", "");
			reqBusiParams.put("source", "");
			reqBusiParams.put("transCode", "");
			reqBusiParams.put("transDate", "");
			reqBusiParams.put("transTime", "");
			reqBusiParams.put("version", "");
			
			String resStr = HttpUtils.sendReq(properties.getProperty("pay_url"), JsonUtils.toJsonString(reqBusiParams), "POST");
			logger.info("WalletCmbc584001 下单响应密文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),resStr);
			
			resMap =  JsonUtils.toObject(resStr, new TypeReference<Map<String,String>>() {});
			if (!resMap.get("gateReturnType").equals("S")) {
				throw new RuntimeException("status : " + resMap.get("gateReturnMessage"));
			}
			resStr = SignEncryptDncryptSignChk.dncrypt(resMap.get("businessContext"));
			logger.info("WalletCmbc584001 下单响应明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),resStr);
			resMap =  JsonUtils.toObject(resStr, new TypeReference<Map<String,String>>() {});
			resStr = resMap.get("body");
			resMap =  JsonUtils.toObject(resStr, new TypeReference<Map<String,String>>() {});
		} catch (Exception e) {
			logger.error("WalletCmbc584001 下单异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}
		
		Map<String,String> resParams = new HashMap<String, String>();
		resParams.put("code_url", new String(Base64Utils.decode(resMap.get("payInfo"))));
		resParams.put("code_img_url", new String(Base64Utils.decode(resMap.get("payInfo"))));
		resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
		resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
		resParams.put("gateway", "native");
		resParams.put("merchantNo", reqParams.get("merchantNo"));
		return resParams; 
	}

	@Override
	public Map<String, String> query(Map<String, String> map) {
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
		
		Map<String, String> reqParams = new HashMap<>();
		reqParams.put("amount", String.valueOf((int)AmountUtils.multiply(Double.valueOf(map.get("amount")), 100)));
		reqParams.put("merchantNo", map.get("merchantNo"));
		reqParams.put("merchantSeq", map.get("interfaceRequestID"));
		reqParams.put("platformId", properties.getProperty("platformId"));
		reqParams.put("tradeType", "1");
		reqParams.put("reserve", "");
		
		
		Map<String, String> resMap;
		try {
			String context = JsonUtils.toJsonString(reqParams);
			logger.info("WalletCmbc584001 查询请求明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),context);
			String sign = SignEncryptDncryptSignChk.getSign(context);
			String reqStr = SignEncryptDncryptSignChk.encrypt(SignEncryptDncryptSignChk.sign(sign, context));
			
			Map<String, String> reqBusiParams = new HashMap<>();
			reqBusiParams.put("businessContext", reqStr);
			reqBusiParams.put("merchantNo", reqStr);
			reqBusiParams.put("merchantSeq", reqStr);
			reqBusiParams.put("reserve1", reqStr);
			reqBusiParams.put("reserve2", reqStr);
			reqBusiParams.put("reserve3", reqStr);
			reqBusiParams.put("reserve4", reqStr);
			reqBusiParams.put("reserve5", reqStr);
			reqBusiParams.put("reserveJson", reqStr);
			reqBusiParams.put("securityType", reqStr);
			reqBusiParams.put("sessionId", reqStr);
			reqBusiParams.put("source", reqStr);
			reqBusiParams.put("transCode", reqStr);
			reqBusiParams.put("transDate", reqStr);
			reqBusiParams.put("transTime", reqStr);
			reqBusiParams.put("version", reqStr);
			
			String resStr = HttpUtils.sendReq(properties.getProperty("pay_url"), JsonUtils.toJsonString(reqBusiParams), "POST");
			logger.info("WalletCmbc584001 查询响应密文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),resStr);
			
			resMap =  JsonUtils.toObject(resStr, new TypeReference<Map<String,String>>() {});
			if (!resMap.get("gateReturnType").equals("S")) {
				throw new RuntimeException("status : " + resMap.get("gateReturnMessage"));
			}
			resStr = SignEncryptDncryptSignChk.dncrypt(resMap.get("businessContext"));
			logger.info("WalletCmbc584001 查询响应明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),resStr);
			resMap =  JsonUtils.toObject(resStr, new TypeReference<Map<String,String>>() {});
			resStr = resMap.get("body");
			resMap =  JsonUtils.toObject(resStr, new TypeReference<Map<String,String>>() {});
		} catch (Exception e) {
			logger.error("WalletCmbc584001 查询异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}
		
		Map<String, String> queryParams = new HashMap<String, String>();
		String responseCode = "";
		String responseMessage = "";

		if ("S".equals(resMap.get("tradeStatus"))) {
			queryParams.put("queryStatus", "SUCCESS");
			responseCode = "S";
			responseMessage = "成功";
		}else if("E".equals(resMap.get("tradeStatus"))){
			responseCode = "E";
			responseMessage = "失败";
			queryParams.put("queryStatus", "FAILED");
			resMap.put("trade_state", "FAILED");
		} else {
			queryParams.put("queryStatus", "UNKNOWN");
		}

		// 响应码
		queryParams.put("responseCode", responseCode);
		// 响应描述
		queryParams.put("responseMessage", responseMessage);
		// 支付结果
		queryParams.put("tranStat", resMap.get("tradeStatus"));
		// 金额
		queryParams.put("amount", resMap.get("amount"));
		// 微信订单号
		queryParams.put("weChatOrderId", resMap.get("bankTradeNo"));
		// 通知时间
		queryParams.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 支付完成时间
		queryParams.put("orderFinishDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 支付完成时间
		queryParams.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 接口编号
		queryParams.put("interfaceCode", map.get("interfaceInfoCode"));
		// 接口请求订单号
		queryParams.put("interfaceRequestId", map.get("originalInterfaceRequestID"));
		// 业务完成方式
		queryParams.put("businessCompleteType", map.get("businessCompleteType"));
		return queryParams;
	}

	@Override
	public InterfaceRequest complete(Map<String, Object> map) {
		// 接口请求订单号
		String interfaceRequestId = map.get("interfaceRequestId").toString();
		// 根据支付流水号查询接口请求信息
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestId);
		logger.info("WalletCmbc584001 支付接口订单号为:{},完成时参数：{}", interfaceRequestId, map);

		if (interfaceRequest == null) {
			logger.error("支付接口订单号为:{},业务处理实体不存在", interfaceRequestId);
			throw new RuntimeException(ExceptionMessages.BUSINESS_HANDLER_ENTITY_NULL);
		}
		// 检验交易完成是否已处理成功 成功跳过,否则继续
		if ((!"".equals(interfaceRequest.getStatus())) && (InterfaceTradeStatus.SUCCESS.equals(interfaceRequest.getStatus()))) {
			logger.error("WalletCmbc584001 支付接口订单号为:{},接口已成功处理", interfaceRequest.getInterfaceRequestID());
			throw new RuntimeException(ExceptionMessages.INTERFACE_REQUEST_NOT_INIT);
		}
		// 交易结果
		String tranStat = map.get("tranStat").toString();
		logger.info("WalletCmbc584001 支付接口订单号为:{},钱包支付-支付订单完成时状态：{}", interfaceRequest.getInterfaceRequestID(), tranStat);

		if ("SUCCESS".equals(tranStat)) {
			interfaceRequest.setStatus(InterfaceTradeStatus.SUCCESS);
			// 交易金额
			double amount = AmountUtils.divide(Double.valueOf(map.get("amount").toString()).doubleValue(), 100.0D);
			// 校验订单金额是否与上送的一致
			if (!AmountUtils.eq(amount, interfaceRequest.getAmount())) {
				logger.error("WalletCmbc584001 支付接口订单号为:{},通知完成支付金额不一致", interfaceRequest.getInterfaceRequestID());
				throw new RuntimeException(ExceptionMessages.PAY_AMOUNT_NOT_ACCORDANCE);
			}
			interfaceRequest.setAmount(amount);
		} else if ("CLOSED".equals(tranStat)) {
			interfaceRequest.setStatus(InterfaceTradeStatus.CLOSED);
			map.put("responseMessage","已关闭");
		} else if ("FAIL".equals(tranStat)) {
			interfaceRequest.setStatus(InterfaceTradeStatus.FAILED);
			map.put("responseMessage","失败");
		} else {
			interfaceRequest.setStatus(InterfaceTradeStatus.UNKNOWN);
			map.put("responseMessage","未知");
		}
		// 响应码
		interfaceRequest.setResponseCode(map.get("responseCode") == null ? "" : map.get("responseCode").toString());
		// 交易结果描述
		interfaceRequest.setResponseMessage(map.get("responseMessage") == null ? "成功" : map.get("responseMessage").toString());
		// 交易完成时间
		if(map.get("completeTime") != null && !interfaceRequest.getStatus().equals(InterfaceTradeStatus.UNKNOWN)){
			try {
				interfaceRequest.setCompleteTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(map.get("completeTime").toString()));
			} catch (ParseException e) {
				logger.error("WalletCmbc584001 完成时间参数异常 订单号:[{}],异常信息[{}]", interfaceRequest.getInterfaceRequestID(),e);
				interfaceRequest.setCompleteTime(new Date());
			}
		}
		// 业务处理完成方式
		interfaceRequest.setBusinessCompleteType(BusinessCompleteType.valueOf(map.get("businessCompleteType").toString()));
		// 记录微信订单号
		if (map.get("bankTradeNo") != null) {
			interfaceRequest.setInterfaceOrderID(map.get("bankTradeNo").toString());
		}
		return interfaceRequest;
	}

	@Override
	public Map<String, String> recion(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
