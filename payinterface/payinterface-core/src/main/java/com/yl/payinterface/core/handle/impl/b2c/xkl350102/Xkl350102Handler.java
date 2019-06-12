package com.yl.payinterface.core.handle.impl.b2c.xkl350102;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.MobileInfoBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.handle.impl.wallet.xkl350101.utils.XklSignUtil;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 新快乐 网银支付
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/8
 */
@Service("b2cXkl350102Handler")
public class Xkl350102Handler implements InternetbankHandler {

    private static final Logger logger = LoggerFactory.getLogger(Xkl350102Handler.class);

    @Override
    public Object[] trade(TradeContext tradeContext) throws BusinessException {
        InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
        Map<String, String> params = tradeContext.getRequestParameters();
        Properties properties = JsonUtils.toObject(params.get("transConfig"), Properties.class);
        String interfaceRequestID = interfaceRequest.getInterfaceRequestID();
        String amount =  String.valueOf((int) AmountUtils.multiply(Double.valueOf(interfaceRequest.getAmount()), 100d));
        MobileInfoBean mobileInfo = JsonUtils.toObject(params.get("cardBean"), new TypeReference<MobileInfoBean>() {});
        Map<String, String> res = new HashMap<>();
        try {
            Map<String,String> pay = new HashMap<>();
            pay.put("merNo", properties.getProperty("merNo"));
            pay.put("orderNo", interfaceRequestID);
            pay.put("transAmt", amount);
            pay.put("commodityName", "在线充值");
            pay.put("acctNo", mobileInfo.getBankCardNo());
            pay.put("gatewayType", properties.getProperty("gatewayType"));
            pay.put("insNotifyUrl", properties.getProperty("insNotifyUrl"));
            pay.put("notifyUrl", properties.getProperty("notifyUrl"));
            pay.put("sign", XklSignUtil.signData(pay, properties.getProperty("key")));
            res = XklSignUtil.httpGet(pay, properties.getProperty("url"));
            if("S".equals(res.get("respType"))) {
                res.put("responseCode", "0000");
                res.put("orderNo", res.get("orderNo"));
                res.put("gateway", "htmlSubmit");
                res.put("interfaceRequestID", interfaceRequestID);
                res.put("html", res.get("respCashierHtml"));
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return new Object[]{properties.getProperty("url"), res, interfaceRequest.getInterfaceRequestID()};
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