package com.yl.payinterface.core.handle.impl.remit.xkl350103;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.Cnaps;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.wallet.xkl350101.utils.XklSignUtil;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 新快乐 代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/8
 */
@Service("remitXkl350103HandlerImpl")
public class Xkl350103HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Xkl350103HandlerImpl.class);

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String interfaceRequestId = map.get("requestNo");
        String amount = map.get("amount");
        String remitAmount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(amount), 100d));
        String accountName = map.get("accountName");
        String cerNo = map.get("cerNo");
        String accountNo = map.get("accountNo");
        String bankName = map.get("bankName");
        String accBankNo = "";
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
        }
        List<Cnaps> cnaps = CommonUtils.cnapsCode(bankCode);
        if (cnaps != null && cnaps.size() > 0) {
            accBankNo = cnaps.get(0).getClearingBankCode();
        }
        try {
            resMap.put("merNo", properties.getProperty("merNo"));
            resMap.put("orderNo", interfaceRequestId);
            resMap.put("transAmt", remitAmount);
            resMap.put("phoneNo", properties.getProperty("phone"));
            resMap.put("customerName", accountName);
            resMap.put("cerdId", cerNo);
            resMap.put("acctNo", accountNo);
            resMap.put("accBankName", bankName);
            resMap.put("accBankNo", accBankNo);
            resMap.put("isT1", properties.getProperty("isT1"));
            resMap.put("sign", XklSignUtil.signData(resMap, properties.getProperty("key")));
            Map<String, String> res = XklSignUtil.httpGet(resMap, properties.getProperty("url"));
            if ("S".equals(res.get("respType")) || "R".equals(res.get("respType"))) {
                resMap.put("tranStat", "UNKNOWN");
                resMap.put("resCode", res.get("respType") + " | S处理中 R未知");
            } else {
                resMap.put("tranStat", "FAILED");
                resMap.put("resCode", res.get("respType") + " | E失败");
            }
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resMsg", res.get("respMsg"));
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
        Map<String, String> resMap = new HashMap<>();
        Map<String,String> remitPayResponseBean = new HashMap<>();
        try {
            resMap.put("merNo", properties.getProperty("merNo"));
            resMap.put("orderNo", interfaceRequestId);
            resMap.put("sign", XklSignUtil.signData(resMap, properties.getProperty("key")));
            Map<String, String> res = XklSignUtil.httpGet(resMap, properties.getProperty("query"));
            if ("S".equals(res.get("respType"))) {
                remitPayResponseBean.put("resCode", "0000");
                remitPayResponseBean.put("tranStat", "SUCCESS");
                remitPayResponseBean.put("resMsg", "成功");
                remitPayResponseBean.put("requestNo", interfaceRequestId);
                remitPayResponseBean.put("interfaceOrderID", interfaceRequestId);
                remitPayResponseBean.put("interfaceCode", interfaceInfoCode);
                remitPayResponseBean.put("amount", res.get("transAmt"));
                remitPayResponseBean.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                remitPayResponseBean.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                remitPayResponseBean.put("compType", BusinessCompleteType.NORMAL.toString());
                logger.info("千付宝代付交易完成 通知接口核心原文 [{}]", remitPayResponseBean);
            }
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return remitPayResponseBean;
    }
}