package com.yl.payinterface.core.handle.impl.remit.ylzf420101;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;

import com.yl.payinterface.core.bean.Cnaps;
import com.yl.payinterface.core.bean.Recognition;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.util.EncodingUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handle.impl.remit.ylzf420101.bean.YLZF420101QueryBean;
import com.yl.payinterface.core.handle.impl.remit.ylzf420101.bean.YLZF420101RemitBean;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.CommonUtils;
import com.yl.payinterface.core.utils.HttpUtils;
import com.yl.payinterface.core.utils.MD5Util;

/**
 * @ClassName YLZF420101HandlerImpl
 * @Description 聚合支付代付
 * @author Administrator
 * @date 2017年11月27日 下午3:21:44
 */
@Service("remitYLZF420101Handler")
public class YLZF420101HandlerImpl implements RemitHandler, ChannelReverseHandler {

	private static final Logger logger = LoggerFactory.getLogger(YLZF420101HandlerImpl.class);
	
	@Override
	public Map<String, String> reverse(Map<String, String> params) {
		return query(params);
	}

	@Override
	public Map<String, String> remit(Map<String, String> map) {

		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
		});
		String merid = properties.getProperty("merid");
		String backurl = properties.getProperty("backurl");
		String url = properties.getProperty("url");
		String key = properties.getProperty("key");
		String cerType = map.get("cerType");
        String cnapsCode = "";
        Recognition recognition = CommonUtils.recognition(map.get("accountNo"));
        List<Cnaps> cnaps = CommonUtils.cnapsCode(recognition.getProviderCode());
        if (cnaps != null && cnaps.size() > 0) {
            cnapsCode = cnaps.get(0).getClearingBankCode();
        }
		String interfaceRequestId = map.get("requestNo");
		String accountType = map.get("accountType");
		if (StringUtils.isBlank(accountType) || "INDIVIDUAL".equals(accountType)) {
			accountType = "0";
		} else {
			accountType = "1";
		}
		YLZF420101RemitBean bean = new YLZF420101RemitBean();
		bean.setTxnamt(String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d)));
		bean.setMerid(merid);
		bean.setOrderid(interfaceRequestId);
		bean.setBin(cnapsCode);
		bean.setAccno(map.get("accountNo"));
		bean.setAccname(map.get("accountName"));
		bean.setBackurl(backurl);
		bean.setCode(accountType);
		if ("ID".equals(cerType) && StringUtils.isNotBlank(map.get("cerNo"))) {
			bean.setCardno(map.get("cerNo").toUpperCase());
		} else {
			logger.info("接口请求号：{}，证件类型错误：{}--{}", interfaceRequestId, cerType, map.get("cerNo"));
			throw new BusinessRuntimeException("cerType ERROR");
		}
		

		String info = JsonUtils.toJsonString(bean);
		String baseInfo = new String(Base64.encodeBase64(info.getBytes()));
		String sign = MD5Util.MD5Encode(baseInfo + key, "UTF-8");

		
     // 返回MAP
        Map<String, String> respMap = new HashMap<>();
		try {
			baseInfo = URLEncoder.encode(baseInfo, "UTF-8");
			String params = "req=" + baseInfo + "&sign=" + sign;
			logger.info("接口请求号：{}，发起代付接口原文信息：{}", interfaceRequestId, info);
			logger.info("接口请求号：{}，发起代付接口密文文信息：{}", interfaceRequestId, params);
			String respStr = HttpUtils.sendReq2(url, params, "POST");
			logger.info("接口请求号：{}，发起代付接口返回信息：{}", interfaceRequestId, respStr);
			Map<String, String> backMap = JsonUtils.toObject(respStr, new TypeReference<Map<String, String>>() {});
			String resp = backMap.get("resp");
			String backSign = backMap.get("sign");
			Map<String, String> respData = JsonUtils.toObject(EncodingUtils.getString(Base64.decodeBase64(resp), "UTF-8"), new TypeReference<Map<String, String>>() {
			});
			logger.info("接口请求号：{}，发起代付接口返回信息：{}", interfaceRequestId, respData);
			
			String reBackSign = MD5Util.MD5Encode(resp + key, "UTF-8");
			
			logger.info(backSign + "----------" + reBackSign);
			
			if ("00".equals(respData.get("respcode")) && reBackSign.equals(backSign)) {
                if ("0000".equals(respData.get("resultcode"))) {
                    respMap.put("resCode", respData.get("resultcode"));
                    respMap.put("resMsg", "付款成功");
                    respMap.put("tranStat", "SUCCESS");
                    respMap.put("requestNo", map.get("requestNo"));
                    respMap.put("amount", map.get("amount"));
                    respMap.put("compType", BusinessCompleteType.NORMAL.name());
                    return respMap;
                } else if ("2002".equals(respData.get("resultcode")) || "2003".equals(respData.get("resultcode"))) {
	                respMap.put("resCode", respData.get("resultcode"));
	                respMap.put("resMsg", respData.get("resultmsg"));
	                respMap.put("tranStat", "FAILED");
	                respMap.put("requestNo", map.get("requestNo"));
	                respMap.put("amount", map.get("amount"));
	                respMap.put("compType", BusinessCompleteType.NORMAL.name());
	                return respMap;
            	} else {
                    respMap.put("requestNo", map.get("requestNo"));
                    respMap.put("amount", map.get("amount"));
                    respMap.put("tranStat", "UNKNOWN");
                    return respMap;
                } 
            } else {
                respMap.put("resCode", "9999");
                respMap.put("resMsg", "请求通道异常");
                respMap.put("tranStat", "UNKNOWN");
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", map.get("amount"));
                respMap.put("compType", BusinessCompleteType.NORMAL.name());
                return respMap;
            }
		} catch (Exception e) {
			logger.info("接口请求号：{}，发起代付失败：{}", interfaceRequestId, e);
		}

		return null;
	}

	@Override
	public Map<String, String> query(Map<String, String> map) {
		
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
		String queryUrl = properties.getProperty("queryUrl");
		String merid = properties.getProperty("merid");
		String key = properties.getProperty("key");
		String interfaceRequestId = map.get("requestNo");
		String fee = properties.getProperty("fee");
		
		
		YLZF420101QueryBean bean = new YLZF420101QueryBean();
		bean.setOrderid(map.get("requestNo"));
		bean.setMerid(merid);
		
		String info = JsonUtils.toJsonString(bean);
		String baseInfo = new String(Base64.encodeBase64(info.getBytes()));
		String sign = MD5Util.MD5Encode(baseInfo + key, "UTF-8");

		
        // 返回MAP
        Map<String, String> respMap = new HashMap<>();
		try {
			baseInfo = URLEncoder.encode(baseInfo, "UTF-8");
			Map<String, String> params = new LinkedHashMap<>();
			params.put("req", baseInfo);
			params.put("sign", sign);
			logger.info("接口请求号：{}，查询代付接口原文信息：{}", interfaceRequestId, info);
			logger.info("接口请求号：{}，查询代付接口密文文信息：{}", interfaceRequestId, params);
			String respStr = HttpClientUtils.send(HttpClientUtils.Method.POST, queryUrl, params);
			logger.info("接口请求号：{}，查询代付接口返回信息：{}", interfaceRequestId, respStr);
			Map<String, String> respData = JsonUtils.toObject(EncodingUtils.getString(Base64.decodeBase64(JsonUtils.toObject(respStr, new TypeReference<Map<String, String>>() {
			}).get("resp")), "UTF-8"), new TypeReference<Map<String, String>>() {
			});
			logger.info("接口请求号：{}，查询代付接口返回信息：{}", interfaceRequestId, respData);
			if ("00".equals(respData.get("respcode"))) {
                if ("0000".equals(respData.get("resultcode"))) {
                    respMap.put("resCode", respData.get("resultcode"));
                    respMap.put("resMsg", "付款成功");
                    respMap.put("tranStat", "SUCCESS");
                    respMap.put("requestNo", map.get("requestNo"));
                    double amount = AmountUtils.subtract(Double.valueOf(respData.get("txnamt")), Double.valueOf(fee));
                    amount = (AmountUtils.divide(amount, 100d, 2, RoundingMode.HALF_UP));
                    respMap.put("amount", String.valueOf(amount));
                    respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                    respMap.put("interfaceOrderID", respData.get("queryid"));
                    return respMap;
                } else if ("2002".equals(respData.get("resultcode")) || "2003".equals(respData.get("resultcode"))) {
                    respMap.put("resCode", respData.get("resultcode"));
                    respMap.put("resMsg", respData.get("resultmsg"));
                    respMap.put("tranStat", "FAILED");
                    respMap.put("requestNo", map.get("requestNo"));
                    double amount = AmountUtils.subtract(Double.valueOf(respData.get("txnamt")), Double.valueOf(fee));
                    amount = (AmountUtils.divide(amount, 100d, 2, RoundingMode.HALF_UP));
                    respMap.put("amount", String.valueOf(amount));
                    respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                    respMap.put("interfaceOrderID", respData.get("queryid"));
                    return respMap;
                } else {
                    respMap.put("requestNo", map.get("requestNo"));
                    respMap.put("amount", map.get("amount"));
                    respMap.put("tranStat", "UNKNOWN");
                }
            }
			
			

		} catch (IOException e) {
			logger.info("接口请求号：{}，查询代付失败：{}", interfaceRequestId, e);
		}
		
		
		return respMap;
	}
	
	
	public static void main(String[] args) {
//		YLZF420101HandlerImpl a = new YLZF420101HandlerImpl();
//		
//		Properties properties = new Properties();
//		properties.put("merid", "89907454");
//		properties.put("key", "296e34eb77d7e941b474685bfb6e772e");
//		properties.put("url", "https://api.zhifujiekou.com/api/qcashgateway");
//		properties.put("queryUrl", "https://api.zhifujiekou.com/api/qcashquery");
//		properties.put("backurl", "http://www.sunboyang.com:8080/myshop/complete_get");
//		properties.put("fee", "50");
//		
//		Map<String, String> map = new HashMap<>();
//		map.put("tradeConfigs", JsonUtils.toJsonString(properties));
//		map.put("requestNo", System.currentTimeMillis() + "");
//		map.put("bankName", "哈尔滨银行");
//		map.put("accountNo", "6224254530100254241");
//		map.put("accountName", "曲冰");
//		map.put("amount", "1.01");
//		// map.put("idCardNo", "23010719910228247X");
//		System.out.println(a.remit(map));
		
		System.out.println("156456sasdfs4789789xx".toUpperCase());
		
	}
	

}
