package com.yl.payinterface.front.web.controller;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.payinterface.front.utils.Base64Utils;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
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
 * 开联通 代付异步通知
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/1
 */
@Controller
@RequestMapping("klt100004Notify")
public class KLT100004CompleteController {

    private static final Logger logger = LoggerFactory.getLogger(KLT100004CompleteController.class);

    private static final String REMIT_INTERFACE_CODE = "KLT_100004_REMIT";

    @Resource
    private RemitHessianService remitHessianService;

    @Resource
    private PayInterfaceHessianService payInterfaceHessianService;

    public static void main(String[] args) {
        try {
            String da = "PHJlc3BvbnNlPjxlbnZlbG9wZT48aGVhZD48dmVyc2lvbj52MS4wLjcuNjwvdmVyc2lvbj48Y2hhcnNldD5VVEYtODwvY2hhcnNldD48L2hlYWQ+PGJvZHk+PHJlc3BvbnNlQ29kZT5FMDAwMDwvcmVzcG9uc2VDb2RlPjxvcmdPcmRlck5vPjIwMTgwNjAxMTU0MDQxNTMxNTwvb3JnT3JkZXJObz48b3JkZXJObz5URDIwMTgwNjAxMTAwNjg5NzA2MTY5PC9vcmRlck5vPjxzdGF0dXM+VFhfU1VDQ0VTUzwvc3RhdHVzPjxvcmRlckFtb3VudD4xMjUwMDA8L29yZGVyQW1vdW50PjxhY2NvdW50VHlwZT5QRVJTT05BTDwvYWNjb3VudFR5cGU+PG1lcmNoYW50SWQ+MTA1ODQwMTgwNTEwMDAzPC9tZXJjaGFudElkPjxvcmRlckRhdGV0aW1lPjIwMTgwNjAxMTU0MDQxPC9vcmRlckRhdGV0aW1lPjxzdGF0dXNEZXNjPuS6pOaYk+aIkOWKnzwvc3RhdHVzRGVzYz48L2JvZHk+PC9lbnZlbG9wZT48c2lnbj48c2lnblR5cGU+MTwvc2lnblR5cGU+PGNlcnRpZmljYXRlPjwvY2VydGlmaWNhdGU+PHNpZ25Db250ZW50PmdBbklSeTNFWEsrS3EybWpqc2t2YVR3V1gvUWF5QlMySmIrNTNYaXk1Z2dxWUhoZFA5NVpUMXRocWRCbnBFdFUxdzFiQVZFbzhIRlovMGlVMXFkMWNOd1Q0cmhyaThMV0VwRUhOUnZVNXFHQ3lXTDhlbE1hL0FMVHh4MkY0Rm9xNDNBY1ExU1ZHZkVmZWJtblg2c0x0QVJ6RmpVNE9OY0ZkcjlINHlUNHFhYXVKM1p1Wm1mdU5wUm5XdzRBSVFZdXFValEwKzYydm9QVXFpVnl3c3FnSjRXakJtQ1VTVGxBUUlRcWVDQjcxVDR2MDZTVTZLZFFycEJqN0YvZEZCclVSb1V3TWEwcUN3REFtazVKMGJUTEhXVlcxOE9WUVFENUFFSW4yZFBvWC9oN1NXMEREZWkycDNlU0x4TXo5cVBhcDFqOVpFS3RVL0ZsOEZJcElFQVBnQT09PC9zaWduQ29udGVudD48L3NpZ24+PC9yZXNwb25zZT4=";
            String res = new String(Base64Utils.decode(da), "UTF-8");
            XMLSerializer xmlSerializer = new XMLSerializer();
            JSONObject result = JSONObject.fromObject(xmlSerializer.read(res));
            String interfaceRequestId = result.getJSONObject("envelope").getJSONObject("body").getString("orderNo");
            System.out.println(interfaceRequestId);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
    }

    @RequestMapping("completeRemit")
    public void completeRemit(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> remitPayResponseBean = new HashMap<>();
        Map<String, String> params = retrieveParams(request.getParameterMap());
        logger.info("开联通 代付异步通知报文：{}", JsonUtils.toJsonString(params));
        try {
//            InterfaceInfoBean interfaceInfoBean = payInterfaceHessianService.interfaceInfoQueryByCode(REMIT_INTERFACE_CODE);
//            Properties properties = JsonUtils.toObject(interfaceInfoBean.getTradeConfigs(), new TypeReference<Properties>(){});
            String res = new String(Base64Utils.decode(params.get("respMsg")));
            XMLSerializer xmlSerializer = new XMLSerializer();
            JSONObject result = JSONObject.fromObject(xmlSerializer.read(res).toString());
            String interfaceRequestId = result.getJSONObject("envelope").getJSONObject("body").getString("orderNo");
            if ("E0000".equals(result.getJSONObject("envelope").getJSONObject("body").getString("responseCode"))) {
                if ("TX_SUCCESS".equals(result.getJSONObject("envelope").getJSONObject("body").getString("status"))) {
                    remitPayResponseBean.put("resCode", "0000");
                    remitPayResponseBean.put("tranStat", "SUCCESS");
                    remitPayResponseBean.put("resMsg", "成功");
                } else {
                    remitPayResponseBean.put("resCode", result.getJSONObject("envelope").getJSONObject("body").getString("responseCode"));
                    remitPayResponseBean.put("tranStat", "FAILED");
                    remitPayResponseBean.put("resMsg", result.getJSONObject("envelope").getJSONObject("body").getString("statusDesc"));
                }
            }
            remitPayResponseBean.put("requestNo", interfaceRequestId);
            remitPayResponseBean.put("interfaceOrderID", interfaceRequestId);
            remitPayResponseBean.put("interfaceCode", REMIT_INTERFACE_CODE);
            remitPayResponseBean.put("amount", String.valueOf(AmountUtils.divide(new Double(result.getJSONObject("envelope").getJSONObject("body").getString("orderAmount")), 100, 2)));
            remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());
            logger.info("央联支付代付 交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
            remitHessianService.complete(remitPayResponseBean);
            response.getWriter().write("success");
        } catch (Exception e) {
            logger.error("开联通代付异步通知异常!异常信息:{}", e);
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