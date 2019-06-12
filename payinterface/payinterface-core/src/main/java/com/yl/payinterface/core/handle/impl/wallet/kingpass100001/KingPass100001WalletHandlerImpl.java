package com.yl.payinterface.core.handle.impl.wallet.kingpass100001;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.wallet.kingpass100001.utils.KingPassUtils;
import com.yl.payinterface.core.handle.impl.wallet.kingpass100001.utils.RSASignUtil;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.HttpUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Shark
 * @Description
 * @Date 2018/5/30 10:33
 */
@Service("kingPass100001WalletHandler")
public class KingPass100001WalletHandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(KingPass100001WalletHandlerImpl.class);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void main(String[] args) {

        String requestId = "TD" + System.currentTimeMillis();

        Map<String, String> map = new TreeMap<>();
        map.put("charset", "02");
        map.put("service", "qrcodeSpdbPreOrder");
        map.put("version", "1.0");
        map.put("merchantId", "800001500020041");
        map.put("requestTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        map.put("requestId", requestId);
        map.put("signType", "RSA256");
        map.put("amount", "1");
        map.put("payChannel", "UPOP");
        map.put("orderId", requestId);
        map.put("goodsName", "123");
        map.put("goodsDesc", "123");
        map.put("terminalId", (System.currentTimeMillis() + "").substring(0, 8));
        map.put("corpOrg", "UPOP");
        map.put("clientIP", "127.0.0.1");
        map.put("offlineNotifyUrl", "http://www.sunboyang.com:8080/myshop/complete_get");

        String buf = KingPassUtils.mapToStr(map);

        try {
            RSASignUtil rsaSignUtil = new RSASignUtil("E:/shanchu/800001500020041.p12", "ZMoRpB");
            rsaSignUtil.setRootCertPath("E:/shanchu/rootca.cer");
            String sign = rsaSignUtil.sign(buf, "UTF-8");
            String cert = rsaSignUtil.getCertInfo();
            buf = buf + "&merchantCert=" + cert + "&merchantSign=" + sign;
            System.out.println(buf);
            String a = HttpUtils.sendReq2("https://jd.jiupaipay.com/paygateway/mpsGate/mpsTransaction", buf, "POST");
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Map<String, String> pay(Map<String, String> param) {
        Properties properties = JsonUtils.toObject(String.valueOf(param.get("tradeConfigs")), new TypeReference<Properties>() {
        });

        String productName = param.get("productName");
        String clientIp = param.get("ClientIp");
        String interfaceRequestID = param.get("interfaceRequestID");
        String interfaceInfoCode = param.get("interfaceInfoCode");
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(param.get("amount")), 100d));

        String service = properties.getProperty("service");
        String version = properties.getProperty("version");
        String merchantId = properties.getProperty("merchantId");
        String signType = properties.getProperty("signType");
        String payChannel = properties.getProperty("payChannel");
        String corpOrg = properties.getProperty("corpOrg");
        String certFilePath = properties.getProperty("certFilePath");
        String rootCertPath = properties.getProperty("rootCertPath");
        String password = properties.getProperty("password");
        String offlineNotifyUrl = properties.getProperty("offlineNotifyUrl");
        String url = properties.getProperty("url");

        Map<String, String> map = new TreeMap<>();
        map.put("charset", "02");
        map.put("service", service);
        map.put("version", version);
        map.put("merchantId", merchantId);
        map.put("requestTime", dateFormat.format(new Date()));
        map.put("requestId", interfaceRequestID);
        map.put("signType", signType);
        map.put("amount", amount);
        map.put("payChannel", payChannel);
        map.put("orderId", interfaceRequestID);
        map.put("goodsName", productName);
        map.put("goodsDesc", productName);
        map.put("terminalId", (System.currentTimeMillis() + "").substring(0, 8));
        map.put("corpOrg", corpOrg);
        map.put("clientIP", clientIp);
        map.put("offlineNotifyUrl", offlineNotifyUrl);

        String buf = KingPassUtils.mapToStr(map);
        Map<String, String> retMap = new HashMap<>();
        try {
            // 加密
            RSASignUtil rsaSignUtil = new RSASignUtil(certFilePath, password);
            rsaSignUtil.setRootCertPath(rootCertPath);
            String sign = rsaSignUtil.sign(buf, "UTF-8");
            String cert = rsaSignUtil.getCertInfo();
            buf = buf + "&merchantCert=" + cert + "&merchantSign=" + sign;
            logger.info("接口编号：{}，请求报文：{}", interfaceRequestID, buf);
            // 发起请求
            String respStr = HttpUtils.sendReq2(url, buf, "POST");
            logger.info("接口编号：{}，返回报文：{}", interfaceRequestID, respStr);
            Map<String, String> respMap = KingPassUtils.strToMap(respStr);

            if ("IPS00000".equals(respMap.get("rspCode"))) {
                retMap.put("code_url", properties.getProperty("gateway") + "=" + interfaceRequestID);
                retMap.put("code_img_url", respMap.get("bankUrl"));
                retMap.put("gateway", "native");
                retMap.put("interfaceInfoCode", interfaceInfoCode);
                retMap.put("interfaceRequestID", interfaceRequestID);
                retMap.put("amount", param.get("amount"));
                CacheUtils.setEx(interfaceRequestID, JsonUtils.toJsonString(retMap), 43200);
            }
        } catch (Exception e) {
            logger.info("接口编号：{}，下单报错：{}", interfaceRequestID, e);
        }
        logger.info("接口编号：{}，返回参数：{}", interfaceRequestID, retMap);
        return retMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }

    @Override
    public InterfaceRequest complete(Map<String, Object> map) {
        return null;
    }
}
