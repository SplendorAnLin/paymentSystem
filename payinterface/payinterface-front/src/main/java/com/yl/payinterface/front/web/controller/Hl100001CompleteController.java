package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
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
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 汇利异步通知
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/10
 */
@Controller
@RequestMapping("hl100001Notify")
public class Hl100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(Hl100001CompleteController.class);

    private static final String ALIPAY_H5 = "HL100001-ALIPAY_H5";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            logger.info("汇利通知原文：{}", sb.toString());
        } catch (Exception e) {
            logger.error("汇利通知接收异常!异常信息:{}", e);
        }
        Map<String, String> params = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>() {
        });
        logger.info("汇利支付宝H5后台异步通知处理后报文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY_H5);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
        });
        try {
            if ("1".equals(params.get("status"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("total_fee")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("traid"));
                walletPayResponseBean.put("interfaceRequestID", params.get("out_trade_no"));
                walletPayResponseBean.put("interfaceCode", ALIPAY_H5);
                walletpayHessianService.complete(walletPayResponseBean);
                response.getWriter().write("ok");
            }
        } catch (Exception e) {
            logger.error("汇利订单完成处理异常!异常信息：{}", e);
        }
    }
}