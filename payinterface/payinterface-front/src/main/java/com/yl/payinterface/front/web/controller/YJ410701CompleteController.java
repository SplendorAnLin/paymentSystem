package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.front.utils.YJ410701.YjSignUtils;
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
 * 云尖支付 异步回调
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/5/22
 */
@Controller
@RequestMapping("/yj410701Notify")
public class YJ410701CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(YJ410701CompleteController.class);

    private static final String ALIPAY_H5 = "YJ410701-ALIPAY_H5";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping("completeTrade")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("云尖支付 支付宝H5后台异步通知原文：{}", params);
        InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY_H5);
        Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
        try {
            if ("1".equals(params.get("status"))) {
                if (!YjSignUtils.checkSign(properties.getProperty("key"), params.get("sign"), params)) {
                    throw new RuntimeException("云尖支付 支付宝H5后台异步验签失败！");
                }
                Map<String, String> walletPayResponseBean = new HashMap<>();
                InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(params.get("sdorderno"));
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "0000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("sdorderno"));
                walletPayResponseBean.put("interfaceRequestID", params.get("sdorderno"));
                walletPayResponseBean.put("interfaceCode", ALIPAY_H5);
                walletpayHessianService.complete(walletPayResponseBean);
                response.getWriter().write("success");
            }
        } catch (Exception e) {
            logger.error("云尖支付 支付宝H5后台异步通知异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeGatewayPayPage")
    public void completeGatewayPayPage(HttpServletRequest request,HttpServletResponse response) {
        // 通道方不建议在同步通知页面处理交易结果，最终结果以异步通知为准。
        try {
            Map<String, String> params = retrieveParams(request.getParameterMap());
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(ALIPAY_H5);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(params.get("sdorderno"));
            response.sendRedirect(properties.getProperty("gateway") + "callback/wechatNative.htm?orderCode="+interfaceRequestQueryBean.getBussinessOrderID());
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
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