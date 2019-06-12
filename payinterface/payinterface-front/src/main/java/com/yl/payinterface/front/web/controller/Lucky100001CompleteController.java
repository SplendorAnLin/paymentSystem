package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
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
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 维基支付 京东H5 完成回调
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/12
 */
@Controller
@RequestMapping("/lucky100001Notify")
public class Lucky100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(Lucky100001CompleteController.class);

    private static final String interfaceInfoCode = "LUCKY100001-JD_H5";

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @Resource
    private WalletpayHessianService walletpayHessianService;

    @RequestMapping("complete")
    public void completeTrade(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("维基支付 京东H5通知原文：{}", params);
        try {
            String interfaceRequestId = params.get("requestOrder");
            InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(interfaceRequestId);
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(interfaceRequestQueryBean.getInterfaceInfoCode());
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            String sign = md5(properties.getProperty("key"), params.get("merchantNo"), params.get("requestOrder"), params.get("orderAmount"),
                    params.get("returnInfo"), params.get("codeUrl"), params.get("responseCode"), params.get("responseDesc"),
                    params.get("orderStatus"));
            if (!sign.equals(params.get("sign"))) {
                throw new RuntimeException("Lucky Pay 验签失败!");
            }
            Map<String, String> walletPayResponseBean = new HashMap<>();
            walletPayResponseBean.put("interfaceCode", interfaceRequestQueryBean.getInterfaceInfoCode());
            walletPayResponseBean.put("interfaceRequestID", interfaceRequestId);
            if ("S".equals(params.get("orderStatus"))) {
                walletPayResponseBean.put("tranStat", "SUCCESS");
                walletPayResponseBean.put("responseCode", "000000");
                walletPayResponseBean.put("responseMessage", "交易成功");
                walletPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(params.get("orderAmount")), 100d)));
                walletPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                walletPayResponseBean.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                walletPayResponseBean.put("interfaceOrderID", params.get("requestOrder"));
                walletPayResponseBean.put("interfaceRequestID", params.get("requestOrder"));
                walletPayResponseBean.put("interfaceCode", interfaceInfoCode);
                walletpayHessianService.complete(walletPayResponseBean);
            }
            response.getWriter().write("0000");
        } catch (Exception e) {
            logger.error("维基支付 京东H5通知异常！信息:{}", e);
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

    /**
     * MD5 验签
     * @param key
     * @param args
     * @return
     * @throws Exception
     */
    public static String md5(String key, String... args) throws Exception {
        StringBuffer text = new StringBuffer();
        for(String s : args) {
            if(s != null) {
                text.append(s);
            }
        }
        text.append(key);
        byte[] bytes = text.toString().getBytes("UTF-8");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bytes);
        bytes = messageDigest.digest();
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < bytes.length; i ++) {
            if((bytes[i] & 0xff) < 0x10) {
                result.append("0");
            }
            result.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return result.toString().toUpperCase();
    }
}