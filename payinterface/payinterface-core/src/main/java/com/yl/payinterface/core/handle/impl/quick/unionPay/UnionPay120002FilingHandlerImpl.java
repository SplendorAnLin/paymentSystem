package com.yl.payinterface.core.handle.impl.quick.unionPay;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.handle.impl.quick.unionPay.bean.UnionPay120001FilingBean;
import com.yl.payinterface.core.handle.impl.quick.unionPay.utils.AddressUtils;
import com.yl.payinterface.core.handle.impl.quick.unionPay.utils.UnionPay120001EncryptionDES;
import com.yl.payinterface.core.handle.impl.quick.unionPay.utils.UnionPay120001Utils;
import com.yl.payinterface.core.handler.QuickPayFilingHandler;
import com.yl.payinterface.core.model.QuickPayFilingInfo;
import com.yl.payinterface.core.service.QuickPayFilingInfoService;
import com.yl.payinterface.core.utils.CodeBuilder;

/**
 * 
 * @ClassName UnionPay120001FilingHandlerImpl
 * @Description 银联报备
 * @author Shark
 * @date 2017年11月18日 下午4:44:33
 */
@Service("unionPay120002FilingHandler")
public class UnionPay120002FilingHandlerImpl implements QuickPayFilingHandler{
	private static Logger logger = LoggerFactory.getLogger(UnionPay120002FilingHandlerImpl.class);
	
	private final static String interfaceInfoCode = "QUICKPAY_UnionPay-120002"; 
	
	@Resource
	private QuickPayFilingInfoService quickPayFilingInfoService;
	
	@Override
	public Map<String, String> filing(Map<String, String> map) {
		Map<String, String> rtMap = new HashMap<String, String>();
		UnionPay120001FilingBean filingBean = new UnionPay120001FilingBean();

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
		String dmType = properties.getProperty("dmType");
		String filingNoticeUrl = properties.getProperty("filingNoticeUrl");
		String filingUrl = properties.getProperty("filingUrl");
		String filingPort = properties.getProperty("filingPort");
		String filingKey = properties.getProperty("filingKey");
		String orgNo = properties.getProperty("orgNo");
		String remitfee = map.get("remitFee");
		String filingInfoCode = map.get("filingInfoCode");
		// 0是新增 1是变更
		String updateFlag = map.get("updateFlag");
		remitfee = "FJ" + getFee(String.valueOf((int) AmountUtils.multiply(Double.valueOf(remitfee), 100D)));
		remitfee = remitfee + "|" + remitfee;
		String quickPayFee = map.get("quickPayFee");
		quickPayFee = "R" + getFee(String.valueOf((int) AmountUtils.multiply(Double.valueOf(quickPayFee), 100000D)));
		String s0QuickPayFee = quickPayFee + "|" + quickPayFee;

		filingBean.setJg_dealernum(customerCode);
		filingBean.setOn_dea_name(name);
		filingBean.setOn_dea_reg_address(AddressUtils.getRoad());
		filingBean.setOn_dea_artif_id(idCardNo);
		filingBean.setOn_dea_phone(phone);
		filingBean.setOn_dea_bank_num(clearBankCode);
		filingBean.setOn_dea_bank_name(bankName);
		filingBean.setOn_dea_settle_name(name);
		filingBean.setOn_dea_settle_card(bankCardNo);
		filingBean.setIspublic("2");
		filingBean.setDm_type(dmType);
		filingBean.setWkAffixRate(remitfee);
		filingBean.setWkRateD1(quickPayFee);
		filingBean.setWkRateS0(s0QuickPayFee);
		filingBean.setDmType(updateFlag);
		filingBean.setAsynUrl(filingNoticeUrl);
		filingBean.setOrgNo(orgNo);
		try {

			logger.info("交易卡号：{},上送商户报文：{}", transCardNo, JsonUtils.toJsonString(filingBean));
			String param = new UnionPay120001EncryptionDES(filingKey).encrypt(URLEncoder.encode(JSONObject.fromObject(filingBean).toString(), "UTF-8"));

			String responseStr = UnionPay120001Utils.connServer(param, filingUrl, filingPort);
		
			logger.info("交易卡号：{}上送商户返回：{}", transCardNo, responseStr);
			
			Map<String, String> returnMap = JsonUtils.toObject(responseStr, new TypeReference<Map<String, String>>() {});
			if ("00".equals(returnMap.get("resultCode")) || "01".equals(returnMap.get("resultCode"))) {
				QuickPayFilingInfo quickPayFilingInfo = new QuickPayFilingInfo();
				quickPayFilingInfo.setBankCardNo(bankCardNo);
				quickPayFilingInfo.setBankName(bankName);
				quickPayFilingInfo.setClearBankCode(clearBankCode);
				quickPayFilingInfo.setIdCardNo(idCardNo);
				quickPayFilingInfo.setName(name);
				quickPayFilingInfo.setPhone(phone);
				quickPayFilingInfo.setQuickPayFee(map.get("quickPayFee"));
				quickPayFilingInfo.setRemitFee(map.get("remitFee"));
				quickPayFilingInfo.setTransCardNo(transCardNo);
				quickPayFilingInfo.setInterfaceInfoCode(interfaceInfoCode);
				quickPayFilingInfo.setCustomerCode(customerCode);
				quickPayFilingInfo.setStatus(InterfaceInfoStatus.TRUE);
				if (StringUtils.isBlank(filingInfoCode)) {
					channelCustomerCode = returnMap.get("on_dealernum");
					quickPayFilingInfo.setChannelCustomerCode(channelCustomerCode);
					quickPayFilingInfoService.save(quickPayFilingInfo);
				} else {
					quickPayFilingInfo.setChannelCustomerCode(channelCustomerCode);
					quickPayFilingInfo.setCode(filingInfoCode);
					quickPayFilingInfoService.update(quickPayFilingInfo);
				}
				rtMap.put("status", "SUCCESS");
				
			}

		} catch (Exception e) {
			logger.info("交易卡号：{}上送商户报错：{}", transCardNo, e);
			rtMap.put("status", "FAILED");
		}
		return rtMap;
	}
	
	private String getFee(String fee) {
		if (fee.length() == 1){
			return "00" + fee;
		} else if (fee.length() == 2) {
			return "0" + fee;
		} else {
			return fee;
		}
	}
	
}
