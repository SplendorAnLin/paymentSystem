package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.payinterface.front.utils.HFB100005.CertUtil;
import com.yl.payinterface.front.utils.HFB100005.ParamUtil;
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
 * 合付宝 银联钱包代付异步通知
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/26
 */
@Controller
@RequestMapping("/hfb100005Notify")
public class HFB100005CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(HFB100005CompleteController.class);

    private static final String REMIT_INTERFACE_CODE = "HFB_100005_REMIT";

    @Resource
    private RemitHessianService remitHessianService;

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @RequestMapping("completeRemit")
    public void completeRemit(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("合付宝 银联钱包代付异步通知报文：{}", JsonUtils.toJsonString(params));
        try {
            String interfaceRequestId = params.get("tranFlow");
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(REMIT_INTERFACE_CODE);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(),new TypeReference<Properties>(){});
            String paramsSign = params.get("sign");
            params.remove("sign");
            String signMsg = ParamUtil.getSignMsg(params);
            boolean sign = CertUtil.getInstance().verify(signMsg, paramsSign, properties.getProperty("keyStorePath"), properties.getProperty("cerPath"), properties.getProperty("keyPass"));
            if (!sign) {
                throw new Exception("签名错误!");
            }
            Map<String,String> remitPayResponseBean = new HashMap<>();
            if ("0000".equals(params.get("rtnCode"))) {
                remitPayResponseBean.put("resCode", "0000");
                remitPayResponseBean.put("tranStat", "SUCCESS");
                remitPayResponseBean.put("resMsg", "成功");
            } else {
                remitPayResponseBean.put("resCode", params.get("rtnCode"));
                remitPayResponseBean.put("tranStat", "FAILED");
                remitPayResponseBean.put("resMsg", "失败|" + params.get("rtnMsg"));
            }
            remitPayResponseBean.put("requestNo", interfaceRequestId);
            remitPayResponseBean.put("interfaceOrderID", interfaceRequestId);
            remitPayResponseBean.put("interfaceCode", REMIT_INTERFACE_CODE);
            remitPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(new Double(params.get("amount")), 100, 2)));
            remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());
            logger.info("央联支付代付 交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
            remitHessianService.complete(remitPayResponseBean);
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
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