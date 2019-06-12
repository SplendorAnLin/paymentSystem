package com.yl.online.model.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 密钥
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class Cipher extends AutoStringIDModel {

	private static final long serialVersionUID = -833106236247657811L;
	/** 所属系统 */
	@NotBlank
	private String system;
	/** 所属者角色 （商户-CUSTOMER） */
	@NotBlank
	private String ownerRole;
	/** 所属者编号 */
	@NotBlank
	private String ownerCode;
	/** 业务 */
	@NotBlank
	private String businessCode;
	/** 加密算法 */
	@NotBlank
	private String algorithm;
	/** 公钥 */
	@NotBlank
	@Length(min = 32, max = 128)
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
