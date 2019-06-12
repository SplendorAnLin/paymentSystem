package com.yl.payinterface.core.handle.impl.auth.ylzf420101;

import java.net.URLEncoder;
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
import com.yl.payinterface.core.handler.AuthPayHandler;
import com.yl.payinterface.core.utils.MD5Util;

@Service("ylzf420101Handler")
public class Ylzf420101Handler implements AuthPayHandler {

    private static Logger logger = LoggerFactory.getLogger(Ylzf420101Handler.class);

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        return null;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        return sale(map);
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {

        // 获取交易配置
        Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), Properties.class);
        String name = properties.getProperty("name");
        String idCardNumber = properties.getProperty("idCardNumber");

        Map<String, String> reqData = new LinkedHashMap<>();
        reqData.put("action", properties.getProperty("action"));
        reqData.put("txnamt", String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100)));
        reqData.put("merid", properties.getProperty("merid"));
        reqData.put("orderid", map.get("interfaceRequestID"));
        reqData.put("backurl", properties.getProperty("backurl"));
        reqData.put("fronturl", properties.getProperty("fronturl"));
        reqData.put("accname", map.get("accountName"));
        reqData.put("accno", map.get("accountNo"));
        reqData.put("cardno", map.get("cardNo"));
        if (NumberUtils.compare(Double.valueOf(map.get("amount")), 10000D) < 0) {
        	reqData.put("payway", "3855");
        } else {
        	reqData.put("accname", name);
        	reqData.put("accno", idCardNumber);
        	reqData.put("payway", "3852");
        }
        logger.info("聚合支付420101 下单请求明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),JsonUtils.toJsonString(reqData));
        String s = new String(Base64.encodeBase64(JsonUtils.toJsonString(reqData).getBytes()));
        String sign = MD5Util.MD5Encode(s + properties.get("key"), "UTF-8");
        Map<String, String> reqPrams  = new HashMap<>();
        reqPrams.put("sign", sign);
        reqPrams.put("req", s);
        reqPrams.put("gateway", "submit");
        reqPrams.put("url", properties.getProperty("payUrl"));
        reqPrams.put("interfaceRequestID", map.get("interfaceRequestID"));
        logger.info("聚合支付420101 下单请求报文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),reqPrams);
        return reqPrams;
    }

    @Override
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




    /////////////////////////             测试               ////////////////////////////

    public static void main(String[] args) {

        pay();

//        query();

    }


    public static void pay(){
        Map<String, Object> reqData = new LinkedHashMap<>();
        reqData.put("action", "goAndPay");
        reqData.put("txnamt", 180000);
        reqData.put("merid", "89907454");
        reqData.put("orderid", com.yl.payinterface.core.utils.CodeBuilder.getOrderIdByUUId());
        reqData.put("backurl", "https://baidu.com");
        reqData.put("fronturl", "https://baidu.com");
        reqData.put("accname", null);
        reqData.put("accno", null);
        reqData.put("cardno", "6215593202011661439");

        try {
            String s = new String(Base64.encodeBase64(JsonUtils.toJsonString(reqData).getBytes()));
            String sign = MD5Util.MD5Encode(s + "296e34eb77d7e941b474685bfb6e772e", "UTF-8");
            String reqInfo = "req=" + URLEncoder.encode(s,"UTF-8") + "&sign=" + sign;

            System.out.println("req加密：" + URLEncoder.encode(s,"UTF-8"));

            System.out.println("sign：" + sign);

            System.out.println("聚合支付420101加密请求信息：" + reqInfo);

            Map<String, String> reqMap = new LinkedHashMap<>();
            reqMap.put("req", URLEncoder.encode(s,"UTF-8"));
            reqMap.put("sign", sign);

//            String respStr = HttpUtils.sendReq("https://api.zhifujiekou.com/apis/gateway", reqInfo, "POST");

            String respStr = HttpClientUtils.send(HttpClientUtils.Method.POST,"https://api.zhifujiekou.com/apis/gateway", reqMap);

            System.out.println("聚合支付420101响应信息：" + respStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void query(){

        Map<String, String> resParams;

        try {

            Map<String, Object> reqData = new LinkedHashMap<>();
            reqData.put("merid", "89907454");
            reqData.put("queryid", "");
            reqData.put("orderid", "100016872060");

            Map<String, String> params = new LinkedHashMap<>();
            params.put("req", new String(Base64.encodeBase64(JsonUtils.toJsonString(reqData).getBytes())));
            params.put("sign", MD5Util.MD5Encode(params.get("req") + "296e34eb77d7e941b474685bfb6e772e", "UTF-8"));

            System.out.println("聚合支付420101请求信息：" + JsonUtils.toJsonString(reqData));
            System.out.println("聚合支付420101加密请求信息：" + JsonUtils.toJsonString(params));

            String respStr = HttpClientUtils.send(HttpClientUtils.Method.POST,"https://api.zhifujiekou.com/api/query", params);
            System.out.println("聚合支付420101加密响应信息" + respStr);

            Map<String, String> respData = JsonUtils.toObject(EncodingUtils.getString(Base64.decodeBase64(JsonUtils.toObject(respStr, new TypeReference<Map<String, String>>() {
            }).get("resp")), "UTF-8"), new TypeReference<Map<String, String>>() {});

            System.out.println("MD5：" + MD5Util.MD5Encode( JsonUtils.toObject(respStr, new TypeReference<Map<String, String>>() {}).get("resp") + "296e34eb77d7e941b474685bfb6e772e", "UTF-8"));

            System.out.println(JsonUtils.toJsonString(respData));

            resParams = new HashMap<>();
            if(respData.get("respcode").equals("00") && respData.get("resultcode").equals("0000")){
                resParams.put("interfaceRequestID", respData.get("orderid"));
                resParams.put("tranStat", "SUCCESS");
                resParams.put("amount", respData.get("txnamt"));
                resParams.put("responseCode", respData.get("resultcode"));
                resParams.put("responseMsg", respData.get("resultmsg"));
                resParams.put("interfaceOrderID", respData.get("queryid"));
            }else {
                resParams.put("tranStat", "UNKNOWN");
            }
            System.out.println("最终："+JsonUtils.toJsonString(resParams));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
