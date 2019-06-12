package com.yl.payinterface.core.handle.impl.b2c.klt100005;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.handle.impl.auth.klt100001.utils.KltUtils;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 开联通 网银
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/11
 */
@Service("b2cKlt100005Handler")
public class Klt100005Handler implements InternetbankHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(Klt100005Handler.class);

    @Override
    public Object[] trade(TradeContext tradeContext) throws BusinessException {
        InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
        Map<String, String> params = tradeContext.getRequestParameters();
        Properties properties = JsonUtils.toObject(params.get("transConfig"), Properties.class);
        String interfaceRequestID = interfaceRequest.getInterfaceRequestID();
        String amount =  String.valueOf((int) AmountUtils.multiply(Double.valueOf(interfaceRequest.getAmount()), 100d));
        String dateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String productName = tradeContext.getRequestParameters().get("goodsName");
        String gateway = properties.getProperty("toPay");
        Map<String, String> data = new HashMap<>();
        try {
            data.put("inputCharset", "1");
            data.put("pickupUrl", properties.getProperty("pickupUrl"));
            data.put("receiveUrl", properties.getProperty("receiveUrl"));
            data.put("version", "v1.0");
            data.put("language", "1");
            data.put("signType", "0");
            data.put("merchantId", properties.getProperty("merchantId"));
            data.put("orderNo", interfaceRequestID);
            data.put("orderAmount", amount);
            data.put("orderCurrency", "156");
            data.put("orderDatetime", dateTime);
            data.put("productName", productName);
            if ("B2C_KLT-100005".equals(interfaceRequest.getInterfaceInfoCode())) {
                data.put("payType", "1");
            } else if ("B2C_KLT-100006".equals(interfaceRequest.getInterfaceInfoCode())) {
                data.put("payType", "11");
            }
            String signMsg = KltUtils.authPay(data, properties.getProperty("key"));
            data.put("signMsg", signMsg);
            data.put("gateway", gateway);
            data.put("url", properties.getProperty("payUrl"));
            data.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return new Object[]{properties.getProperty("url"), data, interfaceRequest.getInterfaceRequestID()};
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
}