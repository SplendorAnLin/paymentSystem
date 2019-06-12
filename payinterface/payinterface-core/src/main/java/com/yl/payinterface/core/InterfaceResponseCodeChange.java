package com.yl.payinterface.core;

import java.util.HashMap;
import java.util.Map;

import com.lefu.commons.utils.lang.StringUtils;

public class InterfaceResponseCodeChange {
	/** 响应码 */
	private static Map<String, Map<String, String>> responseCode = new HashMap<String, Map<String, String>>();
	/** 响应描述 */
	private static Map<String, String> responserDesc = new HashMap<String, String>();

	static {
		responserDesc.put("I0003", "受理成功");
		responserDesc.put("I0000", "支付成功");
		responserDesc.put("I0004", "银行卡信息有误");
		responserDesc.put("I0006", "银行卡余额不足");
		responserDesc.put("I0007", "超出交易限额");
		responserDesc.put("I0005", "支付信息有误");
		responserDesc.put("I0015", "聚合在通道状态异常");
		responserDesc.put("I0011", "暂不支持该银行");
		responserDesc.put("I0016", "参数格式及验签错误");
		responserDesc.put("I0017", "通道系统错误");
		responserDesc.put("I0013", "交易存在风险");
		responserDesc.put("I0012", "通道不支持该笔交易");
		responserDesc.put("I0010", "无此交易");
		responserDesc.put("I0019", "白名单用户已存在");
		responserDesc.put("I0020", "非白名单用户");
		responserDesc.put("I0001", "认证正确");
		responserDesc.put("I0008", "订单已存在");
		responserDesc.put("I0009", "订单未存在");
		responserDesc.put("I0010", "无此交易");
		responserDesc.put("I0002", "认证错误");
		responserDesc.put("I0014", "聚合在通道账户余额不足");
		responserDesc.put("I0018", "暂不支持该银行卡");
		responserDesc.put("I0021", "查询失败");
		responserDesc.put("I0029", "聚合发送手机验证码");
		responserDesc.put("I0030", "申请失败");
		responserDesc.put("I0031", "交易失败");
		responserDesc.put("I0032", "请刷新条码或更换其他方式支付");
		responserDesc.put("I0033", "买家余额不足");
		responserDesc.put("I0034", "用户银行卡余额不足");
		responserDesc.put("I0035", "余额支付功能关闭");
		responserDesc.put("I0036", "支付失败");
		responserDesc.put("I0037", "买家付款日限额超限");
		responserDesc.put("I0038", "商家收款额度超限");
		responserDesc.put("I0039", "用户当面付付款开关关闭");
		responserDesc.put("I0040", "该卡不支持当前支付，请换卡支付");
		responserDesc.put("I0041", "买卖家不能相同");
		responserDesc.put("I0042", "用户支付中，需要输入密码");
		responserDesc.put("I0043", "交易状态未知");

		Map<String, String> walletCIBTM = new HashMap<String, String>();
		walletCIBTM.put("000000", "I0000");
		walletCIBTM.put("000001", "I0043");
		walletCIBTM.put("USERPAYING", "I0042");
		walletCIBTM.put("NOTENOUGH", "I0033");
		walletCIBTM.put("AUTH_CODE_INVALID", "I0032");
		walletCIBTM.put("Auto code invalid", "I0032");
		
		/** 京东 */
		Map<String, String> realNameJD = new HashMap<String, String>();
		realNameJD.put("000000", "I0001");
		realNameJD.put("000001", "I0002");
		realNameJD.put("000003", "I0031");
		responseCode.put("REALAUTH-JD_100000", realNameJD);
		
		/** 阿里Pay */
		Map<String, String> realNameALiPay = new HashMap<String, String>();
		realNameALiPay.put("000000", "I0001");
		realNameALiPay.put("000001", "I0002");
		responseCode.put("REALAUTH_ALIPAY_330001", realNameALiPay);
		
		/** 阿里Pay330002 */
		Map<String, String> realNameALiPay330002 = new HashMap<String, String>();
		realNameALiPay330002.put("000000", "I0001");
		realNameALiPay330002.put("000001", "I0002");
		responseCode.put("REALAUTH_ALIPAY_330002", realNameALiPay330002);

		responseCode.put("CIB330000-ALIPAY_MICROPAY", walletCIBTM);
		responseCode.put("CIB330000-WX_MICROPAY", walletCIBTM);
		
		Map<String, String> walletKingPassTM = new HashMap<String, String>();
		walletKingPassTM.put("000000", "I0000");
		walletKingPassTM.put("000001", "I0043");
		walletKingPassTM.put("WP", "I0042");
		walletKingPassTM.put("PP", "I0042");
		
		responseCode.put("KingPass100001-WX_MICROPAY", walletKingPassTM);
		responseCode.put("KingPass100001-ALIPAY_MICROPAY", walletKingPassTM);

	}

	public static Map<String, String> responseCodeChange(String interfaceCode, String respCode) {

		/**
		 * 公用返回内部相应吗响应描述Map
		 */
		Map<String, String> insideRespMap = new HashMap<String, String>();
		// 如果respCode为空返回系统错误
		if (!StringUtils.notBlank(respCode)) {
			insideRespMap.put("insideRespCode", "I0017");
			insideRespMap.put("insideRespDesc", "通道系统错误");
		}
		// 根据respCode获取内部返回码
		String insideRespCode = responseCode.get(interfaceCode).get(respCode);
		if (StringUtils.notBlank(insideRespCode)) {
			insideRespMap.put("insideRespCode", insideRespCode);
			insideRespMap.put("insideRespDesc", responserDesc.get(insideRespCode));
		} else {
			insideRespMap.put("insideRespCode", "I0017");
			insideRespMap.put("insideRespDesc", "通道系统错误");
		}
		return insideRespMap;
	}

}
