package com.yl.payinterface.core.handle.impl.wallet.lucky100001;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.wallet.lucky100001.bean.Lucky100001PayBean;
import com.yl.payinterface.core.handle.impl.wallet.lucky100001.bean.Lucky100001QueryBean;
import com.yl.payinterface.core.handle.impl.wallet.lucky100001.bean.ResponseJDH5;
import com.yl.payinterface.core.handle.impl.wallet.lucky100001.bean.ResponseQuery;
import com.yl.payinterface.core.handle.impl.wallet.lucky100001.utils.HttpPostUtil;
import com.yl.payinterface.core.handle.impl.wallet.lucky100001.utils.Md5Util;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceRequestService;
import org.codehaus.jackson.type.TypeReference;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 维基支付 京东H5
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/10l
 */
@Service("walletLucky100001Handler")
public class Lucky100001HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Lucky100001HandlerImpl.class);

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String,String> resParams = new HashMap<String, String>();
        try {
            Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
            String interfaceRequestID = map.get("interfaceRequestID");
            String clientIp = map.get("ClientIp");
            String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
            String productName = map.get("productName");
            Lucky100001PayBean pay = new Lucky100001PayBean();
            pay.setMerchantNo(properties.getProperty("merchantNo"));
            pay.setRequestOrder(interfaceRequestID);
            pay.setUserNo("LuckyPay");
            pay.setRequestIp(clientIp);
            pay.setOrderAmount(amount);
            pay.setOrderSubject(productName);
            pay.setGoodsNo(productName);
            pay.setNotifyUrl(properties.getProperty("notifyUrl"));
            pay.setReturnInfo("LuckyPay");
            String requestSign = Md5Util.md5(properties.getProperty("key"),
                    pay.getMerchantNo(), pay.getRequestOrder(), pay.getOrderAmount(),
                    pay.getNotifyUrl(), pay.getReturnInfo());
            pay.setSign(requestSign);
            String requestJSON = JSON.toJSONString(pay);
            Map<String, String> requestMap = JSONObject.parseObject(requestJSON, new com.alibaba.fastjson.TypeReference<Map<String,String>>(){});
            logger.info("京东H5下单参数：{}", JsonUtils.toJsonString(requestMap));
            List<NameValuePair> requestList = new ArrayList<>();
            for (Map.Entry<String, String> entry : requestMap.entrySet()) {
                requestList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            String responseJSON = HttpPostUtil.execute(properties.getProperty("payUrl"), requestList);
            logger.info("京东H5返回报文：{}", responseJSON);
            ResponseJDH5 responseJDH5 = JSON.parseObject(responseJSON, ResponseJDH5.class);
            String responseSign = Md5Util.md5(properties.getProperty("key"),
                    responseJDH5.getMerchantNo(), responseJDH5.getRequestOrder(), responseJDH5.getOrderAmount(),
                    responseJDH5.getReturnInfo(), responseJDH5.getCodeUrl(),
                    responseJDH5.getResponseCode(), responseJDH5.getResponseDesc(), responseJDH5.getOrderStatus());
            if(!responseSign.equals(responseJDH5.getSign())){
                throw new RuntimeException("Lucky Pay 验签失败!");
            }
            resParams.put("code_url", responseJDH5.getCodeUrl());
            resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + responseJDH5.getCodeUrl());
            resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
            resParams.put("interfaceRequestID", interfaceRequestID);
            resParams.put("gateway", "native");
            resParams.put("merchantNo", properties.getProperty("merchantNo"));
        } catch (Exception e) {
            logger.error("下单失败！异常信息:{}", e);
        }
        return resParams;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Map<String, String> queryParams = new HashMap<String, String>();
        String interfaceRequestID = map.get("interfaceRequestID");
        try {
            Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
            Lucky100001QueryBean query = new Lucky100001QueryBean();
            query.setMerchantNo(properties.getProperty("merchantNo"));
            query.setRequestOrder(interfaceRequestID);
            query.setTradeType("PAY");
            String requestSign = Md5Util.md5(properties.getProperty("key"), query.getMerchantNo(), query.getRequestOrder());
            query.setSign(requestSign);
            Map<String, String> requestMap = JSONObject.parseObject(JsonUtils.toJsonString(query), new com.alibaba.fastjson.TypeReference<Map<String, String>>(){});
            List<NameValuePair> requestList = new ArrayList<>();
            for (Map.Entry<String, String> entry : requestMap.entrySet()) {
                requestList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            String responseJSON = HttpPostUtil.execute(properties.getProperty("query"), requestList);
            ResponseQuery responseQuery = JSON.parseObject(responseJSON, ResponseQuery.class);
            String responseSign = Md5Util.md5(properties.getProperty("key"),
                    responseQuery.getMerchantNo(), responseQuery.getRequestOrder(), responseQuery.getOrderAmount(),
                    responseQuery.getReturnInfo(), responseQuery.getResponseCode(), responseQuery.getResponseDesc(),
                    responseQuery.getOrderStatus());
            if(!responseSign.equals(responseQuery.getSign())) {
                throw new RuntimeException("Lucky Query 验签失败!");
            }
            if ("S".equals(responseQuery.getOrderStatus())) {
                queryParams.put("queryStatus", "SUCCESS");
                queryParams.put("tranStat", "0000");
            } else {
                queryParams.put("queryStatus", "UNKNOWN");
                queryParams.put("tranStat", "0001");
            }
            // 响应码
            queryParams.put("responseCode", responseQuery.getResponseCode());
            // 响应描述
            queryParams.put("responseMessage", responseQuery.getResponseDesc());
            // 金额
            queryParams.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(responseQuery.getOrderAmount()), 100d)));
            // 订单号
            queryParams.put("weChatOrderId", responseQuery.getRequestOrder());
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
        } catch (Exception e) {
            logger.error("查询订单异常！信息:{}", e);
        }
        return queryParams;
    }

    @Override
    public InterfaceRequest complete(Map<String, Object> map) {
        return null;
    }
}