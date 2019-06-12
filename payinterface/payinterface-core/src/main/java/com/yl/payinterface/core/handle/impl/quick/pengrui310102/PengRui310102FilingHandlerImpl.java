package com.yl.payinterface.core.handle.impl.quick.pengrui310102;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.quick.pengrui310102.bean.PengRui310102FilingBean;
import com.yl.payinterface.core.handle.impl.quick.pengrui310102.utils.PengRui310102Utils;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;
import com.yl.payinterface.core.utils.CodeBuilder;
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
 * 全时优服 快捷支付报备
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/27
 */
@Service("pengRui310102FilingHandler")
public class PengRui310102FilingHandlerImpl implements QuickPayFilingHandler {

    private static final Logger logger = LoggerFactory.getLogger(PengRui310102FilingHandlerImpl.class);

    private final static String interfaceInfoCode = "QUICKPAY_PengRui-310102";

    @Resource
    private QuickPayFilingInfoService quickPayFilingInfoService;

    @Override
    public Map<String, String> filing(Map<String, String> map) {
        Date reqDate = new Date();
        String orderId = PengRui310102Utils.getOrderIdByUUIdTwo();
        Map<String, String> resMap = new HashMap<String, String>();
        PengRui310102FilingBean pengRui = new PengRui310102FilingBean();
        Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>() {
        });
        String transCardNo = map.get("cardNo");
        String customerCode = map.get("customerCode") == null ? CodeBuilder.getOrderIdByUUId() : map.get("customerCode");
        String channelCustomerCode = map.get("channelCustomerCode");
        String name = map.get("name");
        String idCardNo = map.get("idCardNo");
        String phone = map.get("phone");
        String clearBankCode = map.get("clearBankCode");
        String bankName = map.get("bankName");
        String bankCode = map.get("bankCode");
        String bankCardNo = map.get("bankCardNo");
        String filingInfoCode = map.get("filingInfoCode");
        String quickPayFee = map.get("quickPayFee");
        String remitFee = map.get("remitFee");
        // 0是新增 1是变更
        String updateFlag = map.get("updateFlag");
        String[] bankInfo = PengRui310102Utils.getBankInfo(bankCode, bankName);
        String[] addr = PengRui310102Utils.getAddr();
        pengRui.setMerchantCode(customerCode);
        pengRui.setRateCode(properties.getProperty("rateCode"));
        pengRui.setMerName(name);
        pengRui.setMerAbbr(name);
        pengRui.setIdCardNo(idCardNo);
        pengRui.setBankAccountNo(bankCardNo);
        pengRui.setPhoneno(phone);
        pengRui.setBankAccountName(name);
        pengRui.setBankAccountType("PRIVATE");
        pengRui.setBankName(bankName);
        pengRui.setBankSubName(bankInfo[2]);
        pengRui.setBankCode(bankInfo[0]);
        pengRui.setBankAbbr(bankInfo[1]);
        pengRui.setBankChannelNo(clearBankCode);
        pengRui.setBankProvince(addr[0]);
        pengRui.setBankCity(addr[1]);
        pengRui.setDebitRate(quickPayFee);
        pengRui.setDebitCapAmount(properties.getProperty("debitCapAmount"));
        pengRui.setCreditRate(quickPayFee);
        pengRui.setCreditCapAmount(properties.getProperty("creditCapAmount"));
        pengRui.setWithdrawDepositRate("0");
        pengRui.setWithdrawDepositSingleFee(String.valueOf((int) AmountUtils.multiply(Double.valueOf(remitFee), 100d)));
        pengRui.setIsOrgMerchant("N");
        QuickPayFilingInfo quickPayFilingInfo = new QuickPayFilingInfo();
        quickPayFilingInfo.setBankCardNo(bankCardNo);
        quickPayFilingInfo.setBankName(bankName);
        quickPayFilingInfo.setClearBankCode(clearBankCode);
        quickPayFilingInfo.setIdCardNo (idCardNo);
        quickPayFilingInfo.setName(name);
        quickPayFilingInfo.setPhone(phone);
        quickPayFilingInfo.setQuickPayFee(quickPayFee);
        quickPayFilingInfo.setRemitFee(remitFee);
        quickPayFilingInfo.setTransCardNo(transCardNo);
        quickPayFilingInfo.setInterfaceInfoCode(interfaceInfoCode);
        quickPayFilingInfo.setCustomerCode(customerCode);
        if (updateFlag.equals("0")){
            orderId = PengRui310102Utils.getOrderIdByUUIdTwo();
            pengRui.setToken(PengRui310102Utils.getToken("15", properties.getProperty("token"), properties.getProperty("url"), properties.getProperty("key"), properties.getProperty("partnerNo")));
            pengRui.setHead(PengRui310102Utils.head(reqDate, orderId, properties.getProperty("filing"), properties.getProperty("partnerNo")));
            JSONObject res = PengRui310102Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("filing"), JsonUtils.toJsonString(pengRui), properties.getProperty("partnerNo"), orderId);
            if (!res.containsKey("head") || !res.getJSONObject("head").getString("respMsg").equals("success")){
                throw new RuntimeException("商户报备处理失败!渠道商户号：" + channelCustomerCode);
            }
            channelCustomerCode = res.getString("paltMerchantCode");
            quickPayFilingInfo.setChannelCustomerCode(channelCustomerCode);
            quickPayFilingInfoService.save(quickPayFilingInfo);
            resMap.put("status", "SUCCESS");
        } else {
            pengRui.setPlatMerchantCode(channelCustomerCode);
            String[] changeType = {"1", "2", "4"};
            for (int i = 0; i < changeType.length; i++) {
                orderId = PengRui310102Utils.getOrderIdByUUIdTwo();
                pengRui.setHead(PengRui310102Utils.head(reqDate, orderId, properties.getProperty("updated"), properties.getProperty("partnerNo")));
                pengRui.setToken(PengRui310102Utils.getToken("23", properties.getProperty("token"), properties.getProperty("url"), properties.getProperty("key"), properties.getProperty("partnerNo")));
                pengRui.setChangeType(changeType[i]);
                JSONObject res = PengRui310102Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("updated"), JsonUtils.toJsonString(pengRui), properties.getProperty("partnerNo"), orderId);
                if (!res.containsKey("head") || !res.getJSONObject("head").getString("respMsg").equals("success")){
                    throw new RuntimeException("商户报备信息修改处理失败!渠道商户号：" + channelCustomerCode);
                }
            }
            quickPayFilingInfo.setChannelCustomerCode(channelCustomerCode);
            quickPayFilingInfo.setCode(filingInfoCode);
            quickPayFilingInfoService.update(quickPayFilingInfo);
            resMap.put("status", "SUCCESS");
        }
        return resMap;
    }
}