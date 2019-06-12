package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
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
 * 开联通 银联钱包
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
@Controller
@RequestMapping("klt100003Notify")
public class KLT100003CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(KLT100003CompleteController.class);

    private static final String UNIONPAY = "KLT100003-UNIONPAY_NATIVE";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping("completeAuthPay")
    public void completeAuthPay(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("开联通 银联钱包后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(UNIONPAY);
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
            logger.info("开联通 银联钱包前台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(UNIONPAY);
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
                    throw new RuntimeException("开联通 银联钱包签名验证失败!");
                }
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("orderAmount")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("orderNo"));
                walletPayResponseBean.put("interfaceRequestID", params.get("orderNo"));
                walletPayResponseBean.put("interfaceCode", UNIONPAY);
                walletpayHessianService.complete(walletPayResponseBean);
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