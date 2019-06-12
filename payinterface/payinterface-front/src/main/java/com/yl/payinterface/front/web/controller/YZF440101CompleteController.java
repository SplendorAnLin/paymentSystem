package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.MD5Util;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 云支付 支付宝
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/5
 */
@Controller
@RequestMapping("yzf440101Notify")
public class YZF440101CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(YZF440101CompleteController.class);

    private static final String ALIPAY_H5 = "YZF440101-ALIPAY_H5";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    public static Map<String, String> getSbPay(String sb) {
        String rep = sb.substring(0, sb.indexOf("form-data;") + 11);
        String rep1 = sb.replaceAll(rep, "");
        String rep2 = rep1.substring(0, rep1.indexOf("--------------------------")).replaceAll("name=", "");
        String[] par = rep2.split("\"");
        Map<String, String> map = new HashMap<>();
        for (int i = 1; i < par.length; i++) {
            map.put(par[i], par[i+1]);
            i++;
        }
        return map;
    }

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            logger.info("通知原文：{}", sb.toString());
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        Map<String, String> params = getSbPay(sb.toString());
        logger.info("云支付 支付宝H5后台异步通知处理后报文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY_H5);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
        });
        try {
            if ("1".equals(params.get("fxstatus")) && checkSign(params, properties.getProperty("key"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", params.get("fxfee"));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("fxorder"));
                walletPayResponseBean.put("interfaceRequestID", params.get("fxddh"));
                walletPayResponseBean.put("interfaceCode", ALIPAY_H5);
                walletpayHessianService.complete(walletPayResponseBean);
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("云支付支付宝H5后台异步通知异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeTradeFront")
    public void completeTradeFront(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            logger.info("云支付支付宝H5前台通知原文：{}", sb.toString());
        } catch (IOException e) {
            logger.error("异常信息:{}", e);
        }
    }

    private boolean checkSign(Map<String, String> params, String key){
        StringBuffer sb = new StringBuffer();
        sb.append(params.get("fxstatus"));
        sb.append(params.get("fxid"));
        sb.append(params.get("fxddh"));
        sb.append(params.get("fxfee"));
        sb.append(key);
        if (MD5Util.md5(sb.toString()).equals(params.get("fxsign"))) {
            return true;
        } else {
            return false;
        }
    }
}
