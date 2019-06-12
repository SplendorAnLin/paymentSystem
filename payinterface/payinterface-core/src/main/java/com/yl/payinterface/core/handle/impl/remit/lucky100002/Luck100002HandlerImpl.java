package com.yl.payinterface.core.handle.impl.remit.lucky100002;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.lucky100002.bean.RequestDefrayD0;
import com.yl.payinterface.core.handle.impl.remit.lucky100002.bean.ResponseDefrayD0;
import com.yl.payinterface.core.handle.impl.remit.lucky100002.utils.HttpPostUtil;
import com.yl.payinterface.core.handle.impl.remit.lucky100002.utils.Md5Util;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 维基D0代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/23
 */
@Service("remitLuck100002Handler")
public class Luck100002HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Luck100002HandlerImpl.class);

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        Map<String, String> resMap = new HashMap<>();
        try {
            String bankName = map.get("bankName");
            String accountNo = map.get("accountNo");
            String accountName = map.get("accountName");
            String interfaceRequestId = map.get("requestNo");
            String amount = map.get("amount");
            String cerNo = map.get("cerNo");
            String remitAmount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d));
            String customerNo = map.get("customerNo");
            String remark = map.get("remark");
            Recognition recognition = CommonUtils.recognition(accountNo);
            String bankCode = recognition.getProviderCode();
            if (StringUtils.isBlank(bankCode)) {
                resMap.put("resCode", "9999");
                resMap.put("resMsg", "该银行卡不支持");
                resMap.put("tranStat", "FAILED");
                resMap.put("requestNo", interfaceRequestId);
                resMap.put("amount", amount);
                resMap.put("compType", BusinessCompleteType.NORMAL.name());
            }
            String cardType = "CC";
            if ("DEBIT".equals(map.get("cardType"))) {
                cardType = "DC";
            }
            RequestDefrayD0 df = new RequestDefrayD0();
            df.setMerchantNo(properties.getProperty("merchantNo"));
            df.setRequestOrder(interfaceRequestId);
            df.setUserNo(customerNo);
            df.setRequestIp(properties.getProperty("ip"));
            df.setOrderAmount(remitAmount);
            df.setBankCode(bankCode);
            df.setCardType(cardType);
            df.setCardNo(accountNo);
            df.setHolderName(accountName);
            df.setHolderIdNo(cerNo);
            df.setHolderMobile(properties.getProperty("phone"));
            df.setOrderSubject(remark);
            df.setNotifyUrl(properties.getProperty("notifyUrl"));
            String requestSign = Md5Util.md5(properties.getProperty("key"),
                    df.getMerchantNo(), df.getRequestOrder(), df.getOrderAmount(),
                    df.getBankCode(), df.getCardType(), df.getCardNo(),
                    df.getHolderName(), df.getHolderIdNo(), df.getHolderMobile(),
                    df.getNotifyUrl());
            df.setSign(requestSign);
            String requestJSON = JSON.toJSONString(df);
            logger.info("维基D0代付下单参数：{}", requestJSON);
            Map<String, String> requestMap = JSONObject.parseObject(requestJSON, new com.alibaba.fastjson.TypeReference<Map<String, String>>() {
            });
            List<NameValuePair> requestList = new ArrayList<>();
            for (Map.Entry<String, String> entry : requestMap.entrySet()) {
                requestList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            String responseJSON = HttpPostUtil.execute(properties.getProperty("remit"), requestList);
            logger.info("维基D0返回报文：{}", responseJSON);
            ResponseDefrayD0 responseDefrayD0 = JSON.parseObject(responseJSON, ResponseDefrayD0.class);
            String responseSign = Md5Util.md5(properties.getProperty("key"),
                    responseDefrayD0.getMerchantNo(), responseDefrayD0.getRequestOrder(), responseDefrayD0.getOrderAmount(),
                    responseDefrayD0.getReturnInfo(), responseDefrayD0.getResponseCode(), responseDefrayD0.getResponseDesc(),
                    responseDefrayD0.getOrderStatus());
            if (!responseSign.equals(responseDefrayD0.getSign())) {
                throw new Exception("维基D0代付验签失败！");
            }
            if (!"0000".equals(responseDefrayD0.getResponseCode())) {
                resMap.put("tranStat", "FAILED");
            } else {
                resMap.put("tranStat", "UNKNOWN");
            }
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resCode", responseDefrayD0.getResponseCode());
            resMap.put("resMsg", responseDefrayD0.getResponseDesc());
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }
}