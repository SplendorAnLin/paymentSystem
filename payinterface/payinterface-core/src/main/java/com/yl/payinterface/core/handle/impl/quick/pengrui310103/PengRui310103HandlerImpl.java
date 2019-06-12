package com.yl.payinterface.core.handle.impl.quick.pengrui310103;

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
import com.yl.payinterface.core.handle.impl.quick.pengrui310102.utils.PengRui310102Utils;
import com.yl.payinterface.core.handle.impl.quick.pengrui310103.bean.PengRui310103PayBean;
import com.yl.payinterface.core.handle.impl.quick.pengrui310103.bean.PengRui310103SmsBean;
import com.yl.payinterface.core.handle.impl.quick.pengrui310103.utils.PengRui310103Utils;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.handler.QuickPayOpenCardHandler;
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
 * 全时优服 商旅 快捷支付
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/2/28
 */
@Service("quickPengRui310103Handler")
public class PengRui310103HandlerImpl implements QuickPayHandler, BindCardHandler, QuickPayOpenCardHandler {

    private static final Logger logger = LoggerFactory.getLogger(PengRui310103HandlerImpl.class);

    private static final String interfaceInfoCode = "QUICKPAY_PengRui-310103";

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
        params.put("responseCode", "01");
        return params;
    }

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        PengRui310103SmsBean pengRui = new PengRui310103SmsBean();
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
        Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>() {});
        Date reqDate = new Date();
        String orderId = PengRui310103Utils.getInterfaceRequestId(interfaceRequest.getInterfaceRequestID());
        String cardNo = params.get("cardNo");
        String ownerId = params.get("ownerId");
        String amount = params.get("amount");
        String quickPayFee = params.get("quickPayFee");
        String remitFee = params.get("remitFee");
        if (StringUtils.isBlank(remitFee)) {
            remitFee = quickPayFeeService.getRemitFee(ownerId, Double.valueOf(amount));
        }
        if (StringUtils.isBlank(quickPayFee)) {
            quickPayFee = quickPayFeeService.getQuickPayFee(ownerId);
        }
        // 获取结算卡信息
        Map<Object,Object> settMap = new HashMap<>();
        quickPayFeeService.getSettleInfo(settMap, interfaceRequest.getOwnerID(), cardNo);
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
        BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceInfoCode);
        pengRui.setHead(PengRui310103Utils.head(reqDate, orderId, properties.getProperty("sms"), properties.getProperty("partnerNo")));
        pengRui.setCutDate(PengRui310103Utils.formatDate(reqDate, "yyyyMMdd"));
        pengRui.setNotifyUrl(properties.getProperty("notifyUrl"));
        pengRui.setTxnAmt(String.valueOf((int) AmountUtils.multiply(Double.valueOf(amount), 100d)));
        pengRui.setT0TxnRate(quickPayFee);
        pengRui.setT0TxnFee(String.valueOf((int) AmountUtils.multiply(Double.valueOf(remitFee), 100d)));
        pengRui.setCardHolderName(transCardBean.getAccountName());
        pengRui.setMobile(transCardBean.getPhone());
        pengRui.setIdCard(settleCardBean.getIdNumber());
        pengRui.setCvv2(bindCardInfo.getCvv());
        pengRui.setValidity(bindCardInfo.getValidityMonth() + bindCardInfo.getValidityYear());
        pengRui.setPayBankCardNo(cardNo);
        pengRui.setInBankCardNo(settleCardBean.getAccountNo());
        String[] payBankInfo = PengRui310103Utils.getBankInfo(transCardBean.getBankCode(),"");
        String[] inBankInfo = PengRui310103Utils.getBankInfo(settleCardBean.getBankCode(),"");
        if (payBankInfo[0].equals("905")) {
            throw new RuntimeException("暂不支持此支付卡");
        }
        if (inBankInfo[0].equals("905")) {
            throw new RuntimeException("暂不支持此结算卡");
        }
        pengRui.setPayBankCode(payBankInfo[0]);
        pengRui.setInBankCode(inBankInfo[0]);
        pengRui.setRemark("充值");
        JSONObject res = PengRui310103Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("sms"), JsonUtils.toJsonString(pengRui), properties.getProperty("partnerNo"), orderId);
        Map<String, String> retMap = new HashMap<>();
        if (res!= null && res.containsKey("head") && res.getJSONObject("head").getString("respCode").equals("000000")) {
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
            interfaceRequest.setInterfaceOrderID(res.getString("workId"));
            interfaceRequestService.modify(interfaceRequest);
            Map<String, String> bindCard = new HashMap<>();
            bindCard.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
            bindCard.put("interfaceCode", params.get("interfaceInfoCode"));
            bindCard.put("cardNo", cardNo);
            bindCard.put("realName", settMap.get("realName").toString());
            retMap.put("key", cardNo);
            try {
                CacheUtils.setEx("BINDCARD:" + cardNo, JsonUtils.toJsonString(bindCard), 900);
                CacheUtils.setEx("WORKID:" + cardNo, res.getString("workId"), 3600);
            } catch (Exception e) {
                logger.error("全时优服快捷支付 缓存绑卡信息异常 接口编号:[{}],卡号:[{}],异常信息：[{}]", params.get("interfaceInfoCode"), params.get("cardNo"), e);
            }
            retMap.put("gateway", "quickPay");
        }
        return retMap;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
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
        String workId = CacheUtils.get("WORKID:" + cardNo, String.class);
        if (StringUtils.isBlank(remitFee)) {
            remitFee = quickPayFeeService.getRemitFee(ownerId, Double.valueOf(amount));
        }
        if (StringUtils.isBlank(quickPayFee)) {
            quickPayFee = quickPayFeeService.getQuickPayFee(ownerId);
        }
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
        QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(settleCardBean.getAccountNo(), interfaceInfoCode);
        // 保存交易卡历史信息
        InterfaceRequest interfaceRequestInfo = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestID);
        quickPayFeeService.saveTransCardHis(cardNo, interfaceRequestInfo);
        PengRui310103PayBean payBean = new PengRui310103PayBean();
        payBean.setHead(PengRui310103Utils.head(reqDate, PengRui310103Utils.getInterfaceRequestId(interfaceRequestID), properties.getProperty("pay"), properties.getProperty("partnerNo")));
        payBean.setSmsCode(smsCode);
        payBean.setWorkId(workId);
        JSONObject result = PengRui310102Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("pay"), JsonUtils.toJsonString(payBean), properties.getProperty("partnerNo"), PengRui310102Utils.getInterfaceRequestId(interfaceRequestID));
        Map<String, String> resMap = new HashMap<>();
        resMap.put("responseCode", "01");
        resMap.put("responseMsg", "支付失败!");
        if (result != null && result.containsKey("head") && result.getJSONObject("head").getString("respCode").equals("000000")){
            updateBindCardInfo(cardNo, interfaceInfoCode);
            resMap.put("authTranStat", "SUCCESS");
            resMap.put("compType", BusinessCompleteType.NORMAL.name());
            resMap.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
            resMap.put("interfaceRequestID", interfaceRequestID);
            if (result.containsKey("orderStatus")) {
                if (result.getString("orderStatus").equals("01")) {
                    resMap.put("tranStat", "SUCCESS");
                    resMap.put("responseCode", "00");
                    resMap.put("responseMsg", "交易成功");
                } else if (result.getString("orderStatus").equals("04")) {
                    resMap.put("tranStat", "SUCCESS");
                    resMap.put("responseCode", "01");
                    resMap.put("responseMsg", "已提交银行处理，扣款状态未知");
                } else if (result.getString("orderStatus").equals("02")) {
                    resMap.put("tranStat", "UNKNOWN");
                    resMap.put("responseCode", "01");
                    resMap.put("responseMsg", "支付失败");
                } else {
                    resMap.put("tranStat", "UNKNOWN");
                    resMap.put("responseCode", "01");
                    resMap.put("responseMsg", "超时");
                }
            }
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        InterfaceRequest interfaceRequestInfo = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"));
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {});
        String orderId = PengRui310103Utils.getOrderIdByUUIdTwo();
        Date reqDate = new Date();
        JSONObject dto = new JSONObject();
        dto.put("head", PengRui310103Utils.head(reqDate, orderId, properties.getProperty("query"), properties.getProperty("partnerNo")));
        dto.put("workId", interfaceRequestInfo.getInterfaceOrderID());
        dto.put("orderId", PengRui310103Utils.getInterfaceRequestId(interfaceRequestInfo.getInterfaceRequestID()));
        JSONObject result = PengRui310102Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("query"), dto.toString(), properties.getProperty("partnerNo"), orderId);
        Map<String, String> resMap = new HashMap<String, String>();
        if (result.containsKey("head") && result.getJSONObject("head").getString("respCode").equals("000000")){
            if (result.getString("orderStatus").equals("01")){
                resMap.put("tranStat", "SUCCESS");
                resMap.put("interfaceCode", interfaceInfoCode);
                resMap.put("interfaceRequestID", map.get("interfaceRequestID"));
                resMap.put("amount", String.valueOf(interfaceRequestInfo.getAmount()));
                resMap.put("businessCompleteType", "AUTO_REPAIR");
                resMap.put("responseCode", "000000");
                resMap.put("responseMessage", "支付成功");
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
        return null;
    }

    /**
     *
     * @Description 更新绑卡信息
     * @param cardNo
     * @param interfaceCode
     * @date 2017年11月3日
     */
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