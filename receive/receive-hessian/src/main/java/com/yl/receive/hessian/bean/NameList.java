package com.yl.receive.hessian.bean;
import java.io.Serializable;
import java.util.Date;

import com.yl.receive.hessian.enums.CardType;
import com.yl.receive.hessian.enums.Certificate;
import com.yl.receive.hessian.enums.Status;

/**
 * 白名单
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class NameList implements Serializable {
	private static final long serialVersionUID = 7914961111403128151L;
	
	private Long id;
	private String userName;
	private Certificate typeOfCertificate;
	private String licenseNumber;
	private CardType cardType;
	private String account;
	private String phoneNumber;
	private Status status;
	private String failure;
	private Date createTime;
	private Date successTime;
	private String ownerId;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Certificate getTypeOfCertificate() {
		return typeOfCertificate;
	}
	public void setTypeOfCertificate(Certificate typeOfCertificate) {
		this.typeOfCertificate = typeOfCertificate;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getFailure() {
		return failure;
	}
	public void setFailure(String failure) {
		this.failure = failure;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
}