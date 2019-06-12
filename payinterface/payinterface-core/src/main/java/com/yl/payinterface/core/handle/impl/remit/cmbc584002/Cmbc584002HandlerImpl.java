package com.yl.payinterface.core.handle.impl.remit.cmbc584002;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cmbc.unps.service.BasePostService;
import com.cmbc.unps.util.Constants;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.dpay.hessian.enums.CardType;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.handler.ChannelRecionHandler;
import com.yl.payinterface.core.handler.RemitHandler;

/**
 * 深圳民生
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月18日
 * @version V1.0.0
 */
@Service("cmbcHandler584002")
public class Cmbc584002HandlerImpl implements RemitHandler,ChannelRecionHandler {

	private static Logger logger = LoggerFactory.getLogger(Cmbc584002HandlerImpl.class);
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(Cmbc584002HandlerImpl.class.getClassLoader().getResourceAsStream("serverHost.properties"));
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> remit(Map<String, String> map) {

		try {
			// 加载payment.ini配置文件
//			Configuration.loadConfiguation(Cmbc584002Constant.PAYMENT_PATH);
			Map<String, Object> reqMap = new HashMap<String, Object>();

 
 			Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});

			// 技术参数
			reqMap.put("version", properties.getProperty("version")); // 版本
			reqMap.put("source", properties.getProperty("source")); // 交易渠道
			reqMap.put("merchantNum",  properties.getProperty("merchantNum")); // 商户ID
			            
			reqMap.put("merchantSeq", genMerchantSeq(map.get("requestNo").toString().concat("_"))); // 商户请求流水号
			reqMap.put("transDate", Constants.sf1.format(new Date())); // 交易日期 
			reqMap.put("transTime", Constants.sf2.format(new Date())); // 交易时间
			reqMap.put("transCode", properties.getProperty("transCodeRemit")); // 交易码
			reqMap.put("securityType", properties.getProperty("securitytype")); // 安全规则
			
			// 业务参数
			reqMap.put("defaultTradeType", properties.getProperty("defaultTradeType")); // 10030005 公司网络金融代发
			reqMap.put("contractId",  properties.getProperty("contractId")); // 签约Id， 需与原交易的签约编码一致
			reqMap.put("subject",  "代付"); // 订单标题
			reqMap.put("body", map.get("remark")); // 订单详情
			reqMap.put("currencyCode", "RMB"); // 原订单币种
			reqMap.put("amount", map.get("amount"));	// 单位为元
			reqMap.put("bizType",properties.getProperty("bizType"));	// 产品类型 
			if(map.get("accountType").toString().equals("OPEN")){//对公
				reqMap.put("payeeAccType","0");
			}else{
				if(map.get("cardType").equals(CardType.DEBIT.toString())){//借记卡
					reqMap.put("payeeAccType","1"); // 字典编码参见《统一网络支付平台数据字典.doc》 收款账户类型
				}else if(map.get("cardType").equals(CardType.CREDIT.toString())){//贷记卡
					reqMap.put("payeeAccType", "7"); // 字典编码参见《统一网络支付平台数据字典.doc》 收款账户类型
				}
			}
			String payeePartyId = "";
			try {
				payeePartyId = getCnaps(map.get("bankName")).get(0).get("clearingBankCode");
			} catch (Exception e) {
				logger.error("cmbc remit getCnaps error:{}", e);
				Map<String, String> respMap = new HashMap<>();
				respMap.put("tranStat",InterfaceTradeStatus.FAILED.name());
				respMap.put("requestNo", map.get("requestNo").toString());
				respMap.put("compType", BusinessCompleteType.NORMAL.name());
				respMap.put("amount", respMap.get("amount").toString());
				respMap.put("resCode","9998");
				respMap.put("resMsg","获取清分行号失败");
				return respMap;
			}
			
			reqMap.put("payeePartyId",payeePartyId.replaceAll("\"", "")); //清分行号  字典编码参见《统一网络支付平台数据字典.doc》 收款账户开户行
			reqMap.put("payeePartyName",  map.get("bankName")); // 收款行名
			reqMap.put("payeeAccNo", map.get("accountNo")); // 收款人账号
			reqMap.put("payeeAccName",map.get("accountName")); // 收款人户名
			reqMap.put("notifyUrl",properties.getProperty("notifyUrl"));
			
			//地址的配置的配置
			reqMap.put("transbackurl",properties.getProperty("transbackurl"));
			// 加密配置
			reqMap.put("transfronturl",properties.getProperty("transfronturl"));
	 		reqMap.put("custsm2path",properties.getProperty("custsm2path"));
	 		reqMap.put("custsm2pass",properties.getProperty("custsm2pass"));
	 		reqMap.put("custcertpass",properties.getProperty("custcertpass"));
	 		reqMap.put("cmbccertpath", properties.getProperty("cmbccertpath"));
			
			BasePostService basePostService = new BasePostService();
			Map<String, Object> respMap = null;

			logger.info("请求明文：" +reqMap.toString());
  			try {
				respMap = basePostService.postTrans(reqMap);
			} catch (Exception e) {
				logger.error("cmbc remit request error:{}", e);
				Map<String, String> respMaps = new HashMap<>();
				respMaps.put("tranStat",InterfaceTradeStatus.FAILED.name());
				respMaps.put("requestNo", map.get("requestNo").toString());
				respMaps.put("compType", BusinessCompleteType.NORMAL.name());
				respMaps.put("amount", respMap.get("amount").toString());
				respMaps.put("resCode","9999");
				respMaps.put("resMsg","发往通道方失败");
				return respMaps;
			}
			logger.info("请求结果：" +respMap.toString());
			
			if(respMap.get("gateReturnCode").toString().equals("000000")&& respMap.get("busiType").toString().equals("S")){//交易成功
				if(respMap.get("busiCode").toString().equals("UNP00000")){//支付成功
					respMap.put("tranStat",InterfaceTradeStatus.SUCCESS);
				}else{//等待异步通知
					respMap.put("tranStat",InterfaceTradeStatus.UNKNOWN);
				}
			}else if(respMap.get("busiType").toString().equals("R")){//未知
				respMap.put("tranStat",InterfaceTradeStatus.UNKNOWN);
			}else{
				respMap.put("tranStat",InterfaceTradeStatus.FAILED);
			}
			respMap.put("requestNo", map.get("requestNo").toString());
			respMap.put("compType", BusinessCompleteType.NORMAL);
			respMap.put("amount", respMap.get("amount").toString());
			respMap.put("interfaceOrderID",respMap.get("platSeq").toString());
			respMap.put("resCode",respMap.get("busiCode").toString());
			respMap.put("resMsg",respMap.get("busiMsg").toString());

			return JsonUtils.toObject(JsonUtils.toJsonString(respMap), Map.class);
		} catch (Exception e) {
			logger.error("cmbc remit error:{}", e);
		}
		
		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("tranStat", "UNKNOW");
		resMap.put("resCode", "9997");
		resMap.put("resMsg", "未知异常");
		return resMap;
	}

	// 商户发起交易时使用的流水，为防止与其他交易系统流水号重复，特定义流水号组成规则如下：字母、数字、下划线，长度须为50位。
	// 以商户编号为前缀，需补齐后总长度50位
	public static String genMerchantSeq(String merchantNum) {
 		String merchantSeqPre = merchantNum;
		int seqLength = 50 - merchantSeqPre.length();
		String merchantSeq = merchantSeqPre
				+ String.format("%0" + seqLength + "d",0); // 仅参考，商户自定义流水号生成
		return merchantSeq;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> query(Map<String, String> map){
		try {

			Map<String, Object> reqMap = new HashMap<String, Object>();
 			Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});

			// 技术参数
			reqMap.put("version", properties.getProperty("version")); // 版本
			reqMap.put("source", properties.getProperty("source")); // 交易渠道
			reqMap.put("merchantNum",  properties.getProperty("merchantNum")); // 商户ID
			reqMap.put("merchantSeq", map.get("merchantSeq")); // 商户请求流水号
			reqMap.put("transDate", Constants.sf1.format(new Date())); // 交易日期（民生记录的交易日期） 
			reqMap.put("transTime", Constants.sf2.format(new Date())); // 交易时间
			reqMap.put("transCode", properties.getProperty("transCodeQuery")); // 交易码
			reqMap.put("securityType", properties.getProperty("securitytype")); // 安全规则
			
			// 业务参数
			reqMap.put("contractId",  properties.getProperty("contractId")); // 签约Id， 需与原交易的签约编码一致
			reqMap.put("orgMerchantSeq", genMerchantSeq(map.get("requestNo").toString().concat("_"))); // 原订单流水号
			reqMap.put("orgTransDate", map.get("transDate"));//代付交易日期 
			reqMap.put("defaultTradeType", properties.getProperty("defaultTradeType")); // 10030005 公司网络金融代发/批量代发
			
			
			
			//地址的配置
 			reqMap.put("queryurl",properties.getProperty("queryurl"));
            //加密配置
 	 		reqMap.put("custsm2path",properties.getProperty("custsm2path"));
	 		reqMap.put("custsm2pass",properties.getProperty("custsm2pass"));
	 		reqMap.put("custcertpass",properties.getProperty("custcertpass"));
	 		reqMap.put("cmbccertpath", properties.getProperty("cmbccertpath"));
			
			BasePostService basePostService = new BasePostService();
			Map<String, Object> respMap = null;
			logger.error("请求明文：" +reqMap.toString());
			respMap = basePostService.postQuery(reqMap);
			logger.error("请求结果：" +respMap.toString());
 			return JsonUtils.toObject(JsonUtils.toJsonString(respMap), Map.class);
		
		} catch (Exception e) {
			logger.error("cmbc query error:{}", e);
		}
		
		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("tranStat", "UNKNOW");
		resMap.put("resCode", "9997");
		resMap.put("resMsg", "未知异常");
		return resMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,String> recion(Map<String,String> map) {
		try {

			Map<String, Object> reqMap = new HashMap<String, Object>();
 			Properties properties = JsonUtils.toObject(String.valueOf(map.get("tradeConfigs")), new TypeReference<Properties>(){});

			// 技术参数
			reqMap.put("version", properties.getProperty("version")); // 版本
			reqMap.put("source", properties.getProperty("source")); // 交易渠道
			reqMap.put("merchantNum",  properties.getProperty("merchantNum")); // 商户ID
			reqMap.put("merchantSeq", genMerchantSeq(properties.getProperty("merchantNum")+System.currentTimeMillis())); // 商户请求流水号
			reqMap.put("transDate", Constants.sf1.format(new Date())); // 交易日期（民生记录的交易日期） 
			reqMap.put("transTime", Constants.sf2.format(new Date())); // 交易时间
			reqMap.put("transCode", properties.getProperty("transCodeRecion")); // 交易码
			reqMap.put("securityType", properties.getProperty("securitytype")); // 安全规则
			
			// 业务参数
			reqMap.put("contractId",  properties.getProperty("contractId")); // 签约Id， 需与原交易的签约编码一致
			reqMap.put("fileTranDate", map.get("billDate"));//代付交易日期 
			reqMap.put("defaultTradeType", properties.getProperty("defaultTradeType")); //  固定填：30010002
			reqMap.put("fileType", "detail");//文件类型
			reqMap.put("segmentIndex", "0"); // 当前块，从零开始
			
			//地址的配置
 			reqMap.put("fileurl",properties.getProperty("fileurl"));
            //加密配置
 	 		reqMap.put("custsm2path",properties.getProperty("custsm2path"));
	 		reqMap.put("custsm2pass",properties.getProperty("custsm2pass"));
	 		reqMap.put("custcertpass",properties.getProperty("custcertpass"));
	 		reqMap.put("cmbccertpath", properties.getProperty("cmbccertpath"));

			BasePostService basePostService = new BasePostService();
			Map<String, Object> respMap = null;
			logger.error("请求明文：" +reqMap.toString());
			String respMapStr = basePostService.postFile(reqMap, properties.getProperty("recionFilePath"));
			logger.error("请求结果：" +respMapStr.toString());
 			return JsonUtils.toObject(JsonUtils.toJsonString(respMap), Map.class);
		
		} catch (Exception e) {
			logger.error("cmbc recion error:{}", e);
		}
		
		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("tranStat", "UNKNOW");
		resMap.put("resCode", "9997");
		resMap.put("resMsg", "未知异常");
		return resMap;

	}
	
	
//	private static String convertBankCode(String code){
//		Map<String,String> codes = new HashMap<>();
//		codes.put("ICBC", "102100099996");//中国工商银行
//		codes.put("ABC", "103100000026");// 中国农业银行股份有限公司
//		codes.put("BOC", "104100000004");//中国银行总行
//		codes.put("CCB", "105100000017");//中国建设银行股份有限公司总行	
// 		codes.put("CITICB", "302100011000");//中信银行股份有限公司
//		codes.put("CEB", "303100000006");//中国光大银行	
//		codes.put("HXB", "304100040000");//华夏银行股份有限公司总行	
//		codes.put("CMBC", "305100000013");//中国民生银行
//		codes.put("CGB", "306581000003");//广发银行股份有限公司	
//		codes.put("PAB", "307584007998");//平安银行（原深圳发展银行）	
//		codes.put("CMB", "308584000013");//招商银行股份有限公司	
//		codes.put("CIB", "309391000011");//兴业银行总行	
//		codes.put("SPDB", "310290000013");//上海浦东发展银行	
//		codes.put("CBHB", "318110000014");//渤海银行股份有限公司	
//		codes.put("PSBC", "403100000004");//中国邮政储蓄银行有限责任公司	
//		codes.put("SHB", "325290000012");//上海银行股份有限公司	
//		codes.put("BOB", "313100000013");//北京银行	
//		return codes.get(code);
//	}
	
	
	public List<Map<String, String>> getCnaps(String bankNames) throws Exception {
		if (bankNames == null || "".equals(bankNames)) {
			return new ArrayList<Map<String, String>>();
		}
		String words = "";
		if (bankNames.indexOf(",") > -1) {
			String[] bankName = bankNames.split(",");
			for (int i = 0; i < bankName.length; i++) {
				if (i == 0) {
					words += "words=" + URLEncoder.encode(bankName[i], "UTF-8");
					continue;
				}
				words += "&words=" + URLEncoder.encode(bankName[i], "UTF-8");
			}
		} else {
			words = "words=" + URLEncoder.encode(bankNames, "UTF-8");
			words += "&1=1";
		}
		List<Map<String, String>> list = null;
		try {
		    String url = (String) prop.get("cachecenter.service.url")
					+ "/cnaps/batchSearch.htm?fields=clearingBankCode&fields=code&fields=providerCode&fields=clearingBankLevel&fields=name";
			String resData = HttpClientUtils.send(Method.POST, url, words, true, Charset.forName("UTF-8").name(), 40000);
			JsonNode cnapsNodes = JsonUtils.getInstance().readTree(resData);
			list = new ArrayList<>();
			for (JsonNode cnapsNode : cnapsNodes) {
				if (cnapsNode.size() == 0) {
					Map<String, String> map = new HashMap<String, String>();
					list.add(map);
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("code", cnapsNode.get("code").toString());
				map.put("clearingBankCode", cnapsNode.get("clearingBankCode").toString());
				map.put("providerCode", cnapsNode.get("providerCode").toString());
				map.put("clearingBankLevel", cnapsNode.get("clearingBankLevel").toString());
				map.put("name", cnapsNode.get("name").toString());
				if ("null".equals(map.get("code"))) {
					map.put("code", "");
				}
				list.add(map);
			}
		} catch (Exception e) {
			logger.error("系统异常获取银行编码payeePartyId时出错:",e);
		}
		return list;
	}

}
