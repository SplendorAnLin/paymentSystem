package com.yl.payinterface.core.handle.impl.remit.shand100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.InterfaceRequestQueryBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.b2c.shand100001.utils.CertUtil;
import com.yl.payinterface.core.handle.impl.remit.shand100001.utils.ConfigurationManager;
import com.yl.payinterface.core.handle.impl.remit.shand100001.utils.EncyptUtil;
import com.yl.payinterface.core.handle.impl.remit.shand100001.utils.ShandHttpUtil;
import com.yl.payinterface.core.handle.impl.remit.shand100001.utils.utils;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.hessian.InterfaceRequestHessianService;
import org.apache.http.NameValuePair;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 杉德代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2017年2月23日
 */
@Service("shandHandler100001")
public class Shand100001HandlerImpl implements RemitHandler {

    private static Logger logger = LoggerFactory.getLogger(Shand100001HandlerImpl.class);

    @Resource
    private InterfaceRequestHessianService interfaceRequestHessianService;

    public static void main(String[] args) {
        String url = "https://caspay.sandpay.com.cn/agent-main/openapi/agentpay";
        String mid = "14066132";
        String query = "https://caspay.sandpay.com.cn/agent-main/openapi/queryOrder";
        Properties properties = new Properties();
        properties.setProperty("url", url);
        properties.setProperty("mid", mid);
        properties.setProperty("query", query);
        Map<String, String> params = new HashMap<>();
        params.put("tradeConfigs", JsonUtils.toJsonString(properties));
        params.put("requestNo", "DF1530059794966");
        params.put("orderTime", "20180628145723");
        params.put("amount", "2");
        params.put("accountNo", "6215583202002031321");
        params.put("accountName", "周林");
        params.put("remark", "结算款");
        params.put("interfaceInfoCode", "shandHandler100001");
        new Shand100001HandlerImpl().query(params);
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        Map<String, String> respMap = new HashMap<>();
        Map<String, String> reqParams = new HashMap<>();
        reqParams.put("orderCode", getInterfaceRequestId(map.get("requestNo")));
        reqParams.put("version", "01");
        reqParams.put("productId", "00000004");
        reqParams.put("tranTime", map.get("orderTime"));
        if (map.get("amount").length() != 12) {
            String tmpAmt = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
            for (; ; ) {
                tmpAmt = "0" + tmpAmt;
                if (tmpAmt.length() == 12) {
                    reqParams.put("tranAmt", tmpAmt);
                    break;
                }
            }
        } else {
            reqParams.put("tranAmt", map.get("amount"));
        }
        reqParams.put("currencyCode", "156");
        reqParams.put("accAttr", "0");
        reqParams.put("accNo", map.get("accountNo"));
        reqParams.put("accType", "4");
        reqParams.put("accName", map.get("accountName"));
        reqParams.put("remark", map.get("remark"));
        logger.info("杉德代付下单  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), reqParams);
        try {
            ConfigurationManager.loadProperties(new String[]{"dsfpconfig"});
            ShandHttpUtil httpUtil = new ShandHttpUtil();
            String data = null;
            try {
                data = httpUtil.post(properties.getProperty("url"), properties.getProperty("mid"), "RTPM", JsonUtils.toJsonString(reqParams));
                logger.info("杉德代付下单  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), data);
            } catch (Exception e) {
                logger.error("杉德代付下单异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), e);
            }
            if (data == null) {
                respMap.put("resCode", "9999");
                respMap.put("resMsg", "网络异常");
                respMap.put("tranStat", "FAILED");
                respMap.put("requestNo", map.get("requestNo"));
                respMap.put("amount", map.get("amount"));
                return respMap;
            }
            Map<String, String> resData = JsonUtils.toObject(data, new TypeReference<Map<String, String>>() {
            });
            InterfaceRequestQueryBean interfaceRequestQueryBean = interfaceRequestHessianService.findRequestByInterfaceReqId(map.get("requestNo"));
            respMap.put("requestNo", map.get("requestNo"));
            respMap.put("compType", BusinessCompleteType.NORMAL.name());
            respMap.put("amount", String.valueOf(interfaceRequestQueryBean.getAmount()));
            respMap.put("interfaceOrderID", resData.get("sandSerial"));
            respMap.put("resCode", resData.get("respCode"));
            respMap.put("resMsg", resData.get("respDesc"));
            if ("0000".equals(resData.get("respCode"))) {
                if (resData.get("resultFlag").equals("0")) {
                    respMap.put("tranStat", "SUCCESS");
                } else if (resData.get("resultFlag").equals("1")) {
                    respMap.put("tranStat", "FAILED");
                } else {
                    respMap.put("tranStat", "UNKNOWN");
                }
            } else if ("0001".equals(resData.get("respCode")) || "0002".equals(resData.get("respCode")) || "0003".equals(resData.get("respCode"))
                    || "0004".equals(resData.get("respCode"))) {
                if (resData.get("resultFlag").equals("1")) {
                    respMap.put("tranStat", "FAILED");
                } else {
                    respMap.put("tranStat", "UNKNOWN");
                }
            } else {
                respMap.put("tranStat", "FAILED");
            }
        } catch (Exception e) {
            logger.error("杉德代付下单异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), e);
            throw new RuntimeException(e.getMessage());
        }
        return respMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        Map<String, String> reqParams = new HashMap<>();
        Map<String, String> respMap = new HashMap<>();
        reqParams.put("orderCode", getInterfaceRequestId(map.get("requestNo")));
        reqParams.put("version", "01");
        reqParams.put("productId", "00000004");
        reqParams.put("tranTime", map.get("orderTime"));
        logger.info("杉德代付查询  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), reqParams);
        try {
            ConfigurationManager.loadProperties(new String[]{"dsfpconfig"});
            ShandHttpUtil httpUtil = new ShandHttpUtil();
            String data = httpUtil.post(properties.getProperty("query"), properties.getProperty("mid"), "ODQU", JsonUtils.toJsonString(reqParams));
            logger.info("杉德代付查询  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), data);
            Map<String, String> resData = JsonUtils.toObject(data, new TypeReference<Map<String, String>>() {
            });
            respMap.put("requestNo", map.get("requestNo"));
            respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
            respMap.put("amount", map.get("amount"));
            respMap.put("interfaceOrderID", resData.get("sandSerial"));
            respMap.put("resCode", resData.get("origRespCode"));
            respMap.put("resMsg", resData.get("origRespDesc"));
            if (resData.get("respCode").equals("0000")) {
                if (resData.get("resultFlag").equals("0")) {
                    respMap.put("tranStat", "SUCCESS");
                } else if (resData.get("resultFlag").equals("1")) {
                    respMap.put("tranStat", "FAILED");
                } else {
                    respMap.put("tranStat", "UNKNOWN");
                }
            } else {
                if (resData.get("resultFlag").equals("1")) {
                    respMap.put("tranStat", "FAILED");
                } else {
                    respMap.put("tranStat", "UNKNOWN");
                }
            }
        } catch (Exception e) {
            logger.error("杉德代付查询异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"), map.get("requestNo"), e);
            throw new RuntimeException(e.getMessage());
        }
        return respMap;
    }

    /**
     * 订单号处理
     */
    public String getInterfaceRequestId(String requestId){
        requestId = requestId.replaceAll("DF", "");
        return requestId.replaceAll("-", "");
    }
}