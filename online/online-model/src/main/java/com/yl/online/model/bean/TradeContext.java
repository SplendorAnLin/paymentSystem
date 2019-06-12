package com.yl.online.model.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;
import com.yl.online.model.model.Product;

/**
 * 交易内容Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class TradeContext implements Serializable {

	private static final long serialVersionUID = -7863303612408306651L;
	/** 原始请求信息 */
	private Map<String, String> requestParameters;
	/** 合作方请求信息 */
	private PartnerRequest partnerRequest;
	/** 交易订单 */
	private Order order;
	/** 支付记录 */
	private Payment payment;
	/** 卖方信息 */
	private PartnerInfo sellerInfo;
	/** 商品信息 */
	private List<Product> products;
	/** 安全信息 */
	private Security security;
	/** 返回结果 */
	private Object result;

	public Map<String, String> getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(Map<String, String> requestParameters) {
		this.requestParameters = requestParameters;
	}

	public PartnerRequest getPartnerRequest() {
		return partnerRequest;
	}

	public void setPartnerRequest(PartnerRequest partnerRequest) {
		this.partnerRequest = partnerRequest;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public PartnerInfo getSellerInfo() {
		return sellerInfo;
	}

	public void setSellerInfo(PartnerInfo sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

}
