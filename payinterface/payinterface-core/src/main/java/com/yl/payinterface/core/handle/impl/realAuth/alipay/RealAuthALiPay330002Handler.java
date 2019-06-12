package com.yl.payinterface.core.handle.impl.realAuth.alipay;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.AuthRequestResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handler.RealNameAuthHandler;
import com.yl.payinterface.core.model.InterfaceRequest;

@Service("realAuthALiPay330002Handler")
public class RealAuthALiPay330002Handler implements RealNameAuthHandler {
	
	private static Logger logger = LoggerFactory.getLogger(RealAuthALiPay330002Handler.class);

	@Override
	public Map<String, Object> trade(TradeContext tradeContext) throws BusinessException {
		
		Map<String, Object> responseParams = new LinkedHashMap<String, Object>();
		// 请求参数
		Map<String, String> requestParameters = tradeContext.getRequestParameters();
		// 获取配置信息
		Properties tradeConfigs = JsonUtils.toObject(requestParameters.get("transConfig"), Properties.class);
		// 接口请求
		InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
		String interfaceRequestId = interfaceRequest.getInterfaceRequestID();
		
		String host = tradeConfigs.getProperty("host");
	    String path = tradeConfigs.getProperty("path");
	    String method = tradeConfigs.getProperty("method");
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + tradeConfigs.getProperty("appcode"));
	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("acct_pan", requestParameters.get("accountNo"));
	    querys.put("cert_id", requestParameters.get("certificatesCode"));
	    querys.put("acct_name", requestParameters.get("accountName"));
	    querys.put("phone_num", requestParameters.get("phone"));

	    Map<String, Object> map = new HashMap<>();
		
		try {
			logger.info("阿里Pay330002:接口请求号：{}，发送接口报文为：【{}】", interfaceRequestId, JsonUtils.toJsonString(querys));
			String info = HttpUtils.doGet(host, path, method, headers, querys);
			map = JsonUtils.toObject(info, Map.class);
			logger.info("阿里Pay330002:接口请求号：{}，接口返回报文为：【{}】", interfaceRequestId, info);
		} catch (Exception e) {
			throw new BusinessException("阿里Pay发送请求异常：{}", e);
		}
		
		responseParams.put("interfaceOrderID", interfaceRequest.getBussinessOrderID());
		responseParams.put("tranStat", InterfaceTradeStatus.SUCCESS.name());
		Map<String, Object> respData = (Map<String, Object>) map.get("resp");
		
		if(0 == Integer.valueOf(respData.get("code").toString())){
			responseParams.put("requestResponseCode", "000000");
		} else {
			responseParams.put("requestResponseCode", "000001");
		}
		
		responseParams.put("businessCompleteType", BusinessCompleteType.NORMAL);

		return responseParams;
	}

	@Override
	public Map<String, Object> signatureVerify(AuthRequestResponseBean authRequestResponseBean, String tradeConfigs)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InterfaceRequest complete(Map<String, Object> completeParameters) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] query(TradeContext tradeContext) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		Map<String, Object> responseParams = new LinkedHashMap<String, Object>();
		
		String host = "http://lundroid.market.alicloudapi.com";
	    String path = "/lianzhuo/verifi";
	    String method = "GET";
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + "0f37ce4524e1446cbcd8e019b8688620");
	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("acct_pan", "6217230200003271469");
	    querys.put("cert_id", "23010719910228247X");
	    querys.put("acct_name", "孙勃洋");
	    querys.put("phone_num", "18510412233");

	    Map<String, Object> map = new HashMap<>();
		
		try {
			System.out.println("------------请求参数-----------");
			System.out.println("headers：" + JsonUtils.toJsonString(headers));
			System.out.println("querys：" + JsonUtils.toJsonString(querys));
			
			String info = HttpUtils.doGet(host, path, method, headers, querys);
			
			map = JsonUtils.toObject(info, Map.class);
			
			System.out.println("------------响应参数-----------");
			
			System.out.println("result：" + info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> respData = (Map<String, Object>) map.get("resp");
		System.out.println(respData.get("code"));
		
		responseParams.put("businessCompleteType", BusinessCompleteType.NORMAL);
		
		System.out.println("打印结果：" + JsonUtils.toJsonString(responseParams));
	}

}
