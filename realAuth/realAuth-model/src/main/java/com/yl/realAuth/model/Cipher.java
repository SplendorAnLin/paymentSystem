package com.yl.realAuth.model;


/**
 * 密钥
 * @author rui.wang
 * @since 2014年4月23日
 */
public class Cipher extends AutoStringIDModel {

	private static final long serialVersionUID = -833106236247657811L;
	/** 所属系统 */
	
	private String system;
	/** 所属者角色 （商户-CUSTOMER,服务商-AGENT） */
	
	private String ownerRole;
	/** 所属者编号 */
	
	private String ownerCode;
	/** 业务 */
	
	private String businessCode;
	/** 加密算法 */
	
	private String algorithm;
	/** 公钥 */
	
	private String publicKey;
	/** 私钥 */
	private String privateKey;

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
