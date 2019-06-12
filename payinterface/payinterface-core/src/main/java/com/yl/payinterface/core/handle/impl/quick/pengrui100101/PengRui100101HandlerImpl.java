package com.yl.payinterface.core.handle.impl.quick.pengrui100101;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.handle.impl.quick.pengrui100101.bean.PengRui100101PayBean;
import com.yl.payinterface.core.handle.impl.quick.pengrui100101.utils.PengRui100101Utils;
import com.yl.payinterface.core.handle.impl.quick.pengrui310101.utils.PengRui310101Utils;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
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
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 全时优服 交易处理
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/20
 */
@Service("pengRui100101Handler")
public class PengRui100101HandlerImpl implements QuickPayHandler, BindCardHandler {

    private final static String interfaceInfoCode = "QUICKPAY_PengRui-100101";

    private static final Logger logger = LoggerFactory.getLogger(PengRui100101HandlerImpl.class);

    @Resource
    private InterfaceRequestService interfaceRequestService;

    @Resource
    private CustomerInterface customerInterface;

    @Resource
    private QuickPayFilingInfoService quickPayFilingInfoService;

    @Resource
    private BindCardInfoService bindCardInfoService;

    @Resource
    private QuickPayFilingHandler pengRui100101FilingHandler;

    @Resource
    private QuickPayFeeService quickPayFeeService;

    @Override
    public Map<String, String> bindCard(Map<String, String> params) {
        String interfaceInfoCode = params.get("interfaceCode");
        String cardNo = params.get("bankCardNo");
        String ownerId = params.get("partner");
        String remitFee = params.get("remitFee");
        String quickPayFee = params.get("quickPayFee");
        String amount = params.get("amount");
        if (StringUtils.isBlank(remitFee)) {
            remitFee = quickPayFeeService.getRemitFee(ownerId, Double.valueOf(amount));
        }
        if (StringUtils.isBlank(quickPayFee)) {
            quickPayFee = quickPayFeeService.getQuickPayFee(ownerId);
        }
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
        QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(settleCardBean.getAccountNo(), interfaceInfoCode);
        String status = filing(settleCardBean, transCardBean, filingInfo, quickPayFee, remitFee, params.get("tradeConfigs"));
        if ("FAILED".equals(status)){
            throw new RuntimeException("商户报备处理失败!接口编号：" + interfaceInfoCode);
        }
        params.put("smsCodeType", "NO_YLZF_CODE");
        params.put("responseCode", "00");
        params.put("result", "00");
        params.put("gateway", "quickPay/openCard");
        return params;
    }

    @Override
    public Map<String, String> queryBindCard(Map<String, String> params) {
        params.put("responseCode", "00");
        return params;
    }

    @Override
    public Map<String, String> sendVerifyCode(Map<String, String> params) {
        return null;
    }

    @Override
    public Map<String, String> authPay(Map<String, String> map) {
        String responseCode = "99";
        String html = "";
        Map<String, String> resMap = new HashMap<>();
        PengRui100101PayBean pengRui = new PengRui100101PayBean();
        Date reqDate = new Date();
        Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), new TypeReference<Properties>() {
        });
        String interfaceRequestId = getInterfaceRequestId(map.get("interfaceRequestID"));
        String cardNo = map.get("cardNo");
        String ownerId = map.get("ownerId");
        String remitFee = map.get("remitFee");
        String quickPayFee = map.get("quickPayFee");
        String amount = map.get("amount");
        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, cardNo, CardAttr.TRANS_CARD);
        TransCardBean settleCardBean = customerInterface.findByCustAndAccNo(ownerId, cardNo);
        QuickPayFilingInfo filingInfo = quickPayFilingInfoService.find(settleCardBean.getAccountNo(), interfaceInfoCode);
        String name = transCardBean.getAccountName();
        String phone = transCardBean.getPhone();
        String idCard = transCardBean.getIdNumber();
        String settleCardNo = settleCardBean.getAccountNo();
        String settleBankName = settleCardBean.getBankName();
        String settleClearBankCode = settleCardBean.getClearBankCode();
        if (StringUtils.isBlank(remitFee)) {
            remitFee = quickPayFeeService.getRemitFee(ownerId, Double.valueOf(amount));
        }
        if (StringUtils.isBlank(quickPayFee)) {
            quickPayFee = quickPayFeeService.getQuickPayFee(ownerId);
        }
        InterfaceRequest interfaceRequestInfo = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"));
        // 保存交易卡历史信息
        quickPayFeeService.saveTransCardHis(cardNo, interfaceRequestInfo);
        String multiplyFee = String.valueOf((int) AmountUtils.multiply(Double.valueOf(remitFee), 100d));
        String txnAmt = String.valueOf((int) AmountUtils.multiply(Double.valueOf(amount), 100d));
        String fee = String.valueOf((int) AmountUtils.round(AmountUtils.multiply(Double.valueOf(txnAmt), Double.valueOf(quickPayFee)), 0, RoundingMode.UP));
        String inTxnAmt = String.valueOf((int) AmountUtils.subtract(AmountUtils.subtract(Double.valueOf(txnAmt), Double.valueOf(fee)), Double.valueOf(multiplyFee)));
        // 商户费率同步 报备处理
        String status = filing(settleCardBean, transCardBean, filingInfo, quickPayFee, remitFee, map.get("tradeConfigs"));
        if ("FAILED".equals(status)){
            throw new RuntimeException("商户报备处理失败!接口请求号：" + interfaceRequestId);
        }
        pengRui.setHead(PengRui100101Utils.head(reqDate, interfaceRequestId, properties.getProperty("sale"), properties.getProperty("partnerNo")));
        pengRui.setFrontNotifyUrl(properties.getProperty("frontNotifyUrl"));
        pengRui.setBackNotifyUrl(properties.getProperty("backNotifyUrl"));
        pengRui.setPlatMerchantCode(getTrano(ownerId, cardNo));
        pengRui.setCutDate(PengRui100101Utils.formatDate(reqDate, "yyyyMMdd"));
        pengRui.setTxnAmt(txnAmt);
        pengRui.setCardHolderName(name);
        pengRui.setMobile2(phone);
        pengRui.setIdCard(idCard);
        pengRui.setInBankCardNo(settleCardNo);
        pengRui.setPayBankCardNo(cardNo);
        pengRui.setInBankName(settleBankName);
        pengRui.setInBankUnitNo(settleClearBankCode);
        pengRui.setInTxnAmt(inTxnAmt);
        pengRui.setT0TxnRate(quickPayFee);
        pengRui.setT0TxnFee(String.valueOf((int) AmountUtils.multiply(Double.valueOf(remitFee), 100d)));
        pengRui.setFee(fee);
        try {
            JSONObject result = PengRui100101Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("sale"), JsonUtils.toJsonString(pengRui), properties.getProperty("partnerNo"), interfaceRequestId);
            if (result.getJSONObject("head") != null) {
                JSONObject head = result.getJSONObject("head");
                if (head.containsKey("workId")){
                    InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"), interfaceInfoCode);
                    interfaceRequest.setInterfaceOrderID(head.getString("workId"));
                    interfaceRequestService.modify(interfaceRequest);
                }
                if (head.getString("respCode").equals("000000")) {
                    responseCode = "00";
                    html = result.getString("pageContent");
                }
            }
        } catch (Exception e) {
            logger.error("交易失败：{}", e);
        }
        resMap.put("interfaceRequestID", map.get("interfaceRequestID"));
        resMap.put("responseCode", responseCode);
        resMap.put("payPage", "INTERFACE");
        resMap.put("gateway", "htmlSubmit");
        resMap.put("html", html);
        return resMap;
    }

    @Override
    public Map<String, String> sale(Map<String, String> map) {
        return null;
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
        dto.put("head", PengRui100101Utils.head(reqDate, orderId, properties.getProperty("query"), properties.getProperty("partnerNo")));
        try {
            JSONObject result = PengRui310101Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("query"), dto.toString(), properties.getProperty("partnerNo"), orderId);
            if (result.getJSONObject("head") != null) {
                JSONObject head = result.getJSONObject("head");
                if (head.getString("respCode").equals("000000") && head.getString("respMsg").equals("success")) {
                    if (result.containsKey("payStatus") && result.getString("payStatus").equals("01")){
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
            Map<String, String> resMap = pengRui100101FilingHandler.filing(map);
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
                Map<String, String> resMap = pengRui100101FilingHandler.filing(map);
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
     * 获取渠道商户号
     */
    private String getTrano(String ownerId, String bankCardNo) {
        TransCardBean transCardBean = customerInterface.findByCustAndAccNo(ownerId, bankCardNo);
        QuickPayFilingInfo quickPayFilingInfo = quickPayFilingInfoService.find(transCardBean.getAccountNo(), interfaceInfoCode);
        return quickPayFilingInfo.getChannelCustomerCode();
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