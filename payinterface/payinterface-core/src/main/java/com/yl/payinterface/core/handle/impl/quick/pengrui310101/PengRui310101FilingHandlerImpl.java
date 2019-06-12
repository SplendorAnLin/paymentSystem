package com.yl.payinterface.core.handle.impl.quick.pengrui310101;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.handle.impl.quick.pengrui310101.bean.PengRui310101FilingBean;
import com.yl.payinterface.core.handle.impl.quick.pengrui310101.utils.PengRui310101Utils;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;
import com.yl.payinterface.core.utils.CodeBuilder;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
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
 * 全时优服 入网报备
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/6
 */
@Service("pengRui310101FilingHandler")
public class PengRui310101FilingHandlerImpl implements QuickPayFilingHandler {

    private static final Logger logger = LoggerFactory.getLogger(PengRui310101FilingHandlerImpl.class);

    private final static String interfaceInfoCode = "QUICKPAY_PengRui-310101";

    @Resource
    private QuickPayFilingInfoService quickPayFilingInfoService;

    @Override
    public Map<String, String> filing(Map<String, String> map) {
        Date reqDate = new Date();
        String orderId = PengRui310101Utils.getOrderIdByUUIdTwo();
        Map<String, String> resMap = new HashMap<String, String>();
        PengRui310101FilingBean pengRui = new PengRui310101FilingBean();
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
        String bankCardNo = map.get("bankCardNo");
        String filingInfoCode = map.get("filingInfoCode");
        String quickPayFee = map.get("quickPayFee");
        String remitFee = map.get("remitFee");
        // 0是新增 1是变更
        String updateFlag = map.get("updateFlag");
        pengRui.setMerchantCode(customerCode);
        pengRui.setMerchantName(name);
        pengRui.setShortName(name);
        pengRui.setCardHolderName(name);
        pengRui.setTel(phone);
        pengRui.setMobile(phone);
        pengRui.setIdCard(idCardNo);
        pengRui.setInBankCardNo(bankCardNo);
        pengRui.setArriveMode("T0");
        pengRui.setTxnRate(quickPayFee);
        pengRui.setT0TxnFee(String.valueOf((int) AmountUtils.multiply(Double.valueOf(remitFee), 100d)));
        if (updateFlag.equals("0")) {
            pengRui.setOperateType("A");
        } else {
            pengRui.setOperateType("M");
        }
        pengRui.setHead(PengRui310101Utils.head(reqDate, orderId, properties.getProperty("filing"), properties.getProperty("partnerNo")));
        try {
            JSONObject res = PengRui310101Utils.execute(properties.getProperty("key"), properties.getProperty("url"), properties.getProperty("filing"), JsonUtils.toJsonString(pengRui), properties.getProperty("partnerNo"), orderId);
            if (res.get("head") != null) {
                JSONObject head = res.getJSONObject("head");
                if (head.getString("respCode").equals("000000")) {
                    QuickPayFilingInfo quickPayFilingInfo = new QuickPayFilingInfo();
                    quickPayFilingInfo.setBankCardNo(bankCardNo);
                    quickPayFilingInfo.setBankName(bankName);
                    quickPayFilingInfo.setClearBankCode(clearBankCode);
                    quickPayFilingInfo.setIdCardNo(idCardNo);
                    quickPayFilingInfo.setName(name);
                    quickPayFilingInfo.setPhone(phone);
                    quickPayFilingInfo.setQuickPayFee(quickPayFee);
                    quickPayFilingInfo.setRemitFee(remitFee);
                    quickPayFilingInfo.setTransCardNo(transCardNo);
                    quickPayFilingInfo.setInterfaceInfoCode(interfaceInfoCode);
                    quickPayFilingInfo.setCustomerCode(customerCode);
                    if (StringUtils.isBlank(filingInfoCode)) {
                        channelCustomerCode = res.getString("platMerchantCode");
                        quickPayFilingInfo.setChannelCustomerCode(channelCustomerCode);
                        quickPayFilingInfoService.save(quickPayFilingInfo);
                    } else {
                        quickPayFilingInfo.setChannelCustomerCode(channelCustomerCode);
                        quickPayFilingInfo.setCode(filingInfoCode);
                        quickPayFilingInfo.setPhone(phone);
                        quickPayFilingInfoService.update(quickPayFilingInfo);
                    }
                    resMap.put("status", "SUCCESS");
                }
            }
        } catch (Exception e) {
            logger.error("入网报备出错!报备信息：{}, 错误：{}", JsonUtils.toJsonString(pengRui), e);
            resMap.put("status", "FAILED");
        }
        return resMap;
    }
}