package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口提供方历史Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class InterfaceProviderHistory implements Serializable{
	private static final long serialVersionUID = -748283604846534304L;

	/** 主键 */
	private String id;
	/** 编号 */
	private String code;
	/** 版本号 */
	private Long version;
	/** 创建时间 */
	private Date createTime;
	/** 编号 */
	private String interfaceProviderCode;
	/** 全称 */
	private String fullName;
	/** 简称 */
	private String shortName;
	/** 操作来源 */
	private String invokeSystem;
	/** 操作人 */
	private String operator;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getInterfaceProviderCode() {
		return interfaceProviderCode;
	}
	public void setInterfaceProviderCode(String interfaceProviderCode) {
		this.interfaceProviderCode = interfaceProviderCode;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getInvokeSystem() {
		return invokeSystem;
	}
	public void setInvokeSystem(String invokeSystem) {
		this.invokeSystem = invokeSystem;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}