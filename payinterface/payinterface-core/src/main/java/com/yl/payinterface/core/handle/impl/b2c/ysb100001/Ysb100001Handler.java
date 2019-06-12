package com.yl.payinterface.core.handle.impl.b2c.ysb100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.MobileInfoBean;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handle.impl.b2c.ysb100001.utils.signUtils;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.CommonUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 银生宝 个人网银
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/6
 */
@Service("b2cYsb100001Handler")
public class Ysb100001Handler implements InternetbankHandler {

    private static final Logger logger = LoggerFactory.getLogger(Ysb100001Handler.class);

    public static void main(String[] args) {
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("service", "query_pay_order");
            req.put("version", "1.0");
            req.put("request_time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("partner_id", "241028");
            req.put("_input_charset", "UTF-8");
//            req.put("notify_url", "https://kd.alblog.cn/front/callback");
//            req.put("out_order_no", "TD-" + System.currentTimeMillis());
//            req.put("merchant_no", "241028");
//            req.put("amount", "2.00");
//            req.put("pay_inst", "ICBC");
//            req.put("card_type", "DC");
            req.put("order_no", "TD20180620100586176975");
            req.put("sign", signUtils.getUrlParamsByMap(req, "3ad0517e465a329cebc3c9a67fd3dbe4"));
            req.put("sign_type", "MD5");
            String res = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://47.52.56.25:8025/apigw/service.do", req, false, "UTF-8", 300000);
//            Map<String, String> resPar = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {});
            System.out.println(res);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @Override
    public Object[] trade(TradeContext tradeContext) throws BusinessException {
        InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
        Map<String, String> params = tradeContext.getRequestParameters();
        Properties properties = JsonUtils.toObject(params.get("transConfig"), Properties.class);
        String interfaceRequestID = interfaceRequest.getInterfaceRequestID();
        String amount =  String.valueOf(interfaceRequest.getAmount());
        Map<String, String> res = new HashMap<>();
        Map<String, String> req = new TreeMap<>();
        try {
            req.put("service", "netbank_pay");
            req.put("version", "1.0");
            req.put("request_time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("partner_id", properties.getProperty("customerNo"));
            req.put("_input_charset", "UTF-8");
            req.put("notify_url", properties.getProperty("notifyUrl"));
            req.put("out_order_no", interfaceRequestID);
            req.put("merchant_no", properties.getProperty("customerNo"));
            req.put("amount", amount);
            req.put("pay_inst", params.get("providerCode"));
            req.put("card_type", "DC");
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            req.put("sign_type", "MD5");
            logger.info("银生宝下单报文{}", req);
            String resPar = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("url"), req, false, "UTF-8", 300000);
            logger.info("银生宝返回报文：{}", resPar);
            Map<String, String> resParMap = JsonUtils.toObject(resPar, new TypeReference<Map<String, String>>() {
            });
            if ("T".equals(resParMap.get("is_success"))) {
                res.put("responseCode", "0000");
                res.put("orderNo", interfaceRequestID);
                res.put("gateway", "formSubmit");
                res.put("interfaceRequestID", interfaceRequestID);
                res.put("html", resParMap.get("post_data"));
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
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
        InterfaceRequest queryInterfaceRequest = tradeContext.getInterfaceRequest();
        Map<String, String> req = new TreeMap<>();
        Map<String, String> requestParameters = tradeContext.getRequestParameters();
        Properties properties = JsonUtils.toObject(requestParameters.get("transConfig"), Properties.class);
        HashMap respMap = new HashMap();
        try {
            req.put("service", "query_pay_order");
            req.put("version", "1.0");
            req.put("request_time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            req.put("partner_id", properties.getProperty("customerNo"));
            req.put("_input_charset", "UTF-8");
            req.put("order_no", queryInterfaceRequest.getInterfaceRequestID());
            req.put("sign", signUtils.getUrlParamsByMap(req, properties.getProperty("key")));
            req.put("sign_type", "MD5");
            logger.info("银生宝查询报文{}", req);
            String resPar = HttpClientUtils.send(HttpClientUtils.Method.POST, properties.getProperty("url"), req, false, "UTF-8", 300000);
            logger.info("银生宝查询返回报文：{}", resPar);
            Map<String, String> resParMap = JsonUtils.toObject(resPar, new TypeReference<Map<String, String>>() {
            });
            if ("2".equals(resParMap.get("trans_status"))) {
                respMap.put("tranStat", "SUCCESS");
                respMap.put("amount", resParMap.get("amount"));
                respMap.put("orderNo", resParMap.get("order_no"));
                respMap.put("interfaceOrderID", resParMap.get("trans_no"));
                respMap.put("notifyDate", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
                respMap.put("queryStatus", InterfaceTradeStatus.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return new Object[]{respMap};
    }
}