package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
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
 * 支付通 京东钱包
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/6
 */
@Controller
@RequestMapping("zft100001Notify")
public class ZTF100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(ZTF100001CompleteController.class);

    private static final String JD = "ZTF100001-JD_H5";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("支付通 京东钱包后台异步通知原文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(JD);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
        try {
            if ("10000".equals(params.get("dealCode"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("orderAmount")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("cxOrderNo"));
                walletPayResponseBean.put("interfaceRequestID", params.get("orderNo"));
                walletPayResponseBean.put("interfaceCode", JD);
                walletpayHessianService.complete(walletPayResponseBean);
                response.getWriter().write("SUCCESS");
            }
        } catch (Exception e) {
            logger.error("支付通京东钱包异步通知异常!异常信息:{}", e);
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