package com.yl.payinterface.core.handle.impl.b2c.yw440301;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.handle.impl.b2c.yw440301.utils.signUtils;
import com.yl.payinterface.core.handle.impl.remit.hs100001.utils.HttpUtil;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 伊蚊 个人网银
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/2
 */
@Service("b2cYw440301Handler")
public class Yw440301Handler implements InternetbankHandler {

    private static final Logger logger = LoggerFactory.getLogger(Yw440301Handler.class);

    public static void main(String[] args) {
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("service", "placeQuickPay");
            req.put("inputCharset", "UTF-8");
            req.put("sysMerchNo", "152018062700074");
            req.put("outOrderNo", "TD-" + System.currentTimeMillis());
            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("orderAmt", "100.50");
            req.put("orderTitle", "测试");
            req.put("orderDetail", "测试");
            req.put("frontUrl", "https://kd.alblog.cn/front/callback");
            req.put("backUrl", "https://kd.alblog.cn/front/callback");
            req.put("settleCycle", "T1");
            req.put("selectFinaCode", "ICBC");
            req.put("tranAttr", "DEBIT");
            req.put("tranSubAttr", "FRONT");
            req.put("userId", "C100000");
            req.put("clientIp", "192.168.0.1");
            req.put("sign", signUtils.getUrlParamsByMap(req, "c716ef778fb516f854ca22b0047374ea"));
            req.put("signType", "MD5");



//            req.put("service", "ebankPay");
//            req.put("inputCharset", "UTF-8");
//            req.put("sysMerchNo", "152018062700074");
//            req.put("outOrderNo", "TD-" + System.currentTimeMillis());
//            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//            req.put("orderAmt", "100.50");
//            req.put("orderTitle", "测试");
//            req.put("orderDetail", "测试");
//            req.put("clientIp", "192.168.0.1");
//            req.put("frontUrl", "https://kd.alblog.cn/front/callback");
//            req.put("backUrl", "https://kd.alblog.cn/front/callback");
//            req.put("selectFinaCode", "ICBC");
//            req.put("tranAttr", "DEBIT");
//            req.put("settleCycle", "T1");
//            req.put("sign", signUtils.getUrlParamsByMap(req, "c716ef778fb516f854ca22b0047374ea"));
//            req.put("signType", "MD5");
//            req.put("orderTitle", URLEncoder.encode("测试", "UTF-8"));
//            req.put("orderDetail", URLEncoder.encode("测试", "UTF-8"));
            String res = HttpUtil.httpRequest("http://47.104.25.95:8083/trade/api/placeQuickPay", req, "POST", "UTF-8");
//            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://47.104.25.95:8083/trade/api/ebankPay", req, true, "UTF-8", 300000);
            System.out.println(res);
            System.out.println(JsonUtils.toJsonString(req));
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Object[] trade(TradeContext tradeContext) throws BusinessException {
        InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
        Map<String, String> params = tradeContext.getRequestParameters();
        Map<String, String> req = new TreeMap<>();
        Map<String, String> res = new HashMap<>();
        Properties properties = JsonUtils.toObject(params.get("transConfig"), Properties.class);
        String interfaceRequestID = interfaceRequest.getInterfaceRequestID();
        String amount =  String.valueOf(interfaceRequest.getAmount());
        String providerCode = params.get("providerCode");
        try {
            req.put("service", "ebankPay");
            req.put("inputCharset", "UTF-8");
            req.put("sysMerchNo", properties.getProperty("sysMerchNo"));
            req.put("outOrderNo", interfaceRequestID);
            req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("orderAmt", amount);
            req.put("orderTitle", "ces");
            req.put("orderDetail", "ces");
            req.put("clientIp", "192.168.0.1");
            req.put("frontUrl", properties.getProperty("frontUrl"));
            req.put("backUrl", properties.getProperty("backUrl"));
            req.put("selectFinaCode", providerCode);
            req.put("tranAttr", "DEBIT");
            req.put("settleCycle", "T1");
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            req.put("signType", "MD5");
            logger.info("伊蚊个人网银下单报文：{}", req);
            String resString = HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8");
            logger.info("伊蚊个人网银下单返回报文：{}", resString);
            Map<String, Object> resPar = JsonUtils.toObject(resString, new TypeReference<Map<String, Object>>() {
            });
            if ("0000".equals(resPar.get("retCode"))) {
                res.put("responseCode", "0000");
                res.put("orderNo", interfaceRequestID);
                res.put("gateway", "htmlSubmit");
                res.put("interfaceRequestID", interfaceRequestID);
                res.put("html", resPar.get("autoSubmitForm").toString());
            }
        } catch (Exception e) {
            logger.error("伊蚊个人网银异常!异常信息:{}", e);
        }
        return new Object[]{properties.getProperty("url"), res, interfaceRequest.getInterfaceRequestID()};
    }

    @Override
    public Map<String, Object> signatureVerify(InternetbankSalesResponseBean internetbankSalesResponseBean, Properties tradeConfigs) {
        return null;
    }

    @Override
    public InterfaceRequest complete(Map<String, Object> completeParameters) throws BusinessException {
        return null;
    }

    @Override
    public Object[] query(TradeContext tradeContext) throws BusinessException {
        return new Object[0];
    }
}