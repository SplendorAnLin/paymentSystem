package com.yl.online.model.model;

import org.hibernate.validator.constraints.URL;

/**
 * 产品信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月18日
 * @version V1.0.0
 */
public class Product extends AutoStringIDModel {

	private static final long serialVersionUID = 6152613908995645550L;
	/** 订单编号 */
	private String orderCode;
	/** 商品名称 */
	private String name;
	/** 商品单价 */
	private String price;
	/** 商品数量 */
	private String number;
	/** 商品描述 */
	private String description;
	/** 商品展示URL */
	@URL
	private String showURL;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShowURL() {
		return showURL;
	}

	public void setShowURL(String showURL) {
		this.showURL = showURL;
	}

}
