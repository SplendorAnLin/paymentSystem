package com.yl.payinterface.core.handle.impl.wallet.yilian584001;

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
 * @author Shark
 * @Description
 * @Date 2018/3/8 14:59
 */
@Service("walletYiLian584001Handler")
public class YiLian584001Handler implements WalletPayHandler {

    private static Logger logger = LoggerFactory.getLogger(YiLian584001Handler.class);


    public static void main(String[] args) {
        String key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMQWp2/CoNyAMuMn" +
                "J3qZCn+hPQEjxNrTi66xOdVVQ9PwPm5ilmiVDFdR+XLc4172wTmuZ7NpVYTMPIzy" +
                "7vPHoa37XUIhUUZKCe/bTgFiFl6RIQfmTr90w+YIx8nyRO4BACq5E/N73k7JKvbq" +
                "a+MYQGW+ifTIW7HMKmCtDI3NTDrrAgMBAAECgYEAqXu/Ktz5dryjvyxHdjKzHU96" +
                "z/Jfopf4An0SNK7m2lerTnON90TkjtC+n6YGLD1xE28IGM0xan+w0k7jc/eXaKsE" +
                "AA6OUolENcK24TCgIzmQjoHm4hZCGQhymavrlfyJ9S+N2ehhDn8J1HjzXJMFuYbI" +
                "M94arJc/8LI/syg3pCkCQQDmizEmxRArE90unS8dnieji5tIQIp3Iaon3EN2w5Mw" +
                "gM1i3xo/f+EofKn0rwBQGwCOFZUSMmU8yRv9i/t9m7QNAkEA2b2DCmsEJJ25O2Hv" +
                "pCANIAxK5XJAoKRpMkizY51dwrDqkJ1bZt1dTsnOyNM1jpTZgIxVKXYxbjyqh2Cf" +
                "SpMU1wJAK/KKuxZDv8J4nmMuURoN+lopjrtm6dDMC+8sGR6tF0jmXhujeElbVYl2" +
                "KIOXrq2HDI7GrQJYVB8OK+YcWdRtnQJAZt5DtH5OnMXvJwDj6JRD3yovkCrkIYDL" +
                "Ojhil+NW0o4mo1/UOMrINFrfWL2ABfaIs1SZP1dZjw0WO5MIboECTQJAR0czxjbU" +
                "v395cI1oAQmjFgt0c/8YNKhtqxd3hMq9ZOSHsqN8fdDFDeMCoRXi3YFuhLIB3D6U" +
                "8lDlz+gStOVB7g==";
        try {
            SortedMap<String, String> map = new TreeMap<>();
            map.put("inputCharset", "1");
            map.put("partnerId", "1807180001037104");
            map.put("notifyUrl", "http://www.sunboyang.com:8080/myshop/complete_get");
            map.put("returnUrl", "http://www.sunboyang.com:8080/myshop/complete");
            map.put("orderNo", System.currentTimeMillis() + "");
            map.put("orderAmount", "600");
            map.put("orderCurrency", "156");
            map.put("orderDatetime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            map.put("payMode", "11");
            map.put("isPhone", "0");
            map.put("signMsg", URLEncoder.encode(RSASignature.createSign(map, key), "UTF-8"));
            //map.put("subject", "小李飞刀");
            //map.put("body", "小李飞刀");

            map.put("signType", "1");
            StringBuffer xml = new StringBuffer();
            for (String mapKey : map.keySet()) {
                xml.append(mapKey).append("=").append(map.get(mapKey)).append("&");
            }

            System.out.println(xml.toString());
            String url = "https://www.ylservice.us/trade/pay/createQrCodePay";

            String ss = HttpUtils.sendReq2(url, xml.substring(0, xml.length() - 1), "POST");
            System.out.println(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<String, String> pay(Map<String, String> param) {

        String interfaceType = param.get("interfaceType");
        Properties properties = JsonUtils.toObject(String.valueOf(param.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String privateKayPath = properties.getProperty("privateKayPath");
        String partnerId = properties.getProperty("partnerId");
        String notifyUrl = properties.getProperty("notifyUrl");
        String returnUrl = properties.getProperty("returnUrl");
        String url = properties.getProperty("url");

        String interfaceRequestID = param.get("interfaceRequestID");
        String amount = param.get("amount");
        String productName = param.get("productName");

        String payMode = "6";
        String isPhone = "0";

        if (InterfaceType.QQNATIVE.name().equals(interfaceType)) {
            payMode = "5";
        } else if (InterfaceType.QQH5.name().equals(interfaceType)) {
            payMode = "5";
            isPhone = "1";
        } else if (InterfaceType.JDH5.name().equals(interfaceType)) {
            payMode = "11";
        }

        SortedMap<String, String> map = new TreeMap<>();
        map.put("inputCharset", "1");
        map.put("partnerId", partnerId);
        map.put("notifyUrl", notifyUrl);
        map.put("returnUrl", returnUrl);
        map.put("orderNo", interfaceRequestID);
        map.put("orderAmount", String.valueOf((int) AmountUtils.multiply(Double.valueOf(amount), 100)));
        map.put("orderCurrency", "156");
        map.put("orderDatetime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        map.put("payMode", payMode);
        map.put("isPhone", isPhone);
        map.put("subject", productName);
        map.put("body", productName);

        Map<String, String> resMap;

        try {
            String key = FileUtils.readTxtFile(new File(privateKayPath));

            map.put("signMsg", URLEncoder.encode(RSASignature.createSign(map, key), "utf-8"));
            map.put("signType", "1");

            StringBuffer xml = new StringBuffer();
            for (String mapKey : map.keySet()) {
                xml.append(mapKey).append("=").append(map.get(mapKey)).append("&");
            }
            logger.info("接口请求号：{}，亿联请求报文：{}", interfaceRequestID, xml.toString());
            String returnXml = HttpUtils.sendReq2(url, xml.substring(0, xml.length() - 1), "POST");
            logger.info("接口请求号：{}，亿联返回报文：{}", interfaceRequestID, returnXml);
            resMap = JsonUtils.toObject(returnXml, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            logger.info("接口请求号：{}，亿联请求报错：{}", interfaceRequestID, e);
            throw new RuntimeException(e.getMessage());
        }

        Map<String, String> wxResMap = new HashMap<String, String>();

        if ("0000".equals(resMap.get("errCode"))) {
            wxResMap.put("code_url", resMap.get("qrcode"));
            wxResMap.put("gateway", "native");
        }
        wxResMap.put("interfaceInfoCode", param.get("interfaceInfoCode"));
        wxResMap.put("interfaceRequestID", interfaceRequestID);
        return wxResMap;
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
