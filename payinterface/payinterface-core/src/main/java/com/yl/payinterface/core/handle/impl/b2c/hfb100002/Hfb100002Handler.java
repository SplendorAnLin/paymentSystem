package com.yl.payinterface.core.handle.impl.b2c.hfb100002;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.handle.impl.b2c.hfb100002.utils.CertUtil;
import com.yl.payinterface.core.handle.impl.b2c.hfb100002.utils.ParamUtil;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 合付宝 PC网关
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/15
 */
@Service("b2cHfb100002Handler")
public class Hfb100002Handler implements InternetbankHandler {

    private static final Logger logger = LoggerFactory.getLogger(Hfb100002Handler.class);

    private static Map<String, String> bankCodes = new HashMap();

    @Override
    public Object[] trade(TradeContext tradeContext) throws BusinessException {
        Map<String, String> params = new LinkedHashMap();
        Map<String, String> requestParameters = tradeContext.getRequestParameters();
        Properties transConfig = JsonUtils.toObject(requestParameters.get("transConfig"), Properties.class);
        InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
        try {
            params.put("merchantNo", transConfig.getProperty("merchantNo"));
            params.put("version", transConfig.getProperty("version"));
            params.put("channelNo", transConfig.getProperty("channelNo"));
            params.put("tranSerialNum", interfaceRequest.getInterfaceRequestID());
            params.put("bankId", bankCodes.get(interfaceRequest.getInterfaceProviderCode()));
            if ("DEBIT_CARD".equals(interfaceRequest.getCardType())) {
                params.put("cardType", "01");
            } else {
                params.put("cardType", "02");
            }
            params.put("tranTime", (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
            params.put("currency", "CNY");
            params.put("amount", String.valueOf((int) AmountUtils.multiply(Double.valueOf(interfaceRequest.getAmount()), 100d)));
            params.put("bizType", transConfig.getProperty("bizType"));
            params.put("goodsName", requestParameters.get("goodsName"));
            params.put("notifyUrl", transConfig.getProperty("notifyUrl"));
            params.put("returnUrl", transConfig.getProperty("returnUrl"));
            params.put("buyerName", CertUtil.getInstance().encrypt("李培", transConfig.getProperty("keyStorePath"), transConfig.getProperty("cerPath"), transConfig.getProperty("keyPass")));
            params.put("buyerId", interfaceRequest.getOwnerID());
            params.put("ip", "111.172.254.78");
            String signMsg = ParamUtil.getSignMsg(params);
            logger.info("合付宝 PC网关下单加密参数:{}", signMsg);
            String sign = CertUtil.getInstance().sign(signMsg, transConfig.getProperty("keyStorePath"), transConfig.getProperty("cerPath"), transConfig.getProperty("keyPass"));
            params.put("sign", sign);
            params.put("gateway", "submit");
            params.put("url", transConfig.getProperty("payUrl"));
            params.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
            logger.info("发往合付宝 PC网关通道交易串：{}", params.toString());
        } catch (Exception e) {
            logger.error("合付宝 PC网关下单失败！异常信息:{}", e);
        }
        return new Object[]{transConfig.getProperty("payUrl"), params, interfaceRequest.getInterfaceRequestID()};
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

    static {
        bankCodes.put("PSBC", "01000000");
        bankCodes.put("ICBC", "01020000");
        bankCodes.put("ABC", "01030000");
        bankCodes.put("BOC", "01040000");
        bankCodes.put("CCB", "01050000");
        bankCodes.put("BOCOM", "03010000");
        bankCodes.put("CITIC", "03020000");
        bankCodes.put("CEB", "03030000");
        bankCodes.put("HXB", "03040000");
        bankCodes.put("CMBC", "03050000");
        bankCodes.put("CGB", "03060000");
        bankCodes.put("PAB", "04100000");
        bankCodes.put("CMB", "03080000");
        bankCodes.put("CIB", "03090000");
        bankCodes.put("SPDB", "03100000");
        bankCodes.put("CBHB", "03170000");
        bankCodes.put("BEA", "03200000");
        bankCodes.put("BOSC", "04012900");
        bankCodes.put("BOB", "04031000");
        bankCodes.put("BON", "04083320");
        bankCodes.put("NJCB", "04243010");
        bankCodes.put("BOCD", "64296510");
        bankCodes.put("SRCB", "65012900");
    }
}