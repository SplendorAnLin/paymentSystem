package com.yl.payinterface.core.handle.impl.remit.hq350002;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.hq350002.utils.HQUtils;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceRequestService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 环球付代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/24
 */
@Service("hq350002Handler")
public class Hq350002HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Hq350002HandlerImpl.class);

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        Map<String, String> resMap = new HashMap<>();
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("requestNo"));
        try {
            String accountNo = map.get("accountNo");
            String accountName = map.get("accountName");
            String interfaceRequestId = map.get("requestNo");
            String amount = map.get("amount");
            Map<String, String> transMap = new HashMap<>();
            transMap.put("user_pid", properties.getProperty("partner"));
            transMap.put("user_seller", properties.getProperty("seller"));
            transMap.put("order_no", interfaceRequestId);
            transMap.put("money", amount);
            transMap.put("bank_no", accountNo);
            transMap.put("name", accountName);
            transMap.put("sign", HQUtils.getSign(transMap, properties.getProperty("key")));
            logger.info("环球付代付下单报文：{}", transMap);
            String res = HQUtils.unicodeToString(HQUtils.httpPost(properties.getProperty("url"), HQUtils.getUrlParamsByMap(transMap)));
            logger.info("环球付代付返回报文：{}", res);
            Map<String, String> reMap = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if (!"0".equals(reMap.get("code"))) {
                resMap.put("tranStat", "FAILED");
            } else {
                resMap.put("tranStat", "UNKNOWN");
            }
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resCode", reMap.get("code"));
            resMap.put("resMsg", reMap.get("msg"));
            interfaceRequest.setInterfaceOrderID(reMap.get("order_no"));
            interfaceRequestService.modify(interfaceRequest);
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("requestNo"));
        Map<String, String> respMap = new HashMap<>();
        Map<String, String> reqMap = new HashMap<>();
        try {
            reqMap.put("order_no", interfaceRequest.getInterfaceOrderID());
            reqMap.put("user_pid", properties.getProperty("partner"));
            reqMap.put("user_seller", properties.getProperty("seller"));
            reqMap.put("sign", HQUtils.getQuerySign(reqMap, properties.getProperty("key")));
            logger.info("环球付代付查询报文：{}", reqMap);
            String res = HQUtils.unicodeToString(HQUtils.httpPost(properties.getProperty("queryUrl"), HQUtils.getUrlParamsByMap(reqMap)));
            logger.info("环球付代付查询返回报文：{}", res);
            Map<String, String> reMap = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if ("2001".equals(reMap.get("code"))) {
                respMap.put("resCode", "0000");
                respMap.put("resMsg", "付款成功");
                respMap.put("tranStat", "SUCCESS");
                respMap.put("requestNo", interfaceRequest.getInterfaceRequestID());
                respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
                respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                respMap.put("interfaceOrderID", interfaceRequest.getInterfaceOrderID());
            } else if ("2003".equals(reMap.get("code"))) {
                respMap.put("requestNo", interfaceRequest.getInterfaceRequestID());
                respMap.put("amount", map.get("amount"));
                respMap.put("tranStat", "UNKNOWN");
                respMap.put("requestNo", interfaceRequest.getInterfaceRequestID());
                respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
                respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                respMap.put("interfaceOrderID", interfaceRequest.getInterfaceOrderID());
            } else {
                respMap.put("requestNo", interfaceRequest.getInterfaceRequestID());
                respMap.put("amount", map.get("amount"));
                respMap.put("tranStat", "FAILED");
                respMap.put("resCode", reMap.get("code"));
                respMap.put("resMsg", reMap.get("msg"));
                respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
                respMap.put("interfaceOrderID", interfaceRequest.getInterfaceOrderID());
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return respMap;
    }
}