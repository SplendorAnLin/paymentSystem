package com.yl.payinterface.front.web.controller;

import cfca.org.bouncycastle.util.encoders.Base64;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.front.utils.Shand100001.CertUtil;
import com.yl.payinterface.front.utils.Shand100001.CryptoUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * 衫德网关支付通知
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/23
 */
@Controller
@RequestMapping("/shandGateway")
public class Shand100001GwCompleteController {

    private static Logger logger = LoggerFactory.getLogger(Shand100001GwCompleteController.class);

    private static Properties properties = new Properties();

    @Resource
    private InternetbankHessianService internetbankHessianService;
    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    static {
        try {
            CertUtil.init("/home/cer/sand/sand.cer", "/home/cer/sand/sand.pfx", "123456");
            properties.load(new InputStreamReader(Shand100001GwCompleteController.class.getClassLoader().getResourceAsStream("serverHost.properties")));
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
            logger.info("衫德网关支付通知原文参数: [{}]", sb.toString());
            String[] paramsStr = sb.toString().split("&");
            Map<String, String> paramsMap = new HashMap<>();
            for (String param : paramsStr) {
                String[] tmp = param.split("=");
                paramsMap.put(tmp[0], tmp.length > 1 ? URLDecoder.decode(tmp[1]) : "");
            }
            JSONObject body = JSONObject.fromObject(JSONObject.fromObject(paramsMap.get("data")).get("body"));
            JSONObject head = JSONObject.fromObject(JSONObject.fromObject(paramsMap.get("data")).get("head"));
            logger.info("衫德网关支付通知 响应体: [{}]", body);
            logger.info("衫德网关支付通知 响应头: [{}]", head);
            boolean flag = CryptoUtil.verifyDigitalSign(paramsMap.get("data").getBytes("UTF-8"), Base64.decode(paramsMap.get("sign")), CertUtil.getPublicKey(), "SHA1WithRSA");
            logger.info("衫德网关支付 签名验证结果: [{}]", flag);
            if (!flag) {
                logger.info("衫德网关支付 签名验证失败: [{}]", sb.toString());
            } else {
                Map<String, String> completeMap = new HashMap<>();
                completeMap.put("interfaceCode", "SD_100001-B2C");
                completeMap.put("interfaceRequestID", String.valueOf(body.get("orderCode")));
                if ("1".equals(String.valueOf(body.get("orderStatus")))) {
                    completeMap.put("tranStat", "SUCCESS");
                } else {
                    completeMap.put("tranStat", "UNKNOW");
                }
                completeMap.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(body.get("totalAmount").toString()), 100d)));
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
                InternetbankSalesResponseBean internetbankResponseBean = new InternetbankSalesResponseBean();
                internetbankResponseBean.setInternetResponseMsg(completeMap);
                request.setAttribute("interfaceRequestID",body.getString("orderCode"));
                logger.error("衫德网关支付回調支付系統：{}", JsonUtils.toJsonString(internetbankResponseBean));
                internetbankHessianService.complete(null, internetbankResponseBean);
                return true;
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, String> req = new HashMap<>();
        req.put("sign", "dB5dyLIQzbDYsqFr0bL3+s5aClXRZr994aTrYD8J/uai/02DGvEN9LCYB5oB1pZIopU1wDR9+rmiqK6fWK88tJhBMO/nA+acmw5NyBjwiZEWk8wzkplQA5sTejeYc0ZdXy4avEs+21f4JIrTf4bhOQe5dnsHtvOAbCJr3f/NqhfP+uu2ferbvLjXxha1uIGATX9Wa7hE8InWUO7408GihCxeORh9bZChe9SmeBDgqeB8CyivdUlOGY+vfgIgzKxghd+Vkr4FTcGTp0C564IwSFZya7lw9xT1Q5VfzUHCU9Slv6tWQmdZRzLfjEpRqMUf6IabdTzA63aZa/uwHIZX7w==");
        req.put("extend", "");
        req.put("signType", "01");
        req.put("data", "{\"body\":{\"orderCode\":\"TD20180627101265050246\",\"tradeNo\":\"2018062709165804620521449729\",\"clearDate\":\"20180627\",\"orderStatus\":\"1\",\"payTime\":\"20180627091658\",\"buyerPayAmount\":\"000000100000\",\"accNo\":\"\",\"midFee\":\"000000000350\",\"totalAmount\":\"000000100000\",\"mid\":\"14066132\",\"discAmount\":\"000000000000\",\"bankserial\":\"\"},\"head\":{\"respCode\":\"000000\",\"respTime\":\"20180627091806\",\"version\":\"1.0\"}}");
        req.put("charset", "UTF-8");
        try {
            System.out.println(HttpClientUtils.send(HttpClientUtils.Method.POST, "http://pay.feiyijj.com/payinterface-front/shandGateway/completeTrade", req, true, "UTF-8"));
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }
}