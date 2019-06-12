package com.yl.pay.pos.bean;

import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.PosRequest;
import com.yl.pay.pos.enums.CardType;
import com.yl.pay.pos.enums.RouteType;
import com.yl.pay.pos.enums.YesNo;

/**
 * Title: 银行路由参数
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankChannelRouteBean {
	
	private PosRequest posRequest;		//POS交易请求
	private Order order;				//订单
	private String customerNo;			//商户号
	private String issuer;				//发卡行编号
	private CardType cardType;			//卡种类
	private String cardVerifyCode;		//卡标识号取值
	private String cardStyle;			//卡的样式、品牌
	private String customerMccCode;		//MCC
	private String customerOrg;			//商户地区编码
	private YesNo isIC;				//是否是IC卡
	private RouteType routeType;    //路由类型
	
	public BankChannelRouteBean() {
		super();
	}

	public BankChannelRouteBean(PosRequest posRequest, Order order,
			String customerNo, String issuer, CardType cardType,
			String cardVerifyCode, String customerMccCode,String customerOrg,String cardStyle,YesNo isIC) {
		super();
		this.posRequest = posRequest;
		this.order = order;
		this.customerNo = customerNo;
		this.issuer = issuer;
		this.cardType = cardType;
		this.cardVerifyCode = cardVerifyCode;
		this.customerMccCode = customerMccCode;
		this.customerOrg=customerOrg;
		this.cardStyle=cardStyle;
		this.isIC=isIC;
	}

	public PosRequest getPosRequest() {
		return posRequest;
	}
	public void setPosRequest(PosRequest posRequest) {
		this.posRequest = posRequest;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getCardVerifyCode() {
		return cardVerifyCode;
	}
	public void setCardVerifyCode(String cardVerifyCode) {
		this.cardVerifyCode = cardVerifyCode;
	}

	public String getCustomerMccCode() {
		return customerMccCode;
	}
	public void setCustomerMccCode(String customerMccCode) {
		this.customerMccCode = customerMccCode;
	}

	public String getCustomerOrg() {
		return customerOrg;
	}

	public void setCustomerOrg(String customerOrg) {
		this.customerOrg = customerOrg;
	}

	public String getCardStyle() {
		return cardStyle;
	}

	public void setCardStyle(String cardStyle) {
		this.cardStyle = cardStyle;
	}

	public YesNo getIsIC() {
		return isIC;
	}

	public void setIsIC(YesNo isIC) {
		this.isIC = isIC;
	}

	public RouteType getRouteType() {
		return routeType;
	}

	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}

	
	
}
