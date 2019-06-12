package com.yl.payinterface.core.handle.impl.remit.ft320001;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handle.impl.wallet.cncb330000.MD5;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.CommonUtils;
import com.yl.payinterface.core.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.omg.CORBA.UNKNOWN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 新快乐 代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/8
 */
@Service("remitFt320001Handler")
public class Ft320001HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Ft320001HandlerImpl.class);

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> tradeConfigs = new HashMap<>();
        tradeConfigs.put("customer_no", "882312502725113");
        tradeConfigs.put("key", "CB0DAA2541CCD7BB3C0FEB85DCFEAA70");
        tradeConfigs.put("bank_url", "http://120.27.194.146:8081/H5PayOnline/groupH5");
        tradeConfigs.put("notify_url", "http://m.bank-pay.com:9966/groupH5");
        map.put("tradeConfigs", JsonUtils.toJsonString(tradeConfigs));
        map.put("amount", "1");
        map.put("accountName", "张凯");
        map.put("accountNo", "6222023202040783036");
        map.put("bankName", "工商银行");
//        map.put("requestNo", "test-" + System.currentTimeMillis());
        map.put("requestNo", "test-1533267958158");
        map.put("interfaceInfoCode", "test-1533267958158");

        Ft320001HandlerImpl gx100000Handler = new Ft320001HandlerImpl();
        gx100000Handler.query(map);
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String interfaceRequestId = map.get("requestNo");
        String amount = map.get("amount");
        String accountName = map.get("accountName");
        String accountNo = map.get("accountNo");
        String bankName = map.get("bankName");
        Recognition recognition = CommonUtils.recognition(accountNo);
        String bankCode = recognition.getProviderCode();
        Map<String, String> resMap = new HashMap<>();
        if (StringUtils.isBlank(bankCode)) {
            resMap.put("resCode", "9999");
            resMap.put("resMsg", "该银行卡不支持");
            resMap.put("tranStat", "FAILED");
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("amount", amount);
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            return resMap;
        }

        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("amNumber", properties.getProperty("customer_no"));
        reqMap.put("amount", amount);
        reqMap.put("accname", accountName);
        reqMap.put("accno", accountNo);
        reqMap.put("bankName", bankName);
        reqMap.put("subBranch", bankName);
        reqMap.put("province", "北京");
        reqMap.put("city", "北京");

        ArrayList<String> paramNames = new ArrayList<>(reqMap.keySet());
        Collections.sort(paramNames);
        // 组织待签名信息
        StringBuilder signSource = new StringBuilder();
        for (String paramName : paramNames) {
            signSource.append(paramName + "=" + reqMap.get(paramName) + "&");
        }
        System.out.println("签名字符串:"+signSource.toString().substring(0, signSource.length() - 1) + properties.getProperty("key"));
        String signature = MD5.MD5Encode(signSource.toString().substring(0, signSource.length() - 1) + properties.getProperty("key"));

        reqMap.put("payNumber", interfaceRequestId);
        reqMap.put("tradeType", "yPayAnother");
        reqMap.put("signature", signature);
        String resStr;
        Map<String, String> jsonParams;
        try {
            logger.info("付腾 代付下单:[{}], 请求报文:[{}]", interfaceRequestId, reqMap);
            resStr = HttpUtil.sendPost(properties.getProperty("bank_url"), reqMap);
            logger.info("付腾 代付下单:[{}], 响应报文:[{}]", interfaceRequestId, URLDecoder.decode(resStr));
            jsonParams = JsonUtils.toObject(resStr, new TypeReference<Map>() {
            });

            if ("0000".equals(jsonParams.get("respCode"))) {
                resMap.put("tranStat", "UNKNOWN");
                resMap.put("resCode", jsonParams.get("respCode") + " | 处理中");
            } else {
                resMap.put("tranStat", "FAILED");
                resMap.put("resCode", jsonParams.get("respCode") + " | 失败");
            }
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resMsg", jsonParams.get("respMsg"));
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String interfaceRequestId = map.get("requestNo");
        String interfaceInfoCode = map.get("interfaceInfoCode");
        Map<String, String> reqMap = new HashMap<>();
        Map<String,String> remitPayResponseBean = new HashMap<>();

        reqMap.put("amNumber", properties.getProperty("customer_no"));
        ArrayList<String> paramNames = new ArrayList<>(reqMap.keySet());
        Collections.sort(paramNames);
        // 组织待签名信息
        StringBuilder signSource = new StringBuilder();
        for (String paramName : paramNames) {
            signSource.append(paramName + "=" + reqMap.get(paramName) + "&");
        }
        String signature = MD5.MD5Encode(signSource.toString().substring(0, signSource.length() - 1) + properties.getProperty("key"));

        reqMap.put("payNumber", interfaceRequestId);
        reqMap.put("signature", signature);
        reqMap.put("tradeType", "yQueryPayAnother");

        String resStr;
        Map<String, String> jsonParams;
        try {
            logger.info("付腾 代付查询:[{}], 请求报文:[{}]", interfaceRequestId, reqMap);
            resStr = HttpUtil.sendPost(properties.getProperty("bank_url"), reqMap);
            logger.info("付腾 代付查询:[{}], 响应报文:[{}]", interfaceRequestId, resStr);
            jsonParams = JsonUtils.toObject(resStr, new TypeReference<Map>() {
            });
            if ("0000".equals(jsonParams.get("respCode"))&&"1".equals(jsonParams.get("status"))) {
                remitPayResponseBean.put("resCode", "0000");
                remitPayResponseBean.put("tranStat", "SUCCESS");
                remitPayResponseBean.put("resMsg", "成功");
            }else {
                remitPayResponseBean.put("tranStat", "UNKNOWN");
                remitPayResponseBean.put("resCode", jsonParams.get("status"));
            }
            remitPayResponseBean.put("requestNo", interfaceRequestId);
            remitPayResponseBean.put("interfaceOrderID", interfaceRequestId);
            remitPayResponseBean.put("interfaceCode", interfaceInfoCode);
            remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());
            logger.info("付腾 代付查询交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return remitPayResponseBean;
    }
}