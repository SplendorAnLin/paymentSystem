package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Title: 银行返回码
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK_RESPONSE_DICT")
public class BankResponseDict extends BaseEntity {

	private String interfaceCode;	//接口编码
	private String respCode;		//返回码
	private String respInfo;		//返回信息
	private String category;		//类别 
	private String mappingCode;		//POS映射码
	private String exceptionCode;   //响应吗特殊处理对应异常码
	
	@Column(name = "INTERFACE_CODE", length = 20)
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}	
	
	@Column(name = "RESP_CODE", length = 20)
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	@Column(name = "RESP_INFO", length = 100)
	public String getRespInfo() {
		return respInfo;
	}
	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}
	
	@Column(name = "CATEGORY", length = 20)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name = "MAPPING_CODE", length = 20)
	public String getMappingCode() {
		return mappingCode;
	}
	public void setMappingCode(String mappingCode) {
		this.mappingCode = mappingCode;
	}
	
	@Column(name = "EXCEPTION_CODE", length = 20)
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}	
}
