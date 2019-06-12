package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.AuthPayHessianService;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
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
 * 开联通 认证支付通知处理
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
@Controller
@RequestMapping("klt100001Notify")
public class KLT100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(KLT100001CompleteController.class);

    private static final String AUTHPAY = "AUTHPAY_KLT-100001";

    private static final String AUTHPAY_DEBIT = "AUTHPAY_KLT-100002";

    private static final String AUTHPAY_CREDIT = "AUTHPAY_KLT-100003";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;
    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @Resource
    private AuthPayHessianService authPayHessianService;

    @RequestMapping("completeAuthPay")
    public void completeAuthPay(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 认证支付借贷合一后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(AUTHPAY);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (authPay(properties, params)) {
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    @RequestMapping("completeAuthPayPage")
    public void completeAuthPayPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 认证支付借贷合一前台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(AUTHPAY);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (authPay(properties, params)) {
                InterfaceRequestQueryBean interfaceRequestQueryBean=interfaceRequestHessianService.findByInterfaceOrderId(params.get("orderNo"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    @RequestMapping("completeAuthPayDebit")
    public void completeAuthPayDebit(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 认证支付借记卡后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(AUTHPAY_DEBIT);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (authPay(properties, params)) {
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    @RequestMapping("completeAuthPayPageDebit")
    public void completeAuthPayPageDebit(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 认证支付借记卡前台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(AUTHPAY_DEBIT);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (authPay(properties, params)) {
                InterfaceRequestQueryBean interfaceRequestQueryBean=interfaceRequestHessianService.findByInterfaceOrderId(params.get("orderNo"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    @RequestMapping("completeAuthPayCredit")
    public void completeAuthPayCredit(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 认证支付信用卡后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(AUTHPAY_CREDIT);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (authPay(properties, params)) {
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    @RequestMapping("completeAuthPayPageCredit")
    public void completeAuthPayPageCredit(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 认证支付信用卡前台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(AUTHPAY_CREDIT);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (authPay(properties, params)) {
                InterfaceRequestQueryBean interfaceRequestQueryBean=interfaceRequestHessianService.findByInterfaceOrderId(params.get("orderNo"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    /**
     * 认证支付完成订单
     * @param properties
     * @param params
     * @return
     */
    public boolean authPay(Properties properties, Map<String, String> params) {
        boolean flag = false;
        try {
            if ("1".equals(params.get("payResult"))) {
                if (!KltUtils.authPay(params, properties.getProperty("key"))) {
                    throw new RuntimeException("开联通 认证支付签名验证失败!");
                }
                Map<String, String> authPayResponseBean = new HashMap<>();
                authPayResponseBean.put("tranStat", "SUCCESS");
                authPayResponseBean.put("responseCode", "0000");
                authPayResponseBean.put("responseMessage", "交易成功");
                authPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("orderAmount")), 100d)));
                authPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                authPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                authPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                authPayResponseBean.put("interfaceOrderID", params.get("orderNo"));
                authPayResponseBean.put("interfaceRequestID", params.get("orderNo"));
                if ("99".equals(params.get("payType"))) {
                    authPayResponseBean.put("interfaceCode", AUTHPAY);
                } else if ("39".equals(params.get("payType"))) {
                    authPayResponseBean.put("interfaceCode", AUTHPAY_DEBIT);
                } else if ("40".equals(params.get("payType"))) {
                    authPayResponseBean.put("interfaceCode", AUTHPAY_CREDIT);
                }
                authPayHessianService.complete(authPayResponseBean);
                flag = true;
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
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