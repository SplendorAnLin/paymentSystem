package com.yl.payinterface.core.handle.impl.remit.hfb100005;


import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.Cnaps;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.hfb100005.utils.CertUtil;
import com.yl.payinterface.core.handle.impl.remit.hfb100005.utils.Httpz;
import com.yl.payinterface.core.handle.impl.remit.hfb100005.utils.ParamUtil;
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
 * 合付宝 银联钱包 实时代付
 * 
 * @author AnLin
 * @version V1.0.0
 * @since 2018/3/26
 */
@Service("remitHfb100005Handler")
public class Hfb100005HandlerImpl implements RemitHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(Hfb100005HandlerImpl.class);

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        Map<String, String> resMap = new HashMap<>();
        try {
            String bankName = map.get("bankName");
            String accountNo = map.get("accountNo");
            String accountName = map.get("accountName");
            String interfaceRequestId = map.get("requestNo");
            String cnapsCode = "";
            Recognition recognition = CommonUtils.recognition(accountNo);
            List<Cnaps> cnaps = CommonUtils.cnapsCode(recognition.getProviderCode());
            if (cnaps != null && cnaps.size() > 0) {
                cnapsCode = cnaps.get(0).getClearingBankCode();
            }
            String amount = map.get("amount");
            String remitAmount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d));
            if (StringUtils.isBlank(cnapsCode)) {
                resMap.put("resCode", "9999");
                resMap.put("resMsg", "该银行卡不支持");
                resMap.put("tranStat", "FAILED");
                resMap.put("requestNo", interfaceRequestId);
                resMap.put("amount", amount);
                resMap.put("compType", BusinessCompleteType.NORMAL.name());
            }
            Map<String, String> transMap = new HashMap<>();
            transMap.put("merchantNo", properties.getProperty("merchantNo"));
            transMap.put("version", properties.getProperty("version"));
            transMap.put("channelNo", properties.getProperty("channelNo"));
            transMap.put("tranCode", properties.getProperty("tranCode"));
            transMap.put("tranFlow", interfaceRequestId);
            transMap.put("tranDate", formatDate(new Date(), "yyyyMMdd"));
            transMap.put("tranTime", formatDate(new Date(), "HHmmss"));
            transMap.put("accNo", CertUtil.getInstance().encrypt(accountNo, properties.getProperty("keyStorePath"), properties.getProperty("cerPath"), properties.getProperty("keyPass")));
            transMap.put("accName", CertUtil.getInstance().encrypt(accountName, properties.getProperty("keyStorePath"), properties.getProperty("cerPath"), properties.getProperty("keyPass")));
            transMap.put("bankAgentId", cnapsCode);
            transMap.put("currency", properties.getProperty("currency"));
            transMap.put("bankName", bankName);
            transMap.put("amount", remitAmount);
            transMap.put("remark", map.get("remark"));
            transMap.put("NOTICEURL", properties.getProperty("noticeUrl"));
            String signMsg = ParamUtil.getSignMsg(transMap);
            String sign = CertUtil.getInstance().sign(signMsg, properties.getProperty("keyStorePath"), properties.getProperty("cerPath"), properties.getProperty("keyPass"));
            transMap.put("sign", sign);
            logger.info("请求报文：" + transMap);
            String asynMsg = new Httpz("utf-8",30000,30000).post(properties.getProperty("remit"), transMap);
            logger.info("返回报文：" + asynMsg);
            Map<String, String> res = urlSplit(asynMsg);
            if (!"0000".equals(res.get("rtnCode")) && !"0002".equals(res.get("rtnCode"))) {
                resMap.put("tranStat", "FAILED");
            } else {
                resMap.put("tranStat", "UNKNOWN");
            }
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", amount);
            resMap.put("requestNo", interfaceRequestId);
            resMap.put("resCode", res.get("rtnCode"));
            resMap.put("resMsg", res.get("rtnMsg"));
        } catch (Exception e) {
            logger.error("异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * @param urlParam 带分隔的url参数
     * @return
     */
    public static Map<String, String> urlSplit(String urlParam){
        Map<String, String> map = new HashMap<String, String>();
        String[] param =  urlParam.split("&");
        for(String keyValue : param){
            String[] pair = keyValue.split("=");
            if(pair.length == 2){
                map.put(pair[0], pair[1]);
            }
        }
        return map;
    }
}