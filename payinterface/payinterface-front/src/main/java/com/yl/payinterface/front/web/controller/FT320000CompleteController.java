package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.ToolUtils;
import net.sf.json.JSON;
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
import java.io.PrintWriter;
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
@RequestMapping("/ftNotify")
public class FT320000CompleteController {

    private Logger logger = LoggerFactory.getLogger(FT320000CompleteController.class);
    @Resource
    private WalletpayHessianService walletpayHessianService;
    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    private static final String interfaceCode = "FT320000-ALIPAY_H5";


    @RequestMapping(value = "completePay")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response, PrintWriter pw) {
        logger.info("*************** 付腾 支付宝h5通知START ****************");
        try (BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            logger.info("付腾 支付宝h5通知原文 [{}]", sb.toString());
            Map<String, String> resMap = JsonUtils.toObject(sb.toString(), new TypeReference<Map>() {});
            if ("0000".equals(resMap.get("respCode"))&&"SUCCESS".equals(resMap.get("status"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("interfaceCode", interfaceCode);
                walletPayResponseBean.put("interfaceRequestID", resMap.get("orderNo").toString());
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "000000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", resMap.get("amount").toString());
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
                walletPayResponseBean.put("interfaceOrderID", resMap.get("orderNo").toString());
                walletPayResponseBean.put("payType", "WALLET");
                walletpayHessianService.complete(walletPayResponseBean);
                pw.write("SUCCESS");
                logger.info("付腾 支付宝h5通知响应:SUCCESS");
            }
        } catch (Exception e) {
            logger.error("付腾 支付宝h5通知处理异常:{}",e);
            e.printStackTrace();
        }
        logger.info("*************** 付腾 支付宝h5通知END ****************");
    }

}
