package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

/**
 * 花生QQ扫码完成回调
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/13
 */
@Controller
@RequestMapping("hs100001Notify")
public class HS100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(HS100001CompleteController.class);

    private static final String QQ = "HS100001-QQ_NATIVE";

    private static final String JD = "HS100002-JD_H5";

    private static final String REMIT = "";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> params = retrieveParams(request);
        String sign = params.get("sign");
        params.remove("sign");
        logger.info("花生QQ扫码通知原文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(QQ);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
        try {
            if (verification(params, properties.getProperty("key"), sign) && "01".equals(params.get("ordStatus"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("ordAmt")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("prdOrdNo"));
                walletPayResponseBean.put("interfaceRequestID", params.get("custOrderNo"));
                walletPayResponseBean.put("interfaceCode", QQ);
                walletpayHessianService.complete(walletPayResponseBean);
                response.getWriter().write("SC000000");
            }
        } catch (Exception e) {
            logger.error("花生QQ扫码异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeTradeJD")
    public void completeTradeJD(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> params = retrieveParams(request);
        logger.info("花生JDH5通知原文：{}", params);
        String sign = params.get("sign");
        params.remove("sign");
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(JD);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
        try {
            if (verification(params, properties.getProperty("key"), sign) && "01".equals(params.get("ordStatus"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("ordAmt")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("prdOrdNo"));
                walletPayResponseBean.put("interfaceRequestID", params.get("custOrderNo"));
                walletPayResponseBean.put("interfaceCode", JD);
                walletpayHessianService.complete(walletPayResponseBean);
                response.getWriter().write("SC000000");
            }
        } catch (Exception e) {
            logger.error("花生JDH5异常!异常信息:{}", e);
        }
    }

    @RequestMapping(value = "completeRemit")
    public void completeRemit(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request);
        logger.info("花生JDH5通知原文：{}", params);
        String sign = params.get("sign");
        params.remove("sign");
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(JD);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
        try {

        } catch (Exception e) {
            logger.error("花生代付通知异常!异常信息:{}", e);
        }
    }

    private SortedMap<String, String> retrieveParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        SortedMap<String, String> map = new TreeMap<>();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue()[0])) {
                map.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return map;
    }

    public static boolean verification(Map<String, String> map, String key, String sign) {
        if (map == null) {
            return false;
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
        }
        sb.append("key=" + key);
        String s = sb.toString();
        logger.info("签名原文：{}", s);
        if (sign.equalsIgnoreCase(MD5Util.md5(s))) {
            return true;
        } else {
            return false;
        }
    }
}