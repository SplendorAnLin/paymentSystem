package com.yl.payinterface.core.handle.impl.remit.hs100001;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.Cnaps;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.hs100001.utils.HttpUtil;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.utils.CommonUtils;
import com.yl.payinterface.core.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

/**
 * 花生代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/28
 */
@Service("hs100001Handler")
public class Hs100001HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Hs100001HandlerImpl.class);

    @Resource
    private InterfaceRequestService interfaceRequestService;

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("orgNo", "8180600384");
        properties.setProperty("custId", "18061100002006");
        properties.setProperty("notifyUrl", "https://kd.alblog.cn/front/callback");
        properties.setProperty("key", "39AB18F00AA43154DBFCE9CD2325A96A");
        properties.setProperty("url", "http://121.196.196.167:8889/tran/cashier/TX0001.ac");
        properties.setProperty("query", "http://121.196.196.167:8889/tran/cashier/TX0002.ac");
        Map<String, String> params = new HashMap<>();
        params.put("amount", "4");
        params.put("accountNo", "6215583202002031321");
        params.put("accountName", "周林");
        params.put("bankName", "中国工商银行东西湖支行");
        params.put("requestNo", "TD20180630101536607625");
        params.put("tradeConfigs", JsonUtils.toJsonString(properties));
        new Hs100001HandlerImpl().query(params);
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        Map<String, String> req = new TreeMap<>();
        Map<String, String> resMap = new HashMap<>();
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
        String accountNo = map.get("accountNo");
        String accountName = map.get("accountName");
        String bankName = map.get("bankName");
        String cnapsCode = "";
        Recognition recognition = CommonUtils.recognition(accountNo);
        List<Cnaps> cnaps = CommonUtils.cnapsCode(recognition.getProviderCode());
        if (cnaps != null && cnaps.size() > 0) {
            cnapsCode = cnaps.get(0).getClearingBankCode();
        }
        if (StringUtils.isBlank(cnapsCode)) {
            resMap.put("resCode", "9999");
            resMap.put("resMsg", "该银行卡不支持");
            resMap.put("tranStat", "FAILED");
            resMap.put("requestNo", map.get("requestNo"));
            resMap.put("amount", map.get("amount"));
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
        }
        try {
            req.put("version", "2.1");
            req.put("orgNo", properties.getProperty("orgNo"));
            req.put("custId", properties.getProperty("custId"));
            req.put("custOrdNo", map.get("requestNo"));
            req.put("casType", "00");
            req.put("casAmt", amount);
            req.put("deductWay", "02");
            req.put("callBackUrl", properties.getProperty("notifyUrl"));
            req.put("accountName", accountName);
            req.put("cardNo", accountNo);
            req.put("bankName", bankName);
            req.put("subBankName", bankName);
            req.put("accountType", "1");
            req.put("cnapsCode", cnapsCode);
            req.put("sign", getUrlParamsByMap(req, properties.getProperty("key")));
            logger.info("花生代付下单报文：{}", JsonUtils.toJsonString(req));
            String res = HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8");
            logger.info("花生代付返回报文：{}", res);
            Map<String, String> resParMap = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if ("000000".equals(resParMap.get("code"))) {
                if ("08".equals(resParMap.get("ordStatus"))) {
                    resMap.put("tranStat", "FAILED");
                } else {
                    resMap.put("tranStat", "UNKNOWN");
                }
                resMap.put("compType", BusinessCompleteType.NORMAL.name());
                resMap.put("amount", map.get("amount"));
                resMap.put("requestNo", map.get("requestNo"));
                resMap.put("resCode", resParMap.get("code"));
                resMap.put("resMsg", resParMap.get("msg"));
            }
        } catch (Exception e) {
            logger.error("花生代付下单异常!异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String requestNo = map.get("requestNo");
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("requestNo"));
        Map<String, String> req = new TreeMap<>();
        Map<String, String> respMap = new HashMap<>();
        try {
            req.put("version", "2.1");
            req.put("orgNo", properties.getProperty("orgNo"));
            req.put("custId", properties.getProperty("custId"));
            req.put("custOrdNo", requestNo);
            req.put("sign", getUrlParamsByMap(req, properties.getProperty("key")));
            String res = HttpUtil.httpRequest(properties.getProperty("query"), req, "POST", "UTF-8");
            logger.info("花生代付查询返回报文：{}", res);
            Map<String, String> resParMap = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            if ("000000".equals(resParMap.get("code"))) {
                if ("07".equals(resParMap.get("ordStatus"))) {
                    respMap.put("resCode", "0000");
                    respMap.put("resMsg", "付款成功");
                    respMap.put("tranStat", "SUCCESS");
                } else if ("08".equals(resParMap.get("ordStatus"))) {
                    respMap.put("resCode", resParMap.get("code"));
                    respMap.put("resMsg", resParMap.get("msg"));
                    respMap.put("tranStat", "FAILED");
                } else {
                    respMap.put("resCode", resParMap.get("code"));
                    respMap.put("resMsg", resParMap.get("msg"));
                    respMap.put("tranStat", "UNKNOWN");
                }
            }
            respMap.put("requestNo", requestNo);
            respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
            respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
            respMap.put("interfaceOrderID", interfaceRequest.getInterfaceOrderID());
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return respMap;
    }

    public static String getUrlParamsByMap(Map<String, String> map, String key) {
        logger.info("花生代付下单原文：{}", map);
        if (map == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                sb.append(entry.getKey() + "=" + entry.getValue());
                sb.append("&");
            }
        }
        sb.append("key=" + key);
        String s = sb.toString();
        logger.info("签名原文：{}", s);
        return MD5Util.md5(s).toUpperCase();
    }
}