package com.yl.payinterface.core.handle.impl.auth.shand100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.auth.shand100001.utils.CertUtil;
import com.yl.payinterface.core.handle.impl.auth.shand100001.utils.CryptoUtil;
import com.yl.payinterface.core.handler.AuthPayHandler;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 杉德一键快捷
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/27
 */
@Service("shand100001Handler")
public class Shand100001AuthPayHandler implements AuthPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Shand100001AuthPayHandler.class);

    static {
        try {
            CertUtil.init("/home/cer/sand/sand.cer", "/home/cer/sand/sand.pfx", "123456");
        } catch (Exception e) {
            logger.error("衫德扫码支付加载证书异常:{}", e);
        }
    }

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        return null;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), Properties.class);
        String interfaceRequestID = map.get("interfaceRequestID");
        Map<String, String> res = new HashMap<>();
        JSONObject head = new JSONObject();
        head.put("version", "1.0");
        head.put("method", "sandPay.fastPay.quickPay.index");
        head.put("productId", "00000016");
        head.put("accessType", "1");
        head.put("mid", properties.getProperty("mid"));
        head.put("plMid", "");
        head.put("channelType", "07");
        head.put("reqTime", map.get("orderTime"));
        logger.info("杉德一键快捷请求头：{}", head);
        JSONObject body = new JSONObject();
        body.put("userId", String.valueOf(System.currentTimeMillis()).substring(3, 13));
        body.put("clearCycle", "0");
        body.put("currencyCode", "156");
        body.put("frontUrl", properties.getProperty("frontUrl"));
        body.put("notifyUrl", properties.getProperty("notifyUrl"));
        body.put("orderCode", interfaceRequestID);
        body.put("orderTime", map.get("orderTime"));
        if (map.get("amount").length() != 12) {
            String tmpAmt = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
            for (; ; ) {
                tmpAmt = "0" + tmpAmt;
                if (tmpAmt.length() == 12) {
                    body.put("totalAmount", tmpAmt);
                    break;
                }
            }
        }
        body.put("body", map.get("productName"));
        body.put("subject", map.get("productName"));
        body.put("extend", "");
        logger.info("杉德一键快捷请求体：{}", body);
        JSONObject data = new JSONObject();
        data.put("head", head);
        data.put("body", body);

        try {
            String reqSign = URLEncoder.encode(new String(
                    Base64.encodeBase64(CryptoUtil.digitalSign(JsonUtils.toJsonString(data).getBytes("UTF-8"),
                            CertUtil.getPrivateKey(), "SHA1WithRSA"))), "UTF-8");
            res.put("data", JsonUtils.toJsonString(data));
            res.put("sign", reqSign);
            res.put("charset", "UTF-8");
            res.put("signType", "01");
            res.put("extend", "");
            logger.info("杉德一键快捷下单参数：{}", res);
            res.put("gateway", "shandSubmit");
            res.put("url", properties.getProperty("url"));
            res.put("interfaceRequestID", interfaceRequestID);
        } catch (Exception e) {
            logger.error("杉德一键快捷异常!异常信息:{}", e);
        }
        return res;
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {
        return null;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }
}