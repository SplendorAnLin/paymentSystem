package com.yl.payinterface.core.handle.impl.remit.yw440301;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.yw440301.utils.HttpUtil;
import com.yl.payinterface.core.handle.impl.remit.yw440301.utils.signUtils;
import com.yl.payinterface.core.handler.RemitHandler;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 伊蚊 代付
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/11
 */
@Service("remitYw440301Handler")
public class Yw440301HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Yw440301HandlerImpl.class);

    public static void main(String[] args) {
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("service", "applyAgentPay");
            req.put("inputCharset", "UTF-8");
            req.put("sysMerchNo", "152018070400098");
            req.put("outOrderNo", "DF-" + System.currentTimeMillis());
            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("finaCode", "ICBC");
            req.put("payeeAcct", "6215583202002031321");
            req.put("payeeName", "周林");
            req.put("applyAmt", "10.00");
            req.put("payeeAcctAttr", "PRIVATE");
            req.put("bankName", "中国工商银行");
            req.put("bankProvince", "湖北");
            req.put("bankCity", "武汉");
            req.put("applyReason", "测试");
            req.put("sign", signUtils.getUrlParamsByMap(req, "9e00b53f3b0b2a586cfab6b127f938d5"));
            req.put("signType", "MD5");
            String res = HttpUtil.httpRequest("http://www.51lunapay.com/trade/api/applyAgentPay", req, "POST", "UTF-8");
            System.out.println(res);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestId = map.get("requestNo");
        String accountNo = map.get("accountNo");
        String accountName = map.get("accountName");
        String bankCode = map.get("bankCode");
        String bankName = map.get("bankName");
        String amount = String.valueOf(map.get("amount"));
        Map<String, String> resMap = new HashMap<>();
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("service", "applyAgentPay");
            req.put("inputCharset", "UTF-8");
            req.put("sysMerchNo", properties.getProperty("sysMerchNo"));
            req.put("outOrderNo", interfaceRequestId);
            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("finaCode", bankCode);
            req.put("payeeAcct", accountNo);
            req.put("payeeName", accountName);
            req.put("applyAmt", amount);
            req.put("payeeAcctAttr", "PRIVATE");
            req.put("bankName", bankName);
            req.put("bankProvince", "湖北");
            req.put("bankCity", "武汉");
            req.put("applyReason", "结算款");
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            req.put("signType", "MD5");
            logger.info("伊蚊代付下单报文：{}", req);
            String res = HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8");
            logger.info("伊蚊代付下单返回报文：{}", res);
            resMap.put("tranStat", "UNKNOWN");
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resCode", "");
            resMap.put("resMsg", "");
        } catch (Exception e) {
            logger.error("伊蚊代付异常!异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String interfaceRequestId = map.get("requestNo");
        Map<String,String> respMap = new HashMap<>();
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("service", "queryAgentPay");
            req.put("inputCharset", "UTF-8");
            req.put("sysMerchNo", properties.getProperty("sysMerchNo"));
            req.put("outOrderNo", interfaceRequestId);
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            req.put("signType", "MD5");
            logger.info("伊蚊代付查询报文：{}", req);
            String res = HttpUtil.httpRequest(properties.getProperty("query"), req, "POST", "UTF-8");
            logger.info("伊蚊代付查询返回报文：{}", res);
            Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if ("0000".equals(resPar.get("retCode"))) {
                if ("02".equals(resPar.get("orderStatus")) || "06".equals(resPar.get("orderStatus"))) {
                    respMap.put("resCode", resPar.get("orderStatus"));
                    respMap.put("resMsg", resPar.get("retMsg"));
                    respMap.put("tranStat", "FAILED");
                    respMap.put("requestNo", interfaceRequestId);
                    respMap.put("amount", resPar.get("tranAmt"));
                    respMap.put("interfaceOrderID", resPar.get("tranNo"));
                } else if ("05".equals(resPar.get("orderStatus"))) {
                    respMap.put("resCode", "0000");
                    respMap.put("resMsg", "付款成功");
                    respMap.put("tranStat", "SUCCESS");
                    respMap.put("requestNo", interfaceRequestId);
                    respMap.put("amount", resPar.get("tranAmt"));
                    respMap.put("interfaceOrderID", resPar.get("tranNo"));
                } else {
                    respMap.put("requestNo", interfaceRequestId);
                    respMap.put("amount", resPar.get("tranAmt"));
                    respMap.put("tranStat", "UNKNOWN");
                }
            }
        } catch (Exception e) {
            logger.error("伊蚊代付查询异常!异常信息:{}", e);
        }
        return respMap;
    }
}