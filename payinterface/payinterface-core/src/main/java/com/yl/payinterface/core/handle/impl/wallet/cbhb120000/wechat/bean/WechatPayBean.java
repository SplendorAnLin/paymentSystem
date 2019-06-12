package com.yl.payinterface.core.handle.impl.wallet.cbhb120000.wechat.bean;

import java.io.Serializable;

/**
 * 交易Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月14日
 * @version V1.0.0
 */
public class WechatPayBean implements Serializable {

	private static final long serialVersionUID = 2380987764799595937L;

	private String appid;
	private String mch_id;
	private String sub_mch_id;
	private String nonce_str;
	private String sign;
	private String sign_type;
	private String body;
	private String out_trade_no;
	private String total_fee;
	private String spbill_create_ip;
	private String notify_url;
	private String trade_type;
	private String sub_openid;
	private String attach;

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

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getSub_openid() {
		return sub_openid;
	}

	public void setSub_openid(String sub_openid) {
		this.sub_openid = sub_openid;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	@Override
	public String toString() {
		return "WechatPayBean{" +
				"appid='" + appid + '\'' +
				", mch_id='" + mch_id + '\'' +
				", sub_mch_id='" + sub_mch_id + '\'' +
				", nonce_str='" + nonce_str + '\'' +
				", sign='" + sign + '\'' +
				", sign_type='" + sign_type + '\'' +
				", body='" + body + '\'' +
				", out_trade_no='" + out_trade_no + '\'' +
				", total_fee='" + total_fee + '\'' +
				", spbill_create_ip='" + spbill_create_ip + '\'' +
				", notify_url='" + notify_url + '\'' +
				", trade_type='" + trade_type + '\'' +
				", sub_openid='" + sub_openid + '\'' +
				", attach='" + attach + '\'' +
				'}';
	}
}
