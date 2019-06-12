package com.yl.payinterface.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cmbc.unps.service.BasePostService;
import com.cmbc.unps.util.Constants;
 
/**
 * 直接URL报文交互，返回JSON，用于查询交易
 * 
 * 测试环境地址： http://111.205.207.103:18180/pay/query/service.do
 * 测试商户号：1800000100000277
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月27日
 * @version V1.0.0
 */
public class CopyOfPAY_Q001DemoOnline {

	public static void main(String[] args) {
		
		Map<String, Object> reqMap = new HashMap<String, Object>();
		
		// 技术参数
		reqMap.put("version", "v1.0"); // 版本
		reqMap.put("source", "PC"); // 交易渠道
		reqMap.put("merchantNum", "1800000100000277"); // 商户ID
		reqMap.put("merchantSeq", "18000001000002772016120816192957000000000000000000"); // 商户请求流水号
		reqMap.put("transDate", Constants.sf1.format(new Date())); // 交易日期 
		reqMap.put("transTime", Constants.sf2.format(new Date())); // 交易时间
		reqMap.put("transCode", "PAY_Q001"); // 交易码
		reqMap.put("securityType","SM203"); // 安全规则

		// 业务参数
		reqMap.put("contractId", "01201611231615260003"); // 签约Id
		reqMap.put("orgMerchantSeq", "18000001000002772016120816192957000000000000000000"); // 原订单流水号
		reqMap.put("orgTransDate", "20161208"); // 原订单日期
		reqMap.put("defaultTradeType", "30010002"); // 10030001-即时网关支付,
													// 10030003-见证网关支付

		
		//追加
		reqMap.put("queryurl","https://tbank.cmbc.com.cn:50010/pay/query/service.do");
 		reqMap.put("custsm2path","/cmbc/cmbc.sm2");
 		reqMap.put("custsm2pass","123456");
 		reqMap.put("custcertpass","/cmbc/yl.cer");
 		reqMap.put("cmbccertpath","/cmbc/cmbc.cer");
 		
 		 
		BasePostService basePostService = new BasePostService();
		Map<String, Object> respMap = null;
		try {
			System.out.println("请求明文：" +reqMap);
			respMap = basePostService.postQuery(reqMap);
			System.out.println("请求结果：" +respMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String busiType = (String) respMap.get("busiType"); //业务返回状态
		String busiCode = (String) respMap.get("busiCode"); //业务返回码
		String busiMsg = (String) respMap.get("busiMsg"); //业务返回消息
		
		// 只有当busiType返回为S时下面报文有返回
		String mrcSeq = (String) respMap.get("orgMerchantSeq"); //商户流水号
		String platSeq = (String) respMap.get("platSeq"); //平台交易流水号
		String amount = (String) respMap.get("amount"); //交易金额
		String defaultTradeType = (String) respMap.get("defaultTradeType"); //订单状态
		/*
		 * 订单状态，第一位：0：创建、1：确认、2：已支付、3：已配送、4：已完成、5：已结算、9：已撤销。第二位：0：正常；1：处理中；2：失败；R：未知
		 例如：
		00-支付订单创建未支付
		02-支付订单创建失败
		21-支付成功尚未接收到通知返回
		20-支付成功并收到通知返回
		2R-支付成功但实际支付交易未知
		50-支付已结算至商户
		52-支付已结算但失败（可能商户银行账户有问题）
		5R-支付已结算但实际交易未知（可能商户银行账户有问题或银行支付系统有异常）
		40-代发/见证支付确认付款成功
		 */
		String status = (String) respMap.get("status"); //订单状态

		
		// 输出
		System.out.println(busiType);
		System.out.println(busiCode);
		System.out.println(busiMsg);
		
		System.out.println(mrcSeq);
		System.out.println(platSeq);
		System.out.println(amount);
		System.out.println(defaultTradeType);
		System.out.println(status);

	}

	// 仅参考，商户自定义流水号生成
	// 商户发起交易时使用的流水，为防止与其他交易系统流水号重复，特定义流水号组成规则如下：字母、数字、下划线，长度须为50位。 以商户编号为前缀，需补齐后总长度50位
//	public static long seq = 0;
//	public static synchronized String genMerchantSeq(){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String dateTime = sdf.format(new Date());
//		String merchantSeqPre = "1800000100000277"/*Configuration.getConfig(Constants.PARAM_MERCHANT_NUM)*/ + dateTime;
//		int seqLength = 50 - merchantSeqPre.length();
//		String merchantSeq = merchantSeqPre + String.format("%0" + seqLength + "d", seq); // 仅参考，商户自定义流水号生成
//		seq++;
//		return merchantSeq;
//	}
}
