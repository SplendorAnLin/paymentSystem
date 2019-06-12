package com.yl.payinterface.core.handle.impl.wallet.cbhb120000.wechat.bean;

import java.io.Serializable;

/**
 * 公共Bean
 * 
 * @author 聚合支付有限公司
 * @since 2017年08月07日
 * @version V1.0.0
 */
public class CommonBean implements Serializable {
	private static final long serialVersionUID = 87211542787142995L;

	private String appid;
	private String mch_id;
	private String sub_mch_id;
	private String out_trade_no;
	private String nonce_str;
	private String sign;

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

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
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
	
	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	@Override
	public String toString() {
		return "CommonBean{" +
				"appid='" + appid + '\'' +
				", mch_id='" + mch_id + '\'' +
				", sub_mch_id='" + sub_mch_id + '\'' +
				", out_trade_no='" + out_trade_no + '\'' +
				", nonce_str='" + nonce_str + '\'' +
				", sign='" + sign + '\'' +
				'}';
	}
}
