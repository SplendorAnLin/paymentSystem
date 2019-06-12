package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Title: 交易异常码对照表
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
@Entity
@Table(name = "TRANS_RESPONSE_DICT")
public class TransResponseDict extends BaseEntity{
	
	private static final long serialVersionUID = -3880351824556518666L;
	private String  exceptionCode;			//异常码	
	private String 	responseCode;			//POS响应码	
	private String  responseMsg;			//POS响应信息	
	private String 	description;			//异常详细描述
	private String  catagoryName;			//区别异常种类
	
	@Column(name = "EXCEPTION_CODE", length = 20)
	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	@Column(name = "RESPONSE_CODE", length = 20)
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@Column(name = "RESPONSE_MSG", length = 100)
	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "CATAGORY_NAME", length = 200)
	public String getCatagoryName() {
		return catagoryName;
	}

	public void setCatagoryName(String catagoryName) {
		this.catagoryName = catagoryName;
	}	
	
}

