package com.yl.payinterface.core.handle.impl.remit.zc100001;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.yw440301.utils.HttpUtil;
import com.yl.payinterface.core.handle.impl.remit.zc100001.utils.signUtils;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.utils.MD5Util;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * 中辰 代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/18
 */
@Service("zc100001Handler")
public class Zc100001HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Zc100001HandlerImpl.class);

    @Resource
    private InterfaceRequestService interfaceRequestService;

    public static void main(String[] args) {
        Map<String, String> req = new TreeMap<>();
        try {
//            req.put("version", "V2.0");
//            req.put("merchantId", "9962987698");
//            req.put("orderId", "DF-" + System.currentTimeMillis());
//            req.put("bankCode", "ICBC");
//            req.put("accNo", "6215583202002031321");
//            req.put("accNm", "周林");
//            req.put("transAmt", "300.00");
            req.put("version", "V2.0");
            req.put("merchantId", "9962987698");
            req.put("busType", "02");
            req.put("orderId", "TD20180718101473335904");
            req.put("signType", "MD5");
            req.put("sign", signUtils.getUrlParamsByMap(req, "1dc79f548e5f4e8ba29b8bbda5629f02"));
            String res = HttpUtil.httpRequest("http://pay.iiapx.pw/query/gateway", req, "POST", "UTF-8");
//            String res = HttpUtil.httpRequest("http://pay.iiapx.pw/transfer/gateway", req, "POST", "UTF-8");
            System.out.println(res);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        Map<String, String> resMap = new HashMap<>();
        String interfaceRequestId = map.get("requestNo");
        String accountNo = map.get("accountNo");
        String accountName = map.get("accountName");
        String bankCode = map.get("bankCode");
        String amount = map.get("amount");
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("version", "V2.0");
            req.put("merchantId", properties.getProperty("merchantId"));
            req.put("orderId", interfaceRequestId);
            req.put("bankCode", bankCode);
            req.put("accNo", accountNo);
            req.put("accNm", accountName);
            req.put("transAmt", amount);
            req.put("signType", "MD5");
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            logger.info("中辰代付请求参数：{}", req);
            String res = HttpUtil.httpRequest(properties.getProperty("transfer"), req, "POST", "UTF-8");
            logger.info("中辰代付请求返回参数：{}", res);
            resMap.put("tranStat", "UNKNOWN");
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resCode", "");
            resMap.put("resMsg", "");
        } catch (Exception e) {
            logger.error("中辰代付异常!异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String interfaceRequestId = map.get("requestNo");
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestId);
        Map<String,String> respMap = new HashMap<>();
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("version", "V2.0");
            req.put("merchantId", properties.getProperty("merchantId"));
            req.put("busType", "02");
            req.put("orderId", interfaceRequestId);
            req.put("signType", "MD5");
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            logger.info("中辰代付查询请求参数：{}", req);
            String res = HttpUtil.httpRequest(properties.getProperty("query"), req, "POST", "UTF-8");
            logger.info("中辰代付查询请求返回参数：{}", res);
            Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if ("0".equals(resPar.get("respCode"))) {
                if ("1".equals(resPar.get("transStatus"))) {
                    respMap.put("resCode", "0000");
                    respMap.put("resMsg", "付款成功");
                    respMap.put("tranStat", "SUCCESS");
                    respMap.put("requestNo", interfaceRequestId);
                    respMap.put("amount", resPar.get("orderAmt"));
                    respMap.put("interfaceOrderID", resPar.get("orderId"));
                } else if ("0".equals(resPar.get("transStatus")) || "3".equals(resPar.get("transStatus"))) {
                    respMap.put("requestNo", interfaceRequestId);
                    respMap.put("amount", resPar.get("tranAmt"));
                    respMap.put("tranStat", "UNKNOWN");
                } else {
                    respMap.put("resCode", resPar.get("respCode"));
                    respMap.put("resMsg", resPar.get("respDesc"));
                    respMap.put("tranStat", "FAILED");
                    respMap.put("requestNo", interfaceRequestId);
                    respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
                    respMap.put("interfaceOrderID", interfaceRequestId);
                }
            } else {
                respMap.put("resCode", resPar.get("respCode"));
                respMap.put("resMsg", resPar.get("respDesc"));
                respMap.put("tranStat", "FAILED");
                respMap.put("requestNo", interfaceRequestId);
                respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
                respMap.put("interfaceOrderID", interfaceRequestId);
            }
        } catch (Exception e) {
            logger.error("中辰代付查询异常!异常信息:{}", e);
        }
        return respMap;
    }
}