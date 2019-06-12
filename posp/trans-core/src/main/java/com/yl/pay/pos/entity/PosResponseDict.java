package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Title: POS返回码
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
@Entity
@Table(name = "POS_RESPONSE_DICT")
public class PosResponseDict extends BaseEntity {

	private String respCode;		//返回码
	private String respInfo;		//返回信息
	private String respRemark;		//描述
	private String category;		//类别码
	private String mappingCode;		//映射码
	
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
	
	@Column(name = "RESP_REMARK", length = 100)
	public String getRespRemark() {
		return respRemark;
	}
	public void setRespRemark(String respRemark) {
		this.respRemark = respRemark;
	}	
}
