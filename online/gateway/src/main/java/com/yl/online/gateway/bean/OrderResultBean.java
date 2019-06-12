package com.yl.online.gateway.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * 订单结果
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月4日
 * @version V1.0.0
 */
public class OrderResultBean implements Serializable {

	private static final long serialVersionUID = 8777111497227281377L;
	/** 接口编号 */
	@NotNull
	private String apiCode;
	/** 版本号 */
	@NotNull
	private String versionCode;
	/** 合作方唯一订单号 */
	@NotNull
	private String outOrderId;
	/** 交易订单编号 */
	@NotNull
	private String tradeOrderCode;
	/** 金额 */
	private BigDecimal amount;
	/** 商品名称 */
	private String productName;
	/** 商品描述 */
	private String productDesc;
	/** 商户名称 */
	private String partnerName;
	/** 购买日期 */
	private Date buyDate;
	/** 要展示的接口信息 */
	private Map<String, List<String>> interfaceCodes;

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

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public Map<String, List<String>> getInterfaceCodes() {
		return interfaceCodes;
	}

	public void setInterfaceCodes(Map<String, List<String>> interfaceCodes) {
		this.interfaceCodes = interfaceCodes;
	}

	@Override
	public String toString() {
		return "OrderResultBean [apiCode=" + apiCode + ", versionCode=" + versionCode + ", outOrderId=" + outOrderId + ", tradeOrderCode=" + tradeOrderCode
				+ ", amount=" + amount + ", productName=" + productName + ", productDesc=" + productDesc + ", partnerName=" + partnerName + ", buyDate=" + buyDate
				+ ", interfaceCodes=" + interfaceCodes + "]";
	}

}
