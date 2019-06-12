package com.yl.payinterface.core.handle.impl.quick.dianpayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.C;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handle.impl.quick.dianpayer.utils.DianPayerMD5;
import com.yl.payinterface.core.handle.impl.quick.dianpayer.utils.HttpUtils;
import com.yl.payinterface.core.handle.impl.quick.dianpayer.utils.SymmtricCryptoUtil;
import com.yl.payinterface.core.handle.impl.wallet.cbhb120000.wechat.utils.CommonsUtil;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.model.BindCardInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.service.BindCardInfoService;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.service.QuickPayFeeService;

/** 
 * @ClassName DianPayer310101Handler 
 * @Description TODO(这里用一句话描述这个类的作用) 
 * @author Administrator 
 * @date 2018年2月27日 下午3:26:42  
 */
@Service("quickDianPayer310101Handler")
public class DianPayer310101Handler implements BindCardHandler, QuickPayHandler{

	private static Logger logger = LoggerFactory.getLogger(DianPayer310101Handler.class);

	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private InterfaceInfoService interfaceInfoService;
	@Resource
	private QuickPayFeeService quickPayFeeService;
	@Resource
	private BindCardInfoService bindCardInfoService;
	
	@Override
	public Map<String, String> sendVerifyCode(Map<String, String> params) {
		Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>() {
		});
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
		String notifyUrl = properties.getProperty("notifyUrl");
		String frontUrl = properties.getProperty("frontUrl");
		String merchantId = properties.getProperty("merchantId");
		String key = properties.getProperty("key");
		String encKey = properties.getProperty("encKey");
		String url = properties.getProperty("url");
		String partnerId = properties.getProperty("partnerId");
		
		
		String cardNo = params.get("cardNo");
		String interfaceCode = params.get("interfaceCode");
		String interfaceRequestID = params.get("interfaceRequestID");

		String settleType = params.get("settleType");
		if (C.SETTLE_TYPE_AMOUNT.equals(settleType)) {
			throw new BusinessRuntimeException("9010");
		}
		String remitFee = params.get("remitFee");
		String quickPayFee = params.get("quickPayFee");

		if (StringUtils.isBlank(remitFee)) {
			remitFee = quickPayFeeService.getRemitFee(interfaceRequest.getOwnerID(), interfaceRequest.getAmount());
		}
		if (StringUtils.isBlank(quickPayFee)) {
			quickPayFee = quickPayFeeService.getQuickPayFee(interfaceRequest.getOwnerID());
		}
		logger.info("接口请求号：{}，代付费率：{}，快捷费率：{}", interfaceRequest.getInterfaceRequestID(), remitFee, quickPayFee);

		// 检查交易卡信息
		TransCardBean transCardBean = customerInterface.findTransCardByAccNo(interfaceRequest.getOwnerID(), cardNo, CardAttr.TRANS_CARD);
		// 获取结算卡信息
		TransCardBean settleInfo = quickPayFeeService.getSettleInfo(interfaceRequest.getOwnerID(), cardNo);
		// 保存交易卡历史信息
		quickPayFeeService.saveTransCardHis(cardNo, interfaceRequest);

		BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceCode);
		// 判断银行卡是否支持
		String bankCode = properties.getProperty(transCardBean.getBankCode());
		if (StringUtils.isBlank(bankCode)) {
			throw new BusinessRuntimeException("9006");
		}
		String settlementBankCode = properties.getProperty(settleInfo.getBankCode());
		if (StringUtils.isBlank(settlementBankCode)) {
			throw new BusinessRuntimeException("9007");
		}
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("amount", String.valueOf((int) AmountUtils.multiply(interfaceRequest.getAmount(), 100)));
		reqMap.put("payType", "kjPay");
		reqMap.put("orderId", interfaceRequestID.replace("-", "").replace("TD", ""));
		reqMap.put("businessTime", System.currentTimeMillis() + "");
		reqMap.put("notifyUrl", notifyUrl);
		reqMap.put("frontUrl", frontUrl);
		reqMap.put("orderDesc", settleInfo.getAccountName());
		reqMap.put("merchantId", merchantId);
		reqMap.put("merchantName", settleInfo.getAccountName());
		
		StringBuffer quickInfoMap = new StringBuffer();
		quickInfoMap.append("{'idNo':'" + settleInfo.getIdNumber() + "',");
		quickInfoMap.append("'name':'" + settleInfo.getAccountName() + "',");
		quickInfoMap.append("'mobile':'" + transCardBean.getPhone() + "',");
		quickInfoMap.append("'settlementAcc':'" + settleInfo.getAccountNo() + "',");
		quickInfoMap.append("'acc':'" + cardNo + "',");
		quickInfoMap.append("'cvv':'" + bindCardInfo.getCvv() + "',");
		quickInfoMap.append("'t0Fee':'" + String.valueOf((int)AmountUtils.multiply(Double.valueOf(remitFee), 100)) + "',");
		quickInfoMap.append("'feeRate':'" + String.valueOf(AmountUtils.multiply(Double.valueOf(quickPayFee), 100)) + "',");
		quickInfoMap.append("'validityDate':'" + bindCardInfo.getValidityMonth()  +  bindCardInfo.getValidityYear() + "',");
		quickInfoMap.append("'bankCode':'" + bankCode + "',");
		quickInfoMap.append("'settlementBankCode':'" + settlementBankCode + "'}");
		reqMap.put("mExtraMap", quickInfoMap.toString());
		logger.info("接口请求号：{}，发起交易报文：{}", interfaceRequest.getInterfaceRequestID(), quickInfoMap.toString());
		
		Map<String, Object> returnMsg = this.sendRequest(key, encKey, partnerId, "pushOrder", JsonUtils.toJsonString(reqMap), url);
		logger.info("接口请求号：{}，发起交易返回报文：{}", interfaceRequest.getInterfaceRequestID(), returnMsg.toString());
		Map<String, String> retMap = new HashMap<>();
		if ("00".equals(returnMsg.get("errCode"))) {
			retMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
			retMap.put("orderCode", interfaceRequest.getBussinessOrderID());
			retMap.put("payCardNo", params.get("cardNo"));
			retMap.put("settleCardNo", settleInfo.getAccountNo());
			retMap.put("payerName", settleInfo.getAccountName());
			retMap.put("certNo", settleInfo.getIdNumber());
			retMap.put("phone", transCardBean.getPhone());
			retMap.put("responseCode", "00");
			retMap.put("token", params.get("token"));
			retMap.put("amount", Double.toString(interfaceRequest.getAmount()));
			retMap.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
			retMap.put("responseCode", "00");
			retMap.put("gateway", "quickPay");
		} else {
			retMap.put("responseCode", "2001");
			retMap.put("responseMessage", "短信验证码发送失败");
			retMap.put("gateway", "quickPay");
		} 
		retMap.put("cardNo", cardNo);
		retMap.put("interfaceInfoCode", params.get("interfaceInfoCode"));
		return retMap;
	}

	@Override
	public Map<String, String> authPay(Map<String, String> map) {
		return sendVerifyCode(map);
	}

	@Override
	public Map<String, String> sale(Map<String, String> map) {
		Properties properties = JsonUtils.toObject(map.get("tradeConfigs"), new TypeReference<Properties>() {});
		String merchantId = properties.getProperty("merchantId");
		String key = properties.getProperty("key");
		String encKey = properties.getProperty("encKey");
		String url = properties.getProperty("url");
		String partnerId = properties.getProperty("partnerId");
		String notifyUrl = properties.getProperty("notifyUrl");
		String frontUrl = properties.getProperty("frontUrl");
		
		InterfaceRequest interfaceRequest = JsonUtils.toObject(map.get("interfaceRequest"), new TypeReference<InterfaceRequest>() {});
		String smsCode = map.get("smsCode");
		
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("payType", "kjPayC");
		reqMap.put("orderId", interfaceRequest.getInterfaceRequestID().replace("-", "").replace("TD", ""));
		reqMap.put("merchantId", merchantId);
		reqMap.put("amount", String.valueOf((int) AmountUtils.multiply(interfaceRequest.getAmount(), 100)));
		reqMap.put("businessTime", System.currentTimeMillis() + "");
		reqMap.put("notifyUrl", notifyUrl);
		reqMap.put("frontUrl", frontUrl);
		reqMap.put("mExtraMap", "{smsCode:'" + smsCode + "'}");
		
		Map<String, Object> returnMsg = this.sendRequest(key, encKey, partnerId, "pushOrder", JsonUtils.toJsonString(reqMap), url);
		Map<String, String> respMap = new HashMap<>();
		respMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
        respMap.put("amount", Double.toString(interfaceRequest.getAmount()));
        respMap.put("authTranStat", "UNKNOWN");
		if ("00".equals(returnMsg.get("errCode"))) {
			respMap.put("authTranStat", "SUCCESS");
            respMap.put("compType", BusinessCompleteType.NORMAL.name());
            
            respMap.put("tranStat", "UNKNOWN");
            respMap.put("responseCode", "01");
            respMap.put("responseMsg", "交易成功，扣款状态未知");
            return respMap;
		} else {
			respMap.put("responseCode", "9003");
            respMap.put("responseMsg", "交易失败");
		}
		return respMap;
	}
	@Override
	public Map<String, String> query(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, String> bindCard(Map<String, String> params) {
		params.put("smsCodeType", "NOT_SMS_CODE");
		params.put("responseCode", "00");
		params.put("result", "00");
		params.put("gateway", "quickPay/openCard");
		return params;
	}

	@Override
	public Map<String, String> queryBindCard(Map<String, String> params) {
		params.put("responseCode", "01");
		return params;
	}

	/**
	 * 
	 * @Description 发送请求
	 * @param key MD5秘钥
	 * @param encKey 
	 * @param partnerId 商户号
	 * @param serviceType 接口类型
	 * @param resultStr 请求内容
	 * @param url 请求路径
	 * @return
	 * @date 2018年2月28日
	 */
	public Map<String, Object> sendRequest(String key, String encKey, String partnerId, String serviceType, String resultStr, String url) {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("random", CommonsUtil.getNonceStr());
		treeMap.put("version", "2.1");
		treeMap.put("encoding", "utf-8");
		treeMap.put("partnerId", partnerId);
		treeMap.put("service_type", serviceType);

		try {
			String tmp = SymmtricCryptoUtil.decryptOrEncrypt(resultStr, encKey, true, "utf-8");
			treeMap.put("request_json", tmp);
		} catch (Exception e) {
			throw new RuntimeException("加密错误");
		}
		
		
		treeMap.put("sign", DianPayerMD5.getSignData(treeMap, key));
		
		logger.info("{}",treeMap);
		try {
			StringBuilder outSb = new StringBuilder();
			HttpUtils.sendRequest(url, treeMap, outSb);
			String back = outSb.toString();
			logger.info("返回信息为：{}", back);
			String[] retInfo = back.split("&");
			Map<String, Object> backMap = new HashMap<String, Object>();
			String resultKey = "result_json";
			for(String info : retInfo) {
				if (info.contains("=")) {
					if (info.contains(resultKey)) {
						backMap.put(resultKey, info.substring(resultKey.length() + 1));
					} else {
						String[] k = info.split("=");
						backMap.put(k[0], k[1]);
					}
				}
			}
			
			if ("00".equals(backMap.get("respCode"))) {
				String resultJson = SymmtricCryptoUtil.decryptOrEncrypt(backMap.get("result_json") + "", encKey, false, "utf-8");
				return JsonUtils.toObject(resultJson, new TypeReference<Map<String, Object>>() {});
			}
			return backMap;
			
		} catch (Exception e) {
			throw new RuntimeException("发送请求错误！", e);
		}
	}
	
	
	
	public static void main(String[] args) {
		/*Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("amount", String.valueOf((int) AmountUtils.multiply(100D, 100)));
		reqMap.put("payType", "kjPay");
		reqMap.put("orderId", System.currentTimeMillis()+"ff");
		reqMap.put("businessTime", System.currentTimeMillis() + "");
		reqMap.put("notifyUrl", "http://www.sunboyang.com:8080/myshop/complete");
		reqMap.put("frontUrl", "http://www.sunboyang.com:8080/myshop/complete");
		reqMap.put("orderDesc", "123");
		reqMap.put("merchantId", "900010000008723");
//		reqMap.put("merchantName", settleInfo.getAccountName());
		
		StringBuffer quickInfoMap = new StringBuffer();
		quickInfoMap.append("{'idNo':'23010719910228247X',");
		quickInfoMap.append("'name':'孙勃洋',");
		quickInfoMap.append("'mobile':'18510412233',");
		quickInfoMap.append("'settlementAcc':'6217230200003271469',");
		quickInfoMap.append("'acc':'6225768773991953',");
		quickInfoMap.append("'cvv':'472',");
		quickInfoMap.append("'t0Fee':'"+ String.valueOf((int)AmountUtils.multiply(Double.valueOf(0.3), 100))+"',");
		quickInfoMap.append("'feeRate':'"+ String.valueOf(AmountUtils.multiply(Double.valueOf(0.0038), 100))+"',");
		quickInfoMap.append("'validityDate':'0921',");
		quickInfoMap.append("'bankCode':'308',");
		quickInfoMap.append("'settlementBankCode':'102'}");
		reqMap.put("mExtraMap", quickInfoMap.toString());*/
		
		Map<String, String> reqMap = new HashMap<String, String>();
		
		
		reqMap.put("amount", String.valueOf((int) AmountUtils.multiply(100D, 100)));
		reqMap.put("businessTime", System.currentTimeMillis() + "");
		reqMap.put("notifyUrl", "http://www.sunboyang.com:8080/myshop/complete");
		reqMap.put("frontUrl", "http://www.sunboyang.com:8080/myshop/complete");
//		reqMap.put("merchantName", settleInfo.getAccountName());
		
		reqMap.put("payType", "kjPayC");
		reqMap.put("orderId", "20180228100965929596");
		reqMap.put("merchantId", "900010000008723");
		reqMap.put("mExtraMap", "{smsCode:'" + 194519 + "'}");
		
		
		Map<String, Object> returnMsg = new DianPayer310101Handler().sendRequest("iUAyVQao6CwTyqR3P4TT", "tR6QWHcUkFT6LxMfeT9CTg0k", "900010000008723", "pushOrder", JsonUtils.toJsonString(reqMap), "https://app.dianpayer.com/gateway.do");
	}

}
