package com.yl.payinterface.core.handle.impl.wallet.cncb330000;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.payinterface.core.ExceptionMessages;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 中信钱包支付
 * 
 * @author 聚合支付有限公司
 * @since 2017年1月15日
 * @version V1.0.0
 */
@Service("walletCncb330000Handler")
public class Cncb330000HandlerImpl implements WalletPayHandler{
	
	private static Logger logger = LoggerFactory.getLogger(Cncb330000HandlerImpl.class);
	@Resource
	private InterfaceRequestService interfaceRequestService;
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("productName", "测试");
		map.put("interfaceRequestID", String.valueOf(System.nanoTime()));
		map.put("orderTime", new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
		map.put("interfaceInfoCode", "CNCB330000-WXNATIVE");
		Properties properties = new Properties();
		properties.put("pay_url", "https://120.55.176.124:8092/MPay/backTransAction.do");
//		properties.put("pay_url", "https://120.27.165.177:8099/MPay/backTransAction.do");
		properties.put("query_url", "https://120.27.165.177:8099/MPay/backTransAction.do");
		properties.put("shop_url", "http://pay.feiyijj.com/boss/");
		
		// h5
//		properties.put("key", "85654640898178374360892737723796");
//		properties.put("mch_id", "996600008000045");
		
		//native
//		properties.put("key", "87778689534841559781364948321406");
//		properties.put("mch_id", "996600008000049");
		
		properties.put("key", "81608813751332412874418291407333");
		properties.put("mch_id", "886600000004006");
		
		//alipay
//		properties.put("mch_id", "996600008000038");
//		properties.put("key", "82125332911284008007833643557692");
		properties.put("notify_url", "http://jayzhangxiao.eicp.net/payinterface-front/cncbWalletNotify/completePay.htm");
		properties.put("wap_url", "https://m.jd.com");
		properties.put("wap_name", "test");
		properties.put("params", "test001");
		map.put("tradeConfigs", JsonUtils.toJsonString(properties));
		map.put("amount", "0.01");
		map.put("ClientIp", "210.12.209.13");
		map.put("interfaceType", "WXNATIVE");
		
		// query
		map.put("originalInterfaceRequestID", "443606176779000");
		map.put("orderTime", "20170118014139");
		new Cncb330000HandlerImpl().pay(map);
	}

	@Override
	public Map<String, String> pay(Map<String, String> map) {
		// 查询交易配置信息
		Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});
		String url = properties.get("pay_url").toString();
		String key = properties.get("key").toString();

		// 组装下单报文
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("backEndUrl", properties.get("notify_url").toString());
		reqMap.put("channelType", "6002");	//接入渠道
		reqMap.put("currencyType", "156");	//交易币种
		reqMap.put("encoding", "UTF-8");	//编码方式
		reqMap.put("merId", properties.get("mch_id").toString());
		reqMap.put("orderBody", map.get("productName"));	//商品描述
		reqMap.put("orderTime", map.get("orderTime"));	//订单生成时间
		reqMap.put("payAccessType", "02");	//接入支付类型
		reqMap.put("productId", "PAY");	//商品ID
		reqMap.put("signMethod", "02");	//签名方法
		reqMap.put("txnAmt", String.valueOf((int)AmountUtils.multiply(Double.valueOf(map.get("amount")), 100)));	//交易金额
		reqMap.put("termId", "WEB");
		reqMap.put("termIp", map.get("ClientIp"));
		if("WXJSAPI".equals(map.get("interfaceType"))){
			reqMap.put("txnSubType", "010133");	//交易子类型
			reqMap.put("orderDetail", "wap_url="+properties.get("wap_url")+"&wap_name="+properties.get("wap_name")+"&params="+properties.get("params"));
		}
		if("WXNATIVE".equals(map.get("interfaceType"))){
			reqMap.put("txnSubType", "010130");	//交易子类型
		}
		if("ALIPAY".equals(map.get("interfaceType"))){
			reqMap.put("txnSubType", "010302");	//交易子类型
		}
		reqMap.put("txnType", "01");	//交易类型
		reqMap.put("orderId", map.get("interfaceRequestID"));
		reqMap.put("attach", map.get("interfaceInfoCode"));
		
		logger.info("中信钱包下单  接口编号:[{}],订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),reqMap);
		
		reqMap.put("key", key);
		reqMap.put("pay_url", url);

		String res;
		Map<String, Object> resMap;

		try {
			res = MD5.request(reqMap);
			resMap = MD5.getResp(res,key);
			logger.info("中信钱包下单  接口编号:[{}],订单号:[{}],响应报文:[{}]",  map.get("interfaceInfoCode"),map.get("interfaceRequestID"),resMap);
			if (!"0000".equals(String.valueOf(resMap.get("respCode")))) {
				throw new RuntimeException("respCode : " + resMap.get("status"));
			}
		} catch (Exception e) {
			logger.error("中信钱包下单异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),e);
			throw new RuntimeException(e.getMessage());
		}

		Map<String,String> wxResMap = new HashMap<String, String>();
		if("WXJSAPI".equals(map.get("interfaceType"))){
			wxResMap.put("gateway", "native");
			String payurl = String.valueOf(resMap.get("mwebUrl"));
			payurl = payurl.replace("ydsd.html", "ydsdrd.html");
			payurl = payurl + "&cburl=" + properties.getProperty("shop_url") + "&pid=" +String.valueOf(resMap.get("prepayid"));
			logger.info("中信钱包下单  接口编号:[{}],订单号:[{}],微信拉起地址:[{}]", map.get("interfaceInfoCode"),map.get("interfaceRequestID"),payurl);
			wxResMap.put("prepayId", String.valueOf(resMap.get("prepayid")));
		}
		if("WXNATIVE".equals(map.get("interfaceType")) || "ALIPAY".equals(map.get("interfaceType"))){
			wxResMap.put("code_url", String.valueOf(resMap.get("codeUrl")));
			wxResMap.put("gateway", "native");
		}
		InterfaceRequest request = interfaceRequestService.queryByInterfaceRequestID(map.get("interfaceRequestID"));
		if("ALIPAY".equals(map.get("interfaceType"))){
			request.setInterfaceOrderID(String.valueOf(resMap.get("seqId")));
			interfaceRequestService.modify(request);
		}else{
			request.setInterfaceOrderID(String.valueOf(resMap.get("txnSeqId")));
			interfaceRequestService.modify(request);
		}
		wxResMap.put("interfaceInfoCode", map.get("interfaceInfoCode"));
		wxResMap.put("interfaceRequestID", map.get("interfaceRequestID"));
		wxResMap.put("txnSeqId", String.valueOf(resMap.get("txnSeqId")));
		wxResMap.put("txnTime", String.valueOf(resMap.get("txnTime")));
		wxResMap.put("prepayId", String.valueOf(resMap.get("prepayId")));
		return wxResMap;
	}

	@Override
	public Map<String, String> query(Map<String, String> map) {

		// 查询交易配置信息
		Properties properties = (Properties) JsonUtils.toObject((String) map.get("tradeConfigs"), Properties.class);

		String url = properties.get("query_url").toString();
		String key = properties.get("key").toString();

		// 组装查询报文
		Map<String, String> reqMap = new HashMap<String, String>();
		reqMap.put("encoding", "UTF-8");	//编码方式
		reqMap.put("signMethod", "02");	//签名方法
		reqMap.put("txnType", "38");	//交易类型
//		reqMap.put("txnType", "04");	//交易类型
		// XXX支付类型
		if("ALIPAY".equals(map.get("interfaceType"))){
			reqMap.put("txnSubType", "381004");	//交易子类型
		}else{
			reqMap.put("txnSubType", "383000");	//交易子类型
		}
//		reqMap.put("txnSubType", "040303");	//交易子类型
		reqMap.put("channelType", "6002");	//接入渠道
		reqMap.put("payAccessType", "02");	//接入支付类型
		reqMap.put("merId", properties.get("mch_id").toString());
		reqMap.put("origOrderId", map.get("interfaceRequestID"));	//原始商户交易时间
		reqMap.put("origOrderTime", map.get("orderTime"));	//交易起始时间
		reqMap.put("fetchOrderNo", "Y");
		reqMap.put("key", key);
		reqMap.put("pay_url", url);
		reqMap.put("seqId", map.get("interfaceOrderId"));
		
		//refund
//		reqMap.put("origTxnSeqId", "443606176779000");
//		reqMap.put("origSettleDate", "20170118");
//		reqMap.put("txnAmt", "1");
//		reqMap.put("orderId", "443606176779001");
//		reqMap.put("currencyType", "156");
//		reqMap.put("orderTime", map.get("orderTime"));	//交易起始时间
		
		logger.info("中信钱包查询  接口编号:[{}],原始订单号:[{}],请求报文:[{}]", map.get("interfaceInfoCode"),map.get("originalInterfaceRequestID"),reqMap);

		String res;
		Map<String, Object> resMap;
		try {
			res = MD5.request(reqMap);
			resMap = MD5.getResp(res,key);
			logger.info("中信钱包查询  接口编号:[{}],原始订单号:[{}],响应报文:[{}]", map.get("interfaceInfoCode"),map.get("originalInterfaceRequestID"),resMap);
		} catch (Exception e) {
			logger.error("中信钱包查询异常   接口编号:[{}],订单号:[{}],异常信息：[{}]", map.get("interfaceInfoCode"),map.get("originalInterfaceRequestID"),e);
			throw new RuntimeException();
		}

		Map<String, String> queryParams = new HashMap<String, String>();
		String responseCode = "";
		String responseMessage = "";

		if ("0000".equals(String.valueOf(resMap.get("respCode")))) {
			queryParams.put("queryStatus", "SUCCESS");
		} else {
			queryParams.put("queryStatus", "UNKNOWN");
		}

		// 响应码
		queryParams.put("responseCode", responseCode);
		// 响应描述
		queryParams.put("responseMessage", responseMessage);
		// 支付结果
		queryParams.put("tranStat", String.valueOf(resMap.get("origRespCode")));
		// 金额
		queryParams.put("amount", String.valueOf(resMap.get("txnAmt")));
		// 微信订单号
		queryParams.put("weChatOrderId", String.valueOf(resMap.get("origSeqId")));
		// 通知时间
		queryParams.put("notifyDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 支付完成时间
		queryParams.put("orderFinishDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 支付完成时间
		queryParams.put("completeTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 接口编号
		queryParams.put("interfaceCode", map.get("interfaceInfoCode"));
		// 接口请求订单号
		queryParams.put("interfaceRequestId", map.get("originalInterfaceRequestID"));
		// 业务完成方式
		queryParams.put("businessCompleteType", map.get("businessCompleteType"));
		return queryParams;
	}

	@Override
	public InterfaceRequest complete(Map<String, Object> map) {
		// 接口请求订单号
		String interfaceRequestId = map.get("interfaceRequestId").toString();
		// 根据支付流水号查询接口请求信息
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestId);
		logger.info("支付接口订单号为:{},完成时参数：{}", interfaceRequestId, map);

		if (interfaceRequest == null) {
			logger.error("支付接口订单号为:{},业务处理实体不存在", interfaceRequestId);
			throw new RuntimeException(ExceptionMessages.BUSINESS_HANDLER_ENTITY_NULL);
		}
		// 检验交易完成是否已处理成功 成功跳过,否则继续
		if ((!"".equals(interfaceRequest.getStatus())) && (InterfaceTradeStatus.SUCCESS.equals(interfaceRequest.getStatus()))) {
			logger.error("支付接口订单号为:{},接口已成功处理", interfaceRequest.getInterfaceRequestID());
			throw new RuntimeException(ExceptionMessages.INTERFACE_REQUEST_NOT_INIT);
		}
		// 交易结果
		String tranStat = map.get("tranStat").toString();
		logger.info("支付接口订单号为:{},钱包支付-支付订单完成时状态：{}", interfaceRequest.getInterfaceRequestID(), tranStat);

		if ("SUCCESS".equals(tranStat)) {
			interfaceRequest.setStatus(InterfaceTradeStatus.SUCCESS);
			// 交易金额
			double amount = AmountUtils.divide(Double.valueOf(map.get("amount").toString()).doubleValue(), 100.0D);
			// 校验订单金额是否与上送的一致
			if (!AmountUtils.eq(amount, interfaceRequest.getAmount())) {
				logger.error("支付接口订单号为:{},通知完成支付金额不一致", interfaceRequest.getInterfaceRequestID());
				throw new RuntimeException(ExceptionMessages.PAY_AMOUNT_NOT_ACCORDANCE);
			}
			interfaceRequest.setAmount(amount);
		} else if ("CLOSED".equals(tranStat)) {
			interfaceRequest.setStatus(InterfaceTradeStatus.CLOSED);
			map.put("responseMessage","已关闭");
		} else if ("FAIL".equals(tranStat)) {
			interfaceRequest.setStatus(InterfaceTradeStatus.FAILED);
			map.put("responseMessage","失败");
		} else {
			interfaceRequest.setStatus(InterfaceTradeStatus.UNKNOWN);
			map.put("responseMessage","未知");
		}
		// 响应码
		interfaceRequest.setResponseCode(map.get("responseCode") == null ? "" : map.get("responseCode").toString());
		// 交易结果描述
		interfaceRequest.setResponseMessage(map.get("responseMessage") == null ? "成功" : map.get("responseMessage").toString());
		// 交易完成时间
		if(map.get("completeTime") != null && !interfaceRequest.getStatus().equals(InterfaceTradeStatus.UNKNOWN)){
			try {
				interfaceRequest.setCompleteTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(map.get("completeTime").toString()));
			} catch (ParseException e) {
				logger.error("中信钱包通知 完成时间参数异常 订单号:[{}],异常信息[{}]", interfaceRequest.getInterfaceRequestID(),e);
				interfaceRequest.setCompleteTime(new Date());
			}
		}
		// 业务处理完成方式
		interfaceRequest.setBusinessCompleteType(BusinessCompleteType.valueOf(map.get("businessCompleteType").toString()));
		// 记录微信订单号
		if (map.get("transaction_id") != null) {
			interfaceRequest.setInterfaceOrderID(map.get("transaction_id").toString());
		}
		return interfaceRequest;
	}

}
