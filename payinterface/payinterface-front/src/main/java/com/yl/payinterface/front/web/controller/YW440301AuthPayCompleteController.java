package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.AuthPayHessianService;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 伊蚊 认证支付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/5
 */
@Controller
@RequestMapping("yw440301AuthNotify")
public class YW440301AuthPayCompleteController {

    private static final Logger logger = LoggerFactory.getLogger(YW440301AuthPayCompleteController.class);

    private static final String AUTHPAY = "AUTHPAY_YW-440301";

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new InputStreamReader(YW440301AuthPayCompleteController.class.getClassLoader().getResourceAsStream("serverHost.properties")));
        } catch (Exception e) {
            logger.error("properties加载异常!异常信息:{}", e);
        }
    }

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private AuthPayHessianService authPayHessianService;
    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("伊蚊认证支付异步通知原文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(AUTHPAY);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
        });
        try {
            if ("SUCCESS".equals(params.get("tranResult")) && verification(params, properties.getProperty("key"))) {
                complete(params);
                response.getWriter().write("SUCCESS");
            }
        } catch (Exception e) {
            logger.error("伊蚊认证支付异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeTradeFront")
    public void completeTradeFront(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("伊蚊认证支付同步通知原文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(AUTHPAY);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
        });
        try {
            if ("SUCCESS".equals(params.get("tranResult")) && verification(params, properties.getProperty("key"))) {
                complete(params);
                InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(params.get("outOrderNo"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("伊蚊个人网银同步通知异常!异常信息:{}", e);
        }
    }

    private void complete(Map<String, String> params) {
        Map<String, String> completeMap = new HashMap<>();
        completeMap.put("interfaceCode", AUTHPAY);
        completeMap.put("interfaceRequestID", params.get("outOrderNo"));
        completeMap.put("interfaceOrderID", params.get("tranNo"));
        completeMap.put("tranStat", "SUCCESS");
        completeMap.put("amount", params.get("orderAmt"));
        completeMap.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        completeMap.put("completeTime", params.get("tranTime"));
        completeMap.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
        completeMap.put("responseCode", "0000");
        completeMap.put("responseMessage", "交易成功");
        authPayHessianService.complete(completeMap);
    }

    private boolean verification(Map<String, String> params, String key){
        try {
            String sign = params.get("sign");
            params.remove("sign");
            params.remove("signType");
            Map<String, String> res = new TreeMap<>();
            res.putAll(params);
            if (res == null) {
                return false;
            }
            StringBuffer sb = new StringBuffer();
            for (Map.Entry<String, String> entry : res.entrySet()) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
            String s = sb.toString();
            if (s.endsWith("&")) {
                s = StringUtils.substringBeforeLast(s, "&");
            }
            s += key;
            if (DigestUtils.md5Hex(s.getBytes("UTF-8")).equalsIgnoreCase(sign)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("伊蚊认证支付签名验证异常!异常信息:{}", e);
        }
        return false;
    }

    /**
     * 参数转换
     */
    private Map<String, String> retrieveParams(Map<String, String[]> requestMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        for (String key : requestMap.keySet()) {
            String[] value = requestMap.get(key);
            if (value != null) {
                resultMap.put(key, Array.get(value, 0).toString().trim());
            }
        }
        return resultMap;
    }
}