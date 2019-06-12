package com.yl.online.gateway.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

/**
 * 支付页面参数
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月4日
 * @version V1.0.0
 */
public class PayParam implements Serializable {

	private static final long serialVersionUID = -3616862617750357142L;
	/** 接口编号 */
	@NotNull
	private String apiCode;
	/** 版本号 */
	@NotNull
	private String versionCode;
	/** 交易订单编号 */
	@NotNull
	private String tradeOrderCode;
	/** 金额 */
	private BigDecimal amount;
	/** 接口编号 */
	private String interfaceCode;
	/** 客户端IP */
	private String clientIP;
	/** 认证移动支付信息 */
	private MobileInfoBean mobileInfoBean;
	/** openid **/
	private String openid;
	/** 网银取货地址 **/
	private String pageNotifyUrl;

	public String getApiCode() {
		return apiCode;
	}

	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getTradeOrderCode() {
		return tradeOrderCode;
	}

	public void setTradeOrderCode(String tradeOrderCode) {
		this.tradeOrderCode = tradeOrderCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public MobileInfoBean getMobileInfoBean() {
		return mobileInfoBean;
	}

	public void setMobileInfoBean(MobileInfoBean mobileInfoBean) {
		this.mobileInfoBean = mobileInfoBean;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPageNotifyUrl() {
		return pageNotifyUrl;
	}

	public void setPageNotifyUrl(String pageNotifyUrl) {
		this.pageNotifyUrl = pageNotifyUrl;
	}

}
