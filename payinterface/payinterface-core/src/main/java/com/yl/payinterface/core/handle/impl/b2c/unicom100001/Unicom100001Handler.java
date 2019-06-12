package com.yl.payinterface.core.handle.impl.b2c.unicom100001;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.lefu.hessian.exception.BusinessException;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.InternetbankSalesResponseBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 联通网银支付接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月16日
 * @version V1.0.0
 */
@Service("b2cUnicom100001Handler")
public class Unicom100001Handler implements InternetbankHandler{

	private Logger logger = LoggerFactory.getLogger(Unicom100001Handler.class);
	
	@Resource
	private InterfaceRequestService interfaceRequestService;
	
	public static void main(String[] args) throws Exception {
		String url = "https://epay.10010.com/pay/servlet/MerOrderPayReqServlte.htm?reqCharSet=UTF-8";
		String mid = "307100710001186";
		String key = "FN983TOPAPNQ7FA7H26BH2CVD922IOFR";
		String notifyUrl = "http://jayzhangxiao.eicp.net/payinterface-front/UNICOM100001PayNotice/completePay";
		String frontUrl = "http://jayzhangxiao.eicp.net/payinterface-front/UNICOM100001FrtPayNotice/completePay";
		String tranType = "20102";
		Properties properties = new Properties();
		properties.setProperty("payUrl", url);
		properties.setProperty("unicom_urlQ", "https://epay.10010.com/pay/query/order.htm?reqCharSet=UTF-8");
		properties.setProperty("merNo", mid);
		properties.setProperty("notifyUrl", notifyUrl);
		properties.setProperty("frontUrl", frontUrl);
		properties.setProperty("key", key);
		properties.setProperty("tranType", tranType);
		Map<String, String> params = new HashMap<>();
		params.put("transConfig", JsonUtils.toJsonString(properties));
		params.put("interfaceRequestID", "TD20170823101271882625");
		params.put("amount", "1.01");
		params.put("goodsName", "测试");
		params.put("interfaceInfoCode", "UNICOM_100001-B2C");
		params.put("clientIp", "127.0.0.1");
		params.put("providerCode", "ICBC");
		params.put("originalRequestTime", "20170823");
		TradeContext tradeContext = new TradeContext();
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setCreateTime(new Date());
		interfaceRequest.setInterfaceRequestID(params.get("interfaceRequestID"));
		interfaceRequest.setAmount(Double.valueOf(params.get("amount")));
		interfaceRequest.setInterfaceProviderCode(params.get("providerCode"));
		tradeContext.setInterfaceRequest(interfaceRequest);
		tradeContext.setRequestParameters(params);
		System.out.println(new Unicom100001Handler().query(tradeContext)[0]);
	}

	@Override
	public Object[] trade(TradeContext tradeContext) throws BusinessException {
		// 获取接口请求
		InterfaceRequest interfaceRequest = tradeContext.getInterfaceRequest();
		// 定单号 orderNo
		String orderNo = interfaceRequest.getInterfaceRequestID().replace("-", "");
		// 订单 付款金额 amount
		Double amount = interfaceRequest.getAmount();
		// 请求参数
		Map<String, String> requestParameters = tradeContext.getRequestParameters();
		// 获取配置信息
		Properties transConfig = JsonUtils.toObject(requestParameters.get("transConfig"), Properties.class);
		// 获取通道url
		String bankURL = transConfig.getProperty("payUrl")+"?reqCharSet=UTF-8";
		// 获取商户签名密码
		String key = transConfig.getProperty("key");
		// 从配置库中获取配置信息
		String interfaceVersion = "1.0.0.0";// 消息版本号
		String tranType = transConfig.getProperty("tranType");// 接口类型 统一支付网关：固定为20101,直连网银网关：固定为20102
		String bankCode = "";
		if ("20101".equals(tranType)) {// 连接联通沃支付网关
			bankCode = transConfig.getProperty("bankCode");// 银行编码 “接口类型（tranType）为”20102时必填
		} else if ("20102".equals(tranType)) {// 直连网银网关
			// 接口提供方，通过接口提供方获取配置信息中的银企代码
			bankCode = convertBankCode(interfaceRequest.getInterfaceProviderCode());// 直连银企代码
		}
		String merNo = transConfig.getProperty("merNo");// 商户号
		// 商品名称
		String goodsName = requestParameters.get("goodsName");
		String charSet = "UTF-8";// 字符集
		String tradeMode = "0001";// 交易方式 固定值为 0001：立即支付
		String respMode = "1";// 1. 双路确认应答
		String callbackUrl = transConfig.getProperty("frontUrl");// 页面重定向回调URL
		String serverCallUrl = transConfig.getProperty("notifyUrl");// 服务器异步通知地址
		String signType = "MD5";// 签名方式
		Date curDate = interfaceRequest.getCreateTime();
		String orderDate = DateFormatUtils.format(curDate, "yyyyMMdd");
		String reqTime = DateFormatUtils.format(curDate, "yyyyMMddHHmmss");

		// 签名原文
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("interfaceVersion", interfaceVersion);
		params.put("tranType", tranType);
		params.put("bankCode", bankCode);
		params.put("merNo", merNo);
		params.put("goodsName", goodsName);
		params.put("orderDate", orderDate);
		params.put("orderNo", orderNo);
		params.put("merExtend", interfaceRequest.getInterfaceRequestID());
		params.put("amount", Long.valueOf(Double.valueOf(AmountUtils.multiply(amount, 100)).longValue()).toString());
		params.put("charSet", charSet);
		params.put("tradeMode", tradeMode);
		params.put("reqTime", reqTime);
		params.put("respMode", respMode);
		params.put("callbackUrl", callbackUrl);
		params.put("serverCallUrl", serverCallUrl);
		params.put("signType", signType);
		String signSource = UnicomUtil.getSignSourMsg(params, key);
		logger.info("联通沃支付通道交易明文串：{}", signSource);
		// 计算签名密文
		String signMsg = "";
		try {
			signMsg = DigestUtils.md5DigestAsHex(signSource.getBytes(charSet));
		} catch (Exception e) {
			logger.error("联通沃支付MD5计算签名出异常了！！", e);
		}
		// 组织发往银行的报文
		params.put("signMsg", signMsg);
		params.put("gateway", "submit");
		params.put("url", bankURL);
		params.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		logger.info("发往联通沃支付通道交易串：{}", params.toString());
		return new Object[] { bankURL, params, interfaceRequest.getInterfaceRequestID() };
	}

	@Override
	public Map<String, Object> signatureVerify(InternetbankSalesResponseBean internetbankSalesResponseBean, Properties tradeConfigs) {
		// XXX
		return null;
	}

	@Override
	public InterfaceRequest complete(Map<String, Object> completeParameters) throws BusinessException {
		//XXX
		return null;
	}

	@Override
	public Object[] query(TradeContext tradeContext) throws BusinessException {
		// 接口请求信息
		InterfaceRequest queryInterfaceRequest = tradeContext.getInterfaceRequest();
		// 请求参数
		Map<String, String> requestParameters = tradeContext.getRequestParameters();
		// 获取配置信息
		Properties properties = JsonUtils.toObject(requestParameters.get("transConfig"), Properties.class);
		// 取得商户订单查询所需要的信息
		String merNo = properties.getProperty("merNo"); // 商户号
		String orderNo = queryInterfaceRequest.getInterfaceRequestID();// 原始订单号
		String orderTime = requestParameters.get("originalRequestTime");
		String orderDate = "";// 原始订单日期
		if (orderTime != null && !"".equals(orderTime)) {
			orderDate = orderTime.substring(0, 8);
		}
		String key = properties.getProperty("key");
		String charSet = "UTF-8";// 字符集
		String signType = "MD5";// 签名方式

		// 封装查询MAP参数
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("merNo", merNo);
		params.put("orderDate", orderDate);
		params.put("orderNo", orderNo.replaceAll("-",""));
		params.put("charSet", charSet);
		params.put("signType", signType);
		String signSource = UnicomUtil.getSignSourMsg(params, key);
		String respondStr = "";
		try {
			String signMsg = DigestUtils.md5DigestAsHex(signSource.getBytes(charSet));
			params.put("signMsg", signMsg);
			// 查询请求串
			String queryStr = UnicomUtil.generateQueryMsg(params, charSet);
			// 查询请求URL
			String queryUrl = properties.getProperty("unicom_urlQ");
			// 查询请求结果
			respondStr = HttpClientUtils.send(Method.POST, queryUrl, queryStr, true, "UTF-8", 3000);
		} catch (Exception e) {
			logger.error("联通沃支付查询出异常了======", e);
			throw new BusinessException("联通沃支付查询出异常了", e);
		}
		Map<String, Object> returnParamMap = UnicomUtil.queryRespondMap(respondStr);

		/** 查询结果 */
		String queryResult = (String) returnParamMap.get("queryResult");
		logger.info("=============联通沃支付查询返回结果============：" + queryResult);
		/** 订单状态 */
		String orderState = (String) returnParamMap.get("orderState");
		/** 支付金额 */
		String payAmount = (String) returnParamMap.get("payAmount");
		/** 支付号 */
		String payJournl = (String) returnParamMap.get("payJournl");

		Map<String, String> queryParams = new HashMap<>();
		// 判断商户订单查询结果状态，进行后续操作
		if ("SUCCESS".equals(queryResult)) {
			if ("1".equals(orderState)) {
				// 订单状态
				queryParams.put("tranStat", "SUCCESS");
				// 订单金额
				queryParams.put("amount", String.valueOf(AmountUtils.divide(new Double(payAmount), 100, 2)));
				// 订单号
				queryParams.put("orderNo", orderNo);
				// 资金通道支付流水
				queryParams.put("interfaceOrderID", payJournl);
				// 交易日期
				queryParams.put("notifyDate", DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
				// 查询结果
				queryParams.put("queryStatus", InterfaceTradeStatus.SUCCESS.name());
			} else {
				queryParams.put("queryStatus", InterfaceTradeStatus.UNKNOWN.name()); // 商户订单查询失败
			}
		} else {
			queryParams.put("queryStatus", InterfaceTradeStatus.UNKNOWN.name()); // 商户订单查询失败
		}

		// 响应码
		queryParams.put("responseCode", queryResult);
		// 响应描述
		queryParams.put("responseMessage", queryResult);
		// 接口编码
		queryParams.put("interfaceCode", queryInterfaceRequest.getInterfaceInfoCode());
		// 业务完成方式
		queryParams.put("businessCompleteType", requestParameters.get("businessCompleteType"));

		return new Object[] { queryParams };
	}
	
	
	private static Map<String, String> bankCodes = new HashMap<>();
	static {
		bankCodes.put("ICBC", "ICBC_B2C");
		bankCodes.put("CCB", "CCB_B2C");
		bankCodes.put("ABC", "ABC_B2C");
		bankCodes.put("CMB", "CMB_B2C");
		bankCodes.put("BCM", "BOCO_B2C");
		bankCodes.put("BOC", "BOC_B2C");
		bankCodes.put("CEB", "CEB_B2C");
		bankCodes.put("CMBC", "CMBC_B2C");
		bankCodes.put("CIB", "CIB_B2C");
		bankCodes.put("CNCB", "CITIC_B2C");
		bankCodes.put("CGB", "GDB_B2C");
		bankCodes.put("SPDB", "SPDB_B2C");
		bankCodes.put("PAB", "SPA_B2C");
		bankCodes.put("HXB", "HXB_B2C");
		bankCodes.put("NBCB", "NBCB_B2C");
		bankCodes.put("BEA", "HKBEA_B2C");
		bankCodes.put("BOS", "SHB_B2C");
		bankCodes.put("PSBC", "PSBC_B2C");
		bankCodes.put("NJCB", "NJCB_B2C");
		bankCodes.put("CBHB", "CBHB_B2C");
		bankCodes.put("BOCD", "64296510");
		bankCodes.put("BOB", "BCCB_B2C");
		bankCodes.put("HSB", "64296511");
		bankCodes.put("SDB", "SDB_B2C");//深圳发展银行
		bankCodes.put("CZB", "CZ_B2C");//浙商银行
		bankCodes.put("BJRCB", "BJRCB_B2C");//北京农村商业银行
	}
	
	private static String convertBankCode(String bankCode){
		if(bankCodes.get(bankCode) != null){
			return bankCodes.get(bankCode);
		}else{
			throw new RuntimeException("联通网银,银行编号匹配失败:{"+bankCode+"}");
		}
	}
}
