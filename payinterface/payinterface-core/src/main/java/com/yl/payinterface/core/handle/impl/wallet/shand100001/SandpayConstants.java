package com.yl.payinterface.core.handle.impl.wallet.shand100001;


/**
 * 杉德交易常量
 * 
 * @author AnLin
 * @since 2016年11月14日
 * @version V1.0.0
 */
public class SandpayConstants {	
	
	public static final String SUCCESS_RESP_CODE = "000000";
	
	public static String WXPAY_PRODUCTID = "00000005";//	微信扫码
	public static String ALIPAY_PRODUCTID = "00000006";//	支付宝扫码
	public static String B2C_PRODUCTID = "00000008";//		网关支付
	public static String UNIONPAYNATIVE = "00000012";//        银联主扫
	
	public static String QR_ORDERPAY = "sandpay.trade.barpay";//	统一下单并支付
	public static String QR_ORDERCREATE = "sandpay.trade.precreate";//		预下单
	public static String QR_ORDERQUERY = "sandpay.trade.query";//		订单查询
	public static String QR_ORDERCANCEL = "sandpay.trade.cancel";//		订单撤销
	public static String QR_ORDERREFUND = "sandpay.trade.refund";//		退货
	public static String QR_CLEARFILEDOWNLOAD = "sandpay.trade.download";//		对账单下载



	public static enum AccessType {
		merchant("1"),
		platform("2");

		public String code;

		private AccessType(String code) {
			this.code = code;
		}

		public String getCode() {
			return this.code;
		}
	}

	public static enum ChannelType {
		INTERNET("07"),
		MOBILETERMINA("08");

		public String code;

		private ChannelType(String code) {
			this.code = code;
		}

		public String getCode() {
			return this.code;
		}
	}
}
