package com.yl.payinterface.core.handle.impl.wallet.yilian584002;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.handle.impl.wallet.yilian584001.utils.RSASignature;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.FileUtils;
import com.yl.payinterface.core.utils.HttpUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 亿联京东H5
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/9
 */
@Service("walletYiLian584002Handler")
public class YiLian584002Handler implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(YiLian584002Handler.class);

    @Override
    public Map<String, String> pay(Map<String, String> param) {
        Properties properties = JsonUtils.toObject(String.valueOf(param.get("tradeConfigs")), new TypeReference<Properties>() {});
        String privateKayPath = properties.getProperty("privateKayPath");
        String partnerId = properties.getProperty("partnerId");
        String notifyUrl = properties.getProperty("notifyUrl");
        String returnUrl = properties.getProperty("returnUrl");
        String url = properties.getProperty("url");
        String interfaceRequestID = param.get("interfaceRequestID");
        String amount = param.get("amount");
        String interfaceType = param.get("interfaceType");
        String payMode = "";
        Map<String, String> resMap = new HashMap<>();
        String isPhone = "0";
        try {
            SortedMap<String, String> map = new TreeMap<>();
            String key = FileUtils.readTxtFile(new File(privateKayPath));
            map.put("inputCharset", "1");
            map.put("partnerId", partnerId);
            map.put("notifyUrl", notifyUrl);
            map.put("returnUrl", returnUrl);
            map.put("orderNo", interfaceRequestID);
            map.put("orderAmount", String.valueOf((int) AmountUtils.multiply(Double.valueOf(amount), 100)));
            map.put("orderCurrency", "156");
            map.put("orderDatetime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            if (InterfaceType.JDH5.name().equals(interfaceType)) {
                payMode = "11";
            } else if (InterfaceType.QQNATIVE.name().equals(interfaceType)) {
                payMode = "5";
            } else if (InterfaceType.UNIONPAYNATIVE.name().equals(interfaceType)) {
                payMode = "6";
            } else if (InterfaceType.QQH5.name().equals(interfaceType)) {
                payMode = "5";
                isPhone = "1";
            }
            map.put("payMode", payMode);
            map.put("isPhone", isPhone);
            map.put("signMsg", URLEncoder.encode(RSASignature.createSign(map, key), "UTF-8"));
            map.put("signType", "1");
            StringBuffer xml = new StringBuffer();
            for (String mapKey : map.keySet()) {
                xml.append(mapKey).append("=").append(map.get(mapKey)).append("&");
            }
            logger.info("接口请求号：{}，亿联请求报文：{}", interfaceRequestID, xml.toString());
            String returnXml = HttpUtils.sendReq2(url, xml.substring(0, xml.length() - 1), "POST").replaceAll("lib/jquery/jquery-1.9.0.min.js", "http://libs.baidu.com/jquery/1.9.0/jquery.min.js");
            logger.info("接口请求号：{}，亿联返回报文：{}", interfaceRequestID, returnXml);
            resMap.put("gateway", "htmlSubmit");
            resMap.put("interfaceInfoCode", param.get("interfaceInfoCode"));
            resMap.put("interfaceRequestID", interfaceRequestID);
            resMap.put("html", returnXml);
            resMap.put("code_url", properties.getProperty("qrPayUrl") + "?interfaceRequestID=" + interfaceRequestID);
            resMap.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resMap.get("code_url"));
            CacheUtils.setEx(interfaceRequestID, returnXml, 43200);
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return resMap;
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