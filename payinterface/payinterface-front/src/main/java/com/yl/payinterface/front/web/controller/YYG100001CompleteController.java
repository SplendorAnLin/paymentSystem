package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 吖吖谷
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/7/19
 */
@Controller
@RequestMapping("/yyg100001Notify")
public class YYG100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(YYG100001CompleteController.class);

    private static final String ALIPAY = "YYG100001-ALIPAY_H5";

    private static final String WXNATIVE = "YYG100002-WXNATIVE";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping(value = "completeTrade", method= RequestMethod.POST, consumes = "application/json")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            logger.info("吖吖谷通知原文：{}", sb.toString());
            Map<String, String> params = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>() {
            });
            if ("1".equals(params.get("orderStatus"))) {
                complete(params);
                response.getWriter().write("{\"resCode\":\"000000\",\"resMessage\":\"通知接收成功\"}");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    private void complete(Map<String, String> params) {
        Map<String, String> walletPayResponseBean = new HashMap<>();
        walletPayResponseBean.put("tranStat", "SUCCESS");
        walletPayResponseBean.put("responseCode", "0000");
        walletPayResponseBean.put("responseMessage", "交易成功");
        walletPayResponseBean.put("amount", params.get("orderAmount"));
        walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
        walletPayResponseBean.put("interfaceOrderID", params.get("serialNo"));
        walletPayResponseBean.put("interfaceRequestID", params.get("orderNo"));
        if ("11".equals(params.get("payType"))) {
            walletPayResponseBean.put("interfaceCode", WXNATIVE);
        } else {
            walletPayResponseBean.put("interfaceCode", ALIPAY);
        }
        walletpayHessianService.complete(walletPayResponseBean);
    }
}