package com.yl.payinterface.core.handle.impl.auth.mipay;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.*;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerSettle;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.CardAttr;
import com.yl.boss.api.enums.CardStatus;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.FeeType;
import com.yl.payinterface.core.exception.BusinessRuntimeException;
import com.yl.payinterface.core.handle.impl.auth.mipay.model.DownCard;
import com.yl.payinterface.core.handle.impl.auth.mipay.model.NewInvoiceData;
import com.yl.payinterface.core.handle.impl.auth.mipay.model.RequestData;
import com.yl.payinterface.core.handle.impl.auth.mipay.model.ResponseData;
import com.yl.payinterface.core.handle.impl.auth.mipay.utils.Aes;
import com.yl.payinterface.core.handle.impl.auth.mipay.utils.HttpUtils;
import com.yl.payinterface.core.handle.impl.auth.mipay.utils.MD5Util;
import com.yl.payinterface.core.handler.AuthPayHandler;
import com.yl.payinterface.core.handler.BindCardHandler;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.CodeBuilder;
import com.yl.payinterface.core.utils.FeeUtils;
import com.yl.payinterface.core.utils.HolidayUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 米刷快捷通用接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年12月14日
 * @version V1.0.0
 */
@Service("authMiPayY1000002Handler")
public class Mipay100002AuthPayHandler implements AuthPayHandler, BindCardHandler{

	private static Logger logger = LoggerFactory.getLogger(Mipay100002AuthPayHandler.class);
	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private InterfaceInfoService interfaceInfoService;

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put("mch_id", "100009");
		properties.put("notify_url", "http://111.172.254.19/payinterface-front/mipayAuthNotify/completePay.htm");
		properties.put("pay_url", "http://pay.mishua.cn/zhonlinepay/service/down/trans/payDzero");
		properties.put("call_back_Url", "http://111.172.254.19/payinterface-front/mipayAuthFrtNotify/completePay.htm");
		properties.put("query_url", "http://pay.mishua.cn/zhonlinepay/service/down/trans/checkDzero");
		properties.put("enc_key", "SD1G56ASD46F5B2M");
		properties.put("sign_key", "F5XC68");
		Map<String, String> map = new HashMap<>();
		map.put("tradeConfigs", JsonUtils.toJsonString(properties));
		map.put("amount", "10");
		map.put("interfaceRequestID", CodeBuilder.build("TD", "yyyyMMdd"));
		map.put("interfaceInfoCode", "MiPay100001_AUTHPAY");
		map.put("productName", "充值");
		map.put("bankCardNo", "6217000010082842264");
		map.put("payerName", "张晓杰");
		map.put("orderTime", DateFormatUtils.format(new Date(), "yyyyMMddHHMMss"));
		map.put("certNo", "130728199208250012");
//		map.put("interfaceRequestID", "TD-20170812-100745970921");
		System.out.println(new Mipay100002AuthPayHandler().pay(map));
	}

	public Map<String, String> pay(Map<String, String> map) {
		// todo
		return null;
	}
	
	@Override
	public Map<String, String> query(Map<String, String> map) {
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
		Map<String, String> reqMap;
		Map<String, String> params = new HashMap<>();
		Map<String, String> resParams;
		params.put("mchNo", properties.getProperty("mch_id"));
		params.put("versionNo", "1");
		params.put("tradeNo", map.get("interfaceRequestID"));
		String sourceStr = JsonUtils.toJsonString(params);
		try {
			String aesedStr = Aes.encrypt(sourceStr, properties.getProperty("enc_key"));
			RequestData req = new RequestData();
			req.setMchNo(properties.getProperty("mch_id"));
			req.setPayload(aesedStr);
			req.setSign(MD5Util.MD5Encode(aesedStr + properties.getProperty("sign_key"), "UTF-8").toUpperCase());
			String reqBody = JsonUtils.toJsonString(req);
			logger.info("米刷认证支付 查询请求报文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),reqBody);
			String response = HttpUtils.post(properties.getProperty("query_url"), reqBody);
			logger.info("米刷认证支付 查询响应报文  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),response);

			ResponseData res = JsonUtils.toObject(response, ResponseData.class);
			String sign = MD5Util.MD5Encode(res.getPayload() + properties.getProperty("sign_key"), "UTF-8").toUpperCase();

			if(sign.equals(res.getSign())){
				reqMap = JsonUtils.toObject(Aes.decrypt(res.getPayload(), properties.getProperty("enc_key")), new TypeReference<Map<String, String>>(){});
				logger.info("米刷认证支付 查询响应明文  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),reqMap);
			}else{
				throw new RuntimeException("米刷认证支付 查询响应报文 验签失败");
			}
			resParams = new HashMap<>();
			if("00".equals(reqMap.get("status"))){
				resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
				resParams.put("tranStat", "SUCCESS");
				resParams.put("amount", reqMap.get("status"));
				resParams.put("responseCode", reqMap.get("status"));
				resParams.put("responseMsg", reqMap.get("statusDesc"));
				resParams.put("interfaceOrderID", reqMap.get("transNo"));
				if("SUCCESS".equals(reqMap.get("qfSatus"))){
					resParams.put("responseCode", reqMap.get("status") + "-" + reqMap.get("qfSatus"));
					resParams.put("responseMsg", "交易成功,代付成功");
				}else if("FAILED".equals(reqMap.get("qfSatus"))){
					resParams.put("responseCode", reqMap.get("status") + "-" + reqMap.get("qfSatus"));
					resParams.put("responseMsg", "交易成功,代付失败");
				}
			}else{
				resParams.put("tranStat", "UNKNOWN");
			}
		}catch (Exception e){
			logger.error("米刷认证支付 查询报文异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}
		return resParams;
	}
	
	@Override
	public Map<String, String> authPay(Map<String, String> map) {
		return sendVerifyCode(map);
	}

	@Override
	public Map<String, String> sendVerifyCode(Map<String, String> params) {
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(params.get("interfaceRequestID"));
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(interfaceRequest.getInterfaceInfoCode());
		Properties properties = JsonUtils.toObject(interfaceInfo.getTradeConfigs(), new TypeReference<Properties>(){});
		NewInvoiceData inv = new NewInvoiceData();
		Map<String,String> resParams = new HashMap<String, String>();
		try {
			inv.setVersionNo(1);
			inv.setMchNo(properties.getProperty("mch_id"));
			inv.setDescription("充值");
			inv.setPrice(new BigDecimal(interfaceRequest.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
			inv.setTradeNo(interfaceRequest.getInterfaceRequestID());
			inv.setOrderDate(DateFormatUtils.format(interfaceRequest.getCreateTime(), "yyyyMMddHHMMss"));
			// 支付卡卡号
			inv.setPayCardNo(params.get("cardNo"));
			checkCardInfo(inv, interfaceRequest.getOwnerID());
			// 获取结算信息
			getSettleInfo(inv, interfaceRequest.getOwnerID());
			inv.setToken(params.get("token"));
			inv.setNotifyUrl(properties.getProperty("notify_url"));
			if(StringUtils.notBlank(params.get("callbackUrl"))){
				inv.setCallbackUrl(params.get("callbackUrl"));
			}else {
				inv.setCallbackUrl(properties.getProperty("call_back_Url")+"?order"+interfaceRequest.getBussinessOrderID()+"&");
			}
			inv.setTradeNo(interfaceRequest.getInterfaceRequestID());
			String sourceStr = JsonUtils.toJsonString(inv);
			logger.info("米刷认证支付 发送验证码请求明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", interfaceRequest.getInterfaceInfoCode(),params.get("interfaceRequestID"),sourceStr);
			String aesedStr = Aes.encrypt(sourceStr, properties.getProperty("enc_key"));
			RequestData req = new RequestData();
			req.setMchNo(properties.getProperty("mch_id"));
			req.setPayload(aesedStr);
			req.setSign(MD5Util.MD5Encode(aesedStr + properties.getProperty("sign_key"), "UTF-8").toUpperCase());
			String reqBody = JsonUtils.toJsonString(req);

			logger.info("米刷认证支付 发送验证码请求报文  接口编号:[{}],订单号:[{}],请求报文:[{}]", interfaceRequest.getInterfaceInfoCode(),params.get("interfaceRequestID"),reqBody);
			String resStr = HttpUtils.post(properties.getProperty("sendSmsUrl"), reqBody);
			logger.info("米刷认证支付 发送验证码响应报文  接口编号:[{}],订单号:[{}],响应报文:[{}]", interfaceRequest.getInterfaceInfoCode(),params.get("interfaceRequestID"),resStr);
			ResponseData res = JsonUtils.toObject(resStr, ResponseData.class);
			if("Successful".equals(res.getState())){
				String sign = MD5Util.MD5Encode(res.getPayload() + properties.getProperty("sign_key"), "UTF-8").toUpperCase();
				Map<String, String> reqMap;
				if(sign.equals(res.getSign())){
					reqMap = JsonUtils.toObject(Aes.decrypt(res.getPayload(), properties.getProperty("enc_key")), new TypeReference<Map<String, String>>(){});
					logger.info("米刷认证支付 发送验证码响应报文  接口编号:[{}],订单号:[{}],响应明文:[{}]", interfaceRequest.getInterfaceInfoCode(),params.get("interfaceRequestID"),reqMap);
					if("00".equals(reqMap.get("status"))){
						resParams.put("orderCode", interfaceRequest.getBussinessOrderID());
						resParams.put("interfaceRequestID", params.get("interfaceRequestID"));
						resParams.put("payCardNo", params.get("cardNo"));
						resParams.put("settleCardNo", inv.getCardNo());
						resParams.put("payerName", inv.getAccName());
						resParams.put("certNo", inv.getAccIdCard());
						resParams.put("phone", inv.getAccTel());
						resParams.put("responseCode", "00");
						resParams.put("token", params.get("token"));
						resParams.put("amount", String.valueOf(inv.getPrice()));
						resParams.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
						resParams.put("gateway", "quickPay");
						String key = CodeBuilder.getOrderIdByUUId();
						Map<String, String> smsMap = new HashMap<>();
						smsMap.put("interfaceRequestID", params.get("interfaceRequestID"));
						smsMap.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
						smsMap.put("cardNo", params.get("cardNo"));
						smsMap.put("token", params.get("token"));
						try {
							CacheUtils.setEx("AUTH-SENDSMS:"+ key,JsonUtils.toJsonString(smsMap), 600);
						}catch (Exception e){
							logger.error("米刷认证支付 缓存验证码信息异常  接口编号:[{}],订单号:[{}],异常信息：[{}]", interfaceRequest.getInterfaceInfoCode(),params.get("interfaceRequestID"),e);
						}
						resParams.put("key", key);
					}else{
						// 短信验证码发送超过最大次数
						throw new BusinessRuntimeException("2003");
					}
					return resParams;
				}else{
					// 0002 验签失败
					throw new BusinessRuntimeException("2002");
				}
			}else{
				resParams.put("interfaceRequestID", params.get("interfaceRequestID"));
				resParams.put("responseCode", "2001");
				resParams.put("responseMessage", "短信验证码发送失败");
			}
			return resParams;
		} catch (Exception e) {
			if(e instanceof BusinessRuntimeException){
				resParams.put("responseCode", e.getMessage().equals("2002")||e.getMessage().equals("2003")?"2001":e.getMessage());
				resParams.put("interfaceRequestID", params.get("interfaceRequestID"));
				return resParams;
			}
			logger.error("米刷认证支付 发送验证码报文异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", interfaceRequest.getInterfaceInfoCode(),params.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Map<String, String> sale(Map<String, String> map) {
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
		NewInvoiceData inv = new NewInvoiceData();
		inv.setVersionNo(1);
		inv.setMchNo(properties.getProperty("mch_id"));
		inv.setNotifyUrl(properties.getProperty("notifyUrl"));
		inv.setTradeNo(map.get("interfaceRequestID"));
		inv.setToken(map.get("token"));
		inv.setSmsCode(map.get("smsCode"));
		inv.setNotifyUrl(properties.getProperty("notify_url"));

		String sourceStr = JsonUtils.toJsonString(inv);
		logger.info("米刷认证支付 支付请求明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceCode"),map.get("interfaceRequestID"),sourceStr);
		try {
			String aesedStr = Aes.encrypt(sourceStr, properties.getProperty("enc_key"));
			RequestData req = new RequestData();
			req.setMchNo(properties.getProperty("mch_id"));
			req.setPayload(aesedStr);
			req.setSign(MD5Util.MD5Encode(aesedStr + properties.getProperty("sign_key"), "UTF-8").toUpperCase());
			String reqBody = JsonUtils.toJsonString(req);
			logger.info("米刷认证支付 支付请求  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceCode"),map.get("interfaceRequestID"),reqBody);
			String response = HttpUtils.post(properties.getProperty("pay_url"), reqBody);
			logger.info("米刷认证支付 支付请求  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceCode"),map.get("interfaceRequestID"),response);
			ResponseData res = JsonUtils.toObject(response, ResponseData.class);
			if("Successful".equals(res.getState())){
				String sign = MD5Util.MD5Encode(res.getPayload() + properties.getProperty("sign_key"), "UTF-8").toUpperCase();
				if(sign.equals(res.getSign())){
					String resData = Aes.decrypt(res.getPayload(), properties.getProperty("enc_key"));
					logger.info("米刷认证支付 支付请求  接口编号:[{}],订单号:[{}],响应明文:[{}]", map.get("interfaceCode"),map.get("interfaceRequestID"),resData);
					map = JsonUtils.toObject(resData, new TypeReference<Map<String, String>>(){});
					if("00".equals(map.get("status"))){
						map.put("responseCode", "00");
						map.put("responseMessage", "交易成功");
						map.put("tranStat", "SUCCESS");
						map.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
					}else if("01".equals(map.get("status"))){
						map.put("responseCode", "2001");
						map.put("responseMessage", "交易失败");
						map.put("tranStat", "FAILED");
						map.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
					}else{
						map.put("tranStat", "UNKNOWN");
					}
				}
			}else{
				map.put("responseCode", "2001");
				map.put("responseMessage", "交易失败");
				map.put("tranStat", "FAILED");
				map.put("businessCompleteType", BusinessCompleteType.NORMAL.name());
			}
		}catch (Exception e){
			logger.error("米刷认证支付 支付請求异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}
		return map;
	}

	private void getSettleInfo(NewInvoiceData ionvoiceData, String ownerId){
		CustomerSettle customerSettle = customerInterface.getCustomerSettle(ownerId);
		if(customerSettle == null){
			throw new BusinessRuntimeException("3001");
		}
		if("OPEN".equals(customerSettle.getCustomerType())){
			throw new BusinessRuntimeException("3002");
		}
		// 结算账户银行
		ionvoiceData.setBankName(customerSettle.getOpenBankName());
		// 结算账户账号
		ionvoiceData.setCardNo(customerSettle.getAccountNo());
		/// 持卡人姓名
		ionvoiceData.setAccName(customerSettle.getAccountName());
		if(StringUtils.isBlank(ionvoiceData.getAccIdCard())){

			String idCrad = customerInterface.findCustomerCardByNo(ownerId);
			if(StringUtils.isBlank(idCrad)){
				throw new BusinessRuntimeException("3003");
			}
			// 持卡人身份证号
			ionvoiceData.setAccIdCard(idCrad);
		}

		// 获取商户费率 结算手续费
		CustomerFee remitFee;
		if(HolidayUtils.isHoliday()){
			remitFee = customerInterface.getCustomerFee(ownerId, "HOLIDAY_REMIT");
			if(remitFee == null){
				throw new BusinessRuntimeException("3005");
			}
		}else {
			remitFee = customerInterface.getCustomerFee(ownerId, "REMIT");
			if(remitFee == null){
				throw new BusinessRuntimeException("3004");
			}
		}
		CustomerFee authFee = customerInterface.getCustomerFee(ownerId, "AUTHPAY");
		if(authFee == null){
			throw new BusinessRuntimeException("3006");
		}
		double custFee = FeeUtils.computeFee(ionvoiceData.getPrice().doubleValue(), FeeType.valueOf(authFee.getFeeType().name()),authFee.getFee());
		if(AmountUtils.leq(AmountUtils.subtract(ionvoiceData.getPrice().doubleValue(), custFee), remitFee.getFee())){
			throw new BusinessRuntimeException("3007");
		}
		// 交易手续费
		ionvoiceData.setDownPayFee(new BigDecimal(AmountUtils.multiply(authFee.getFee(), 1000d)).setScale(2, BigDecimal.ROUND_HALF_UP));
		// 结算手续费
		ionvoiceData.setDownDrawFee(new BigDecimal(remitFee.getFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	private void checkCardInfo(NewInvoiceData ionvoiceData, String ownerId){
		TransCardBean transCardBean = customerInterface.findTransCardByAccNo(ownerId, ionvoiceData.getPayCardNo(), CardAttr.TRANS_CARD);
		if(transCardBean == null){
			throw new BusinessRuntimeException("3009");
		}
		if(transCardBean.getCardStatus() != CardStatus.NORAML){
			throw new BusinessRuntimeException("3008");
		}
		ionvoiceData.setAccTel(transCardBean.getPhone());
	}

	@Override
	public Map<String, String> bindCard(Map<String, String> params) {
		Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>(){});
		DownCard inv = new DownCard();
		inv.setVersionNo(1);
		inv.setMchNo(properties.getProperty("mch_id"));
		inv.setNotifyUrl(properties.getProperty("bindCardNotifyUrl"));
		String key = CodeBuilder.getOrderIdByUUId();
		inv.setCallbackUrl(properties.getProperty("bindCardCallbackUrl")+"?&key="+key);
		inv.setCardNo(params.get("cardNo"));
		String sourceStr = JsonUtils.toJsonString(inv);
		logger.info("米刷认证支付 绑卡请求明文  接口编号:[{}],卡号:[{}],请求报文:[{}]", params.get("interfaceCode"),params.get("cardNo"),sourceStr);
		try{
			String aesedStr = Aes.encrypt(sourceStr, properties.getProperty("enc_key"));
			RequestData req = new RequestData();
			req.setMchNo(properties.getProperty("mch_id"));
			req.setPayload(aesedStr);
			req.setSign(MD5Util.MD5Encode(aesedStr + properties.getProperty("sign_key"), "UTF-8").toUpperCase());
			String reqBody = JsonUtils.toJsonString(req);
			logger.info("米刷认证支付 绑卡请求报文  接口编号:[{}],卡号:[{}],请求报文:[{}]", params.get("interfaceCode"),params.get("cardNo"),reqBody);
			String response = HttpUtils.post(properties.getProperty("bindCardPayUrl"), reqBody);
			logger.info("米刷认证支付 绑卡响应报文  接口编号:[{}],卡号:[{}],响应报文:[{}]", params.get("interfaceCode"),params.get("cardNo"),response);
			ResponseData res = JsonUtils.toObject(response, ResponseData.class);
			String sign = MD5Util.MD5Encode(res.getPayload() + properties.getProperty("sign_key"), "UTF-8").toUpperCase();
			if("Failed".equals(res.getState())){
				params.put("responseCode", "01");
				return params;
			}
			if(sign.equals(res.getSign())){
				String resDate = Aes.decrypt(res.getPayload(), properties.getProperty("enc_key"));
				logger.info("米刷认证支付 绑卡响应报文  接口编号:[{}],卡号:[{}],响应明文:[{}]", params.get("interfaceCode"),params.get("cardNo"),resDate);
				Map<String, String> resParams = JsonUtils.toObject(resDate, new TypeReference<Map<String, String>>(){});
				if("00".equals(resParams.get("status"))){
					params.put("url", resParams.get("openUrl"));
					params.put("responseCode", "00");

					Map<String, String> bindCardInfo = new HashMap<>();
					bindCardInfo.put("tradeOrderCode", params.get("tradeOrderCode"));
					bindCardInfo.put("amount", params.get("amount"));
					bindCardInfo.put("interfaceCode", params.get("interfaceCode"));
					bindCardInfo.put("cardNo", params.get("cardNo"));
					try {
						CacheUtils.setEx("BINDCARD:"+key, JsonUtils.toJsonString(bindCardInfo), 900);
					}catch (Exception e){
						logger.error("米刷认证支付 缓存绑卡信息异常 接口编号:[{}],卡号:[{}],异常信息：[{}]", params.get("interfaceInfoCode"),params.get("cardNo"),e);
					}
				}else{
					params.put("responseCode", "01");
				}
				params.put("key", key);
				params.put("cardNo", params.get("cardNo"));
				params.put("interfaceInfoCode", params.get("interfaceInfoCode"));
				params.put("gateway", "auth/submit");
			}
		}catch (Exception e){
			logger.error("米刷认证支付 绑卡异常 接口编号:[{}],卡号:[{}],异常信息：[{}]", params.get("interfaceInfoCode"),params.get("cardNo"),e);
			throw new RuntimeException(e.getMessage());
		}
		return params;
	}

	@Override
	public Map<String, String> queryBindCard(Map<String, String> params) {
		Properties properties = JsonUtils.toObject(String.valueOf(params.get("tradeConfigs")), new TypeReference<Properties>(){});
		DownCard inv = new DownCard();
		inv.setVersionNo(1);
		inv.setMchNo(properties.getProperty("mch_id"));
		inv.setCardNo(params.get("cardNo"));
		String sourceStr = JsonUtils.toJsonString(inv);
		logger.info("米刷认证支付 绑卡查询请求明文  接口编号:[{}],卡号:[{}],请求报文:[{}]", params.get("interfaceInfoCode"),params.get("cardNo"),sourceStr);
		try {
			String aesedStr = Aes.encrypt(sourceStr, properties.getProperty("enc_key"));
			RequestData req = new RequestData();
			req.setMchNo(properties.getProperty("mch_id"));
			req.setPayload(aesedStr);
			req.setSign(MD5Util.MD5Encode(aesedStr + properties.getProperty("sign_key"), "UTF-8").toUpperCase());
			String reqBody = JsonUtils.toJsonString(req);
			logger.info("米刷认证支付 绑卡查询请求报文  接口编号:[{}],卡号:[{}],请求报文:[{}]", params.get("interfaceInfoCode"),params.get("cardNo"),reqBody);
			String response = HttpUtils.post(properties.getProperty("queryCardUrl"), reqBody);
			logger.info("米刷认证支付 绑卡查询响应报文  接口编号:[{}],卡号:[{}],响应报文:[{}]", params.get("interfaceInfoCode"),params.get("cardNo"),response);
			ResponseData res = JsonUtils.toObject(response, ResponseData.class);
			String sign = MD5Util.MD5Encode(res.getPayload() + properties.getProperty("sign_key"), "UTF-8").toUpperCase();
			if(sign.equals(res.getSign())){
				String resData = Aes.decrypt(res.getPayload(), properties.getProperty("enc_key"));
				logger.info("米刷认证支付 绑卡查询响应明文  接口编号:[{}],卡号:[{}],响应报文:[{}]", params.get("interfaceInfoCode"),params.get("cardNo"),resData);
				Map<String, String> resParams = JsonUtils.toObject(resData, new TypeReference<Map<String, String>>(){});
				params = new HashMap<>();
				if("00".equals(resParams.get("status"))){
					params.put("token", resParams.get("token"));
					params.put("responseCode", "00");
				}else{
					params.put("responseCode", "01");
				}
				params.put("cardNo", params.get("cardNo"));
				params.put("interfaceInfoCode", params.get("interfaceInfoCode"));
			}
		}catch (Exception e){
			logger.error("米刷认证支付 绑卡查询异常 接口编号:[{}],卡号:[{}],异常信息：[{}]", params.get("interfaceInfoCode"),params.get("cardNo"),e);
			throw new RuntimeException(e.getMessage());
		}
		return params;
	}
}
