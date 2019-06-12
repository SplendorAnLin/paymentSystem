package com.yl.boss.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商户证件信息历史Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "CUSTOMER_CERT_HISTORY")
public class CustomerCertHistory extends AutoIDEntity{
	
	private static final long serialVersionUID = -3374131465600669380L;
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
	private String oper;
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Column(name = "BUSI_LICE_CERT", length = 100)
	public String getBusiLiceCert() {
		return busiLiceCert;
	}
	public void setBusiLiceCert(String busiLiceCert) {
		this.busiLiceCert = busiLiceCert;
	}
	
	@Column(name = "TAX_REG_CERT", length = 100)
	public String getTaxRegCert() {
		return taxRegCert;
	}
	public void setTaxRegCert(String taxRegCert) {
		this.taxRegCert = taxRegCert;
	}
	
	@Column(name = "ORGANIZATION_CERT", length = 100)
	public String getOrganizationCert() {
		return organizationCert;
	}
	public void setOrganizationCert(String organizationCert) {
		this.organizationCert = organizationCert;
	}
	
	@Column(name = "OPEN_BANK_ACC_CERT", length = 100)
	public String getOpenBankAccCert() {
		return openBankAccCert;
	}
	public void setOpenBankAccCert(String openBankAccCert) {
		this.openBankAccCert = openBankAccCert;
	}
	
	@Column(name = "ID_CARD", length = 100)
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

    @Column(name = "VALID_START_TIME",columnDefinition = "DATE")
    public Date getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(Date validStartTime) {
        this.validStartTime = validStartTime;
    }
    @Column(name = "VALID_END_TIME",columnDefinition = "DATE")
    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }
    @Column(name = "BUSINESS_SCOPE",length = 100)
    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }
    @Column(name = "BUSINESS_ADDRESS",length = 50)
    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }
    @Column(name = "ENTERPRISE_URL",length = 50)
    public String getEnterpriseUrl() {
        return enterpriseUrl;
    }

    public void setEnterpriseUrl(String enterpriseUrl) {
        this.enterpriseUrl = enterpriseUrl;
    }
    @Column(name = "ICP",length = 30)
    public String getIcp() {
        return icp;
    }

    public void setIcp(String icp) {
        this.icp = icp;
    }
    @Column(name = "ATTACHMENT",length = 200)
    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    @Column(name = "CONSUMER_PHONE",length = 15)
    public String getConsumerPhone() {
        return consumerPhone;
    }

    public void setConsumerPhone(String consumerPhone) {
        this.consumerPhone = consumerPhone;
    }
    @Column(name = "LEGAL_PERSON",length = 30)
    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }
    @Column(name = "ENTERPRISE_CODE",length = 40)
    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        this.enterpriseCode = enterpriseCode;
    }

	@Column(name = "OPER", length = 50)
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public CustomerCertHistory(CustomerCert customerCert, String oper) {
		super();
        this.attachment=customerCert.getAttachment();
        this.busiLiceCert=customerCert.getBusiLiceCert();
        this.businessAddress=customerCert.getBusinessAddress();
        this.businessScope=customerCert.getBusinessScope();
        this.enterpriseUrl=customerCert.getEnterpriseUrl();
        this.enterpriseCode=customerCert.getEnterpriseCode();
        this.consumerPhone=customerCert.getConsumerPhone();
        this.icp=customerCert.getIcp();
        this.validStartTime=customerCert.getValidStartTime();
        this.validEndTime=customerCert.getValidEndTime();
		this.customerNo = customerCert.getCustomerNo();
		this.busiLiceCert = customerCert.getBusiLiceCert();
		this.taxRegCert = customerCert.getTaxRegCert();
		this.organizationCert = customerCert.getOrganizationCert();
		this.openBankAccCert = customerCert.getOpenBankAccCert();
		this.idCard = customerCert.getIdCard();
		this.createTime = new Date();
		this.oper = oper;
	}
	
	public CustomerCertHistory() {
		super();
	}
	
}
