package com.yl.payinterface.core.handle.impl.quick.pengrui310101;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.handle.impl.quick.pengrui310101.bean.PengRui310101PayBean;
import com.yl.payinterface.core.handle.impl.quick.pengrui310101.utils.PengRui310101Utils;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
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
 * 全时优服 银联快捷支付
 * 带积分
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/7
 */
@Service("pengRui310101Handler")
public class PengRui310101HandlerImpl implements QuickPayHandler, BindCardHandler {

    private final static String interfaceInfoCode = "QUICKPAY_PengRui-310101";

    private static final Logger logger = LoggerFactory.getLogger(PengRui310101HandlerImpl.class);

    @Resource
    private CustomerInterface customerInterface;

    @Resource
    private BindCardInfoService bindCardInfoService;

    @Resource
    private QuickPayFilingInfoService quickPayFilingInfoService;

    @Resource
    private QuickPayFeeService quickPayFeeService;

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Resource
    private QuickPayFilingHandler pengRui310101FilingHandler;

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
        TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(params.get("ownerId"), params.get("cardNo"));
        params.put("smsCodeType", "NOT_SMS_CODE");
        params.put("responseCode","00");
        params.put("settleCardNo", settleCardBean.getAccountNo());
        params.put("orderCode", interfaceRequest.getBussinessOrderID());
        params.put("payCardNo", params.get("cardNo"));
        params.put("phone", settleCardBean.getPhone());
        params.put("payerName", settleCardBean.getAccountName());
        params.put("certNo", settleCardBean.getIdNumber());
        return params;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        return sendVerifyCode(map);
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {
        Map<String, String> resMap = new HashMap<>();
        Date reqDate = new Date();
        Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), new TypeReference<Properties>() {
        });
        String interfaceRequestId = getInterfaceRequestId(map.get("interfaceRequestID"));
        String cardNo = map.get("cardNo");
        String ownerId = map.get("ownerId");
        String interfaceCode = map.get("interfaceCode");
        String remitFee = map.get("remitFee");
        String quickPayFee = map.get("quickPayFee");
        String amount = map.get("amount");
        String productName = map.get("productName") == null ? "充值" : map.get("productName");
        if (StringUtils.isBlank(remitFee)) {
            remitFee = String.valueOf(AmountUtils.multiply(Double.valueOf(quickPayFeeService.getRemitFee(ownerId, Double.valueOf(amount))), 100d));
        }
        if (StringUtils.isBlank(quickPayFee)) {
            quickPayFee = quickPayFeeService.getQuickPayFee(ownerId);
        }
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
        BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceCode);
        QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(settleCardBean.getAccountNo(), interfaceInfoCode);
        InterfaceRequest interfaceRequestInfo = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"));
        // 保存交易卡历史信息
        quickPayFeeService.saveTransCardHis(cardNo, interfaceRequestInfo);
        // 商户费率同步 报备处理
        String status = filing(settleCardBean, transCardBean, filingInfo, quickPayFee, remitFee, map.get("tradeConfigs"));
        if ("FAILED".equals(status)){
            throw new RuntimeException("商户报备处理失败!接口请求号：" + interfaceRequestId);
        }
        String expDate = bindCardInfo.getValidityMonth() + bindCardInfo.getValidityYear();
        PengRui310101PayBean payBean = new PengRui310101PayBean();
        payBean.setPlatMerchantCode(getTrano(ownerId, cardNo));
        payBean.setCutDate(PengRui310101Utils.formatDate(reqDate, "yyyyMMdd"));
        payBean.setPayBankCardNo(cardNo);
        payBean.setCardHolderName(transCardBean.getAccountName());
        payBean.setMobile(transCardBean.getPhone());
        payBean.setIdCard(transCardBean.getIdNumber());
        payBean.setCvv2(bindCardInfo.getCvv());
        payBean.setValidity(expDate);
        payBean.setTxnAmt(String.valueOf((int) AmountUtils.multiply(Double.valueOf(amount), 100d)));
        payBean.setExpireTime(PengRui310101Utils.formatDate(DateUtils.addDays(reqDate, 1), "yyyyMMddHHmmss"));
        payBean.setRemark(productName);
        payBean.setHead(PengRui310101Utils.head(reqDate, interfaceRequestId, properties.getProperty("sale"), properties.getProperty("partnerNo")));
        try {
            JSONObject result = PengRui310101Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("sale"), JsonUtils.toJsonString(payBean), properties.getProperty("partnerNo"), interfaceRequestId);
            if (result.getJSONObject("head") != null) {
                JSONObject head = result.getJSONObject("head");
                if (result.containsKey("payId")){
                    InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"), interfaceInfoCode);
                    interfaceRequest.setInterfaceOrderID(result.getString("payId"));
                    interfaceRequestService.modify(interfaceRequest);
                }
                if (head.getString("respCode").equals("000000")) {
                    resMap.put("authTranStat", "SUCCESS");
                    resMap.put("compType", BusinessCompleteType.NORMAL.name());
                    resMap.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
                    resMap.put("interfaceRequestID", resInterfaceRequestId(interfaceRequestId));
                    if (result.containsKey("orderStatus") && "1".equals(result.getString("orderStatus"))){
                        resMap.put("tranStat", "SUCCESS");
                        resMap.put("responseCode", "00");
                        resMap.put("responseMessage", "交易成功");
                    } else {
                        resMap.put("tranStat", "UNKNOWN");
                        resMap.put("responseCode", "01");
                        resMap.put("responseMessage", "交易成功，扣款状态未知");
                    }
                    return resMap;
                } else if (head.getString("respCode").equals("YWY1")){
                    resMap.put("responseCode", "9011");
                    resMap.put("responseMessage", "信息验证有误");
                    resMap.put("interfaceRequestID", resInterfaceRequestId(interfaceRequestId));
                    return resMap;
                } else {
                    resMap.put("responseCode", "9012");
                    resMap.put("responseMessage", "支付结果未知");
                    resMap.put("interfaceRequestID", resInterfaceRequestId(interfaceRequestId));
                    return resMap;
                }
            }
        } catch (Exception e) {
            logger.error("下单失败! 下单信息:{},错误:{}", JsonUtils.toJsonString(payBean), e);
        }
        return resMap;
    }

    @Override
    public Map<String, String> query(Map<String, String> map) {
        Date reqDate = new Date();
        String orderId = PengRui310101Utils.getOrderIdByUUIdTwo();
        Map<String, String> resMap = new HashMap<String, String>();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String interfaceRequestID = map.get("interfaceRequestID");
        InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestID, interfaceInfoCode);
        JSONObject dto = new JSONObject();
        dto.put("oriOrderId", getInterfaceRequestId(interfaceRequestID));
        dto.put("head", PengRui310101Utils.head(reqDate, orderId, properties.getProperty("query"), properties.getProperty("partnerNo")));
        try {
            JSONObject result = PengRui310101Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("query"), dto.toString(), properties.getProperty("partnerNo"), orderId);
            if (result.getJSONObject("head") != null) {
                JSONObject head = result.getJSONObject("head");
                if (head.getString("respCode").equals("000000")) {
                    if (result.containsKey("payStatus") && result.get("payStatus").equals("01")) { // 支付成功
                        resMap.put("tranStat", "SUCCESS");
                        resMap.put("interfaceCode", interfaceInfoCode);
                        resMap.put("interfaceRequestID", interfaceRequestID);
                        resMap.put("amount", String.valueOf(interfaceRequest.getAmount()));
                        resMap.put("businessCompleteType", "AUTO_REPAIR");
                        resMap.put("responseCode", "000000");
                        resMap.put("responseMessage", "支付成功");
                        return resMap;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("订单查询失败,查询订单号:{},异常:{}", interfaceRequestID, e);
            throw new RuntimeException(e.getMessage());
        }
        return resMap;
    }

    @Override
    public Map<String, String> bindCard(Map<String, String> params) {
        params.put("responseCode", "00");
        params.put("result", "00");
        params.put("smsCodeType", "NOT_SMS_CODE");
        params.put("gateway", "quickPay/openCard");
        return params;
    }

    @Override
    public Map<String, String> queryBindCard(Map<String, String> params) {
        params.put("responseCode", "01");
        params.put("smsCodeType", "NOT_SMS_CODE");
        return params;
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
        if (filingInfo == null) {
            map.put("updateFlag", "0");
            Map<String, String> resMap = pengRui310101FilingHandler.filing(map);
            if ("SUCCESS".equals(resMap.get("status"))) {
                res = "SUCCESS";
            }
        } else {
            if (!filingInfo.getRemitFee().equals(remitFee) || !filingInfo.getQuickPayFee().equals(quickPayFee) ||
                    !filingInfo.getPhone().equals(settleCardBean.getPhone())) {
                map.put("updateFlag", "1");
                map.put("customerCode", filingInfo.getCustomerCode());
                map.put("channelCustomerCode", filingInfo.getChannelCustomerCode());
                map.put("filingInfoCode", filingInfo.getCode());
                Map<String, String> resMap = pengRui310101FilingHandler.filing(map);
                if ("SUCCESS".equals(resMap.get("status"))) {
                    res = "SUCCESS";
                }
            } else {
                res = "SUCCESS";
            }
        }
        return res;
    }

    /**
     * 订单号处理
     */
    public String getInterfaceRequestId(String requestId){
        requestId = requestId.replaceAll("TD", "");
        return requestId.replaceAll("-", "");
    }

    /**
     * 订单号还原
     */
    public String resInterfaceRequestId(String requestId){
        String q = requestId.substring(8);
        String h = requestId.substring(0,8);
        return "TD-" + h + "-" + q;
    }
}