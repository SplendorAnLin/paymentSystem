package com.yl.payinterface.core.handle.impl.b2c.hee100001;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * 汇付宝 个人网银
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/9/12
 */
@Service("b2cHee100001Handler")
public class hee100001Handler implements InternetbankHandler {

    private static final Logger logger = LoggerFactory.getLogger(hee100001Handler.class);

    public static void main(String[] args) {
        Map<String, String> req = new TreeMap<>();
        req.put("merchantId", "102779");
        req.put("merchantOrderNo", "TD-" + System.currentTimeMillis());
        req.put("merchantUserId", "C10000");
        req.put("productCode", "CP12");
        req.put("payAmount", "10.00");
        req.put("requestTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        req.put("version", "1.0");
        req.put("notifyUrl", "https://kd.alblog.cn/front/callback");
        req.put("callBackUrl", "https://kd.alblog.cn/front/callback");
        req.put("sign", getUrlParamsByMap(req, "d1c3e95b619c476210c6ada8cce94d6f"));

    }

    @Override
    public Object[] trade(TradeContext tradeContext) throws BusinessException {
        InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
        Map<String, String> params = tradeContext.getRequestParameters();
        Properties properties = JsonUtils.toObject(params.get("transConfig"), Properties.class);
        String interfaceRequestID = interfaceRequest.getInterfaceRequestID();
        String amount =  String.valueOf(interfaceRequest.getAmount());
        Map<String, String> req = new TreeMap<>();
        try {
            req.put("merchantId", properties.getProperty("merchantId"));
            req.put("merchantOrderNo", interfaceRequestID);
            req.put("merchantUserId", String.valueOf(System.currentTimeMillis()));
            req.put("productCode", "HY_B2CEBANKPC");
            req.put("payAmount", amount);
            req.put("requestTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("version", "1.0");
            req.put("notifyUrl", properties.getProperty("notifyUrl"));
            req.put("callBackUrl", properties.getProperty("callBackUrl"));
            req.put("onlineType", "hard");
            req.put("sign", getUrlParamsByMap(req, properties.getProperty("key")));
            req.put("gateway", "submit");
            req.put("url", properties.getProperty("payUrl"));
            req.put("interfaceRequestID", interfaceRequestID);
        } catch (Exception e) {
            logger.error("汇付宝个人网银异常!异常信息：{}", e);
        }
        return new Object[]{properties.getProperty("url"), req, interfaceRequest.getInterfaceRequestID()};
    }

    @Override
    public Map<String, Object> signatureVerify(InternetbankSalesResponseBean internetbankSalesResponseBean, Properties tradeConfigs) {
        return null;
    }

    @Override
    public InterfaceRequest complete(Map<String, Object> completeParameters) throws BusinessException {
        return null;
    }

    @Override
    public Object[] query(TradeContext tradeContext) throws BusinessException {
        return new Object[0];
    }

    /**
     * Map 转 URL 并加密
     *
     * @param map
     * @return
     */
    public static String getUrlParamsByMap(Map<String, String> map, String key) {
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        sb.append("key=" + key);
        String s = sb.toString();
        logger.info("签名原串：{}", s);
        return MD5Util.md5(s);
    }
}