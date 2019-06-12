package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.RemitHessianService;
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
 * 维基D0代付异步通知
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/23
 */
@Controller
@RequestMapping("/lucky100002Notify")
public class Lucky100002CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(Lucky100002CompleteController.class);

    private static final String REMIT_INTERFACE_CODE = "LUCK_100002_REMIT";

    @Resource
    private RemitHessianService remitHessianService;

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    @RequestMapping("completeRemit")
    public void completeRemit(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("维基D0代付异步通知报文：{}", JsonUtils.toJsonString(params));
        try {
            String interfaceRequestId = params.get("requestOrder");
            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(REMIT_INTERFACE_CODE);
            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(),new TypeReference<Properties>(){});
            String sign = md5(properties.getProperty("key"), params.get("merchantNo"), params.get("requestOrder"), params.get("orderAmount"),
                    params.get("returnInfo"), params.get("codeUrl"), params.get("responseCode"), params.get("responseDesc"),
                    params.get("orderStatus"));
            if (!sign.equals(params.get("sign"))) {
                throw new RuntimeException("Lucky Pay D0代付验签失败!");
            }
            Map<String,String> remitPayResponseBean = new HashMap<>();
            if ("0000".equals(params.get("responseCode")) && "S".equals(params.get("orderStatus"))) {
                remitPayResponseBean.put("resCode", "0000");
                remitPayResponseBean.put("tranStat", "SUCCESS");
                remitPayResponseBean.put("resMsg", "成功");
            } else {
                remitPayResponseBean.put("resCode", params.get("responseCode"));
                remitPayResponseBean.put("tranStat", "FAILED");
                remitPayResponseBean.put("resMsg", "失败|" + params.get("responseDesc"));
            }
            remitPayResponseBean.put("requestNo", interfaceRequestId);
            remitPayResponseBean.put("interfaceOrderID", interfaceRequestId);
            remitPayResponseBean.put("interfaceCode", REMIT_INTERFACE_CODE);
            remitPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(new Double(params.get("orderAmount")), 100, 2)));
            remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());
            logger.info("央联支付代付 交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
            remitHessianService.complete(remitPayResponseBean);
            response.getWriter().write("0000");
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