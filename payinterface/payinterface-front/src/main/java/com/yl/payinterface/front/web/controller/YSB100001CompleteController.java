package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.hessian.bean.AuthBean;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.payinterface.front.utils.MD5Util;
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

/**
 * 银生宝 网银通知
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/7
 */
@Controller
@RequestMapping("ysb100001Notify")
public class YSB100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(YSB100001CompleteController.class);

    private static final String YSB = "YSB_100001-B2C";

    private static final String REMIT_INTERFACE_CODE = "YSB_100002_REMIT";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private InternetbankHessianService internetbankHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @Resource
    private RemitHessianService remitHessianService;

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("银生宝个人网银异步通知原文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(YSB);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
        try {
            if ("01".equals(params.get("status")) && verification(params, properties.getProperty("key"))) {
                Map<String, String> internetbankPayResponseBean = new HashMap<>();
                InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(params.get("order_no"));
                InternetbankSalesResponseBean internetbankSalesResponseBean = new InternetbankSalesResponseBean();
                internetbankPayResponseBean.put("interfaceOrderID", params.get("order_no"));
                internetbankPayResponseBean.put("interfaceRequestID", params.get("order_no"));
                internetbankPayResponseBean.put("responseCode", "0000");
                internetbankPayResponseBean.put("responseMessage", "交易成功");
                internetbankPayResponseBean.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
                internetbankPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                internetbankPayResponseBean.put("tranStat", "SUCCESS");
                internetbankPayResponseBean.put("interfaceCode", YSB);
                internetbankSalesResponseBean.setInternetResponseMsg(internetbankPayResponseBean);
                internetbankHessianService.complete(new AuthBean(), internetbankSalesResponseBean);
                response.getWriter().write("OK");
            }
        } catch (Exception e) {
            logger.error("银生宝网银通知异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeRemit")
    public void completeRemit(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        Map<String,String> remitPayResponseBean = new HashMap<>();
        logger.info("银生宝代付异步通知原文：{}", params);
        String interfaceRequestId = params.get("order_no");
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(YSB);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>() {
        });
        try {
            if (verification(params, properties.getProperty("key"))) {
                if ("4".equals(params.get("status"))) {
                    remitPayResponseBean.put("resCode", "0000");
                    remitPayResponseBean.put("tranStat", "SUCCESS");
                    remitPayResponseBean.put("resMsg", "成功");
                } else if ("5".equals(params.get("status"))) {
                    remitPayResponseBean.put("resCode", params.get("error_code"));
                    remitPayResponseBean.put("tranStat", "FAILED");
                    remitPayResponseBean.put("resMsg", "失败|" + params.get("error_message"));
                }
                InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(interfaceRequestId);
                remitPayResponseBean.put("requestNo", interfaceRequestId);
                remitPayResponseBean.put("interfaceOrderID", interfaceRequestId);
                remitPayResponseBean.put("interfaceCode", REMIT_INTERFACE_CODE);
                remitPayResponseBean.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
                remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());
                logger.info("央联支付代付 交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
                remitHessianService.complete(remitPayResponseBean);
                response.getWriter().write("OK");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    private static boolean verification(Map<String, String> params, String key){
        String sign = params.get("sign");
        boolean flag = false;
        String par = MD5Util.md5("order_no=" + params.get("order_no") + "&status=" + params.get("status") + key);
        if (par.equals(sign)) {
            flag = true;
        }
        return flag;
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