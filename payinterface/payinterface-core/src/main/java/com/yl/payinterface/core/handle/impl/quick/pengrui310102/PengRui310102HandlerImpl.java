package com.yl.payinterface.core.handle.impl.quick.pengrui310102;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.bean.QuickPayOpenCardResponseBean;
import com.yl.payinterface.core.enums.AuthPayBindCardInfoStatus;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.quick.pengrui310101.utils.PengRui310101Utils;
import com.yl.payinterface.core.handle.impl.quick.pengrui310102.bean.PengRui310102OpenCardBean;
import com.yl.payinterface.core.handle.impl.quick.pengrui310102.bean.PengRui310102PayBean;
import com.yl.payinterface.core.handle.impl.quick.pengrui310102.bean.PengRui310102SmsBean;
import com.yl.payinterface.core.handle.impl.quick.pengrui310102.utils.PengRui310102Utils;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.handler.QuickPayOpenCardHandler;
import com.yl.payinterface.core.hessian.QuickPayHessianService;
import com.yl.payinterface.core.model.BindCardInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.BindCardInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFeeService;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;
import net.sf.json.JSONObject;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 全时优服 快捷支付
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/27
 */
@Service("quickPengRui310102Handler")
public class PengRui310102HandlerImpl implements QuickPayHandler, BindCardHandler, QuickPayOpenCardHandler {

    private static final Logger logger = LoggerFactory.getLogger(PengRui310102HandlerImpl.class);

    private static final String interfaceInfoCode = "QUICKPAY_PengRui-310102";

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

    @Resource
    private QuickPayFilingHandler pengRui310102FilingHandler;

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
        params.put("responseCode", "00");
        params.put("result", "00");
        params.put("gateway", gateway);
        return params;
    }

    @Override
    public Map<String, String> queryBindCard(Map<String, String> params) {
        Map<String, String> res = new HashMap<>();
        String responseCode = "00";
        String cardNo = params.get("cardNo");
        String interfaceInfoCode = params.get("interfaceCode");
        String ownerId = params.get("partner");
        String remitFee = params.get("remitFee");
        String quickPayFee = params.get("quickPayFee");
        String amount = params.get("amount");
        if (org.apache.commons.lang3.StringUtils.isBlank(remitFee)) {
            remitFee = quickPayFeeService.getRemitFee(ownerId, Double.valueOf(amount));
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(quickPayFee)) {
            quickPayFee = quickPayFeeService.getQuickPayFee(ownerId);
        }
        TransCardBean transCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
        TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
        QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(transCardBean.getAccountNo(), interfaceInfoCode);
        String status = filing(settleCardBean, transCardBean, filingInfo, quickPayFee, remitFee, params.get("tradeConfigs"));
        if ("SUCCESS".equals(status)) {
            responseCode = "01";
        }
        res.put("responseCode", responseCode);
        return res;
    }

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
        Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>() {});
        Date reqDate = new Date();
        String orderId = PengRui310102Utils.getOrderIdByUUIdTwo();
        String cardNo = params.get("cardNo");
        String ownerId = params.get("ownerId");
        String amount = params.get("amount");
        // 获取结算卡信息
        Map<Object,Object> settMap = new HashMap<>();
        quickPayFeeService.getSettleInfo(settMap, interfaceRequest.getOwnerID(), cardNo);
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        String[] bankInfo = PengRui310102Utils.getBankInfo(transCardBean.getBankCode(), transCardBean.getBankName());
        PengRui310102SmsBean pengRui = new PengRui310102SmsBean();
        pengRui.setToken(PengRui310102Utils.getToken("20", properties.getProperty("token"), properties.getProperty("url"), properties.getProperty("key"), properties.getProperty("partnerNo")));
        pengRui.setHead(PengRui310102Utils.head(reqDate, orderId, properties.getProperty("sendVerifyCode"), properties.getProperty("partnerNo")));
        pengRui.setConsumeOrderId(PengRui310102Utils.getInterfaceRequestId(params.get("interfaceRequestID")));
        pengRui.setPlatMerchantCode(getTrano(ownerId, cardNo));
        pengRui.setMerchantRateCode(properties.getProperty("rateCode"));
        pengRui.setPayAmount(String.valueOf((int) AmountUtils.multiply(Double.valueOf(amount), 100d)));
        pengRui.setAccountName(transCardBean.getAccountName());
        pengRui.setCardNo(transCardBean.getAccountNo());
        pengRui.setCardType(transCardBean.getCardType().name().substring(0, 1));
        pengRui.setCertType("ID");
        pengRui.setCertNo(transCardBean.getIdNumber());
        pengRui.setPhoneno(transCardBean.getPhone());
        pengRui.setBankCode(bankInfo[0]);
        pengRui.setBankAbbr(bankInfo[1]);
        JSONObject res = PengRui310102Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("sendVerifyCode"), JsonUtils.toJsonString(pengRui), properties.getProperty("partnerNo"), orderId);
        Map<String, String> retMap = new HashMap<>();
        if (res.containsKey("head") && res.getJSONObject("head").getString("respMsg").equals("success")){
            retMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
            retMap.put("orderCode", interfaceRequest.getBussinessOrderID());
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
            Map<String, String> bindCardInfo = new HashMap<>();
            bindCardInfo.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
            bindCardInfo.put("interfaceCode", params.get("interfaceInfoCode"));
            bindCardInfo.put("cardNo", cardNo);
            bindCardInfo.put("realName", settMap.get("realName").toString());
            retMap.put("key", cardNo);
            try {
                CacheUtils.setEx("BINDCARD:" + cardNo, JsonUtils.toJsonString(bindCardInfo), 900);
            } catch (Exception e) {
                logger.error("全时优服快捷支付 缓存绑卡信息异常 接口编号:[{}],卡号:[{}],异常信息：[{}]", params.get("interfaceInfoCode"), params.get("cardNo"), e);
            }
            retMap.put("gateway", "quickPay");
        }
        return retMap;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        String gateway = "htmlSubmit";
        String cardNo = map.get("cardNo");
        BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceInfoCode);
        if (bindCardInfo == null || bindCardInfo.getStatus().name().equals("FAILED")) {
            QuickPayOpenCardResponseBean responseBean = openCardInfo(map);
            if (responseBean.getResponseCode().equals("00")) {
                return sendVerifyCode(map);
            }
            map.put("html", responseBean.getResponseMessage());
            map.put("payPage", "INTERFACE");
            map.put("responseCode", "00");
            map.put("gateway", gateway);
            return map;
        }
        return sendVerifyCode(map);
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
        Date reqDate = new Date();
        String interfaceRequestID = map.get("interfaceRequestID");
        String smsCode = map.get("smsCode");
        String cardNo = map.get("cardNo");
        String ownerId = map.get("ownerId");
        Double amount = Double.valueOf(map.get("amount"));
        String remitFee = map.get("remitFee");
        String quickPayFee = map.get("quickPayFee");
        if (StringUtils.isBlank(remitFee)) {
            remitFee = quickPayFeeService.getRemitFee(ownerId, Double.valueOf(amount));
        }
        if (StringUtils.isBlank(quickPayFee)) {
            quickPayFee = quickPayFeeService.getQuickPayFee(ownerId);
        }
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
        QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(settleCardBean.getAccountNo(), interfaceInfoCode);
        String[] bankInfo = PengRui310102Utils.getBankInfo(transCardBean.getBankCode(), transCardBean.getBankName());
        // 商户费率同步
        String status = filing(settleCardBean, transCardBean, filingInfo, quickPayFee, remitFee, map.get("tradeConfigs"));
        if ("FAILED".equals(status)){
            throw new RuntimeException("商户报备处理失败!接口请求号：" + interfaceRequestID);
        }
        // 保存交易卡历史信息
        InterfaceRequest interfaceRequestInfo = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestID);
        quickPayFeeService.saveTransCardHis(cardNo, interfaceRequestInfo);
        PengRui310102PayBean payBean = new PengRui310102PayBean();
        payBean.setHead(PengRui310102Utils.head(reqDate, PengRui310102Utils.getInterfaceRequestId(interfaceRequestID), properties.getProperty("sale"), properties.getProperty("partnerNo")));
        payBean.setToken(PengRui310102Utils.getToken("21", properties.getProperty("token"), properties.getProperty("url"), properties.getProperty("key"), properties.getProperty("partnerNo")));
        payBean.setConsumeOrderId(PengRui310102Utils.getInterfaceRequestId(interfaceRequestID));
        payBean.setPlatMerchantCode(getTrano(ownerId, cardNo));
        payBean.setSmsCode(smsCode);
        payBean.setMerchantRateCode(properties.getProperty("rateCode"));
        payBean.setPayAmount(String.valueOf((int) AmountUtils.multiply(amount, 100d)));
        payBean.setAccountName(transCardBean.getAccountName());
        payBean.setCardNo(transCardBean.getAccountNo());
        payBean.setCardType(transCardBean.getCardType().name().substring(0, 1));
        payBean.setCertType("ID");
        payBean.setCertNo(transCardBean.getIdNumber());
        payBean.setPhoneno(transCardBean.getPhone());
        payBean.setBankCode(bankInfo[0]);
        payBean.setBankAbbr(bankInfo[1]);
        payBean.setProductName("在线消费");
        payBean.setProductDesc("在线充值");
        payBean.setCallBackUrl(properties.getProperty("offlineNotifyUrl"));
        JSONObject result = PengRui310102Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("sale"), JsonUtils.toJsonString(payBean), properties.getProperty("partnerNo"), PengRui310102Utils.getInterfaceRequestId(interfaceRequestID));
        Map<String, String> resMap = new HashMap<>();
        if (result.containsKey("head")){
            JSONObject head = result.getJSONObject("head");
            resMap.put("authTranStat", "SUCCESS");
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
            resMap.put("interfaceRequestID", interfaceRequestID);
            if (head.getString("respCode").equals("000000") && result.containsKey("payAmount") && result.containsKey("payNo")){
                if (result.getString("payAmount").equals(String.valueOf((int) AmountUtils.multiply(amount, 100d)))) {
                    resMap.put("tranStat", "UNKNOWN");
                    resMap.put("responseCode", "01");
                    resMap.put("responseMsg", "交易成功，扣款状态未知");
                }
                return resMap;
            } else if (head.getString("respCode").equals("E81259")) {
                resMap.put("responseCode", "98");
                resMap.put("responseMsg", "短信验证码错误");
            } else {
                resMap.put("responseCode", "99");
                resMap.put("responseMsg", "下单失败");
            }
            return resMap;
        } else {
            resMap.put("responseCode", "99");
            resMap.put("responseMsg", "返回参数不正确");
            return resMap;
        }
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Date reqDate = new Date();
        String orderId = PengRui310102Utils.getOrderIdByUUIdTwo();
        Map<String, String> resMap = new HashMap<String, String>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
        String interfaceRequestID = map.get("interfaceRequestID");
        TransCardBean settle = customerInterface.findByInterfaceRequestId(interfaceRequestID);
        if (settle == null) {
            resMap.put("tranStat", "ERROR");
            return resMap;
        }
        JSONObject dto = new JSONObject();
        dto.put("head", PengRui310102Utils.head(reqDate, orderId, properties.getProperty("query"), properties.getProperty("partnerNo")));
        dto.put("token", PengRui310102Utils.getToken("22", properties.getProperty("token"), properties.getProperty("url"), properties.getProperty("key"), properties.getProperty("partnerNo")));
        dto.put("consumeOrderId", PengRui310102Utils.getInterfaceRequestId(interfaceRequestID));
        dto.put("merchantCode", getTranoBySettle(settle.getAccountNo()));
        JSONObject result = PengRui310102Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("query"), dto.toString(), properties.getProperty("partnerNo"), orderId);
        if (result.containsKey("head") && result.getJSONObject("head").getString("respCode").equals("000000")){
            if (result.getString("orderStatus").equals("01")){
                resMap.put("tranStat", "SUCCESS");
                resMap.put("interfaceCode", interfaceInfoCode);
                resMap.put("interfaceRequestID", interfaceRequestID);
                resMap.put("amount", String.valueOf(AmountUtils.divide(Double.valueOf(result.getString("payAmount")), 100d)));
                resMap.put("businessCompleteType", "AUTO_REPAIR");
                resMap.put("responseCode", "000000");
                resMap.put("responseMessage", "支付成功");
                return resMap;
            }
        }
        return resMap;
    }

    @Override
    public QuickPayOpenCardResponseBean sendOpenCardVerifyCode(Map<String, String> params) {
        return null;
    }

    @Override
    public QuickPayOpenCardResponseBean openCardInfo(Map<String, String> params) {
        String cardNo = params.get("cardNo");
        String interfaceInfoCode = params.get("interfaceCode");
        String ownerId = params.get("ownerId");
        Date reqDate = new Date();
        String orderId = PengRui310102Utils.getOrderIdByUUIdTwo();
        Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>() {});
        TransCardBean settle = customerInterface.findByCustAndAccNo(ownerId, cardNo);
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(settle.getAccountNo(), interfaceInfoCode);
        BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceInfoCode);
        String[] bankInfo = PengRui310102Utils.getBankInfo(transCardBean.getBankCode(), transCardBean.getBankName());
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
        String param = "?tradeOrderCode=" + interfaceRequest.getBussinessOrderID() + "&amount=" + interfaceRequest.getAmount() + "&interfaceCode=" + interfaceInfoCode +
                "&cardNo=" + cardNo;
        Map<String, Object> retParam = new HashMap<>();
        retParam.put("tradeOrderCode", interfaceRequest.getBussinessOrderID());
        retParam.put("amount", interfaceRequest.getAmount());
        retParam.put("interfaceCode", interfaceInfoCode);
        retParam.put("cardNo", cardNo);
        retParam.put("bindCardStatus", "SUCCESS");
        CacheUtils.setEx("ORDERID:" + orderId, JsonUtils.toJsonString(retParam), 3600);
        PengRui310102OpenCardBean pengRui = new PengRui310102OpenCardBean();
        pengRui.setToken(PengRui310102Utils.getToken("18", properties.getProperty("token"), properties.getProperty("url"), properties.getProperty("key"), properties.getProperty("partnerNo")));
        pengRui.setHead(PengRui310102Utils.head(reqDate, orderId, properties.getProperty("bindCard"), properties.getProperty("partnerNo")));
        pengRui.setSmsOrderId(orderId);
        pengRui.setPlatMerchantCode(filingInfo.getChannelCustomerCode());
        pengRui.setRateCode(properties.getProperty("rateCode"));
        pengRui.setAccountName(transCardBean.getAccountName());
        pengRui.setCardNo(transCardBean.getAccountNo());
        pengRui.setCardType(transCardBean.getCardType().name().substring(0, 1));
        pengRui.setCertType("ID");
        pengRui.setCertNo(transCardBean.getIdNumber());
        pengRui.setPhoneno(transCardBean.getPhone());
        pengRui.setCvn2(bindCardInfo.getCvv());
        pengRui.setExpired(bindCardInfo.getValidityYear() + bindCardInfo.getValidityMonth());
        pengRui.setPageReturnUrl(properties.getProperty("pageNotify") + param);
        pengRui.setOfflineNotifyUrl(properties.getProperty("openCardNotify") + param);
        pengRui.setBankCode(bankInfo[0]);
        pengRui.setBankAbbr(bankInfo[1]);
        QuickPayOpenCardResponseBean responseBean = new QuickPayOpenCardResponseBean();
        JSONObject res = PengRui310102Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("bindCard"), JsonUtils.toJsonString(pengRui), properties.getProperty("partnerNo"), orderId);
        if (!res.containsKey("head") || !res.getJSONObject("head").getString("respMsg").equals("success") || !res.getString("activateStatus").equals("SUCCESS")){
            if (res.containsKey("html")) {
                responseBean.setStatus("FAILED");
                responseBean.setResponseCode("01");
                responseBean.setResponseMessage(res.getString("html"));
            } else {
                responseBean.setStatus("FAILED");
                responseBean.setResponseCode("99");
                responseBean.setResponseMessage(res.getJSONObject("head").getString("respMsg"));
            }
        } else if (res.containsKey("head") && res.getJSONObject("head").getString("respMsg").equals("success") && res.getString("activateStatus").equals("SUCCESS")) {
            responseBean.setStatus("SUCCESS");
            responseBean.setResponseCode("00");
            responseBean.setResponseMessage("开卡成功");
        } else {
            responseBean.setStatus("FAILED");
            responseBean.setResponseCode("99");
            responseBean.setResponseMessage("开卡失败");
        }
        return responseBean;
    }

    /**
     * 获取渠道商户号
     *
     * @param ownerId
     * @param bankCardNo
     * @return
     */
    private String getTrano(String ownerId, String bankCardNo) {
        TransCardBean transCardBean = customerInterface.findByCustAndAccNo(ownerId, bankCardNo);
        QuickPayFilingInfo quickPayFilingInfo = quickPayFilingInfoService.find(transCardBean.getAccountNo(), interfaceInfoCode);
        return quickPayFilingInfo.getChannelCustomerCode();
    }

    /**
     * 根据结算卡卡号获取渠道商户号
     * @param ownerId
     * @param bankCardNo
     * @return
     */
    private String getTranoBySettle(String bankCardNo) {
        QuickPayFilingInfo quickPayFilingInfo = quickPayFilingInfoService.find(bankCardNo, interfaceInfoCode);
        return quickPayFilingInfo.getChannelCustomerCode();
    }

    /**
     * 商户报备参数组装
     * filingInfoCode == null 新增报备 否则修改报备信息
     */
    public String filing(TransCardBean settleCardBean, TransCardBean transCardBean, QuickPayFilingInfo filingInfo, String quickPayFee, String remitFee, String tradeConfigs) {
        Map<String, String > map = new HashMap<>();
        String res = "FAILED";
        map.put("cardNo", transCardBean.getAccountNo());
        map.put("bankCardNo", settleCardBean.getAccountNo());
        map.put("name", settleCardBean.getAccountName());
        map.put("idCardNo", settleCardBean.getIdNumber());
        map.put("phone", settleCardBean.getPhone());
        map.put("clearBankCode", settleCardBean.getClearBankCode());
        map.put("bankName", settleCardBean.getBankName());
        map.put("tradeConfigs", tradeConfigs);
        map.put("quickPayFee", quickPayFee);
        map.put("remitFee", remitFee);
        map.put("bankCode", settleCardBean.getBankCode());
        if (filingInfo == null) {
            map.put("updateFlag", "0");
            Map<String, String> resMap = pengRui310102FilingHandler.filing(map);
            if ("SUCCESS".equals(resMap.get("status"))) {
                res = "SUCCESS";
            }
        } else {
            if (!filingInfo.getRemitFee().equals(remitFee) || !filingInfo.getQuickPayFee().equals(quickPayFee) ||
                    !filingInfo.getPhone().equals(settleCardBean.getPhone()) || !filingInfo.getBankCardNo().equals(settleCardBean.getAccountNo())) {
                map.put("updateFlag", "1");
                map.put("customerCode", filingInfo.getCustomerCode());
                map.put("channelCustomerCode", filingInfo.getChannelCustomerCode());
                map.put("filingInfoCode", filingInfo.getCode());
                Map<String, String> resMap = pengRui310102FilingHandler.filing(map);
                if ("SUCCESS".equals(resMap.get("status"))) {
                    res = "SUCCESS";
                }
            } else {
                res = "SUCCESS";
            }
        }
        return res;
    }

    private void updateBindCardInfo(String cardNo, String interfaceCode) {
        if (StringUtils.notBlank(cardNo) && StringUtils.notBlank(interfaceCode)) {
            // 如果绑卡信息不是成功就更新成功
            BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceCode);
            if (bindCardInfo.getStatus() != AuthPayBindCardInfoStatus.SUCCESS) {
                bindCardInfo.setStatus(AuthPayBindCardInfoStatus.SUCCESS);
                bindCardInfoService.update(bindCardInfo);
            }
        }
    }
}