package com.yl.payinterface.core.handle.impl.auth.mipay;

import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.handle.impl.auth.mipay.model.NewInvoiceData;
import com.yl.payinterface.core.handle.impl.auth.mipay.model.RequestData;
import com.yl.payinterface.core.handle.impl.auth.mipay.model.ResponseData;
import com.yl.payinterface.core.handle.impl.auth.mipay.utils.Aes;
import com.yl.payinterface.core.handle.impl.auth.mipay.utils.HttpUtils;
import com.yl.payinterface.core.handle.impl.auth.mipay.utils.MD5Util;
import com.yl.payinterface.core.handler.AuthPayHandler;
import com.yl.payinterface.core.utils.CodeBuilder;
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
 * @author 米刷有限公司
 * @since 2017年12月14日
 * @version V1.0.0
 */
@Service("authMiPayY1000001Handler")
public class Mipay100001AuthPayHandler implements AuthPayHandler{

	private static Logger logger = LoggerFactory.getLogger(Mipay100001AuthPayHandler.class);
	@Resource
	private CustomerInterface customerInterface;

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
		System.out.println(new Mipay100001AuthPayHandler().pay(map));
	}

	public Map<String, String> pay(Map<String, String> map) {
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
		NewInvoiceData inv = new NewInvoiceData();
		Map<String,String> resParams = new HashMap<String, String>();
		try {
			inv.setVersionNo(1);
			inv.setMchNo(properties.getProperty("mch_id"));
			inv.setPrice(new BigDecimal(map.get("amount")).setScale(2, BigDecimal.ROUND_HALF_UP));
			inv.setDescription("充值");
			inv.setNotifyUrl(properties.getProperty("notify_url"));
			if(StringUtils.notBlank(map.get("callbackUrl"))){
				inv.setCallbackUrl(map.get("callbackUrl"));
			}else {
				inv.setCallbackUrl(properties.getProperty("call_back_Url"));
			}
			inv.setTradeNo(map.get("interfaceRequestID"));
			inv.setOrderDate(map.get("orderTime"));
			// 持卡人姓名
			inv.setAccName(map.get("payerName"));
			// 持卡人身份证号
			inv.setAccIdCard(map.get("certNo"));
			// 支付卡卡号
			inv.setPayCardNo(map.get("bankCardNo"));
			// 获取结算信息
			getSettleInfo(inv, map.get("ownerId"));
			String sourceStr = JsonUtils.toJsonString(inv);
			logger.info("米刷认证支付 下单请求明文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),sourceStr);
			String aesedStr = Aes.encrypt(sourceStr, properties.getProperty("enc_key"));
			RequestData req = new RequestData();
			req.setMchNo(properties.getProperty("mch_id"));
			req.setPayload(aesedStr);
			req.setSign(MD5Util.MD5Encode(aesedStr + properties.getProperty("sign_key"), "UTF-8").toUpperCase());
			String reqBody = JsonUtils.toJsonString(req);

			logger.info("米刷认证支付 下单请求报文  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),reqBody);
			String resStr = HttpUtils.post(properties.getProperty("pay_url"), reqBody);
			logger.info("米刷认证支付 下单响应报文  接口编号:[{}],订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),resStr);
			ResponseData res = JsonUtils.toObject(resStr, ResponseData.class);
			if("Failed".equals(res.getState())){
				resParams.put("tranStr", "FAILED");
				resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
				resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
				resParams.put("payCardNo", map.get("payCardNo"));
				resParams.put("resCode", "FAILED");
				resParams.put("resMsg", res.getMessage());
				resParams.put("gateway", "authPaySubmit");
				return resParams;
			}
			String sign = MD5Util.MD5Encode(res.getPayload() + properties.getProperty("sign_key"), "UTF-8").toUpperCase();

			Map<String, String> reqMap;
			if(sign.equals(res.getSign())){
				reqMap = JsonUtils.toObject(Aes.decrypt(res.getPayload(), properties.getProperty("enc_key")), new TypeReference<Map<String, String>>(){});
			}else{
				throw new RuntimeException("米刷认证支付 下单响应报文 验签失败");
			}

			resParams.put("tranStr", reqMap.get("tranStr"));
			resParams.put("interfaceInfoCode", map.get("interfaceInfoCode"));
			resParams.put("interfaceRequestID", map.get("interfaceRequestID"));
			resParams.put("payCardNo", map.get("bankCardNo"));
			resParams.put("resCode", "SUCCESS");
			resParams.put("resMsg", "下单成功");
			resParams.put("gateway", "authPaySubmit");
			return resParams;
		} catch (Exception e) {
			logger.error("米刷认证支付 下单报文异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}
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
	public Map<String, String> sendVerifyCode(Map<String, String> params) {
		return null;
	}

	@Override
	public Map<String, String> authPay(Map<String, String> map) {
		return authPay(map);
	}

	@Override
	public Map<String, String> sale(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	private void getSettleInfo(NewInvoiceData ionvoiceData, String ownerId){

		// todo  获取结算卡信息
		// 结算账户银行
		ionvoiceData.setBankName("中国建设银行");
		// 结算账户账号
		ionvoiceData.setCardNo("6217000010082842264");
		/// 持卡人姓名
		ionvoiceData.setAccName("张晓杰");
		if(StringUtils.isBlank(ionvoiceData.getAccIdCard())){
			// todo 获取身份证号
			// 持卡人身份证号
			ionvoiceData.setAccIdCard("130728199208250012");
		}

		// 获取商户费率 结算手续费
		CustomerFee remitFee;
		if(HolidayUtils.isHoliday()){
			remitFee = customerInterface.getCustomerFee(ownerId, "HOLIDAY_REMIT");
		}else {
			remitFee = customerInterface.getCustomerFee(ownerId, "REMIT");
		}
		CustomerFee authFee = customerInterface.getCustomerFee(ownerId, "AUTHPAY");
		ionvoiceData.setDownPayFee(new BigDecimal(authFee.getFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
		ionvoiceData.setDownDrawFee(new BigDecimal(remitFee.getFee()).setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	private boolean checkCardInfo(){

		return false;
	}

}
