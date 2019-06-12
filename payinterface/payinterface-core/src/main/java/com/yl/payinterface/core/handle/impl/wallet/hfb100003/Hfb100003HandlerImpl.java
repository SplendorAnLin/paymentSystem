package com.yl.payinterface.core.handle.impl.wallet.hfb100003;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.wallet.hfb100003.utils.CertUtil;
import com.yl.payinterface.core.handle.impl.wallet.hfb100003.utils.Httpz;
import com.yl.payinterface.core.handle.impl.wallet.hfb100003.utils.ParamUtil;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 合付宝 银联钱包
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/20
 */
@Service("hfb100003Handler")
public class Hfb100003HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Hfb100003HandlerImpl.class);

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("channelNo", "05");
        properties.put("cerPath", "F:\\pay\\cert\\hfb\\SS20170907011890_20170907170049931.cer");
        properties.put("bindId", "YSM201709141140144958595809101");
        properties.put("pay", "http://paygate.hefupal.cn/paygate/v1/smpay");
        properties.put("notifyUrl", "https://kd.alblog.cn/front/callback");
        properties.put("version", "v1");
        properties.put("tranCode", "YS1003");
        properties.put("merchantNo", "S20170907011890");
        properties.put("keyPass", "a111111");
        properties.put("bizType", "02");
        properties.put("unionPay", "7");
        properties.put("keyStorePath", "F:\\pay\\cert\\hfb\\CS20170907011890_20170907170049931.pfx");
        Map<String, String> map = new HashMap<>();
        map.put("tradeConfigs", JsonUtils.toJsonString(properties));
        map.put("interfaceRequestID", "TD" + System.currentTimeMillis());
        map.put("ClientIp", "192.168.0.1");
        map.put("amount", "1.00");
        map.put("productName", "测试");
        map.put("interfaceInfoCode", "ALIPA");
        new Hfb100003HandlerImpl().pay(map);
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String,String> resParams = new HashMap<>();
        try {
            Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
            String interfaceRequestID = map.get("interfaceRequestID");
            String clientIp = map.get("ClientIp");
            String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d));
            String productName = map.get("productName");
            Map<String, String> transMap = new HashMap<>();
            transMap.put("merchantNo", properties.getProperty("merchantNo"));
            transMap.put("version", properties.getProperty("version"));
            transMap.put("channelNo", properties.getProperty("channelNo"));
            transMap.put("tranCode", properties.getProperty("tranCode"));
            transMap.put("tranFlow", interfaceRequestID);
            transMap.put("tranDate", formatDate(new Date(), "yyyyMMdd"));
            transMap.put("tranTime", formatDate(new Date(), "HHmmss"));
            transMap.put("amount", amount);
            transMap.put("payType", properties.getProperty("unionPay"));
            transMap.put("bindId", properties.getProperty("bindId"));
            transMap.put("notifyUrl", properties.getProperty("notifyUrl"));
            transMap.put("bizType", properties.getProperty("bizType"));
            transMap.put("goodsName", productName);
            transMap.put("buyerName", CertUtil.getInstance().encrypt("李培", properties.getProperty("keyStorePath"), properties.getProperty("cerPath"), properties.getProperty("keyPass")));
            transMap.put("buyerId", "C100000");
            transMap.put("remark", productName);
            transMap.put("YUL3", clientIp);
            String signMsg = ParamUtil.getSignMsg(transMap);
            String sign = CertUtil.getInstance().sign(signMsg, properties.getProperty("keyStorePath"), properties.getProperty("cerPath"), properties.getProperty("keyPass"));
            transMap.put("sign", sign);
            logger.info("请求报文：" + transMap);
            String asynMsg = new Httpz("utf-8",30000,30000).post(properties.getProperty("pay"), transMap);
            logger.info("返回报文：" + asynMsg);
            Map<String, String> res = urlSplit(asynMsg);
            if ("0000".equals(res.get("rtnCode"))) {
                resParams.put("code_url", res.get("qrCodeURL"));
                resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + res.get("qrCodeURL"));
                resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
                resParams.put("interfaceRequestID", interfaceRequestID);
                resParams.put("gateway", "native");
                resParams.put("merchantNo", properties.getProperty("merchantNo"));
            }
        } catch (Exception e) {
            logger.error("下单失败！异常信息:{}", e);
        }
        return resParams;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }

    @Override
    public InterfaceRequest complete(Map<String, Object> map) {
        return null;
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 只针对内部方法调用
     * @param urlParam 带分隔的url参数
     * @return
     */
    private static Map<String, String> urlSplit(String urlParam){
        Map<String, String> map = new HashMap<String, String>();
        String[] param =  urlParam.split("&");
        for(String keyValue : param){
            String[] pair = keyValue.split("=");
            if(pair.length == 2){
                map.put(pair[0], pair[1]);
            } else if(pair.length == 3){
                map.put(pair[0], pair[1].substring(0, pair[1].length() - 4) + "uuid=" + pair[2]);
            }
        }
        return map;
    }
}