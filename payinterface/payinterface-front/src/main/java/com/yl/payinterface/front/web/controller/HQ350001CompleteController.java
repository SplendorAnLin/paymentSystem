package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.MD5Util;
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
 * 环球支付异步回调
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/18
 */
@Controller
@RequestMapping("hq350001Notify")
public class HQ350001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(HQ350001CompleteController.class);

    private static final String ALIPAY = "HQ350001-ALIPAY_H5";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping("complete")
    public void completeAuthPay(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("环球支付 支付宝H5后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (complete(properties, params)) {
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    @RequestMapping("completePay")
    public void completeAuthPayPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("环球支付 支付宝H5前台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (complete(properties, params)) {
                InterfaceRequestQueryBean interfaceRequestQueryBean=interfaceRequestHessianService.findByInterfaceOrderId(params.get("out_order_no"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    /**
     * 交易完成处理
     * @param properties
     * @param params
     * @return
     */
    public boolean complete(Properties properties, Map<String, String> params) {
        boolean flag = false;
        String sing = params.get("sign");
        if (!getSign(params.get("out_order_no"), params.get("total_fee"), params.get("trade_status"), properties.getProperty("partner"),
                properties.getProperty("key")).equals(sing)) {
            throw new RuntimeException("环球支付 支付宝H5 签名验证失败!");
        }
        if ("TRADE_SUCCESS".equals(params.get("trade_status"))) {
            Map<String, String> walletPayResponseBean = new HashMap<>();
            walletPayResponseBean.put("tranStat", "SUCCESS");
            walletPayResponseBean.put("responseCode", "0000");
            walletPayResponseBean.put("responseMessage", "交易成功");
            walletPayResponseBean.put("amount", params.get("total_fee"));
            walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
            walletPayResponseBean.put("interfaceOrderID", params.get("trade_no"));
            walletPayResponseBean.put("interfaceRequestID", params.get("out_order_no"));
            walletPayResponseBean.put("interfaceCode", ALIPAY);
            walletpayHessianService.complete(walletPayResponseBean);
            flag = true;
        }
        return flag;
    }

    /**
     * 计算MD5
     * @param args
     * @return
     */
    public String getSign(String... args) {
        String sign = "";
        StringBuffer text = new StringBuffer();
        for(String s : args) {
            if(s != null) {
                text.append(s);
            }
        }
        sign = MD5Util.md5(text.toString());
        return sign;
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