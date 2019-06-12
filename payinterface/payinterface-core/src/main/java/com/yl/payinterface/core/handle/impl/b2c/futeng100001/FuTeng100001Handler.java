package com.yl.payinterface.core.handle.impl.b2c.futeng100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.HttpUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 付腾支付接口
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年11月16日
 */
@Service("b2cFuTeng100002Handler")
public class FuTeng100001Handler implements InternetbankHandler {

    private Logger logger = LoggerFactory.getLogger(FuTeng100001Handler.class);

    @Override
    public Object[] trade(TradeContext tradeContext) throws BusinessException {

        // 获取接口请求
        InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
        Map<String, String> params = tradeContext.getRequestParameters();
        // 获取交易配置
        Properties properties = JsonUtils.toObject(params.get("transConfig"), Properties.class);

        Map<String, String> res = null;
        try {
            String sign = com.yl.payinterface.core.handle.impl.auth.futeng100001.utils.MD5Util.MD5Encode(properties.getProperty("merId") + properties.getProperty("key"), "UTF-8");
            StringBuffer url = new StringBuffer(properties.getProperty("payUrl"));
            url.append("?money=");
            url.append((int) AmountUtils.multiply(interfaceRequest.getAmount(), 100d));
            url.append("&remark=");
            url.append(java.net.URLEncoder.encode(params.get("goodsName"), "UTF-8"));
            url.append("&mercNo=");
            url.append(properties.getProperty("merId"));
            url.append("&subOderNo=");
            url.append(interfaceRequest.getInterfaceRequestID());
            url.append("&backUrl=");
            url.append(properties.getProperty("notifyUrl"));
            url.append("&signCode=");
            url.append(sign);
            logger.info("付腾认证支付 请求地址：[{}]", url.toString());
            String result = HttpUtils.sendReq(url.toString(), "", "POST");
            logger.info("付腾认证支付 请求地址：[{}],响应报文：[{}]", url.toString(), result);
            Map<String, String> resData = JsonUtils.toObject(result, new TypeReference<Map<String, String>>() {
            });
            res = new HashMap<>();
            if (resData.get("resultCode").equals("0000")) {
                res.put("responseCode", resData.get("resultCode"));
                res.put("orderNo", resData.get("orderNo"));
                res.put("gateway", "htmlSubmit");
                res.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
                res.put("html", resData.get("html"));
                return new Object[]{properties.getProperty("bankUrl"), res, interfaceRequest.getInterfaceRequestID()};
            } else {
                throw new BusinessRuntimeException("付腾认证支付 下单异常 : " + String.valueOf(resData.get("resultCode")));
            }
        } catch (Exception e) {
            logger.error("付腾认证支付接口下单异常！[{}]", e);
        }
        return new Object[]{properties.getProperty("bankUrl"), res, interfaceRequest.getInterfaceRequestID()};
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