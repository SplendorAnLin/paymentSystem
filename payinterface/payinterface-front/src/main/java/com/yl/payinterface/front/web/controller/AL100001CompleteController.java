package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.front.utils.AL100001.AlPayUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 爱农 网银异步通知
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/8
 */
@Controller
@RequestMapping("/al100001Notify")
public class AL100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(AL100001CompleteController.class);

    private static final String interfaceInfoCode = "AL_100001-B2C";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private InternetbankHessianService internetbankHessianService;
    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping("complete")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("爱农 网银异步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(interfaceInfoCode);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (!params.get("signature").equals(AlPayUtils.getSign(params, properties.getProperty("key")))) {
                throw new RuntimeException("爱农网银通知签名错误!");
            }
            if ("1001".equals(params.get("respCode"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("txnAmt")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("merOrderId"));
                walletPayResponseBean.put("interfaceRequestID", params.get("merOrderId"));
                walletPayResponseBean.put("interfaceCode", interfaceInfoCode);
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("爱农网银通知异常!异常信息:{}", e);
        }
    }

    @RequestMapping("returnUrl")
    public void returnUrl(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("爱农 网银同步通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(interfaceInfoCode);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            if (!params.get("signature").equals(AlPayUtils.getSign(params, properties.getProperty("key")))) {
                throw new RuntimeException("爱农网银通知签名错误!");
            }
            if ("1001".equals(params.get("respCode"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("txnAmt")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("merOrderId"));
                walletPayResponseBean.put("interfaceRequestID", params.get("merOrderId"));
                walletPayResponseBean.put("interfaceCode", interfaceInfoCode);
                InterfaceRequestQueryBean interfaceRequestQueryBean=interfaceRequestHessianService.findByInterfaceOrderId(params.get("merOrderId"));
                response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
            }
        } catch (Exception e) {
            logger.error("爱农网银通知异常!异常信息:{}", e);
        }
    }

    /**
     * 参数转换
     */
    private Map<String, String> retrieveParams(Map<String, String[]> requestMap) {
        Map<String, String> resultMap = new HashMap<String, String>();
        for (String key : requestMap.keySet()) {
            String[] value = requestMap.get(key);
            if (value != null) {
                resultMap.put(key, Array.get(value, 0).toString().trim());
            }
        }
        return resultMap;
    }
}