package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.HFB100003.CertUtil;
import com.yl.payinterface.front.utils.HFB100003.ParamUtil;
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
 * 合付宝 银联钱包支付回调
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/20
 */
@Controller
@RequestMapping("/hfb100003Notify")
public class HFB100003CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(HFB100001CompleteController.class);

    private static final String interfaceInfoCode = "HFB100003-UNIONPAY_NATIVE";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @RequestMapping("complete")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            logger.info("合付宝 银联钱包通知原文：{}", params);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(interfaceInfoCode);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            String paramsSign = params.get("sign");
            params.remove("sign");
            String signMsg = ParamUtil.getSignMsg(params);
            boolean sign = CertUtil.getInstance().verify(signMsg, paramsSign, properties.getProperty("keyStorePath"), properties.getProperty("cerPath"), properties.getProperty("keyPass"));
            if (!sign) {
                throw new Exception("签名错误!");
            }
            if ("0000".equals(params.get("rtnCode"))) {
                Map<String, String> walletPayResponseBean = new HashMap<>();
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("amount")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("paySerialNo"));
                walletPayResponseBean.put("interfaceRequestID", params.get("tranFlow"));
                walletPayResponseBean.put("interfaceCode", interfaceInfoCode);
                walletpayHessianService.complete(walletPayResponseBean);
            }
        } catch (Exception e) {
            logger.error("完成回调处理失败！异常信息:{}", e);
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
