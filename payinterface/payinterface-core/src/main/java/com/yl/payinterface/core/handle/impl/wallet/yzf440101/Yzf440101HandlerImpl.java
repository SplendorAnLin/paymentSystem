package com.yl.payinterface.core.handle.impl.wallet.yzf440101;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.wallet.yzf440101.utils.HttpUtil;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.MD5Util;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 云支付 支付宝
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/5
 */
@Service("yzf440101Handler")
public class Yzf440101HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Yzf440101HandlerImpl.class);

    public static void main(String[] args) {
        try {
            Map<String, String> req = new HashMap<>();
            req.put("fxid", "2018114");
            req.put("fxddh", "TD" + System.currentTimeMillis());
            req.put("fxdesc", "测试的");
            req.put("fxfee", "100.00");
            req.put("fxnotifyurl", "https://kd.alblog.cn/front/callback");
            req.put("fxbackurl", "https://kd.alblog.cn/front/callback");
            req.put("fxpay", "zfbyd");
            req.put("fxsign", getSign(req, "SACUiCByqsHivexWjBYnafueWvJkIcHR"));
            req.put("fxip", "192.168.0.1");
            String res = HttpUtil.httpRequest("http://juhe.juhegame.com/Pay", req, "POST", "UTF-8");
            System.out.println(res);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String interfaceRequestID = map.get("interfaceRequestID");
        String amount = map.get("amount");
        String productName = map.get("productName");
        Map<String, String> req = new HashMap<>();
        Map<String,String> resParams = new HashMap<>();
        try {
            req.put("fxid", properties.getProperty("userId"));
            req.put("fxddh", interfaceRequestID);
            req.put("fxdesc", productName);
            req.put("fxfee", amount);
            req.put("fxnotifyurl", properties.getProperty("frontUrl"));
            req.put("fxbackurl", properties.getProperty("backUrl"));
            req.put("fxpay", "zfbyd");
            req.put("fxsign", getSign(req, properties.getProperty("key")));
            req.put("fxip", map.get("ClientIp"));
            logger.info("云支付 下单参数：{}", req);
            String res = HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8");
            logger.info("云支付 下单返回参数：{}", res);
            Map<String, Object> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, Object>>() {
            });
            if ("1".equals(resPar.get("status").toString())) {
                resParams.put("code_url", resPar.get("payurl").toString());
                resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s=" + resParams.get("code_url"));
                resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
                resParams.put("interfaceRequestID", interfaceRequestID);
                resParams.put("gateway", "native");
                resParams.put("merchantNo", properties.getProperty("userId"));
            }
        } catch (Exception e) {
            logger.error("云支付 支付宝异常!异常信息:{}", e);
        }
        return resParams;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        String interfaceRequestID = map.get("interfaceRequestID");
        Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), new TypeReference<Properties>(){});
        Map<String, String> queryParams = new HashMap<String, String>();
        try {
            Map<String, String> req = new HashMap<>();
            req.put("fxid", properties.getProperty("userId"));
            req.put("fxddh", interfaceRequestID);
            req.put("fxaction", "orderquery");
            req.put("fxsign", MD5Util.md5(properties.getProperty("userId") + interfaceRequestID + "orderquery" + properties.getProperty("key")));
            logger.info("云支付 查询参数：{}", req);
            String res = HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8");
            logger.info("云支付 查询返回参数：{}", res);
            Map<String, Object> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, Object>>() {
            });
            if ("1".equals(resPar.get("fxstatus").toString())) {
                queryParams.put("queryStatus", "SUCCESS");
                queryParams.put("tranStat", "0000");
                queryParams.put("responseCode", "0000");
                queryParams.put("responseMessage", "支付成功");
                queryParams.put("amount", resPar.get("fxfee").toString());
                queryParams.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                queryParams.put("orderFinishDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                queryParams.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                queryParams.put("interfaceRequestId", map.get("interfaceRequestID"));
                queryParams.put("businessCompleteType", BusinessCompleteType.AUTO_REPAIR.name());
            }
        } catch (Exception e) {
            logger.error("云支付支付宝查询异常!异常信息:{}", e);
        }
        return queryParams;
    }

    @Override
    public InterfaceRequest complete(Map<String, Object> map) {
        return null;
    }

    private static String getSign(Map<String, String> req, String key){
        StringBuffer sb = new StringBuffer();
        sb.append(req.get("fxid"));
        sb.append(req.get("fxddh"));
        sb.append(req.get("fxfee"));
        sb.append(req.get("fxnotifyurl"));
        sb.append(key);
        return MD5Util.md5(sb.toString());
    }
}