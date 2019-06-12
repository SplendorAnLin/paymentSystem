package com.yl.payinterface.core.handle.impl.b2c.cbapay370000;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handle.impl.remit.cbapay370000.MD5Util;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.CodeBuilder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 网上有名网关支付接口
 * 
 * @author 聚合支付有限公司
 * @since 2017年07月19日
 * @version V1.0.0
 */
@Service("b2cCbapay370000Handler")
public class Cbapay370000Handler implements InternetbankHandler {
	private static Logger logger = LoggerFactory.getLogger(Cbapay370000Handler.class);
	@Resource
	private InterfaceRequestService interfaceRequestService;
	private static Map<String, String> bankCodes = new HashMap();

	public Cbapay370000Handler() {
	}

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("payUrl", "http://test.cbapay.com/user/merchant/biz/MerchantmerchantPay.do");
		properties.setProperty("merchantId", "00000000000194");
		properties.setProperty("notifyUrl", "http://m.bank-pay.com:8888/payinterface-front/cbaB2cPayNotify/completePay");
		properties.setProperty("frontUrl", "http://m.bank-pay.com:8888/payinterface-front/cbaB2cPayNotify/completePay");
		properties.setProperty("key", "yfc6VbD7baaCf5i7");
		properties.setProperty("transType", "0101");
		properties.setProperty("versionId", "3");
		properties.setProperty("channelMerchantId", "WSYM_WSYM_000002");
		properties.setProperty("queryUrl", "http://test.cbapay.com/user/merchant/biz/tranQueryOrder.do");
		Map<String, String> params = new HashMap();
		params.put("transConfig", JsonUtils.toJsonString(properties));
		params.put("interfaceRequestID", CodeBuilder.build("TD", "yyyyMMdd"));
		params.put("amount", "1.01");
		params.put("goodsName", "测试");
		params.put("interfaceInfoCode", "CBAPAY370000-B2C");
		params.put("providerCode", "ICBC");
		TradeContext tradeContext = new TradeContext();
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setCreateTime(new Date());
		interfaceRequest.setInterfaceRequestID((String)params.get("interfaceRequestID"));
		interfaceRequest.setAmount(Double.valueOf((String)params.get("amount")).doubleValue());
		interfaceRequest.setInterfaceProviderCode((String)params.get("providerCode"));
		interfaceRequest.setOriginalInterfaceRequestID((String)params.get("interfaceRequestID"));
		tradeContext.setInterfaceRequest(interfaceRequest);
		tradeContext.setRequestParameters(params);
		System.out.println((new Cbapay370000Handler()).query(tradeContext)[0]);
	}

	public Object[] trade(TradeContext tradeContext) throws BusinessException {
		InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
		Map<String, String> requestParameters = tradeContext.getRequestParameters();
		Properties transConfig = (Properties)JsonUtils.toObject((String)requestParameters.get("transConfig"), Properties.class);
		Map<String, String> params = new LinkedHashMap();
		params.put("versionId", transConfig.getProperty("versionId"));
		params.put("merchantId", transConfig.getProperty("merchantId"));
		params.put("orderId", interfaceRequest.getInterfaceRequestID());
		params.put("orderAmount", String.valueOf((int)AmountUtils.round(AmountUtils.multiply(interfaceRequest.getAmount(), 100.0D), 2, RoundingMode.HALF_UP)));
		params.put("orderDate", (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
		params.put("currency", "RMB");
		params.put("transType", transConfig.getProperty("transType"));
		params.put("retUrl", transConfig.getProperty("notifyUrl"));
		params.put("bizType", "00");
		params.put("returnUrl", transConfig.getProperty("frontUrl"));
		params.put("prdDisUrl", "");
		params.put("prdName", Str2Hex((String)requestParameters.get("goodsName")));
		params.put("prdShortName", "");
		params.put("prdDesc", "");
		params.put("merRemark", "");
		params.put("rptType", "1");
		params.put("prdUnitPrice", "");
		params.put("buyCount", "");
		params.put("defPayWay", "");
		params.put("buyMobNo", "");
		params.put("cpsFlg", "0");
		params.put("signType", "MD5");
		logger.info("网上有名支付通道交易明文串：{}", params);

		try {
			params.put("signature", sign(params, transConfig.getProperty("key")));
			if(StringUtils.notBlank(transConfig.getProperty("channelMerchantId"))) {
				params.put("channelMerchantId", transConfig.getProperty("channelMerchantId"));
			}

			params.put("txnCod", "MerchantmerchantPay");
			params.put("bankCode", convertBankCode(interfaceRequest.getInterfaceProviderCode()));
			params.put("bankFlag", transConfig.getProperty("bankFlag"));
			params.put("isNoLogin", "1");
		} catch (Exception var7) {
			logger.error("网上有名支付MD5计算签名出异常了！！", var7);
		}

		params.put("gateway", "submit");
		params.put("url", transConfig.getProperty("payUrl"));
		params.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		logger.info("发往网上有名支付通道交易串：{}", params.toString());
		return new Object[]{transConfig.getProperty("payUrl"), params, interfaceRequest.getInterfaceRequestID()};
	}

	public Map<String, Object> signatureVerify(InternetbankSalesResponseBean internetbankSalesResponseBean, Properties tradeConfigs) {
		return null;
	}

	public InterfaceRequest complete(Map<String, Object> completeParameters) throws BusinessException {
		return null;
	}

	public Object[] query(TradeContext tradeContext) throws BusinessException {
		InterfaceRequest queryInterfaceRequest = tradeContext.getInterfaceRequest();
		Map<String, String> requestParameters = tradeContext.getRequestParameters();
		Properties properties = (Properties)JsonUtils.toObject((String)requestParameters.get("transConfig"), Properties.class);
		HashMap<String, String> params = new LinkedHashMap();
		params.put("endDate", "");
		params.put("merchantId", properties.getProperty("merchantId"));
		params.put("prdOrdNo", queryInterfaceRequest.getOriginalInterfaceRequestID());
		params.put("startDate", "");
		params.put("versionId", properties.getProperty("versionId"));
		String respondStr = "";
		HashMap respMap = new HashMap();

		try {
			String signMsg = sign(params, properties.getProperty("key"));
			if(StringUtils.notBlank(properties.getProperty("channelMerchantId"))) {
				params.put("channelMerchantId", properties.getProperty("channelMerchantId"));
			}

			params.put("signature", signMsg);
			logger.info("网上有名支付通道 查询请求参数：{}", params.toString());
			respondStr = HttpClientUtils.send(Method.POST, properties.getProperty("queryUrl"), params);
			logger.info("网上有名支付通道 查询响应参数：{}", respondStr);
			JSONObject jsonObject = JSONObject.fromObject(respondStr);
			String retCode = jsonObject.getString("retCode");
			if("00000".equals(retCode)) {
				throw new BusinessException("网上有名支付 查询结果异常: " + retCode);
			}

			String retMsg = jsonObject.getString("retMsg");
			String orderList = jsonObject.getString("payTranId");
			JSONArray orders = jsonObject.getJSONArray(orderList);
			JSONObject order = JSONObject.fromObject(orders.get(0));
			Map<String, String> resParams = new LinkedHashMap();
			resParams.put("fee", order.getString("fee"));
			resParams.put("ordStatus", order.getString("ordStatus"));
			resParams.put("prdOrdNo", order.getString("prdOrdNo"));
			resParams.put("payOrdNo", order.getString("payOrdNo"));
			resParams.put("merchantId", order.getString("merchantId"));
			resParams.put("orderTime", order.getString("orderTime"));
			resParams.put("ordAmt", order.getString("ordAmt"));
			if(verSign(resParams, order.getString("signature"), properties.getProperty("key"))) {
				if("01".equals(order.getString("ordStatus"))) {
					respMap.put("tranStat", "SUCCESS");
					respMap.put("amount", Double.valueOf(AmountUtils.divide((new Double(order.getString("ordAmt"))).doubleValue(), 100.0D, 2)));
					respMap.put("orderNo", queryInterfaceRequest.getOriginalInterfaceRequestID());
					respMap.put("interfaceOrderID", order.getString("payOrdNo"));
					respMap.put("notifyDate", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
					respMap.put("queryStatus", InterfaceTradeStatus.SUCCESS);
				} else {
					respMap.put("orderNo", queryInterfaceRequest.getOriginalInterfaceRequestID());
					respMap.put("tranStat", "UNKNOWN");
				}
			}
		} catch (Exception var16) {
			logger.error("网上有名支付查询出异常了======", var16);
			throw new BusinessException("网上有名支付查询出异常了", var16);
		}

		return new Object[]{respMap};
	}

	private static String convertBankCode(String bankCode) {
		if(bankCodes.get(bankCode) != null) {
			return (String)bankCodes.get(bankCode);
		} else {
			throw new RuntimeException("网上有名网关,银行编号匹配失败:{" + bankCode + "}");
		}
	}

	private static boolean verSign(Map<String, String> params, String serverSign, String signKey) {
		return serverSign.equals(sign(params, signKey));
	}

	private static String sign(Map<String, String> params, String signKey) {
		StringBuffer sb = new StringBuffer();
		Iterator it = params.entrySet().iterator();

		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			sb.append(k);
			sb.append("=");
			sb.append(v);
			if(it.hasNext()) {
				sb.append("&");
			}
		}

		logger.info("网上有名 网关支付 签名字符串 : {}", sb.toString() + signKey);
		return MD5Util.getMD5(sb.toString() + signKey);
	}

	private static String Str2Hex(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		Object var3 = null;

		byte[] bs;
		try {
			bs = str.getBytes("UTF-8");
		} catch (Exception var6) {
			bs = str.getBytes();
		}

		for(int i = 0; i < bs.length; ++i) {
			int bit = (bs[i] & 240) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 15;
			sb.append(chars[bit]);
		}

		return sb.toString().trim();
	}

	static {
		bankCodes.put("ICBC", "ICBCD");
		bankCodes.put("CCB", "CCBD");
		bankCodes.put("ABC", "ABCD");
		bankCodes.put("CMB", "CMB");
		bankCodes.put("BCM", "BOCOM");
		bankCodes.put("BOC", "BOC");
		bankCodes.put("CGB", "GDB");
		bankCodes.put("CNCB", "CNCB");
		bankCodes.put("CMBC", "CMBCD");
		bankCodes.put("CIB", "CIB");
		bankCodes.put("CEB", "CEBD");
		bankCodes.put("HXB", "HXB");
		bankCodes.put("BOS", "BOS");
		bankCodes.put("PSBC", "PSBCD");
		bankCodes.put("BOB", "BCCB");
		bankCodes.put("BJRCB", "BRCB");
		bankCodes.put("PAB", "PAB");
		bankCodes.put("BCM", "BOCOM");
		bankCodes.put("SRCB", "SRCB");
	}
}