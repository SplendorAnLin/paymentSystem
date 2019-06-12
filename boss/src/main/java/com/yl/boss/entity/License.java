package com.yl.boss.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 扫码入网Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "LICENSE_ACCESS")
public class License implements Serializable {

	private static final long serialVersionUID = 370164497489334343L;
	
	/** ID */
	private int id;
	
	/** 水牌编号 */
	private String cardNo;
	
	/** 水牌校验码 */
	private String checkCode;
	
	/** 用户手机号 */
	private String phone;
	
	/** 密码 */
	private String passWord;
	
	/** 明文密码 */
	private String publicPWD;
	
	/** 商户名称 */
	private String fullName;
	
	/** 所在省 */
	private String province;
	
	/** 所在市 */
	private String city;
	
	/** 经营地址 */
	private String addr;
	
	/** 法人姓名 */
	private String linkName;
	
	/** 身份证号 */
	private String idCard;
	
	/** 结算卡账户名 */
	private String accName;
	
	/** 结算卡卡号 */
	private String accNo;
	
	/** 分支行信息 */
	private String bankName;

	/** 手持身份证 */
	private String HandheldId;
	
	/** 手持银行卡 */
	private String HandheldBank;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 修改时间 */
	private Date updateTime;
	
	@Id
	@Column(name = "ID", length = 10, unique = true)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "CARDNO", length = 50)
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "CHECKCODE", length = 50)
	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Column(name = "PHONE", length = 50)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "PASSWORD", length = 50)
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Column(name = "PUB_PWD", length = 50)
	public String getPublicPWD() {
		return publicPWD;
	}

	public void setPublicPWD(String publicPWD) {
		this.publicPWD = publicPWD;
	}

	@Column(name = "FULL_NAME", length = 50)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "PROVINCE", length = 50)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "CITY", length = 50)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ADDR", length = 50)
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name = "LINK_NAME", length = 50)
	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Column(name = "ID_CARD", length = 50)
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "ACC_NAME", length = 50)
	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	@Column(name = "ACC_NO", length = 50)
	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	@Column(name = "BANK_NAME", length = 50)
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Column(name = "HANDHELD_ID", length = 255)
	public String getHandheldId() {
		return HandheldId;
	}

	public void setHandheldId(String handheldId) {
		HandheldId = handheldId;
	}

	@Column(name = "HANDHELD_BANK", length = 255)
	public String getHandheldBank() {
		return HandheldBank;
	}

	public void setHandheldBank(String handheldBank) {
		HandheldBank = handheldBank;
	}

	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", columnDefinition = "DATE")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}