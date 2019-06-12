package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.RemitHessianService;
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

/**
 * 国美代付异步通知
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/15
 */
@Controller
@RequestMapping("gm100001Notify")
public class GM100001CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(GM100001CompleteController.class);

    private static final String REMIT_INTERFACE_CODE = "GM_100001_REMIT";

    @Resource
    private RemitHessianService remitHessianService;

    @RequestMapping("completeRemit")
    public void completeRemit(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = retrieveParams(request.getParameterMap());
        Map<String,String> remitPayResponseBean = new HashMap<>();
        logger.info("国美代付异步通知报文：{}", params);
        try {
            Map<String, Object> res = getUrlParams(params.get("dstbdata"));
            if ("00".equals(res.get("returncode"))) {
                remitPayResponseBean.put("resCode", "0000");
                remitPayResponseBean.put("tranStat", "SUCCESS");
                remitPayResponseBean.put("resMsg", "成功");
            } else {
                remitPayResponseBean.put("resCode", res.get("returncode").toString());
                remitPayResponseBean.put("tranStat", "FAILED");
                remitPayResponseBean.put("resMsg", "失败|" + res.get("errtext").toString());
            }
            remitPayResponseBean.put("requestNo", res.get("dsorderid").toString());
            remitPayResponseBean.put("interfaceOrderID", res.get("orderid").toString());
            remitPayResponseBean.put("interfaceCode", REMIT_INTERFACE_CODE);
            remitPayResponseBean.put("amount", res.get("amount").toString());
            remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());
            logger.info("央联支付代付 交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
            remitHessianService.complete(remitPayResponseBean);
            response.getWriter().write("00");
        } catch (Exception e) {
            logger.error("国美代付异步通知异常!异常信息:{}", e);
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

    public static Map<String, Object> getUrlParams(String param) {
        Map<String, Object> map = new HashMap<>(0);
        if (StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }
}