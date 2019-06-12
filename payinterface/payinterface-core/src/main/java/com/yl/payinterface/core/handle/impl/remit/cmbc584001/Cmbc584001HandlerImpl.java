package com.yl.payinterface.core.handle.impl.remit.cmbc584001;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handler.ChannelRecionHandler;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.HttpUtils;

/**
 * 民生代付
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月18日
 * @version V1.0.0
 */
@Service("cmbcHandler584001")
public class Cmbc584001HandlerImpl implements RemitHandler,ChannelRecionHandler {

	private static Logger logger = LoggerFactory.getLogger(Cmbc584001HandlerImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> remit(Map<String, String> map) {

		try {
			Properties properties = JsonUtils.toJsonToObject(map.get("tradeConfigs"), Properties.class);
			String resString = HttpUtils.sendReq(properties.getProperty("payUrl"), JsonUtils.toJsonString(map), "POST");
			return JsonUtils.toObject(resString, Map.class);
		} catch (Exception e) {
			logger.error("cmbc remit error:{}", e);
		}
		
		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("tranStat", "UNKNOW");
		resMap.put("resCode", "9997");
		resMap.put("resMsg", "未知异常");
		return resMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> query(Map<String, String> map){
		try {
			Properties properties = JsonUtils.toJsonToObject(map.get("tradeConfigs"), Properties.class);
			String resString = HttpUtils.sendReq(properties.getProperty("queryUrl"), JsonUtils.toJsonString(map), "POST");
			return JsonUtils.toObject(resString, Map.class);
		} catch (Exception e) {
			logger.error("cmbc query error:{}", e);
		}
		
		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("tranStat", "UNKNOW");
		resMap.put("resCode", "9997");
		resMap.put("resMsg", "未知异常");
		return resMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> recion(Map<String,String> params) {
		try {
			Properties properties = JsonUtils.toJsonToObject(params.get("tradeConfigs"), Properties.class);
			String resString = HttpUtils.sendReq(properties.getProperty("recionUrl"), JsonUtils.toJsonString(params), "POST");
			return JsonUtils.toObject(resString, Map.class);
		} catch (Exception e) {
			logger.error("cmbc recion error:{}", e);
		}
		
		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("tranStat", "UNKNOW");
		resMap.put("resCode", "9997");
		resMap.put("resMsg", "未知异常");
		return resMap;

	}

}
