package com.yl.payinterface.core.handle.impl.wallet.cbhb120000.wechat.utils;

import java.io.Serializable;

/**
 * 下载账单Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月18日
 * @version V1.0.0
 */
public class DownloadBillBean implements Serializable {

	private static final long serialVersionUID = 103023241154013124L;

	private String appid;
	private String mch_id;
	private String sub_mch_id;
	private String nonce_str;
	private String sign;
	private String bill_date;
	/**
	 * ALL，返回当日所有订单信息，默认值
	 * SUCCESS，返回当日成功支付的订单
	 * REFUND，返回当日退款订单
	 * REVOKED，已撤销的订单
	 */
	private String bill_type;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBill_date() {
		return bill_date;
	}

	public void setBill_date(String bill_date) {
		this.bill_date = bill_date;
	}

	public String getBill_type() {
		return bill_type;
	}
	
	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	/**
	 * ALL，返回当日所有订单信息，默认值
	 * SUCCESS，返回当日成功支付的订单
	 * REFUND，返回当日退款订单
	 * REVOKED，已撤销的订单
	 */
	public void setBill_type(String bill_type) {
		if (!bill_type.equals("SUCCESS") || !bill_type.equals("REFUND") || !bill_type.equals("REVOKED")) {
			this.bill_type = "ALL";
		} else {
			this.bill_type = bill_type;
		}

	}

	@Override
	public String toString() {
		return "DownloadBillBean [appid=" + appid + ", mch_id=" + mch_id
				+ ", sub_mch_id=" + sub_mch_id + ", nonce_str=" + nonce_str
				+ ", sign=" + sign + ", bill_date=" + bill_date
				+ ", bill_type=" + bill_type + "]";
	}

}
