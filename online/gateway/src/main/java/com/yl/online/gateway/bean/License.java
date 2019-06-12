package com.yl.online.gateway.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 扫码入网
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月4日
 * @version V1.0.0
 */
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
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPublicPWD() {
		return publicPWD;
	}

	public void setPublicPWD(String publicPWD) {
		this.publicPWD = publicPWD;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public License() {
		super();
	}

	public String getHandheldId() {
		return HandheldId;
	}

	public void setHandheldId(String handheldId) {
		HandheldId = handheldId;
	}

	public String getHandheldBank() {
		return HandheldBank;
	}

	public void setHandheldBank(String handheldBank) {
		HandheldBank = handheldBank;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public License(int id, String cardNo, String checkCode, String phone, String passWord, String publicPWD,
			String fullName, String province, String city, String addr, String linkName, String idCard, String accName,
			String accNo, String bankName, String handheldId, String handheldBank, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.cardNo = cardNo;
		this.checkCode = checkCode;
		this.phone = phone;
		this.passWord = passWord;
		this.publicPWD = publicPWD;
		this.fullName = fullName;
		this.province = province;
		this.city = city;
		this.addr = addr;
		this.linkName = linkName;
		this.idCard = idCard;
		this.accName = accName;
		this.accNo = accNo;
		this.bankName = bankName;
		HandheldId = handheldId;
		HandheldBank = handheldBank;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
}