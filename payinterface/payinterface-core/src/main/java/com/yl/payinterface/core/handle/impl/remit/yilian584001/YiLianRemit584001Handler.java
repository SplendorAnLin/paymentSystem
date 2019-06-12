package com.yl.payinterface.core.handle.impl.remit.yilian584001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.wallet.yilian584001.utils.RSASignature;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.CommonUtils;
import com.yl.payinterface.core.utils.FileUtils;
import com.yl.payinterface.core.utils.HttpUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author Shark
 * @Description
 * @Date 2018/3/14 16:46
 */
@Service("remitYiLian584001Handler")
public class YiLianRemit584001Handler implements RemitHandler {

    private static Logger logger = LoggerFactory.getLogger(YiLianRemit584001Handler.class);

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String partnerId = properties.getProperty("partnerId");
        String notifyUrl = properties.getProperty("notifyUrl");
        String cashType = properties.getProperty("cashType");
        String privateKayPath = properties.getProperty("privateKayPath");
        String url = properties.getProperty("url");


        String interfaceRequestId = map.get("requestNo");
        String amount = map.get("amount");
        String productName = map.get("productName");
        String accountName = map.get("accountName");
        String bankName = map.get("bankName");
        String accountNo = map.get("accountNo");
        String cerNo = map.get("cerNo");
        String bankCode = map.get("bankCode");


        Map<String, String> respMap = new HashMap<>();
        if (bankCode == null) {
            Recognition recognition = CommonUtils.recognition(accountNo);
            bankCode = recognition.getProviderCode();
        }
        if (bankCode == null || properties.getProperty(bankCode) == null) {
            respMap.put("resCode", "9999");
            respMap.put("resMsg", "该银行卡不支持");
            respMap.put("tranStat", "FAILED");
            respMap.put("requestNo", interfaceRequestId);
            respMap.put("amount", amount);
            respMap.put("compType", BusinessCompleteType.NORMAL.name());
        }
        String cnapsCode = properties.getProperty(bankCode);

        logger.info("接口请求号：{}，银行编码：{}，对应通道编码：{}", interfaceRequestId,bankCode,properties.getProperty(bankCode));


        SortedMap<String, String> reqMap = new TreeMap<>();
        reqMap.put("inputCharset", "1");
        reqMap.put("partnerId", partnerId);
        reqMap.put("notifyUrl", notifyUrl);
        reqMap.put("orderNo", interfaceRequestId);
        reqMap.put("orderAmount", String.valueOf((int) AmountUtils.multiply(Double.valueOf(amount), 100)));
        reqMap.put("cashType", cashType);
        reqMap.put("orderCurrency", "156");
        reqMap.put("accountName", accountName);
        reqMap.put("bankName", bankName);
        reqMap.put("bankCardNo", accountNo);
        reqMap.put("canps", cnapsCode);
        reqMap.put("idCard", cerNo);

        Map<String, String> resMap;

        try {
            String key = FileUtils.readTxtFile(new File(privateKayPath));

            reqMap.put("signMsg", URLEncoder.encode(RSASignature.createSign(reqMap, key), "utf-8"));
            reqMap.put("signType", "1");

            StringBuffer xml = new StringBuffer();
            for (String mapKey : reqMap.keySet()) {
                xml.append(mapKey).append("=").append(reqMap.get(mapKey)).append("&");
            }
            logger.info("接口请求号：{}，亿联代付请求报文：{}", interfaceRequestId, xml.toString());
            String returnXml = HttpUtils.sendReq2(url, xml.substring(0, xml.length() - 1), "POST");
            logger.info("接口请求号：{}，亿联代付返回报文：{}", interfaceRequestId, returnXml);
            resMap = JsonUtils.toObject(returnXml, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            logger.info("接口请求号：{}，亿联代付请求报错：{}", interfaceRequestId, e);
            throw new RuntimeException(e.getMessage());
        }


        // 如果返回值不是0000则失败。
        if (!"0000".equals(resMap.get("errCode"))) {
            respMap.put("resCode", resMap.get("errCode"));
            respMap.put("resMsg", resMap.get("errMsg"));
            respMap.put("tranStat", "FAILED");
            respMap.put("requestNo", interfaceRequestId);
            respMap.put("amount", amount);
            respMap.put("compType", BusinessCompleteType.NORMAL.name());
        } else {
            respMap.put("requestNo", interfaceRequestId);
            respMap.put("amount", amount);
            respMap.put("tranStat", "UNKNOWN");
        }

        return respMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});

        String partnerId = properties.getProperty("partnerId");
        String requestNo = map.get("requestNo");
        String privateKayPath = properties.getProperty("privateKayPath");
        String url = properties.getProperty("queryUrl");

        SortedMap<String, String> reqMap = new TreeMap<>();
        reqMap.put("inputCharset", "1");
        reqMap.put("partnerId", partnerId);
        reqMap.put("orderNo", requestNo);
        Map<String, String> resMap;

        try {
            String key = FileUtils.readTxtFile(new File(privateKayPath));

            reqMap.put("signMsg", URLEncoder.encode(RSASignature.createSign(reqMap, key), "utf-8"));
            reqMap.put("signType", "1");

            StringBuffer xml = new StringBuffer();
            for (String mapKey : reqMap.keySet()) {
                xml.append(mapKey).append("=").append(reqMap.get(mapKey)).append("&");
            }
            logger.info("接口请求号：{}，亿联代付查询请求报文：{}", requestNo, xml.toString());
            String returnXml = HttpUtils.sendReq2(url, xml.substring(0, xml.length() - 1), "POST");
            logger.info("接口请求号：{}，亿联代付查询返回报文：{}", requestNo, returnXml);
            resMap = JsonUtils.toObject(returnXml, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            logger.info("接口请求号：{}，亿联代付查询请求报错：{}", requestNo, e);
            throw new RuntimeException(e.getMessage());
        }
        Map<String, String> respMap = new HashMap<>();

        if ("0000".equals(resMap.get("errCode"))) {
            if ("3".equals(resMap.get("payResult"))) {
                respMap.put("resCode", resMap.get("errCode"));
                respMap.put("resMsg", "付款成功");
                respMap.put("tranStat", "SUCCESS");
                respMap.put("requestNo", requestNo);
                respMap.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(resMap.get("orderAmount")), 100d)));
                respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                respMap.put("interfaceOrderID", resMap.get("paymentOrderId"));
            } else if ("4".equals(resMap.get("payResult"))) {
                respMap.put("resCode", resMap.get("errCode"));
                respMap.put("resMsg", resMap.get("errMsg"));
                respMap.put("tranStat", "FAILED");
                respMap.put("requestNo", requestNo);
                respMap.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(resMap.get("orderAmount")), 100d)));
                respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                respMap.put("interfaceOrderID", resMap.get("paymentOrderId"));
            } else {
                respMap.put("requestNo", requestNo);
                respMap.put("amount", map.get("amount"));
                respMap.put("tranStat", "UNKNOWN");
            }
        } else {
            respMap.put("requestNo", requestNo);
            respMap.put("amount", map.get("amount"));
            respMap.put("tranStat", "UNKNOWN");
        }

        return respMap;
    }
}
