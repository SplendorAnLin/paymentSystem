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
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.XKL350101.XklSignUtil;
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
 * 新快乐 异步通知处理
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/8
 */
@Controller
@RequestMapping("xkl350101Notify")
public class XKL350101CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(XKL350101CompleteController.class);

    private static final String QQ_NATIVE = "XKL350101-QQ_NATIVE";

    private static final String B2C = "XKL_350102-B2C";

    private static final String ALIPAY_NATIVE = "XKL350102-ALIPAY_H5";

    private static final String ALIPAY_JSAPI = "XKL350104-ALIPAYJSAPI";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @Resource
    private InternetbankHessianService internetbankHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("新快乐 QQ扫码后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(QQ_NATIVE);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            String sign = params.get("sign");
            params.remove("sign");
            if (!sign.equals(XklSignUtil.signData(params, properties.getProperty("key")))) {
                throw new RuntimeException("新快乐 QQ扫码后台异步通知验签失败！");
            }
            if ("S".equals(params.get("orderStatus"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(params.get("orderNo"));
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("orderNo"));
                walletPayResponseBean.put("interfaceRequestID", params.get("orderNo"));
                walletPayResponseBean.put("interfaceCode", QQ_NATIVE);
                walletpayHessianService.complete(walletPayResponseBean);
            }
            response.getWriter().write("success");
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    @RequestMapping("completeAliPayTrade")
    public void completeAliPayTrade(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("新快乐 AliPay后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY_NATIVE);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            String sign = params.get("sign");
            params.remove("sign");
            if (!sign.equals(XklSignUtil.signData(params, properties.getProperty("key")))) {
                throw new RuntimeException("新快乐 AliPay后台异步通知验签失败！");
            }
            if ("S".equals(params.get("orderStatus"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(params.get("orderNo"));
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("orderNo"));
                walletPayResponseBean.put("interfaceRequestID", params.get("orderNo"));
                walletPayResponseBean.put("interfaceCode", ALIPAY_NATIVE);
                walletpayHessianService.complete(walletPayResponseBean);
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    @RequestMapping("completeAlijsapiTrade")
    public void completeAlijsapiTrade(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("新快乐 AliJSapiPay后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY_JSAPI);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            String sign = params.get("sign");
            params.remove("sign");
            if (!sign.equals(XklSignUtil.signData(params, properties.getProperty("key")))) {
                throw new RuntimeException("新快乐  AliJSapiPay后台异步通知验签失败！");
            }
            if ("S".equals(params.get("orderStatus"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(params.get("orderNo"));
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("orderNo"));
                walletPayResponseBean.put("interfaceRequestID", params.get("orderNo"));
                walletPayResponseBean.put("interfaceCode", ALIPAY_JSAPI);
                walletpayHessianService.complete(walletPayResponseBean);
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeGatewayPay")
    public void completeGatewayPay(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("新快乐 网银后台异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(QQ_NATIVE);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            String sign = params.get("sign");
            params.remove("sign");
            if (!sign.equals(XklSignUtil.signData(params, properties.getProperty("key")))) {
                throw new RuntimeException("新快乐 网银后台异步通知验签失败！");
            }
            if ("S".equals(params.get("orderStatus"))) {
                gatewayPay(params);
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    @RequestMapping("completeGatewayPayPage")
    public void completeGatewayPayPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("新快乐 前台同步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(QQ_NATIVE);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            String sign = params.get("sign");
            params.remove("sign");
            if (!sign.equals(XklSignUtil.signData(params, properties.getProperty("key")))) {
                throw new RuntimeException("新快乐 前台同步通知验签失败！");
            }
            if ("S".equals(params.get("orderStatus"))) {
                gatewayPay(params);
                InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(params.get("orderNo"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
    }

    /**
     * 网银统一处理
     * @param params
     */
    public void gatewayPay(Map<String, String> params) {
        InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(params.get("orderNo"));
        Map<String, String> internetbankPayResponseBean = new HashMap<>();
        InternetbankSalesResponseBean internetbankSalesResponseBean = new InternetbankSalesResponseBean();
        internetbankPayResponseBean.put("interfaceOrderID", params.get("orderNo"));
        internetbankPayResponseBean.put("interfaceRequestID", params.get("orderNo"));
        internetbankPayResponseBean.put("interfaceCode", B2C);
        internetbankPayResponseBean.put("responseCode", "0000");
        internetbankPayResponseBean.put("responseMessage", "交易成功");
        internetbankPayResponseBean.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
        internetbankPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
        internetbankPayResponseBean.put("tranStat", "SUCCESS");
        internetbankSalesResponseBean.setInternetResponseMsg(internetbankPayResponseBean);
        internetbankHessianService.complete(new AuthBean(), internetbankSalesResponseBean);
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