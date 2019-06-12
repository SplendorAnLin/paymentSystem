package com.yl.payinterface.core.handle.impl.wallet.hfb100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.wallet.hfb100001.utils.CertUtil;
import com.yl.payinterface.core.handle.impl.wallet.hfb100001.utils.Httpz;
import com.yl.payinterface.core.handle.impl.wallet.hfb100001.utils.ParamUtil;
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
 * 合付宝 QQ扫码支付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/13
 */
@Service("hfb100001Handler")
public class Hfb100001HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Hfb100001HandlerImpl.class);

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String,String> resParams = new HashMap<String, String>();
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
            transMap.put("payType", properties.getProperty("qqScan"));
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
                resParams.put("code_img_url", res.get("qrCodeURL"));
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

    public static void main(String[] args) {
        String s = "YUL1=null&YUL2=null&YUL3=39.104.68.226&channelNo=05&ext1=null&ext2=null&merchantNo=S20180824050607&qrCodeURL=http://cashier.hefupal.com/paygate/redirect/ODA4MjQxMDk0Njg4MzQwMjg3MDc4NA==&rtnCode=0000&rtnMsg=交易成功&sign=gLTS3CM6+9rM6lwXYCq3BTMZty16x47yPdhce6cG9MoyqF2eRpf5wt42Mks2chrCP+HQFmILYA/B6Yz2VAc1M8LXost7Zt2rP3w8h8smMMVAYdRjB6/aaIn6nLClMVkGqHcE24pC1Q3fo9Fa49rQ5D5hl2NTLaOWpEPJLGIbkZU=&tranCode=YS1003&tranFlow=TD20180824101119633609&version=v1";
        Map<String, String> res = urlSplit(s);
        System.out.println(res);
    }

    /**
     * @param urlParam 带分隔的url参数
     * @return
     */
    public static Map<String, String> urlSplit(String urlParam){
        Map<String, String> map = new HashMap<String, String>();
        String[] param =  urlParam.split("&");
        for(String keyValue : param){
            String[] pair = keyValue.split("=");
            if(pair.length == 2){
                map.put(pair[0], pair[1]);
            }
            if (pair[0].equals("qrCodeURL")) {
                map.put(pair[0], pair[1] + "==");
            }
        }
        return map;
    }
}