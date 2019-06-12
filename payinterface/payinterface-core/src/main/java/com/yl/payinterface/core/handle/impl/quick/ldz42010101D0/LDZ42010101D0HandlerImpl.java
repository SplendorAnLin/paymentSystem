package com.yl.payinterface.core.handle.impl.quick.ldz42010101D0;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.enums.CardStatus;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.C;
import com.yl.payinterface.core.enums.FeeType;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFeeService;
import com.yl.payinterface.core.utils.FeeUtils;
import com.yl.payinterface.core.utils.RSAUtils;

/**
 * 快捷支付D0
 */
@Service("quickLDZ42010101D0Handler")
public class LDZ42010101D0HandlerImpl implements QuickPayHandler, BindCardHandler {

    private static Logger logger = LoggerFactory.getLogger(LDZ42010101D0HandlerImpl.class);

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Resource
    private QuickPayFeeService quickPayFeeService;

    @Resource
    private CustomerInterface customerInterface;

    private static Properties prop = new Properties();
    static {
        try {
            prop.load(new InputStreamReader(LDZ42010101D0HandlerImpl.class.getClassLoader().getResourceAsStream("serverHost.properties")));
        } catch (IOException e) {
            logger.error("LDZ42010101D0HandlerImpl load Properties error:", e);
        }
    }



    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        return null;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        return sale(map);
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {
        Map<String, String> respMap = new HashMap<>();

        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"));
        String minFee = properties.getProperty("minFee");

        SortedMap<Object, Object> settleData = new TreeMap<Object, Object>();

        // 检查交易卡信息
        checkCardInfo(settleData, interfaceRequest.getOwnerID(), map.get("cardNo"));

        // 获取结算卡信息
        quickPayFeeService.getSettleInfo(settleData, interfaceRequest.getOwnerID(), map.get("cardNo"));

        // 保存交易卡历史信息
     	quickPayFeeService.saveTransCardHis(map.get("cardNo"), interfaceRequest);
     	
     	String settleType = map.get("settleType");
     	double fee = 0d;
     	if (C.SETTLE_TYPE_AMOUNT.equals(settleType)) {
     		String settleAmount = map.get("settleAmount");
     		logger.info("接口请求号：{}，结算金额为：{}", interfaceRequest.getInterfaceRequestID(), settleAmount);
     		fee = AmountUtils.subtract(interfaceRequest.getAmount(), Double.valueOf(settleAmount));
     	} else {
     		String remitFee = map.get("remitFee");
     		String quickPayFee = map.get("quickPayFee");
     		
     		if (StringUtils.isBlank(remitFee)) {
     			remitFee = quickPayFeeService.getRemitFee(interfaceRequest.getOwnerID(), Double.valueOf(map.get("amount")));
     		}
     		if (StringUtils.isBlank(quickPayFee)) {
     			quickPayFee = quickPayFeeService.getQuickPayFee(interfaceRequest.getOwnerID());
     		}
     		logger.info("接口请求号：{}，代付费率：{}，快捷费率：{}", interfaceRequest.getInterfaceRequestID(), remitFee, quickPayFee);
     		// 计算应扣手续费。
     		fee = FeeUtils.computeFee(Double.valueOf(map.get("amount")), FeeType.RATIO, Double.valueOf(quickPayFee));
     		fee = AmountUtils.add(Double.valueOf(remitFee), fee);
     		fee = AmountUtils.round(fee, 2, RoundingMode.HALF_UP);
     		if (Double.compare(fee, Double.valueOf(minFee)) < 0) {
     			fee = Double.valueOf(minFee);
     		}
     	}
     	logger.info("接口请求号：{}，计算手续费为：{}", interfaceRequest.getInterfaceRequestID(), fee);
     	
        
        try {

            Map<String, String> reqMap = new LinkedHashMap<String, String>();
            reqMap.put("txnAmt", String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100)));
            reqMap.put("orderId", map.get("interfaceRequestID").replace("-", ""));
            reqMap.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            reqMap.put("frontUrl", properties.getProperty("frontUrl"));
            reqMap.put("backUrl", properties.getProperty("backUrl"));
            reqMap.put("accNo", map.get("cardNo"));
            reqMap.put("realName", settleData.get("realName").toString());
            reqMap.put("identityNo", settleData.get("identityCard").toString());
            reqMap.put("phoneNo", settleData.get("phoneNo").toString());
            reqMap.put("toBankName", toCachecenterIin(settleData.get("cardNo").toString()).get("agencyName"));
            reqMap.put("toAccNo", settleData.get("cardNo").toString());
            reqMap.put("userFee", String.valueOf((int) AmountUtils.multiply(fee, 100)));
            reqMap.put("extFiled", map.get("interfaceRequestID"));
            reqMap.put("toBankCode", toCachecenterIin(settleData.get("cardNo").toString()).get("code"));
            reqMap.put("terminalMerCode", "");
            reqMap.put("terminalMerName", "");
            reqMap.put("terminalMerState", "");
            reqMap.put("terminalMerCity", "");
            reqMap.put("terminalMerAddress", "");
            reqMap.put("t0drawFee", "");
            reqMap.put("t0drawRate", "");
            reqMap.put("t1consFee", "");
            reqMap.put("t1consRate", "");
            reqMap.put("version", properties.getProperty("version"));

            logger.info("接口请求号：{}，来逗阵快捷支付D0 支付请求明文：{}", interfaceRequest.getInterfaceRequestID(), JsonUtils.toJsonString(reqMap));

            Map<String, String> data = new LinkedHashMap<String, String>();
            data.put("data", Base64.encodeBase64String(RSAUtils.encryptByPublicKey(JsonUtils.toJsonString(reqMap).getBytes("UTF-8"), properties.getProperty("publicKey"))));
            data.put("url", properties.getProperty("h5Url") + properties.getProperty("channelType") + "/" + properties.getProperty("appId"));

            logger.info("接口请求号：{}，来逗阵快捷支付D0 支付请求报文：{}", interfaceRequest.getInterfaceRequestID(), data);

            String respInfo = HttpClientUtils.send(HttpClientUtils.Method.POST, "http://util.bank-pay.com/postForWard", data);

            logger.info("接口请求号：{}，来逗阵快捷支付D0 支付响应报文：{}", interfaceRequest.getInterfaceRequestID(), respInfo);


            respMap.put("interfaceRequestID", map.get("interfaceRequestID"));
            respMap.put("responseCode", "00");
            respMap.put("payPage", "INTERFACE");
            respMap.put("gateway", "htmlSubmit");
            respMap.put("html", respInfo);

        } catch (Exception e) {
            logger.error("来逗阵快捷支付D0异常 接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceCode"), map.get("interfaceRequestID"), e);
        }
        return respMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        return null;
    }

    private void checkCardInfo(Map<Object, Object> map, String ownerId, String cardNo) {
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        if (transCardBean == null) {
            throw new BusinessRuntimeException("3009");
        }
        if (transCardBean.getCardStatus() != CardStatus.NORAML) {
            throw new BusinessRuntimeException("3008");
        }
        map.put("phoneNo", transCardBean.getPhone());
    }


    /**
     * @Description 卡识别
     * @date 2016年10月30日
     */
    public static Map<String, String> toCachecenterIin(String cardNo) {
        try {
            if(cardNo != null && cardNo != ""){
                String url = prop.get("cachecenter.service.url") + "/iin/recognition.htm?cardNo=" + cardNo + "&fields=agencyName";
                String resData = JsonUtils.toJsonString(HttpClientUtils.send(HttpClientUtils.Method.POST, url, "", true, Charset.forName("UTF-8").name(), 6000));

                String agencyName = JsonUtils.toObject(JsonUtils.getInstance().readTree(resData).getTextValue(), new TypeReference<Map<String, Object>>(){}).get("agencyName").toString();
                Map<String, String> map = new HashMap<String, String>();
                map.put("agencyName", agencyName);

                String carUrl = prop.get("cachecenter.service.url") + "/cnaps/suggestSearch.htm?word=" + agencyName + "&clearBankLevel=1";
                String carResData = JsonUtils.toJsonString(HttpClientUtils.send(HttpClientUtils.Method.POST, carUrl, "", true, Charset.forName("UTF-8").name(), 6000));
                map.put("code", JsonUtils.toObject(JsonUtils.getInstance().readTree(carResData).getTextValue().replace("[", "").replace("]", ""), new TypeReference<Map<String, Object>>(){}).get("code").toString());
                return map;
            }
        } catch (IOException e) {
            logger.error("卡号：{}，系统异常:{}", cardNo, e);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(JsonUtils.toJsonString(toCachecenterIin("手机号\n")));
    }

    @Override
    public Map<String, String> bindCard(Map<String, String> params) {
        return null;
    }

    @Override
    public Map<String, String> queryBindCard(Map<String, String> params) {
        params.put("responseCode", "00");
        return params;
    }
}
