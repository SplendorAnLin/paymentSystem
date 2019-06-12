package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.bean.AuthBean;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.front.utils.KLT100001.KltUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 开联通 个人网银完成回调
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/11
 */
@Controller
@RequestMapping("/klt100002Notify")
public class KLT100002CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(KLT100002CompleteController.class);

    private static final String DEBIT = "B2C_KLT-100005";

    private static final String CREDIT = "B2C_KLT-100006";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private InternetbankHessianService internetbankHessianService;
    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;


    @RequestMapping("completeTradeDebit")
    public void completeTradeDebit(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 个人网银异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(DEBIT);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (b2cCompleteTrade(properties, params)) {
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeTradeCredit")
    public void completeTradeCredit(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 个人网银异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(CREDIT);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (b2cCompleteTrade(properties, params)) {
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @RequestMapping("returnUrlDebit")
    public void returnUrlDebit(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 个人网银同步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(DEBIT);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (b2cCompleteTrade(properties, params)) {
                InterfaceRequestQueryBean interfaceRequestQueryBean=interfaceRequestHessianService.findByInterfaceOrderId(params.get("orderNo"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @RequestMapping("returnUrlCredit")
    public void returnUrlCredit(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 个人网银同步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(CREDIT);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (b2cCompleteTrade(properties, params)) {
                InterfaceRequestQueryBean interfaceRequestQueryBean=interfaceRequestHessianService.findByInterfaceOrderId(params.get("orderNo"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    public boolean b2cCompleteTrade(Properties properties, Map<String, String> params) {
        boolean flag = false;
        try {
            if ("1".equals(params.get("payResult"))) {
                if (!KltUtils.authPay(params, properties.getProperty("key"))) {
                    throw new RuntimeException("开联通 个人网银支付签名验证失败!");
                }
                Map<String, String> internetbankPayResponseBean = new HashMap<>();
                InternetbankSalesResponseBean internetbankSalesResponseBean = new InternetbankSalesResponseBean();
                internetbankPayResponseBean.put("interfaceOrderID", params.get("orderNo"));
                internetbankPayResponseBean.put("interfaceRequestID", params.get("orderNo"));
                internetbankPayResponseBean.put("responseCode", "0000");
                internetbankPayResponseBean.put("responseMessage", "交易成功");
                internetbankPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("orderAmount")), 100d)));
                internetbankPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                internetbankPayResponseBean.put("tranStat", "SUCCESS");
                if ("1".equals(params.get("payType"))) {
                    internetbankPayResponseBean.put("interfaceCode", DEBIT);
                } else if ("11".equals(params.get("payType"))) {
                    internetbankPayResponseBean.put("interfaceCode", CREDIT);
                }
                internetbankSalesResponseBean.setInternetResponseMsg(internetbankPayResponseBean);
                internetbankHessianService.complete(new AuthBean(), internetbankSalesResponseBean);
                flag = true;
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return flag;
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