package com.yl.payinterface.core.handle.impl.b2c.al100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handle.impl.b2c.al100001.utils.AlPayUtils;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.HttpUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 爱农 网银支付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/7
 */
@Service("b2cAl100001Handler")
public class Al100001Handler implements InternetbankHandler {

    private static final Logger logger = LoggerFactory.getLogger(Al100001Handler.class);

    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<String, String>();
//        map.put("signMethod", "MD5");
//        map.put("version", "1.0.0");
//        map.put("txnType", "01");
//        map.put("txnSubType", "00");
//        map.put("bizType", "000000");
//        map.put("accessType", "0");
//        map.put("accessMode", "01");
//        map.put("merId", "200000000000001");
//        map.put("merOrderId", "TD201805081625226632");
//        map.put("txnTime", (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
//        map.put("txnAmt", "200");
//        map.put("currency", "CNY");
//        map.put("frontUrl", "http://kd.alblog.cn/front/callback");
//        map.put("backUrl", "http://kd.alblog.cn/front/callback");
//        map.put("payType", "0201");
//        map.put("channelId", "");
//        map.put("subject", "");
//        map.put("body", "");
        map.put("signMethod", "MD5");
        map.put("version", "1.0.0");
        map.put("txnType", "00");
        map.put("txnSubType", "01");
        map.put("merId", "200000000000001");
        map.put("merOrderId", "TD201805081625226632");
        try {
            map.put("signature", AlPayUtils.getSign(map, "88888888"));

            String res = HttpUtil.sendReq("http://180.169.129.78:38280/bas/BgTrans?" + AlPayUtils.getWebForm(map, false, false));
            Map<String, Object> resMap = AlPayUtils.getUrlParams(res);
            if ("1002".equals(resMap.get("respCode"))) {
                System.out.println("111");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Object[] trade(TradeContext tradeContext) throws BusinessException {
        Map<String, String> map = new TreeMap<String, String>();
        Map<String, String> requestParameters = tradeContext.getRequestParameters();
        Properties transConfig = JsonUtils.toObject(requestParameters.get("transConfig"), Properties.class);
        InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
        try {
            map.put("signMethod", "MD5");
            map.put("version", "1.0.0");
            map.put("txnType", "01");
            map.put("txnSubType", "00");
            map.put("bizType", "000000");
            map.put("accessType", "0");
            map.put("accessMode", "01");
            map.put("merId", transConfig.getProperty("merId"));
            map.put("merOrderId", interfaceRequest.getInterfaceRequestID());
            map.put("txnTime", (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
            map.put("txnAmt", String.valueOf((int) AmountUtils.multiply(Double.valueOf(interfaceRequest.getAmount()), 100d)));
            map.put("currency", "CNY");
            map.put("frontUrl", transConfig.getProperty("frontUrl"));
            map.put("backUrl", transConfig.getProperty("backUrl"));
            map.put("payType", "0201");
            map.put("signature", AlPayUtils.getSign(map, transConfig.getProperty("key")));
            map.put("gateway", "submit");
            map.put("url", transConfig.getProperty("payUrl"));
            map.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
            logger.info("爱农 网银支付下单报文：{}", map);
        } catch (Exception e) {
            logger.error("爱农 网银支付下单异常!异常信息:{}", e);
        }
        return new Object[]{transConfig.getProperty("payUrl"), map, interfaceRequest.getInterfaceRequestID()};
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
        InterfaceRequest queryInterfaceRequest = tradeContext.getInterfaceRequest();
        Map<String, String> requestParameters = tradeContext.getRequestParameters();
        Properties properties = JsonUtils.toObject(requestParameters.get("transConfig"), Properties.class);
        Map<String, String> map = new TreeMap<>();
        HashMap respMap = new HashMap();
        try {
            map.put("signMethod", "MD5");
            map.put("version", "1.0.0");
            map.put("txnType", "00");
            map.put("txnSubType", "01");
            map.put("merId", properties.getProperty("merId"));
            map.put("merOrderId", queryInterfaceRequest.getInterfaceOrderID());
            map.put("signature", AlPayUtils.getSign(map, properties.getProperty("key")));
            String res = HttpUtil.sendReq(properties.getProperty("url") + AlPayUtils.getWebForm(map, false, false));
            logger.info("查询响应报文：{}", res);
            Map<String, Object> resMap = AlPayUtils.getUrlParams(res);
            if ("1001".equals(resMap.get("respCode"))) {
                respMap.put("tranStat", "SUCCESS");
                respMap.put("amount", Double.valueOf(AmountUtils.divide((new Double(resMap.get("txnAmt").toString())).doubleValue(), 100.0D, 2)));
                respMap.put("orderNo", queryInterfaceRequest.getOriginalInterfaceRequestID());
                respMap.put("interfaceOrderID", queryInterfaceRequest.getOriginalInterfaceRequestID());
                respMap.put("notifyDate", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
                respMap.put("queryStatus", InterfaceTradeStatus.SUCCESS);
            } else {
                respMap.put("orderNo", queryInterfaceRequest.getOriginalInterfaceRequestID());
                respMap.put("tranStat", "UNKNOWN");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return new Object[]{respMap};
    }
}