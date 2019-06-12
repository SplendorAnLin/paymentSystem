package com.yl.dpay.core.entity;

import java.io.Serializable;

import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.Status;
import com.yl.dpay.core.enums.FireType;

/**
 * 代付配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
public class ServiceConfig extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 6636498409775611220L;
	/**
	 * 持有者ID
	 */
	private String ownerId;
	/**
	 * 持有者角色
	 */
	private OwnerRole ownerRole;
	/**
	 * 是否有效
	 */
	private String valid = "TRUE";
	/**
	 * 是否手动审核
	 */
	private String manualAudit = "FALSE"; // TRUE：自动审核
	/**
	 * 代付复核密码接收手机号
	 */
	private String phone; // 手动审核必输
	/**
	 * 代付复核密码
	 */
	private String complexPassword; // 手动审核必输

	/**
	 * 代付是否开通手机验证复核
	 */
	private String usePhoneCheck = "FALSE";
	
	/**
	 * 代付公钥
	 */
	private String publicKey;
	
	/**
	 * 代付私钥
	 */
	private String privateKey;
	
	/**
	 * ip
	 */
	private String custIp;
	
	/**
	 * 域名
	 */
	private String domain;
	
	/**
	 * 最小金额
	 */
	private double minAmount;
	
	/**
	 * 最大金额
	 */
	private double maxAmount;
	
	/**
	 * 假日付状态
	 */
	private Status holidayStatus;
	/**
	 * 日最大交易额
	 */
	private double dayMaxAmount;
	/**
	 * 运营是否手动审核
	 */
	private String bossAudit = "FALSE"; // TRUE：自动审核
	private String startTime;		//代付开始时间
	private String endTime;			//代付结束时间
	/**
	 * 发起方式
	 */
	private FireType fireType;

	
	public OwnerRole getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(OwnerRole ownerRole) {
		this.ownerRole = ownerRole;
	}

	public ServiceConfig() {
		super();
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getComplexPassword() {
		return complexPassword;
	}

	public void setComplexPassword(String complexPassword) {
		this.complexPassword = complexPassword;
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

	public String getCustIp() {
		return custIp;
	}

	public void setCustIp(String custIp) {
		this.custIp = custIp;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getManualAudit() {
		return manualAudit;
	}

	public void setManualAudit(String manualAudit) {
		this.manualAudit = manualAudit;
	}

	public String getUsePhoneCheck() {
		return usePhoneCheck;
	}

	public void setUsePhoneCheck(String usePhoneCheck) {
		this.usePhoneCheck = usePhoneCheck;
	}

	public double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Status getHolidayStatus() {
		return holidayStatus;
	}

	public void setHolidayStatus(Status holidayStatus) {
		this.holidayStatus = holidayStatus;
	}

	public double getDayMaxAmount() {
		return dayMaxAmount;
	}

	public void setDayMaxAmount(double dayMaxAmount) {
		this.dayMaxAmount = dayMaxAmount;
	}

	public String getBossAudit() {
		return bossAudit;
	}

	public void setBossAudit(String bossAudit) {
		this.bossAudit = bossAudit;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public FireType getFireType() {
		return fireType;
	}

	public void setFireType(FireType fireType) {
		this.fireType = fireType;
	}
	
}
