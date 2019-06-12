package com.yl.payinterface.core.handle.impl.quick.kingpass100001;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.enums.CardType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.bean.QuickPayOpenCardResponseBean;
import com.yl.payinterface.core.enums.AuthPayBindCardInfoStatus;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.quick.kingpass100001.utils.DesUtils;
import com.yl.payinterface.core.handle.impl.quick.kingpass100001.utils.sendUtils;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.handler.QuickPayOpenCardHandler;
import com.yl.payinterface.core.model.BindCardInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.BindCardInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFeeService;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;
import com.yl.payinterface.core.utils.CodeBuilder;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 九派人脸快捷
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/6/4
 */
@Service("quickKingPass100001Handler")
public class KingPass100001HandlerImpl implements QuickPayHandler, BindCardHandler, QuickPayOpenCardHandler {

    private static final Logger logger = LoggerFactory.getLogger(KingPass100001HandlerImpl.class);

    private static final String interfaceInfoCode = "QUICKPAY_KINGPASS-100001";

    @Resource
    private QuickPayFilingInfoService quickPayFilingInfoService;

    @Resource
    private CustomerInterface customerInterface;

    @Resource
    private BindCardInfoService bindCardInfoService;

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Resource
    private QuickPayFeeService quickPayFeeService;

    @Override
    public Map<String, String> bindCard(Map<String, String> params) {
        String cardNo = params.get("cardNo");
        BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceInfoCode);
        String gateway = "quickPay/openCard";
        if (bindCardInfo != null && bindCardInfo.getStatus().name().equals("SUCCESS")) {
            InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
            TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(params.get("ownerId"), params.get("cardNo"));
            params.put("settleCardNo", settleCardBean.getAccountNo());
            params.put("orderCode", interfaceRequest.getBussinessOrderID());
            params.put("payCardNo", cardNo);
            params.put("phone", settleCardBean.getPhone());
            params.put("payerName", settleCardBean.getAccountName());
            params.put("certNo", settleCardBean.getIdNumber());
            gateway = "auth/pay";
        }
        params.put("smsCodeType", "NO_YLZF_CODE");
        params.put("responseCode", "00");
        params.put("result", "00");
        params.put("gateway", gateway);
        return params;
    }

    @Override
    public Map<String, String> queryBindCard(Map<String, String> params) {
        String cardNo = params.get("cardNo");
        BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceInfoCode);
        String gateway = "quickPay/openCard";
        if (bindCardInfo != null && bindCardInfo.getStatus().name().equals("SUCCESS")) {
            InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
            TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(params.get("ownerId"), params.get("cardNo"));
            params.put("settleCardNo", settleCardBean.getAccountNo());
            params.put("orderCode", interfaceRequest.getBussinessOrderID());
            params.put("payCardNo", cardNo);
            params.put("phone", settleCardBean.getPhone());
            params.put("payerName", settleCardBean.getAccountName());
            params.put("certNo", settleCardBean.getIdNumber());
            gateway = "auth/pay";
        }
        params.put("responseCode", "99");
        params.put("result", "00");
        params.put("gateway", gateway);
        return params;
    }

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        String ownerId = params.get("ownerId");
        String cardNo = params.get("cardNo");
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(params.get("amount")), 100d));
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
        Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>() {});
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceInfoCode);
        Map<String, String> rpmQuickPayInit = new HashMap<>();
        Map<Object,Object> settMap = new HashMap<>();
        quickPayFeeService.getSettleInfo(settMap, interfaceRequest.getOwnerID(), cardNo);
        Map<String, String> retMap = new HashMap<>();
        try {
            rpmQuickPayInit.put("charset", "02");
            rpmQuickPayInit.put("version", "1.2");
            rpmQuickPayInit.put("merchantId", properties.getProperty("merchantId"));
            rpmQuickPayInit.put("requestTime", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
            rpmQuickPayInit.put("requestId", CodeBuilder.getOrderIdByUUId());
            rpmQuickPayInit.put("service", "rpmQuickPayInit");
            rpmQuickPayInit.put("signType", "RSA256");
            rpmQuickPayInit.put("memberId", ownerId);
            rpmQuickPayInit.put("orderId", interfaceRequest.getInterfaceRequestID());
            rpmQuickPayInit.put("contractId", bindCardInfo.getToken());
            if (transCardBean.getCardType() == CardType.CREDIT) {
                rpmQuickPayInit.put("payType", "CQP");
            } else {
                rpmQuickPayInit.put("payType", "DQP");
            }
            rpmQuickPayInit.put("amount", amount);
            rpmQuickPayInit.put("currency", "CNY");
            rpmQuickPayInit.put("orderTime", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
            rpmQuickPayInit.put("clientIP", "111.172.245.117");
            rpmQuickPayInit.put("validUnit", "02");
            rpmQuickPayInit.put("validNum", "1");
            rpmQuickPayInit.put("offlineNotifyUrl", DesUtils.desEnCode(properties.getProperty("notifyUrl")));
            rpmQuickPayInit.put("terminalType", "02");
            rpmQuickPayInit.put("orderDetail", "聚合网络");
            rpmQuickPayInit.put("tradeUse", "聚合网络充值");
            rpmQuickPayInit.put("tradeAbstract", "聚合网络");
            rpmQuickPayInit.put("busTypeScene", "03");
            rpmQuickPayInit.put("busTypeCode", "130001");
            rpmQuickPayInit.put("extData", "{\"mercPayContractNo\":\"20180602albc\"}");
            Map<String, String> respMap = sendUtils.sendPost(rpmQuickPayInit, properties.getProperty("p12"), properties.getProperty("cer"), properties.getProperty("passWord"), properties.getProperty("url"));
            Map<String, String> bindCard = new HashMap<>();
            bindCard.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
            bindCard.put("interfaceCode", params.get("interfaceInfoCode"));
            bindCard.put("cardNo", cardNo);
            bindCard.put("realName", settMap.get("realName").toString());
            CacheUtils.setEx("BINDCARD:" + cardNo, JsonUtils.toJsonString(bindCardInfo), 900);
            retMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
            retMap.put("orderCode", respMap.get("payOrderId"));
            retMap.put("payCardNo", params.get("cardNo"));
            retMap.put("settleCardNo", settMap.get("cardNo").toString());
            retMap.put("payerName", settMap.get("realName").toString());
            retMap.put("certNo", settMap.get("identityCard").toString());
            retMap.put("phone", transCardBean.getPhone());
            retMap.put("responseCode", "00");
            retMap.put("token", params.get("token"));
            retMap.put("amount", Double.toString(interfaceRequest.getAmount()));
            retMap.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
            retMap.put("responseCode", "00");
            retMap.put("key", cardNo);
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return retMap;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        return sendVerifyCode(map);
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"));
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
        Map<String, String> rpmQuickPayCommit = new HashMap<>();
        String cardNo = map.get("cardNo");
        String ownerId = map.get("ownerId");
        String amount = String.valueOf((int) AmountUtils.multiply(Double.valueOf(map.get("amount")), 100d));
        String eventId = CacheUtils.get("eventId" + cardNo, String.class);
        BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceInfoCode);
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        Map<String, String> resMap = new HashMap<>();
        try {
            rpmQuickPayCommit.put("charset", "02");
            rpmQuickPayCommit.put("version", "1.2");
            rpmQuickPayCommit.put("merchantId", properties.getProperty("merchantId"));
            rpmQuickPayCommit.put("requestTime", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
            rpmQuickPayCommit.put("requestId", CodeBuilder.getOrderIdByUUId());
            rpmQuickPayCommit.put("service", "rpmQuickPayCommit");
            rpmQuickPayCommit.put("signType", "RSA256");
            rpmQuickPayCommit.put("memberId", map.get("ownerId"));
            rpmQuickPayCommit.put("orderId", interfaceRequest.getInterfaceRequestID());
            rpmQuickPayCommit.put("contractId", bindCardInfo.getToken());
            rpmQuickPayCommit.put("checkCode", map.get("smsCode"));
            if (transCardBean.getCardType() == CardType.CREDIT) {
                rpmQuickPayCommit.put("payType", "CQP");
            } else {
                rpmQuickPayCommit.put("payType", "DQP");
            }
            rpmQuickPayCommit.put("amount", amount);
            rpmQuickPayCommit.put("currency", "CNY");
            rpmQuickPayCommit.put("orderTime", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
            rpmQuickPayCommit.put("clientIP", "111.172.245.117");
            rpmQuickPayCommit.put("validUnit", "02");
            rpmQuickPayCommit.put("validNum", "1");
            rpmQuickPayCommit.put("offlineNotifyUrl", DesUtils.desEnCode(properties.getProperty("notifyUrl")));
            rpmQuickPayCommit.put("eventID", eventId);
            Map<String, String> respMap = sendUtils.sendPost(rpmQuickPayCommit, properties.getProperty("p12"), properties.getProperty("cer"), properties.getProperty("passWord"), properties.getProperty("url"));
            resMap.put("authTranStat", "SUCCESS");
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
            resMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
            resMap.put("tranStat", "UNKNOWN");
            resMap.put("responseCode", "01");
            resMap.put("responseMsg", "交易成功，扣款状态未知");
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Map<String, String> rpmPayQuery = new HashMap<>();
        Map<String, String> resMap = new HashMap<String, String>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
        try {
            rpmPayQuery.put("charset", "02");
            rpmPayQuery.put("version", "1.0");
            rpmPayQuery.put("merchantId", properties.getProperty("merchantId"));
            rpmPayQuery.put("requestTime", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
            rpmPayQuery.put("requestId", CodeBuilder.getOrderIdByUUId());
            rpmPayQuery.put("service", "rpmPayQuery");
            rpmPayQuery.put("signType", "RSA256");
            rpmPayQuery.put("orderId", map.get("interfaceRequestID"));
            Map<String, String> respMap = sendUtils.sendPost(rpmPayQuery, properties.getProperty("p12"), properties.getProperty("cer"), properties.getProperty("passWord"), properties.getProperty("url"));
            if ("PD".equals(respMap.get("payResult"))) {
                resMap.put("tranStat", "SUCCESS");
                resMap.put("interfaceCode", interfaceInfoCode);
                resMap.put("interfaceRequestID", map.get("interfaceRequestID"));
                resMap.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(respMap.get("amount")), 100d)));
                resMap.put("businessCompleteType", "AUTO_REPAIR");
                resMap.put("responseCode", "000000");
                resMap.put("responseMessage", "支付成功");
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return resMap;
    }

    @Override
    public QuickPayOpenCardResponseBean sendOpenCardVerifyCode(Map<String, String> params) {
        Properties properties = JsonUtils.toObject(params.get("tradeConfigs"), new TypeReference<Properties>() {});
        Map<String, String> rpmBindCardInit = new HashMap<>();
        String eventId = CacheUtils.get("eventId" + params.get("cardNo"), String.class);
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(params.get("customerNo"), params.get("cardNo"), CardAttr.TRANS_CARD);
        QuickPayOpenCardResponseBean quickPayOpenCardResponseBean = new QuickPayOpenCardResponseBean();
        try {
            rpmBindCardInit.put("charset", "02");
            rpmBindCardInit.put("version", "1.0");
            rpmBindCardInit.put("merchantId", properties.getProperty("merchantId"));
            rpmBindCardInit.put("requestTime", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
            rpmBindCardInit.put("requestId", CodeBuilder.getOrderIdByUUId());
            rpmBindCardInit.put("service", "rpmBindCardInit");
            rpmBindCardInit.put("signType", "RSA256");
            rpmBindCardInit.put("memberId", params.get("customerNo"));
            rpmBindCardInit.put("orderId", CodeBuilder.getOrderIdByUUId());
            rpmBindCardInit.put("idType", "00");
            rpmBindCardInit.put("idNo", DesUtils.desEnCode(transCardBean.getIdNumber()));
            rpmBindCardInit.put("userName", transCardBean.getAccountName());
            rpmBindCardInit.put("phone", transCardBean.getPhone());
            rpmBindCardInit.put("cardNo", DesUtils.desEnCode(transCardBean.getAccountNo()));
            if (transCardBean.getCardType() == CardType.CREDIT) {
                rpmBindCardInit.put("cardType", "1");
            } else {
                rpmBindCardInit.put("cardType", "0");
            }
            Map<String, String> respMap = sendUtils.sendPost(rpmBindCardInit, properties.getProperty("p12"), properties.getProperty("cer"), properties.getProperty("passWord"), properties.getProperty("url"));
            if (respMap != null && respMap.get("contractId") != null) {
                CacheUtils.setEx("contractId" + params.get("cardNo"), respMap.get("contractId"), 600);
                quickPayOpenCardResponseBean.setStatus("SUCCESS");
            }
        } catch (Exception e) {
            logger.error("九派人脸快捷短信发送异常!异常信息:{}", e);
        }
        return quickPayOpenCardResponseBean;
    }

    @Override
    public QuickPayOpenCardResponseBean openCardInfo(Map<String, String> params) {
        String cardNo = params.get("cardNo");
        String smsCode = params.get("smsCode");
        Properties properties = JsonUtils.toObject(params.get("tradeConfigs"), new TypeReference<Properties>() {});
        Map<String, String> rpmBindCardCommit = new HashMap<>();
        String contractId = CacheUtils.get("contractId" + cardNo, String.class);
        String eventId = CacheUtils.get("eventId" + cardNo, String.class);
        QuickPayOpenCardResponseBean openCardResponseBean = new QuickPayOpenCardResponseBean();
        try {
            rpmBindCardCommit.put("charset", "02");
            rpmBindCardCommit.put("version", "1.1");
            rpmBindCardCommit.put("merchantId", properties.getProperty("merchantId"));
            rpmBindCardCommit.put("requestTime", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
            rpmBindCardCommit.put("requestId", CodeBuilder.getOrderIdByUUId());
            rpmBindCardCommit.put("service", "rpmBindCardCommit");
            rpmBindCardCommit.put("signType", "RSA256");
            rpmBindCardCommit.put("contractId", contractId);
            rpmBindCardCommit.put("checkCode", smsCode);
            rpmBindCardCommit.put("eventID", eventId);
            Map<String, String> respMap = sendUtils.sendPost(rpmBindCardCommit, properties.getProperty("p12"), properties.getProperty("cer"), properties.getProperty("passWord"), properties.getProperty("url"));
            if ("0".equals(respMap.get("cardSts"))) {
                CacheUtils.setEx("contractId" + params.get("cardNo"), respMap.get("contractId"), 600);
                openCardResponseBean.setStatus("SUCCESS");
                openCardResponseBean.setToken(respMap.get("contractId"));
            }
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
        return openCardResponseBean;
    }
}