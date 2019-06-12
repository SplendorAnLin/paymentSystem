package com.yl.payinterface.core.handle.impl.realAuth.jd;

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
import com.yl.payinterface.core.utils.HttpUtils;

@Service("realAuthJDHandler")
public class RealAuthJDHandler implements RealNameAuthHandler {

	private static Logger logger = LoggerFactory.getLogger(RealAuthJDHandler.class);

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

		String url = tradeConfigs.getProperty("url");

		String accName = requestParameters.get("accountName"); // 姓名

		String cardPhone = requestParameters.get("phone"); // 手机号

		String certificateNo = requestParameters.get("certificatesCode"); // 身份证号

		String cardNo = requestParameters.get("accountNo"); // 银行卡卡号

		String appkey = tradeConfigs.getProperty("appkey_example"); // 万象平台提供的appkey

		String data = "accName=" + accName + "&cardPhone=" + cardPhone + "&certificateNo=" + certificateNo + "&cardNo=" + cardNo + "&appkey=" + appkey;

		Map<String, Object> map = new HashMap<>();
		
		try {
			
			logger.info("京东万象:接口请求号：{}，发送接口报文为：【{}】", interfaceRequestId, data);
			String info = HttpUtils.sendReq2(url, data, "POST");
			// String info = "{\"code\":\"10000\",\"charge\":false,\"msg\":\"查询成功\",\"result\":{\"success\":\"true\",\"respMsg\":\"验证通过\",\"comfrom\":\"jd_query\",\"respCode\":\"000000\",\"serialNo\":\"201708102356556654174770178331\"}}";
			map = JdResponseUtils.getReturnInfo(info);
			logger.info("京东万象:接口请求号：{}，接口返回报文为：【{}】", interfaceRequestId, info);
		} catch (Exception e) {
			throw new BusinessException("京东万象发送请求异常：{}", e);
		}
		// 如果code等于10000或者已经收费了
		if ("10000".equals(map.get("code"))) {
			Map<String, Object> resultMap = (Map<String, Object>)map.get("result");
			if (resultMap != null && resultMap.size() != 0) {
				responseParams.put("interfaceOrderID", resultMap.get("serialNo"));
				if ("true".equals(resultMap.get("success"))) {
					responseParams.put("tranStat", InterfaceTradeStatus.SUCCESS.name());
					responseParams.put("requestResponseCode", "000000");
				} else {
					responseParams.put("tranStat", InterfaceTradeStatus.SUCCESS.name());
					responseParams.put("requestResponseCode", "000001");
				}
			} else if ("true".equals(map.get("charge"))) {
				responseParams.put("tranStat", InterfaceTradeStatus.SUCCESS.name());
				responseParams.put("requestResponseCode", "000001");
			} else {
				responseParams.put("requestResponseCode", "000003");
				responseParams.put("tranStat", InterfaceTradeStatus.FAILED.name());
			}
		} else {
			responseParams.put("requestResponseCode", "000003");
			responseParams.put("tranStat", InterfaceTradeStatus.FAILED.name());
		}
		
		responseParams.put("businessCompleteType", BusinessCompleteType.NORMAL);

		return responseParams;
	}

	@Override
	public Map<String, Object> signatureVerify(AuthRequestResponseBean authRequestResponseBean, String tradeConfigs) throws BusinessException {
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
//		String url = "https://way.jd.com/YOUYU365/keyelement";
//		String data = "accName=孙勃洋&cardPhone=18510412233&certificateNo=23010719910228247X&cardNo=6225768773991953&appkey=e471c7bc61a3eedbff3d76f01115a591";
//		System.out.println(data);
		try {
			//String info = HttpUtils.sendReq2(url, data, "POST");
			String info = "{\"code\":\"10000\",\"charge\":false,\"msg\":\"查询成功\",\"result\":{\"success\":\"true\",\"respMsg\":\"验证通过\",\"comfrom\":\"jd_query\",\"respCode\":\"000000\",\"serialNo\":\"201708102356556654174770178331\"}}";
			Map map = JdResponseUtils.getReturnInfo(info);
			System.out.println(JsonUtils.toJsonString(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
