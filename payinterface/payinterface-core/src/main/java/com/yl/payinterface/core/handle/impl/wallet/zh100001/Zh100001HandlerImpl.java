package com.yl.payinterface.core.handle.impl.wallet.zh100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.handle.impl.wallet.zh100001.utils.Zh100001Utils;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 众横QT
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/9
 */
@Service("Zh100001Handler")
public class Zh100001HandlerImpl implements WalletPayHandler {

    private static final Logger logger = LoggerFactory.getLogger(Zh100001HandlerImpl.class);

    public static void main(String[] args) {
        Map<String, String> req = new TreeMap<>();
        req.put("txamt", "100");
        req.put("txcurrcd", "CNY");
        req.put("pay_type", "800201");
        req.put("out_trade_no", "TD-" + System.currentTimeMillis());
        req.put("txdtm", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        req.put("goods_name", "Apple");
        req.put("mchid", "dKwaWtjONn");
        req.put("limit_pay", "no_credit");
        req.put("sign", Zh100001Utils.getUrlParamsByMap(req, "A0DB3D1817C546E4BAFCA0911D25E7DD"));
        System.out.println(Zh100001Utils.httpSend(req, "https://openapi.qfpay.com/trade/v1/payment", "75AC9EEC91C74288A3C78156E7D345DB"));
    }

    @Override
    public Map<String, String> pay(Map<String, String> map) {
        Map<String,String> resParams = new HashMap<>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
        String interfaceRequestID = map.get("interfaceRequestID");
        String productName = map.get("productName");
        String interfaceType = map.get("interfaceType");
        Map<String, String> req = new TreeMap<>();
        req.put("txamt", amount);
        req.put("txcurrcd", "CNY");
        if (InterfaceType.ALIPAY.name().equals(interfaceType)) {
            req.put("pay_type", "800101");
        } else if (InterfaceType.WXNATIVE.name().equals(interfaceType)){
            req.put("pay_type", "800201");
        }
        req.put("out_trade_no", interfaceRequestID);
        req.put("txdtm", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        req.put("goods_name", productName);
        req.put("mchid", properties.getProperty("mchid"));
        req.put("limit_pay", "no_credit");
        req.put("sign", Zh100001Utils.getUrlParamsByMap(req, properties.getProperty("key")));
        try {
            String res = Zh100001Utils.httpSend(req, properties.getProperty("url"), properties.getProperty("code"));
            if (res != null){
                Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
                });
                resParams.put("code_url", resPar.get("qrcode"));
                resParams.put("code_img_url", resPar.get("qrcode"));
                resParams.put("interfaceInfoCode", interfaceRequestID);
                resParams.put("interfaceRequestID", interfaceRequestID);
                resParams.put("gateway", "native");
                resParams.put("merchantNo", properties.getProperty("mchid"));
            }
        } catch (Exception e) {
            logger.error("众横QT下单异常!异常信息：{}", e);
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
}