package com.yl.payinterface.core.handle.impl.b2c.shand100001.utils;

import com.alibaba.fastjson.JSON;
import com.yl.payinterface.core.handle.impl.b2c.shand100001.request.SandpayRequest;
import com.yl.payinterface.core.handle.impl.b2c.shand100001.response.SandpayResponse;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class SandpayClient {

    private static Logger logger = LoggerFactory.getLogger(SandpayClient.class);
    private static int connectTimeout = 3000;
    private static int readTimeout = 15000;

    public SandpayClient() {
    }

    public static <T extends SandpayResponse> T execute(SandpayRequest<T> req, String serverUrl) throws Exception {
        return execute(req, serverUrl, connectTimeout, readTimeout);
    }

    public static <T extends SandpayResponse> T execute(SandpayRequest<T> request, String serverUrl, int connectTimeout, int readTimeout) throws Exception {
        Map<String, String> reqMap = new HashMap();
        String reqData = JSON.toJSONString(request);
        String reqSign = new String(Base64.encodeBase64(CryptoUtil.digitalSign(reqData.getBytes(), CertUtil.getPrivateKey(), "SHA1WithRSA")));
        reqMap.put("charset", "UTF-8");
        reqMap.put("data", reqData);
        reqMap.put("signType", "01");
        reqMap.put("sign", reqSign);
        reqMap.put("extend", "");
        logger.info("[sandpay][{}][req]==>{}", request.getTxnDesc(), reqMap);
        String result = ShandHttpClient.doPost(serverUrl, reqMap, connectTimeout, readTimeout);
//        String result = HttpClientUtils.send(HttpClientUtils.Method.POST, serverUrl, reqMap, true, "UTF-8", 30000);
        logger.debug("[sandpay][{}][resp]<=={}", request.getTxnDesc(), result);
        result = URLDecoder.decode(result, "utf-8");
        logger.info("[sandpay][{}][resp]<=={}", request.getTxnDesc(), result);
        Map<String, String> respMap = SDKUtil.convertResultStringToMap(result);
        String respData = (String)respMap.get("data");
        String respSign = (String)respMap.get("sign");
        boolean valid = CryptoUtil.verifyDigitalSign(respData.getBytes("UTF-8"), Base64.decodeBase64(respSign), CertUtil.getPublicKey(), "SHA1WithRSA");
        if (!valid) {
            logger.error("verify sign fail.");
            throw new RuntimeException("verify sign fail.");
        } else {
            logger.info("verify sign success");
            return JSON.parseObject(respData, request.getResponseClass());
        }
    }
}