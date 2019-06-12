package com.yl.boss.api.bean;
import java.util.Date;

/**
 * 商户证件信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class CustomerCertBean implements  java.io.Serializable{
	
	private static final long serialVersionUID = 2771344285558760839L;
	
	private Long id;
    private Integer optimistic;
	private String customerNo;				//商户编号
	private String busiLiceCert;			//企业营业执照|个人身份证正面
	private String taxRegCert;				//企业税务登记证|个人身份证反面
	private String organizationCert;		//组织机构证|个人银行卡正面
	private String openBankAccCert;			//银行开户许可证|个人银行卡反面
	private String idCard;					//企业法人身份证|个人手持身份证
	private Date validStartTime;//营业有效期开始时间
    private Date validEndTime;//营业有效期截止时间
    private String businessScope;//经营范围
    private String businessAddress;//经营地址
    private String enterpriseUrl;//企业网址
    private String icp;//icp备案号
    private String attachment;//补充材料
    private String consumerPhone;//客服电话
    private String legalPerson;//法人姓名
    private String enterpriseCode;//企业码
	private Date createTime;
	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	public String getBusiLiceCert() {
		return busiLiceCert;
	}
	public void setBusiLiceCert(String busiLiceCert) {
		this.busiLiceCert = busiLiceCert;
	}
	
	public String getTaxRegCert() {
		return taxRegCert;
	}
	public void setTaxRegCert(String taxRegCert) {
		this.taxRegCert = taxRegCert;
	}
	
	public String getOrganizationCert() {
		return organizationCert;
	}
	public void setOrganizationCert(String organizationCert) {
		this.organizationCert = organizationCert;
	}
	
	public String getOpenBankAccCert() {
		return openBankAccCert;
	}
	public void setOpenBankAccCert(String openBankAccCert) {
		this.openBankAccCert = openBankAccCert;
	}
	
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getOptimistic() {
		return optimistic;
	}
	public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}
	public Date getValidStartTime() {
		return validStartTime;
	}
	public Date getValidEndTime() {
		return validEndTime;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public String getBusinessAddress() {
		return businessAddress;
	}
	public String getEnterpriseUrl() {
		return enterpriseUrl;
	}
	public String getIcp() {
		return icp;
	}
	public String getAttachment() {
		return attachment;
	}
	public String getConsumerPhone() {
		return consumerPhone;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public String getEnterpriseCode() {
		return enterpriseCode;
	}
	public void setValidStartTime(Date validStartTime) {
		this.validStartTime = validStartTime;
	}
	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	public void setEnterpriseUrl(String enterpriseUrl) {
		this.enterpriseUrl = enterpriseUrl;
	}
	public void setIcp(String icp) {
		this.icp = icp;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public void setConsumerPhone(String consumerPhone) {
		this.consumerPhone = consumerPhone;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public void setEnterpriseCode(String enterpriseCode) {
		this.enterpriseCode = enterpriseCode;
	}
	
}
