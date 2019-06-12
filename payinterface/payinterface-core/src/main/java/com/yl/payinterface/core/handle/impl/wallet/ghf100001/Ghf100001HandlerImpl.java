package com.yl.payinterface.core.handle.impl.wallet.ghf100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.wallet.ghf100001.util.SignUtils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.CodeBuilder;
import com.yl.payinterface.core.utils.HttpUtil;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 刚好付支付宝H5
 *
 * @author xiaojie.zhang
 * @version V1.0.0
 * @since 2018/7/26
 */
@Service("ghf100001Handler")
public class Ghf100001HandlerImpl implements WalletPayHandler {

    private static Logger logger = LoggerFactory.getLogger(Ghf100001HandlerImpl.class);

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("pay_url", "http://39.108.161.90/scwl/unifiedorder/gateway");
        properties.put("service_code", "scwl.trade.unifiedorder");
        properties.put("partner_id", "TEST1");
        properties.put("ip", "127.0.0.1");
        properties.put("signType", "MD5");
        properties.put("version", "1.0.0");
        properties.put("notify_url", "http://winpay.me/payinterface-front/ghf100001Notify/complete");
        properties.put("gateway", "native");
        properties.put("key", "TEST12");

        Map<String, String> params = new HashMap<>();
        params.put("tradeConfigs", JsonUtils.toJsonString(properties));
        params.put("interfaceRequestID", CodeBuilder.build("TD", "yyyyMMdd"));
        params.put("amount", "21.02");
        params.put("orderTimeFmt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("interfaceInfoCode", "GHF100001-ALI_JSAPI");
        new Ghf100001HandlerImpl().pay(params);
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });

        Map<String, String> resParams = new HashMap<>();

        Map<String, String> params = new TreeMap<>();
        params.put("service_code", properties.getProperty("service_code"));
        params.put("partner_id", properties.getProperty("partner_id"));
        params.put("signType", properties.getProperty("signType"));
        params.put("out_trade_no", map.get("interfaceRequestID"));
        params.put("total_fee", String.valueOf((int) AmountUtils.round(AmountUtils.multiply(Double.valueOf(map.get("amount")), 100), 2, RoundingMode.HALF_UP)));
        params.put("create_ip", properties.getProperty("ip"));
        params.put("nonceStr", CodeBuilder.getOrderIdByUUId());
        params.put("version", properties.getProperty("version"));
        params.put("body", "recharge");
        params.put("notify_url", properties.getProperty("notify_url"));
        params.put("sign", SignUtils.sign(params, properties.getProperty("key")));

        String resStr;
        Map<String, String> jsonParams;
        try {
            logger.info("刚好付 支付宝H5下单:[{}], 请求报文:[{}]", map.get("interfaceRequestID"), params);
            resStr = HttpUtil.sendReq(properties.getProperty("pay_url"), JsonUtils.toJsonString(params), "POST");
            logger.info("刚好付 支付宝H5下单:[{}], 响应报文:[{}]", map.get("interfaceRequestID"), resStr);

            jsonParams = JsonUtils.toObject(resStr, new TypeReference<Map>() {
            });
            if ("0".equals(jsonParams.get("result_code"))) {
                resParams.put("code_url", jsonParams.get("code_url"));
                resParams.put("code_img_url", "https://api.zk1006.cn/qrCode?s="+jsonParams.get("code_url"));
                resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
                resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
                resParams.put("gateway", "native");
            }
        } catch (Exception e) {
            logger.info("刚好付 支付宝H5下单异常:[{}], 异常信息:[{}]", map.get("interfaceRequestID"), e);
        }

        logger.info("刚好付 支付宝H5下单:[{}], 返回报文:[{}]", map.get("interfaceRequestID"), resParams);
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
}
