package com.yl.receive.hessian.bean;

import java.io.Serializable;
import java.util.Set;

import com.yl.receive.hessian.enums.AccType;
import com.yl.receive.hessian.enums.CardType;
import com.yl.receive.hessian.enums.CerType;

/**
 * 路由信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class RouteInfoBean implements Serializable{
	
	private static final long serialVersionUID = -1988423224494412611L;
	/**
	 * 权重
	 */
	private int priority;
	/**
	 * 付款接口编号
	 */
	private String remitCode;
	/**
	 * 支持银行
	 */
	private Set<String> bankCodes;
	/**
	 * 支持账户类型
	 */
	private Set<AccType> accountTypes;
	/**
	 * 支持卡类型
	 */
	private Set<CardType> cardTypes;
	/**
	 * 支持认证类型
	 */
	private Set<CerType> cerTypes;
	/**
	 * 指定商户列表
	 */
	private Set<String> customerNoList;
	

	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public String getRemitCode() {
		return remitCode;
	}


	public void setRemitCode(String remitCode) {
		this.remitCode = remitCode;
	}


	public Set<String> getBankCodes() {
		return bankCodes;
	}


	public void setBankCodes(Set<String> bankCodes) {
		this.bankCodes = bankCodes;
	}


	public Set<AccType> getAccountTypes() {
		return accountTypes;
	}


	public void setAccountTypes(Set<AccType> accountTypes) {
		this.accountTypes = accountTypes;
	}


	public Set<CardType> getCardTypes() {
		return cardTypes;
	}


	public void setCardTypes(Set<CardType> cardTypes) {
		this.cardTypes = cardTypes;
	}


	public Set<CerType> getCerTypes() {
		return cerTypes;
	}


	public void setCerTypes(Set<CerType> cerTypes) {
		this.cerTypes = cerTypes;
	}


	public Set<String> getCustomerNoList() {
		return customerNoList;
	}


	public void setCustomerNoList(Set<String> customerNoList) {
		this.customerNoList = customerNoList;
	}
	
}
