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
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.handle.impl.wallet.cib330000.WxSign;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 聚合支付网关通用接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年12月14日
 * @version V1.0.0
 */
@Service("internetbankYl430001Handler")
public class Yl430000InternetBankPayHandler implements InternetbankHandler{

	private static Logger logger = LoggerFactory.getLogger(Yl430000InternetBankPayHandler.class);
	@Resource
	private InterfaceRequestService interfaceRequestService;
	
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
			params.put("tongdao", properties.getProperty("tongdao"));
			params.put("pay_productname", map.get("goodsName"));
			params.put("pay_md5sign", sign);
			params.put("description", URLEncoder.encode(map.get("goodsName")));
			
			logger.info("internetbankYl430001Handler 组装下单请求报文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),params);
			return params;
		} catch (Exception e) {
			logger.error("internetbankYl430001Handler 组装下单报文异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public InterfaceRequest complete(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] trade(TradeContext tradeContext) throws BusinessException {
		Map<String, String> map= tradeContext.getRequestParameters();
		map.put("amount", String.valueOf(tradeContext.getInterfaceRequest().getAmount()));
		map.put("interfaceRequestID", tradeContext.getInterfaceRequest().getInterfaceRequestID());
		map = pay(map);
		return new Object[]{"",map};
	}

	@Override
	public Map<String, Object> signatureVerify(
			InternetbankSalesResponseBean internetbankSalesResponseBean,
			Properties tradeConfigs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] query(TradeContext tradeContext) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
