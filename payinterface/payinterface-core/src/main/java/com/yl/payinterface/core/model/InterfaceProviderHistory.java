package com.yl.payinterface.core.model;


/**
 * 接口提供方历史
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public class InterfaceProviderHistory extends AutoStringIDModel {

	private static final long serialVersionUID = 4971212602801600876L;
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

	public InterfaceProviderHistory() {
		super();
	}

	public InterfaceProviderHistory(InterfaceProvider interfaceProvider, String invokeSystem, String operator) {
		this.fullName = interfaceProvider.getFullName();
		this.interfaceProviderCode = interfaceProvider.getCode();
		this.shortName = interfaceProvider.getShortName();
		this.invokeSystem = invokeSystem;
		this.operator = operator;

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
