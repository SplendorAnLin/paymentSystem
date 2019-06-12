package com.pay.sign.bean;

import java.io.Serializable;

import com.pay.sign.dbentity.PosRequest;
import com.pay.sign.enums.TransType;

/**
 * 
 * Title: 
 * Description:  扩展Bean 
 * Copyright: 2015年6月12日下午2:47:17
 * Company: SDJ
 * @author zhongxiang.ren
 */
public class ExtendPayBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//以下为交易过程中公共属性
	private String responseCode;								//响应码
	private String exceptionCode;								//异常码	
	private Double transAmount;									//交易金额（元）
	
	//以下为交易过程中公共对象
	private TransType transType;								//交易类型	
	private PosRequest posRequest;								//POS请求对象
	
	//8583报文信息
	private UnionPayBean unionPayBean;					
		
	/**
	 * 构造
	 */
	public ExtendPayBean() {
		super();
	}
	
	public TransType getTransType() {
		return transType;
	}
	public void setTransType(TransType transType) {
		this.transType = transType;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public UnionPayBean getUnionPayBean() {
		return unionPayBean;
	}

	public void setUnionPayBean(UnionPayBean unionPayBean) {
		this.unionPayBean = unionPayBean;
	}

	public Double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}

	public PosRequest getPosRequest() {
		return posRequest;
	}

	public void setPosRequest(PosRequest posRequest) {
		this.posRequest = posRequest;
	}
	
}
