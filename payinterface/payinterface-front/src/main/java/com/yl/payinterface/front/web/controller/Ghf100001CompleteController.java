package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.GHF100001.SignUtils;
import org.codehaus.jackson.type.TypeReference;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 刚好付 支付宝h5通知
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2016年4月24日
 */
@Controller
@RequestMapping("/ghfNotify")
public class Ghf100001CompleteController {

    private Logger logger = LoggerFactory.getLogger(Ghf100001CompleteController.class);
    @Resource
    private WalletpayHessianService walletpayHessianService;
    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    private static final String interfaceCode = "GHF100001-ALIPAY_H5";

    @RequestMapping(value = "completePay")
    public void completeTrade(Model model, HttpServletRequest request, HttpServletResponse response) {
        logger.info("*************** 刚好付 支付宝h5通知START ****************");
        try (BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"))) {
            SortedMap<String, String> returnMap = new TreeMap<>();

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            logger.info("刚好付 支付宝h5通知原文 [{}]", sb.toString());
            Map<String, String> resMap = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>() {
            });

            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(interfaceCode);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
            });

            if (SignUtils.checkSign(resMap, properties.getProperty("key"))) {
                if ("SUCCESS".equals(resMap.get("pay_status"))) {
                    Map<String, String> walletPayResponseBean = new HashMap<>();
                    walletPayResponseBean.put("interfaceCode", interfaceCode);
                    walletPayResponseBean.put("interfaceRequestID", String.valueOf(resMap.get("out_trade_no")));
                    walletPayResponseBean.put("tranStat", "SUCCESS");
                    walletPayResponseBean.put("amount", String.valueOf(Double.parseDouble((String) resMap.get("total_fee")) / 100));
                    walletPayResponseBean.put("responseCode", String.valueOf(resMap.get("result_code")));
                    walletPayResponseBean.put("responseMessage", "交易成功");
                    walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                    walletPayResponseBean.put("completeTime", String.valueOf(resMap.get("payed_time")));
                    walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
                    walletPayResponseBean.put("interfaceOrderID", String.valueOf(resMap.get("transaction_id")));
                    walletPayResponseBean.put("payType", "WALLET");
                    walletpayHessianService.complete(walletPayResponseBean);

                    returnMap.put("return_code", "0");
                    returnMap.put("return_msg", "OK");
                    response.getWriter().write(JsonUtils.toJsonString(returnMap));
                }
            }
        } catch (Exception e) {
            logger.error("刚好付 支付宝h5通知处理异常：{}", e);
        }

    }

}
