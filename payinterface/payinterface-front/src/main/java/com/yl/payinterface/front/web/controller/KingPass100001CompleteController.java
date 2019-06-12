package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.QuickPayHessianService;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.kingpass.KingPassUtils;
import com.yl.payinterface.front.utils.kingpass.RSASignUtil;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Shark
 * @Description
 * @Date 2018/5/30 20:45
 */
@Controller
@RequestMapping("/kingPass100001Notify")
public class KingPass100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(KingPass100001CompleteController.class);

    private static final String UNIONPAY_INTERFACE_CODE = "KINGPASS100001-UNIONPAY_NATIVE";

    private static final String QUICKPAY_INTERFACE_CODE = "QUICKPAY_KINGPASS-100001";

    private static final String REMIT_INTERFACE_CODE = "KingPass_100001_REMIT";

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private QuickPayHessianService quickPayHessianService;

    @Resource
    private RemitHessianService remitHessianService;

    @RequestMapping(value = "completeUnionPay")
    public void completeUnionPay(Model model, HttpServletRequest request, HttpServletResponse response) {
        logger.info("九派银联支付成功通知开始");
        Map<String, String> map = getParam(request);
        logger.info("九派银联支付成功通知报文：{}", map);
        String serverSign = map.get("serverSign");
        String serverCert = map.get("serverCert");
        map.remove("serverSign");
        map.remove("serverCert");
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(UNIONPAY_INTERFACE_CODE);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
        });
        String certFilePath = properties.getProperty("certFilePath");
        String rootCertPath = properties.getProperty("rootCertPath");
        String password = properties.getProperty("password");
        RSASignUtil rsaSignUtil = new RSASignUtil(certFilePath, password);
        rsaSignUtil.setRootCertPath(rootCertPath);
        String res = KingPassUtils.mapToStr(map);
        try {
            boolean flag = rsaSignUtil.verify(res, serverSign, serverCert, "UTF-8");
            if (flag) {
                if ("PD".equals(map.get("orderSts"))) {
                    Map<String, String> walletPayResponseBean = new HashMap<>();
                    walletPayResponseBean.put("interfaceCode", UNIONPAY_INTERFACE_CODE);
                    walletPayResponseBean.put("interfaceRequestID", map.get("orderId"));
                    walletPayResponseBean.put("tranStat", "SUCCESS");
                    walletPayResponseBean.put("responseCode", "000000");
                    walletPayResponseBean.put("responseMessage", "交易成功");
                    walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(map.get("amount")), 100d)));
                    walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                    walletPayResponseBean.put("completeTime", String.valueOf(map.get("payTime")));
                    walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
                    walletPayResponseBean.put("interfaceOrderID", "");
                    walletpayHessianService.complete(walletPayResponseBean);
                }
            }
            response.getWriter().write("success");
        } catch (Exception e) {
            logger.info("九派银联支付成功通知错误：{}", e);
        }
    }

    @RequestMapping(value = "completeQuickPay")
    public void completeQuickPay(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = getParam(request);
        logger.info("九派人脸快捷支付成功通知报文：{}", map);
        String serverSign = map.get("serverSign");
        String serverCert = map.get("serverCert");
        map.remove("serverSign");
        map.remove("serverCert");
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(UNIONPAY_INTERFACE_CODE);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
        });
        String certFilePath = properties.getProperty("cer");
        String rootCertPath = properties.getProperty("p12");
        String password = properties.getProperty("passWord");
        RSASignUtil rsaSignUtil = new RSASignUtil(certFilePath, password);
        rsaSignUtil.setRootCertPath(rootCertPath);
        String res = KingPassUtils.mapToStr(map);
        try {
            if (rsaSignUtil.verify(res, serverSign, serverCert, "UTF-8") && "PD".equals(map.get("orderSts"))) {
                Map<String, String> completeMap = new HashMap<>();
                completeMap.put("interfaceCode", QUICKPAY_INTERFACE_CODE);
                completeMap.put("interfaceRequestID", map.get("orderId"));
                completeMap.put("tranStat", "SUCCESS");
                completeMap.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(map.get("amount")), 100d)));
                completeMap.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                completeMap.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                completeMap.put("businessCompleteType", BusinessCompleteType.NORMAL.toString());
                quickPayHessianService.complete(completeMap);
                response.getWriter().write("SUCCESS");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @RequestMapping(value = "completeRemit")
    public void completeRemit(HttpServletRequest request, HttpServletResponse response) {
        logger.info("*************** 九派代付通知START ***************");
        try {
            Map<String, String> resMap = new HashMap<>();
            resMap.put("charset", request.getParameter("charset"));
            resMap.put("version", request.getParameter("version"));
            resMap.put("merchantId", request.getParameter("merchantId"));
            resMap.put("txTyp", request.getParameter("txTyp"));
            resMap.put("orderNo", request.getParameter("orderNo"));
            resMap.put("accType", request.getParameter("accType"));
            resMap.put("accNo", request.getParameter("accNo"));
            resMap.put("accName", request.getParameter("accName"));
            resMap.put("stlMercId", request.getParameter("stlMercId"));
            resMap.put("amount", request.getParameter("amount"));
            resMap.put("fee", request.getParameter("fee"));
            resMap.put("crdType", request.getParameter("crdType"));
            resMap.put("lBnkNo", request.getParameter("lBnkNo"));
            resMap.put("lBnkNam", request.getParameter("lBnkNam"));
            resMap.put("idType", request.getParameter("idType"));
            resMap.put("idInfo", request.getParameter("idInfo"));
            resMap.put("validPeriod", request.getParameter("validPeriod"));
            resMap.put("cvv2", request.getParameter("cvv2"));
            resMap.put("cellPhone", request.getParameter("cellPhone"));
            resMap.put("txDesc", request.getParameter("txDesc"));
            resMap.put("bnkRsv", request.getParameter("bnkRsv"));
            resMap.put("reqReserved1", request.getParameter("reqReserved1"));
            resMap.put("reqReserved2", request.getParameter("reqReserved2"));
            resMap.put("orderSts", request.getParameter("orderSts"));
            resMap.put("signType", request.getParameter("signType"));
            resMap.put("serverCert", request.getParameter("serverCert"));
            resMap.put("serverSign", request.getParameter("serverSign"));
            logger.info("九派代付通知 [{}]", resMap);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(REMIT_INTERFACE_CODE);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
            });
            if (checkSign(resMap, properties.getProperty("rootCertPath"))) {
                Map<String, String> remitPayResponseBean = new HashMap<>();
                if ("S".equals(resMap.get("orderSts"))) {
                    remitPayResponseBean.put("resCode", "1");
                    remitPayResponseBean.put("tranStat", "SUCCESS");
                    remitPayResponseBean.put("resMsg", "付款成功");
                } else {
                    response.getWriter().write("FAILED");
                    throw new RuntimeException("order status unknown : " + resMap.get("orderSts"));
                }
                remitPayResponseBean.put("requestNo", String.valueOf(resMap.get("orderNo")).replaceAll("X", "-"));
                remitPayResponseBean.put("interfaceOrderID", "");
                remitPayResponseBean.put("interfaceCode", REMIT_INTERFACE_CODE);
                remitPayResponseBean.put("amount", String.valueOf((int) AmountUtils.multiply(Double.valueOf(resMap.get("amount")), 100)));
                remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());
                logger.info("九派代付 交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
                remitHessianService.complete(remitPayResponseBean);
                response.getWriter().write("result=SUCCESS");
            } else {
                throw new RuntimeException("order sign check error");
            }
        } catch (Exception e) {
            logger.error("九派代付通知处理异常：{}", e);
            try {
                response.getWriter().write("FAILED");
            } catch (IOException e1) {
                logger.error("九派代付响应端异常：{}", e1);
            }
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

    private static boolean checkSign(Map<String, String> params, String rootCertPath) {
        String serverSign = params.get("serverSign");
        String serverCert = params.get("serverCert");
        Map responseMap = new HashMap();
        responseMap.putAll(params);
        Set set1 = params.keySet();
        Iterator iterator1 = set1.iterator();
        while (iterator1.hasNext()) {
            String key0 = (String) iterator1.next();
            String tmp = params.get(key0);
            if (com.lefu.commons.utils.lang.StringUtils.equals(tmp, "null") || com.lefu.commons.utils.lang.StringUtils.isBlank(tmp)) {
                responseMap.remove(key0);
            }
        }
        RSASignUtil rsautil = new RSASignUtil(rootCertPath);
        String sf = rsautil.coverMap2String(responseMap);
        try {
            return rsautil.verify(sf, serverSign, serverCert, "utf-8");
        } catch (Exception e) {
            logger.error("九派代付通知报文 验签异常 : ", e);
        }
        return false;
    }
}