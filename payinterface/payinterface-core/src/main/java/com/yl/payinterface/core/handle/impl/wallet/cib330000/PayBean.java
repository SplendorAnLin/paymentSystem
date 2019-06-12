package com.yl.payinterface.core.handle.impl.wallet.cib330000;

import java.io.Serializable;

/**
 * 交易Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月14日
 * @version V1.0.0
 */
public class PayBean implements Serializable {

	private static final long serialVersionUID = 2380987764799595937L;

	private String service;
	private String mch_id;
	private String out_trade_no;
	private String body;
	private String total_fee;
	private String mch_create_ip;
	private String notify_url;
	private String nonce_str;
	private String sub_openid;
	private String sign;
	private String is_raw;
	private String attach;
	/** 授权码 */
	private String auth_code;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSub_openid() {
		return sub_openid;
	}

	public void setSub_openid(String sub_openid) {
		this.sub_openid = sub_openid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMch_create_ip() {
		return mch_create_ip;
	}

	public void setMch_create_ip(String mch_create_ip) {
		this.mch_create_ip = mch_create_ip;
	}

	public String getIs_raw() {
		return is_raw;
	}

	public void setIs_raw(String is_raw) {
		this.is_raw = is_raw;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	@Override
	public String toString() {
		return "PayBean [service=" + service + ", mch_id=" + mch_id + ", out_trade_no=" + out_trade_no + ", body=" + body + ", total_fee=" + total_fee + ", mch_create_ip=" + mch_create_ip + ", notify_url=" + notify_url + ", nonce_str=" + nonce_str + ", sub_openid=" + sub_openid + ", sign=" + sign
				+ ", is_raw=" + is_raw + ", attach=" + attach + ", auth_code=" + auth_code + "]";
	}

}
