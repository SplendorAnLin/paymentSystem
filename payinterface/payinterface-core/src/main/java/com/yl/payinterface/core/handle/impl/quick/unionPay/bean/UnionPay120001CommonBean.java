package com.yl.payinterface.core.handle.impl.quick.unionPay.bean;

/***
 * 
 * @ClassName UnionPay120001CommonBean
 * @Description 银联请求公共类
 * @author 聚合支付
 * @date 2017年11月18日 下午3:15:58
 */
public class UnionPay120001CommonBean {

	/** 消息类型 */
	private String msgtype;
	/** 编码 */
	private String encoded = "UTF-8";
	/** 机构编号 */
	private String dmnum;
	/** 商户编号 */
	private String trano;
	/** 商户流水号 */
	private String stan;
	/** 签名 */
	private String signature;

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getEncoded() {
		return encoded;
	}

	public void setEncoded(String encoded) {
		this.encoded = encoded;
	}

	public String getDmnum() {
		return dmnum;
	}

	public void setDmnum(String dmnum) {
		this.dmnum = dmnum;
	}

	public String getTrano() {
		return trano;
	}

	public void setTrano(String trano) {
		this.trano = trano;
	}

	public String getStan() {
		return stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "UnionPay120001CommonBean [msgtype=" + msgtype + ", encoded=" + encoded + ", dmnum=" + dmnum + ", trano=" + trano + ", stan=" + stan + ", signature=" + signature + "]";
	}

}
