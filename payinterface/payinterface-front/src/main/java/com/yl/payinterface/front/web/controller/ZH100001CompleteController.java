package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 众横QT
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/9
 */
@Controller
@RequestMapping("/ZH100001Notify")
public class ZH100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(ZH100001CompleteController.class);

    private static final String ALIPAY = "ZH100001-ALIPAY_H5";

    private static final String WXNATIVE = "ZH100002-WXNATIVE";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @RequestMapping("completeTradeWx")
    public void completeTradeWx(HttpServletRequest request, HttpServletResponse response){
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(WXNATIVE);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
        completeTrade(request, response, properties, WXNATIVE);
    }

    @RequestMapping("completeTradeAp")
    public void completeTradeAp(HttpServletRequest request, HttpServletResponse response){
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
        completeTrade(request, response, properties, ALIPAY);
    }

    private void completeTrade(HttpServletRequest request, HttpServletResponse response, Properties properties, String interfaceCode){
        try {
            Map<String, String> headerMap = new HashMap<>();
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = (String) headerNames.nextElement();
                String value = request.getHeader(key);
                headerMap.put(key, value);
            }
            BufferedReader reader = null;
            reader = request.getReader();
            String line = "";
            String jsonStr = null;
            StringBuffer inputString = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                inputString.append(line);
            }
            jsonStr = inputString.toString();
            logger.info("header：{}, 通知报文：{}", headerMap.toString(), jsonStr);
            String qfSign = headerMap.get("x-qf-sign");
            String newSign = MD5Util.md5(jsonStr + properties.getProperty("key")).toUpperCase();
            if (qfSign.equalsIgnoreCase(newSign)){
                Map<String, String> params = JsonUtils.toObject(jsonStr, new TypeReference<Map<String, String>>() {
                });
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("txamt")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("syssn"));
                walletPayResponseBean.put("interfaceRequestID", params.get("out_trade_no"));
                walletPayResponseBean.put("interfaceCode", interfaceCode);
                logger.info("完成信息：{}, Hessian:{}", walletPayResponseBean, walletpayHessianService);
                walletpayHessianService.complete(walletPayResponseBean);
                response.getWriter().print("SUCCESS");
            } else {
                new RuntimeException("签名验证失败！");
            }
        } catch (Exception e){
            logger.error("验证签名失败：{}", e);
        }
    }
}