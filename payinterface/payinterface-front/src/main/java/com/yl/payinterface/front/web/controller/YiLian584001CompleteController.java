package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.FileUtils;
import com.yl.payinterface.front.utils.YiLian.RSASignature;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Shark
 * @Description
 * @Date 2018/3/9 20:23
 */
@Controller
@RequestMapping("/yiLian584001Notify")
public class YiLian584001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(YiLian584001CompleteController.class);
    private static final String REMIT_INTERFACE_CODE = "YILIAN_584001_REMIT";
    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;
    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;
    @Resource
    private WalletpayHessianService walletpayHessianService;
    @Resource
    private RemitHessianService remitHessianService;


    @RequestMapping(value = "complete")
    public void completeTrade(Model model, HttpServletRequest request, HttpServletResponse response) {
        SortedMap<String, String> map = getParam(request);
        logger.info("亿联交易通知报文：{}", map);
        String interfaceRequestId = map.get("orderNo");
        InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(interfaceRequestId);

		/*接口配置*/
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(interfaceRequestQueryBean.getInterfaceInfoCode());
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(),new TypeReference<Properties>() {});
        String publicKayPath = properties.getProperty("publicKayPath");

        try {
            String publicKey = FileUtils.readTxtFile(new File(publicKayPath));
            if (RSASignature.checkSign(map, publicKey)) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("interfaceCode", interfaceRequestQueryBean.getInterfaceInfoCode());
                walletPayResponseBean.put("interfaceRequestID", interfaceRequestId);
                if ("1".equals(map.get("payResult"))) {
                    walletPayResponseBean.put("tranStat", "SUCCESS");
                    walletPayResponseBean.put("responseCode", "000000");
                    walletPayResponseBean.put("responseMessage", "交易成功");
                    walletPayResponseBean.put("amount", interfaceRequestQueryBean.getAmount() + "");
                    walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                    walletPayResponseBean.put("completeTime", String.valueOf(map.get("payDatetime")));
                    walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
                    walletPayResponseBean.put("interfaceOrderID", map.get("paymentOrderId"));
                    walletpayHessianService.complete(walletPayResponseBean);
                }
            }
            response.getWriter().write("success");
        } catch (Exception e) {
            logger.info("亿联交易通知报错：{}", e);
        }
    }

    @RequestMapping(value = "completeRemit")
    public void completeRemit(Model model, HttpServletRequest request, HttpServletResponse response) {
        SortedMap<String, String> map = getParam(request);
        logger.info("亿联代付通知报文：{}", map);

        String interfaceRequestId = map.get("orderNo");

        /*接口配置*/
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(REMIT_INTERFACE_CODE);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(),new TypeReference<Properties>() {});
        String publicKayPath = properties.getProperty("publicKayPath");

        try {
            String publicKey = FileUtils.readTxtFile(new File(publicKayPath));
            if (RSASignature.checkSign(map, publicKey)) {
                Map<String,String> remitPayResponseBean = new HashMap<>();
                if ("3".equals(map.get("payResult"))) {
                    remitPayResponseBean.put("resCode", "0000");
                    remitPayResponseBean.put("tranStat", "SUCCESS");
                    remitPayResponseBean.put("resMsg", "成功");
                } else if("4".equals(map.get("payResult"))) {
                    remitPayResponseBean.put("resCode", "-1");
                    remitPayResponseBean.put("tranStat", "FAILED");
                    remitPayResponseBean.put("resMsg", "失败|" + map.get("payResult"));
                } else {
                    remitPayResponseBean.put("tranStat", "UNKNOWN");
                }
                remitPayResponseBean.put("requestNo", interfaceRequestId);
                remitPayResponseBean.put("interfaceOrderID", map.get("paymentOrderId"));
                remitPayResponseBean.put("interfaceCode", REMIT_INTERFACE_CODE);
                remitPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(new Double(map.get("orderAmount")), 100, 2)));
                remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());

                logger.info("央联支付代付 交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
                remitHessianService.complete(remitPayResponseBean);
            }
            response.getWriter().write("success");
        } catch (Exception e) {
            logger.info("亿联代付通知报错：{}", e);
        }
    }




    private SortedMap<String, String> getParam(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();

        SortedMap<String, String> map = new TreeMap<>();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            if (entry.getValue() != null && StringUtils.isNotBlank(entry.getValue()[0])) {
                map.put(entry.getKey(), entry.getValue()[0]);
            }
        }

        return map;
    }
}
