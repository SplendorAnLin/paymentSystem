package com.yl.online.model.bean;

import com.yl.online.model.enums.RepeatFlag;

/**
 * 创建消费订单Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class CreateSalesOrderBean extends CreateOrderBean {

	private static final long serialVersionUID = 6687675979079698068L;
	/** 同一订单是否可重复提交 */
	private RepeatFlag retryFalg;
	/** 页面重定向URL */
	private String redirectURL;
	/** 商品信息 */
	private String products;

	public RepeatFlag getRetryFalg() {
		return retryFalg;
	}

	public void setRetryFalg(RepeatFlag retryFalg) {
		this.retryFalg = retryFalg;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

}
