package com.yl.payinterface.core.handle.impl.b2c.ylzf420101;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.util.EncodingUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.MD5Util;

@Service("ylzfB2C420101Handler")
public class YLZFB2C420101Handler implements InternetbankHandler {

    private static Logger logger = LoggerFactory.getLogger(YLZFB2C420101Handler.class);

    public Map<String, String> query(Map<String, String> map) {

        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});

        Map<String, String> respParams = null;

        try {

            Map<String, Object> reqData = new LinkedHashMap<>();
            reqData.put("merid", properties.getProperty("merid"));
            reqData.put("queryid", map.get("interfaceRequestID"));
            reqData.put("orderid", map.get("interfaceInfoCode"));

            Map<String, String> params = new LinkedHashMap<>();
            params.put("req", new String(Base64.encodeBase64(JsonUtils.toJsonString(reqData).getBytes())));
            params.put("sign", MD5Util.MD5Encode(params.get("req") + properties.get("key"), "UTF-8"));

            logger.info("聚合支付420101 查询请求报文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),params);
            String respStr = HttpClientUtils.send(HttpClientUtils.Method.POST,properties.getProperty("queryUrl"), params);
            logger.info("聚合支付420101 查询响应报文  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),respStr);

            String sign = MD5Util.MD5Encode( JsonUtils.toObject(respStr, new TypeReference<Map<String, String>>() {}).get("resp") + properties.get("key"), "UTF-8");

            if(!params.get("sign").equals(sign)){
                throw new RuntimeException("聚合支付420101 查询响应报文 验签失败");
            }

            Map<String, String> respData = JsonUtils.toObject(EncodingUtils.getString(Base64.decodeBase64(JsonUtils.toObject(respStr, new TypeReference<Map<String, String>>() {
            }).get("resp")), "UTF-8"), new TypeReference<Map<String, String>>() {});

            respParams = new HashMap<>();
            if(respData.get("respcode").equals("00") && respData.get("resultcode").equals("0000")){
                respParams.put("interfaceRequestID", respData.get("orderid"));
                respParams.put("tranStat", "SUCCESS");
                respParams.put("amount", respData.get("txnamt"));
                respParams.put("responseCode", respData.get("resultcode"));
                respParams.put("responseMsg", respData.get("resultmsg"));
                respParams.put("interfaceOrderID", respData.get("queryid"));
            }else {
                respParams.put("tranStat", "UNKNOWN");
            }
        } catch (Exception e) {
            logger.error("聚合支付420101 查询报文异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),e);
            throw new RuntimeException(e.getMessage());
        }

        return respParams;

    }

	/* (non-Javadoc)
	 * @see com.yl.payinterface.core.handler.InternetbankHandler#trade(com.yl.payinterface.core.bean.TradeContext)
	 */
	@Override
	public Object[] trade(TradeContext tradeContext) throws BusinessException {
		// 获取接口请求
		InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
		
		Map<String, String> params = tradeContext.getRequestParameters();
		// 获取交易配置
		Properties properties = JsonUtils.toObject(params.get("transConfig"), Properties.class);
        String name = properties.getProperty("name");
        String idCardNumber = properties.getProperty("idCardNumber");

        Map<String, String> reqData = new LinkedHashMap<>();
        reqData.put("action", properties.getProperty("action"));
        reqData.put("txnamt", String.valueOf((int) AmountUtils.multiply(interfaceRequest.getAmount(), 100)));
        reqData.put("merid", properties.getProperty("merid"));
        reqData.put("orderid", interfaceRequest.getInterfaceRequestID());
        reqData.put("backurl", properties.getProperty("backurl"));
        reqData.put("fronturl", properties.getProperty("fronturl"));
        reqData.put("cardno", params.get("bankCardNo"));
        if (NumberUtils.compare(interfaceRequest.getAmount(), 10000D) <= 0) {
        	reqData.put("payway", "3855");
        } else {
        	reqData.put("accname", name);
        	reqData.put("accno", idCardNumber);
        	reqData.put("payway", "3852");
        }
        logger.info("聚合支付420101 下单请求明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", interfaceRequest.getInterfaceInfoCode(), interfaceRequest.getInterfaceRequestID(), reqData);
        String s = new String(Base64.encodeBase64(JsonUtils.toJsonString(reqData).getBytes()));
        String sign = MD5Util.MD5Encode(s + properties.get("key"), "UTF-8");
        Map<String, String> reqPrams  = new HashMap<>();
        reqPrams.put("sign", sign);
        reqPrams.put("req", s);
        reqPrams.put("gateway", "submit");
        reqPrams.put("url", properties.getProperty("payUrl"));
        reqPrams.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
        logger.info("聚合支付420101 下单请求报文  接口编号:[{}],订单号:[{}],请求报文:[{}]", interfaceRequest.getInterfaceInfoCode(), interfaceRequest.getInterfaceRequestID(), reqPrams);
        return new Object[] { properties.getProperty("payUrl"), reqPrams, interfaceRequest.getInterfaceRequestID() };
	}

	/* (non-Javadoc)
	 * @see com.yl.payinterface.core.handler.InternetbankHandler#signatureVerify(com.yl.payinterface.core.bean.InternetbankSalesResponseBean, java.util.Properties)
	 */
	@Override
	public Map<String, Object> signatureVerify(InternetbankSalesResponseBean internetbankSalesResponseBean, Properties tradeConfigs) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yl.payinterface.core.handler.InternetbankHandler#complete(java.util.Map)
	 */
	@Override
	public InterfaceRequest complete(Map<String, Object> completeParameters) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yl.payinterface.core.handler.InternetbankHandler#query(com.yl.payinterface.core.bean.TradeContext)
	 */
	@Override
	public Object[] query(TradeContext tradeContext) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
}
