package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 接口合作信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class CooperationInfo implements Serializable {

	private static final long serialVersionUID = -4291860350399676181L;
	/** 合同编号 */
	private String contractCode;
	/** 技术联系人信息 */
	private Contact[] technicalContact;
	/** 商务联系人信息 */
	private Contact[] businessContacts;
	/** 财务联系人信息 */
	private Contact[] financeContact;

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Contact[] getTechnicalContact() {
		return technicalContact;
	}

	public void setTechnicalContact(Contact[] technicalContact) {
		this.technicalContact = technicalContact;
	}

	public Contact[] getBusinessContacts() {
		return businessContacts;
	}

	public void setBusinessContacts(Contact[] businessContacts) {
		this.businessContacts = businessContacts;
	}

	public Contact[] getFinanceContact() {
		return financeContact;
	}

	public void setFinanceContact(Contact[] financeContact) {
		this.financeContact = financeContact;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterfaceCooperationInfo [contractCode=");
		builder.append(contractCode);
		builder.append(", technicalContact=");
		builder.append(Arrays.toString(technicalContact));
		builder.append(", businessContacts=");
		builder.append(Arrays.toString(businessContacts));
		builder.append(", financeContact=");
		builder.append(Arrays.toString(financeContact));
		builder.append("]");
		return builder.toString();
	}

}
