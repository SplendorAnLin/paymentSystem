package com.yl.payinterface.core.handle.impl.yl430000;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.handle.impl.wallet.cib330000.WxSign;
import com.yl.payinterface.core.handler.AuthPayHandler;
import com.yl.payinterface.core.utils.CodeBuilder;

/**
 * 聚合支付快捷通用接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年12月14日
 * @version V1.0.0
 */
@Service("authPayYl430001Handler")
public class Yl430000AuthPayHandler implements AuthPayHandler{

	private static Logger logger = LoggerFactory.getLogger(Yl430000AuthPayHandler.class);
	@Resource
	private InterfaceRequestService interfaceRequestService;
	
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("pay_bankcode", "ICBC");
		properties.put("notify_url", "http://jayzhangxiao.eicp.net/payinterface-front/rxtAuthPayNotify/completePay.htm");
		properties.put("pay_memberid", "10014");
		properties.put("pay_notifyurl", "http://jayzhangxiao.eicp.net/payinterface-front/rxtAuthPayNotify/completePay.htm");
		properties.put("key", "WGbbgw2NK6SgyaykTvzVeKmK16KAIu8");
		properties.put("pay_url", "http://pay.bank-pay.com.cn/Pay_Index.html");
		
		properties.put("wxreturntype", "QR");
		//ShangHaiZfb
		properties.put("tongdao", "Unionpay");
		Map<String, String> map = new HashMap<>();
		map.put("tradeConfigs", JsonUtils.toJsonString(properties));
		map.put("amount", "5100.01");
		map.put("interfaceRequestID", CodeBuilder.build("TD", "yyyyMMdd").replace("-", ""));
		map.put("interfaceCode", "YL_430000-WX_NATIVE");
		map.put("productName", "测试");
//		map.put("bankCardNo", "621626656565656");
		new Yl430000AuthPayHandler().pay(map);
	}
	
	@Override
	public Map<String, String> query(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public Map<String, String> pay(Map<String, String> map) {
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
		
		SortedMap<Object, Object> reqParams = new TreeMap<>();
		reqParams.put("pay_amount", map.get("amount"));
		reqParams.put("pay_applydate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		reqParams.put("pay_bankcode", properties.getProperty("pay_bankcode"));
		reqParams.put("pay_callbackurl", properties.getProperty("notify_url"));
		reqParams.put("pay_memberid", properties.getProperty("pay_memberid"));
		reqParams.put("pay_notifyurl", properties.get("pay_notifyurl"));
		reqParams.put("pay_orderid", map.get("interfaceRequestID"));
		
		try {
			String sign = WxSign.createYlSign(reqParams, properties.getProperty("key"));
			
			Map<String,String> params = new HashMap<>();
			params.put("pay_amount", map.get("amount"));
			params.put("pay_applydate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			params.put("pay_bankcode", properties.getProperty("pay_bankcode"));
			params.put("pay_callbackurl", properties.getProperty("notify_url"));
			params.put("pay_memberid", properties.getProperty("pay_memberid"));
			params.put("pay_notifyurl", properties.getProperty("pay_notifyurl"));
			params.put("pay_orderid", map.get("interfaceRequestID"));
			params.put("wxreturntype", properties.getProperty("wxreturntype"));
			params.put("tongdao", properties.getProperty("tongdao"));
			params.put("pay_productname", map.get("productName"));
			params.put("pay_md5sign", sign);
			params.put("pay_reserved3", map.get("interfaceInfoCode"));
			params.put("description", URLEncoder.encode(map.get("productName")));
			params.put("card_no", map.get("bankCardNo"));
			
			logger.info("authPayYl430001Handler 下单请求报文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),params);
			String resStr = HttpClientUtils.send(Method.POST, properties.getProperty("pay_url"), params);
			logger.info("authPayYl430001Handler 下单响应报文  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),resStr);
			
			Map<String,String> resParams = new HashMap<String, String>();
			resParams.put("code_url", resStr);
			resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
			resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
			resParams.put("gateway", "authPaySubmit");
			return resParams;
		} catch (Exception e) {
			logger.error("authPayYl430001Handler 组装下单报文异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Map<String, String> sendVerifyCode(Map<String, String> params) {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, String> authPay(Map<String, String> map) {
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
		
		SortedMap<Object, Object> reqParams = new TreeMap<>();
		reqParams.put("pay_amount", map.get("amount"));
		reqParams.put("pay_applydate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		reqParams.put("pay_bankcode", properties.getProperty("pay_bankcode"));
		reqParams.put("pay_callbackurl", properties.getProperty("notify_url"));
		reqParams.put("pay_memberid", properties.getProperty("pay_memberid"));
		reqParams.put("pay_notifyurl", properties.get("pay_notifyurl"));
		reqParams.put("pay_orderid", map.get("interfaceRequestID").replace("-", ""));
		
		try {
			String sign = WxSign.createYlSign(reqParams, properties.getProperty("key"));
			
			Map<String,String> params = new HashMap<>();
			params.put("pay_amount", map.get("amount"));
			params.put("pay_applydate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			params.put("pay_bankcode", properties.getProperty("pay_bankcode"));
			params.put("pay_callbackurl", properties.getProperty("notify_url"));
			params.put("pay_memberid", properties.getProperty("pay_memberid"));
			params.put("pay_notifyurl", properties.getProperty("pay_notifyurl"));
			params.put("pay_orderid", map.get("interfaceRequestID").replace("-", ""));
			params.put("wxreturntype", properties.getProperty("wxreturntype"));
			params.put("tongdao", properties.getProperty("tongdao"));
			params.put("pay_productname", map.get("productName"));
			params.put("pay_md5sign", sign);
			params.put("pay_reserved3", map.get("interfaceCode")+"--"+map.get("interfaceRequestID"));
			params.put("description", URLEncoder.encode(map.get("productName")));
			params.put("card_no", map.get("bankCardNo"));
			
			logger.info("authPayYl430001Handler 下单请求报文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceCode"),map.get("interfaceRequestID"),params);
			params.put("interfaceInfoCode", map.get("interfaceCode"));
			params.put("interfaceRequestID", map.get("interfaceRequestID"));
			params.put("gateway", "authPaySubmitYl");
			params.put("pay_url", properties.getProperty("pay_url"));
			return params;
		} catch (Exception e) {
			logger.error("authPayYl430001Handler 组装下单报文异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceCode"),map.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Map<String, String> sale(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

}
