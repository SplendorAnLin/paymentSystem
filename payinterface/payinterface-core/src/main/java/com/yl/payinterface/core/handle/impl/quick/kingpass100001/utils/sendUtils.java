package com.yl.payinterface.core.handle.impl.quick.kingpass100001.utils;

import com.yl.payinterface.core.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * 报文发送工具类
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/2
 */
public class sendUtils {

    private static final Logger logger = LoggerFactory.getLogger(sendUtils.class);

    public static Map<String, String> sendPost(Map<String, String> map, String p12, String cer, String passWord, String url){
        Map<String, String> respMap = null;
        try {
            String buf = KingPassUtils.mapToStr(KingPassUtils.sortMapByKey(map));
            RSASignUtil rsaSignUtil = new RSASignUtil(p12, passWord);
            rsaSignUtil.setRootCertPath(cer);
            String sign = rsaSignUtil.sign(buf, "UTF-8");
            String cert = rsaSignUtil.getCertInfo();
            buf = buf + "&merchantCert=" + cert + "&merchantSign=" + sign;
            logger.info("九派刷脸支付下单报文：{}", buf);
            String respInfo = HttpUtils.sendReq2(url, buf, "POST");
            logger.info("九派刷脸支付返回报文：{}", respInfo);
            respMap = KingPassUtils.strToMap(respInfo);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return respMap;
    }
}