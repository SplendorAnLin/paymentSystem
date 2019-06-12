package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.bean.AuthBean;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * 伊蚊 个人网银
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/3
 */
@Controller
@RequestMapping("yw440301Notify")
public class YW440301CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(YW440301CompleteController.class);

    private static final String B2C = "YW_440301-B2C";

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new InputStreamReader(YW440301CompleteController.class.getClassLoader().getResourceAsStream("serverHost.properties")));
        } catch (Exception e) {
            logger.error("properties加载异常!异常信息:{}", e);
        }
    }

    @Resource
    private InternetbankHessianService internetbankHessianService;

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;
    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("伊蚊个人网银异步通知原文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(B2C);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
        });
        try {
            if ("SUCCESS".equals(params.get("tranResult")) && verification(params, properties.getProperty("key"))) {
                complete(params);
                response.getWriter().write("SUCCESS");
            }
        } catch (Exception e) {
            logger.error("伊蚊个人网银异步通知异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeTradeFront")
    public void completeTradeFront(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("伊蚊个人网银同步通知原文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(B2C);
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
        Map<String, String> internetbankPayResponseBean = new HashMap<>();
        InternetbankSalesResponseBean internetbankSalesResponseBean = new InternetbankSalesResponseBean();
        internetbankPayResponseBean.put("interfaceOrderID", params.get("tranNo"));
        internetbankPayResponseBean.put("interfaceRequestID", params.get("outOrderNo"));
        internetbankPayResponseBean.put("responseCode", "0000");
        internetbankPayResponseBean.put("responseMessage", "交易成功");
        internetbankPayResponseBean.put("amount", params.get("orderAmt"));
        internetbankPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
        internetbankPayResponseBean.put("tranStat", "SUCCESS");
        internetbankPayResponseBean.put("interfaceCode", B2C);
        internetbankSalesResponseBean.setInternetResponseMsg(internetbankPayResponseBean);
        internetbankHessianService.complete(new AuthBean(), internetbankSalesResponseBean);
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
            logger.error("伊蚊个人网银签名验证异常!异常信息:{}", e);
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