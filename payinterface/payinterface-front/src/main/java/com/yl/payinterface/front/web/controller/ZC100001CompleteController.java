package com.yl.payinterface.front.web.controller;

import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
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

/**
 * 中辰支付宝
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/17
 */
@Controller
@RequestMapping("/zc100001Notify")
public class ZC100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(ZC100001CompleteController.class);

    private static final String ALIPAY = "ZF100001-ALIPAY_H5";

    private static final String ALIPAY_H5 = "ZF100002-ALIPAY_H5";

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("中辰支付宝后台异步通知原文：{}", params);
        try {
            if ("SUCCESS".equals(params.get("status"))) {
                complete(params, ALIPAY);
                response.getWriter().write("SUCCESS");
            }
        } catch (Exception e) {
            logger.error("中辰支付宝异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeTradeH5")
    public void completeTradeH5(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("中辰支付宝H5后台异步通知原文：{}", params);
        try {
            if ("SUCCESS".equals(params.get("status"))) {
                complete(params, ALIPAY_H5);
                response.getWriter().write("SUCCESS");
            }
        } catch (Exception e) {
            logger.error("中辰支付宝H5异常!异常信息:{}", e);
        }
    }

    private void complete(Map<String, String> params, String ALIPAY) {
        Map<String, String> walletPayResponseBean = new HashMap<>();
        walletPayResponseBean.put("tranStat", "SUCCESS");
        walletPayResponseBean.put("responseCode", "0000");
        walletPayResponseBean.put("responseMessage", "交易成功");
        walletPayResponseBean.put("amount", params.get("transAmt"));
        walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
        walletPayResponseBean.put("interfaceOrderID", params.get("transId"));
        walletPayResponseBean.put("interfaceRequestID", params.get("orderId"));
        walletPayResponseBean.put("interfaceCode", ALIPAY);
        walletpayHessianService.complete(walletPayResponseBean);
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