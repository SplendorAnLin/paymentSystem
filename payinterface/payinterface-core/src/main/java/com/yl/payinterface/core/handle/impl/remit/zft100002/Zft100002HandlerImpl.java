package com.yl.payinterface.core.handle.impl.remit.zft100002;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.zft100002.utils.ZftSignUtils;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.Base64Utils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 支付通 代笔代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/8
 */
@Service("remitZft100002Handler")
public class Zft100002HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Zft100002HandlerImpl.class);

    public static void main(String[] args) {
        Map<String, String> req = new TreeMap<>();
        try {
            req.put("service", "payForAnotherOne");
            req.put("merchantNo", "CX0003924");
            req.put("orderNo", "DF-" + System.currentTimeMillis());
            req.put("version", "V2.0");
            req.put("accountProp", "1");
            req.put("accountNo", Base64Utils.encode("6215583202002031321".getBytes()));
            req.put("accountName", Base64Utils.encode("周林".getBytes()));
            req.put("bankGenneralName", "工商银行");
            req.put("bankName", "武汉东西湖开发区支行");
            req.put("bankCode", "ICBC");
            req.put("currency", "CNY");
            req.put("bankProvcince", "湖北省");
            req.put("bankCity", "武汉市");
            req.put("orderAmount", "200");
            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("signType", "1");
            req.put("tel", "17607114151");
            req.put("cause", "供应商结算款");
            req.put("orderSource", "1");
            req.put("sign", ZftSignUtils.getUrlParamsByMap(req, "c40893797ae24b9395a40d2b7da2391d"));
            logger.info("支付通代付下单报文：{}", req);
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://139.129.206.79:29606/PayApi/agentPay", req, false, "UTF-8", 300000);
            Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            logger.info("支付通代付返回报文：{}", resPar);

        } catch (Exception e) {
            logger.error("支付通代笔代付异常!异常信息:{}", e);
        }
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestId = map.get("requestNo");
        String accountNo = map.get("accountNo");
        String accountName = map.get("accountName");
        String bankName = map.get("bankName");
        String bankCode = map.get("bankCode");
        String accType = map.get("accountType");
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
        Map<String, String> req = new TreeMap<>();
        Map<String, String> resMap = new HashMap<>();
        try {
            req.put("service", "payForAnotherOne");
            req.put("merchantNo", properties.getProperty("merchantNo"));
            req.put("orderNo", interfaceRequestId);
            req.put("version", "V2.0");
            req.put("accountProp", "1");
            if ("OPEN".equals(accType)) {
                req.put("accountProp", "2");
            }
            req.put("accountNo", Base64Utils.encode(accountNo.getBytes("UTF-8")));
            req.put("accountName", Base64Utils.encode(accountName.getBytes("UTF-8")));
            req.put("bankGenneralName", bankName);
            req.put("bankName", bankName);
            req.put("bankCode", bankCode);
            req.put("currency", "CNY");
            req.put("bankProvcince", "湖北省");
            req.put("bankCity", "武汉市");
            req.put("orderAmount", amount);
            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("signType", "1");
            req.put("sign", ZftSignUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            logger.info("支付通代付下单报文：{}", req);
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("url"), req, false, "UTF-8", 300000);
            Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            logger.info("支付通代付返回报文：{}", resPar);
            resMap.put("tranStat", "UNKNOWN");
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resCode", resPar.get("dealCode"));
            resMap.put("resMsg", resPar.get("dealMsg"));
        } catch (Exception e) {
            logger.error("支付通代笔代付异常!异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }
}