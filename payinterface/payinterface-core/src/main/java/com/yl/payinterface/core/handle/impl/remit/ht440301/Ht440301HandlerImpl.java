package com.yl.payinterface.core.handle.impl.remit.ht440301;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.bean.Cnaps;
import com.yl.payinterface.core.bean.Recognition;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.remit.hs100001.utils.HttpUtil;
import com.yl.payinterface.core.handle.impl.remit.ht440301.utils.signUtils;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 辉腾代付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/8/29
 */
@Service("ht440301Handler")
public class Ht440301HandlerImpl implements RemitHandler {

    private static final Logger logger = LoggerFactory.getLogger(Ht440301HandlerImpl.class);

    @Resource
    private InterfaceRequestService interfaceRequestService;

    private static final String key = "F:\\pay\\cert\\ht\\82018082999990011_prv.pem";

    public static void main(String[] args) {
        Map<String, String> req = new TreeMap<>();
        String requestNo = "DF-" + System.currentTimeMillis();
        req.put("requestNo", requestNo);
        req.put("version", "V1.1");
        req.put("productId", "0104");
        req.put("transId", "35");
        req.put("merNo", "82018082999990011");
        req.put("orderDate", "20180829");
        req.put("orderNo", requestNo);
        req.put("transAmt", "4700");
        req.put("commodityName", "测试");
        req.put("phoneNo", "17677775555");
        req.put("isCompay", "0");
        req.put("customerName", "祁飞");
        req.put("cerdType", "01");
        req.put("cerdId", "542527199210014394");
        req.put("bankName", "中国农业银行股份有限公司北京大郊亭支行");
        req.put("bankNo", "103100040103");
        req.put("acctNo", "6228480018891102271");
        try {
            req.put("signature", signUtils.getSign(req, key));
            System.out.println(req);
            String res = HttpUtil.httpRequest("http://gateway.xmhgh.com/gateway/api/backTransReq", req, "POST", "UTF-8");
            System.out.println(res);
            Map<String, Object> resPar = signUtils.getUrlParams(res);
            System.out.println(resPar);
        } catch (Exception e) {
            logger.error("异常!异常信息：{}", e);
        }
    }

    @Override
    public Map<String, String> remit(Map<String, String> map) {
        Map<String, String> resMap = new HashMap<>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String requestNo = map.get("requestNo");
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100));
        String cnapsCode = "";
        String accountNo = map.get("accountNo");
        String accountName = map.get("accountName");
        String bankName = map.get("bankName");
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
            return resMap;
        }
        try {
            Map<String, String> req = new TreeMap<>();
            req.put("requestNo", requestNo);
            req.put("version", "V1.1");
            req.put("productId", "0104");
            req.put("transId", "35");
            req.put("merNo", properties.getProperty("merNo"));
            req.put("orderDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
            req.put("orderNo", requestNo);
            req.put("transAmt", amount);
            req.put("commodityName", "结算");
            req.put("phoneNo", "17677775555");
            req.put("isCompay", "0");
            req.put("customerName", accountName);
            req.put("cerdType", "01");
            req.put("cerdId", map.get("cerNo"));
            req.put("bankName", bankName);
            req.put("bankNo", cnapsCode);
            req.put("acctNo", accountNo);
            req.put("signature", signUtils.getSign(req, properties.getProperty("keyPath")));
            logger.info("辉腾代付下单报文：{}", req);
            String res = HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8");
            logger.info("辉腾代付返回报文：{}", signUtils.getUrlParams(res));
            resMap.put("tranStat", "UNKNOWN");
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("amount", map.get("amount"));
            resMap.put("requestNo", map.get("requestNo"));
            resMap.put("resCode", "0000");
            resMap.put("resMsg", "处理中");
        } catch (Exception e) {
            logger.error("异常!异常信息：{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
        String requestNo = map.get("requestNo");
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(requestNo);
        Map<String, String> respMap = new HashMap<>();
        Map<String, String> req = new TreeMap<>();
        try {
            req.put("requestNo", requestNo);
            req.put("version", "V1.1");
            req.put("productId", "0104");
            req.put("transId", "36");
            req.put("merNo", properties.getProperty("merNo"));
            req.put("orderDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
            req.put("orderNo", requestNo);
            req.put("signature", signUtils.getSign(req, properties.getProperty("keyPath")));
            logger.info("辉腾代付查询报文：{}", req);
            String res = HttpUtil.httpRequest(properties.getProperty("url"), req, "POST", "UTF-8");
            Map<String, Object> resPar = signUtils.getUrlParams(res);
            logger.info("辉腾代付查询返回报文：{}", resPar);
            if ("0000".equals(resPar.get("respCode")) && "交易成功".equals(resPar.get("respDesc"))) {
                respMap.put("resCode", "0000");
                respMap.put("resMsg", "付款成功");
                respMap.put("tranStat", "SUCCESS");
            } else {
                respMap.put("resCode", resPar.get("respCode").toString());
                respMap.put("resMsg", resPar.get("respDesc").toString());
                respMap.put("tranStat", "UNKNOWN");
            }
            respMap.put("requestNo", requestNo);
            respMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
            respMap.put("compType", BusinessCompleteType.AUTO_REPAIR.name());
            respMap.put("interfaceOrderID", interfaceRequest.getInterfaceOrderID());
        } catch (Exception e) {
            logger.error("异常!异常信息：{}", e);
        }
        return respMap;
    }
}