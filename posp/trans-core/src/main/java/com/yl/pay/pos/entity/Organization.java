package com.yl.pay.pos.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Title: 组织机构
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "ORGANIZATION")
public class Organization implements Serializable{

	private String code;			//机构代码
	private String name;			//机构名称
	private String parentCode;		//上级机构代码
	private Integer optimistic;		//版本标识
	
	@Id
	@Column(name = "CODE", length = 20)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
		
	@Column(name="NAME",length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="PARENT_CODE",length = 20)
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	@Version
	@Column(columnDefinition = "int", length = 11)
	public Integer getOptimistic() {
		return optimistic;
	}
	public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}
}

