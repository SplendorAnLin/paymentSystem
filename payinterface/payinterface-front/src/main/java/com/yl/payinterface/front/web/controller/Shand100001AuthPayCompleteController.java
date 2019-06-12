package com.yl.payinterface.front.web.controller;

import cfca.org.bouncycastle.util.encoders.Base64;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.AuthPayHessianService;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.front.utils.Shand100001.CertUtil;
import com.yl.payinterface.front.utils.Shand100001.CryptoUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 杉德 一键绑卡
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/27
 */
@Controller
@RequestMapping("/shandNotify")
public class Shand100001AuthPayCompleteController {

    private static final Logger logger = LoggerFactory.getLogger(Shand100001AuthPayCompleteController.class);

    @Resource
    private AuthPayHessianService authPayHessianService;

    private static Properties properties = new Properties();
    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    static {
        try {
            CertUtil.init("/home/cer/sand/sand.cer", "/home/cer/sand/sand.pfx", "123456");
            properties.load(new InputStreamReader(Shand100001AuthPayCompleteController.class.getClassLoader().getResourceAsStream("serverHost.properties")));
        } catch (Exception e) {
            logger.error("衫德扫码支付加载证书异常:{}", e);
        }
    }

    @RequestMapping(value = "completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (complete(request, response)) {
                response.getWriter().write("respCode=000000");
            }
        } catch (Exception e) {
            logger.error("衫德网关支付通知处理异常：{}", e);
        }
    }

    @RequestMapping(value = "completeTradeFront")
    public void completeTradeFront(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (complete(request, response)) {
                InterfaceRequestQueryBean interfaceRequestQueryBean=interfaceRequestHessianService.findByInterfaceOrderId(request.getParameter("interfaceRequestID"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("衫德网关支付通知处理异常：{}", e);
        }
    }

    private boolean complete(HttpServletRequest request, HttpServletResponse response){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            logger.info("衫德一键快捷支付通知原文参数: [{}]", sb.toString());
            String[] paramsStr = sb.toString().split("&");
            Map<String, String> paramsMap = new HashMap<>();
            for (String param : paramsStr) {
                String[] tmp = param.split("=");
                paramsMap.put(tmp[0], tmp.length > 1 ? URLDecoder.decode(tmp[1]) : "");
            }
            JSONObject body = JSONObject.fromObject(JSONObject.fromObject(paramsMap.get("data")).get("body"));
            JSONObject head = JSONObject.fromObject(JSONObject.fromObject(paramsMap.get("data")).get("head"));
            logger.info("衫德一键快捷支付通知 响应体: [{}]", body);
            logger.info("衫德一键快捷支付通知 响应头: [{}]", head);
            boolean flag = CryptoUtil.verifyDigitalSign(paramsMap.get("data").getBytes("UTF-8"), Base64.decode(paramsMap.get("sign")), CertUtil.getPublicKey(), "SHA1WithRSA");
            logger.info("衫德一键快捷支付 签名验证结果: [{}]", flag);
            if (!flag) {
                logger.info("衫德一键快捷支付 签名验证失败: [{}]", sb.toString());
            } else {
                Map<String, String> completeMap = new HashMap<>();
                completeMap.put("interfaceCode", "AUTHPAY_SHAND-100001");
                completeMap.put("interfaceRequestID", body.getString("orderCode"));
                if ("1".equals(body.getString("orderStatus"))) {
                    completeMap.put("tranStat", "SUCCESS");
                } else {
                    completeMap.put("tranStat", "UNKNOWN");
                }
                completeMap.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(body.getString("totalAmount")), 100d)));
                completeMap.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                completeMap.put("completeTime", body.getString("payTime"));
                completeMap.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
                completeMap.put("responseCode", head.getString("respCode"));
                if (head.containsKey("respMsg")) {
                    completeMap.put("responseMessage", head.getString("respMsg"));
                } else {
                    completeMap.put("responseMessage", "");
                }
                completeMap.put("interfaceOrderID", body.getString("tradeNo"));
                request.setAttribute("interfaceRequestID",body.getString("orderCode"));
                logger.error("衫德一键快捷支付回調支付系統：{}", JsonUtils.toJsonString(completeMap));
                authPayHessianService.complete(completeMap);
                return true;
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return false;
    }
}