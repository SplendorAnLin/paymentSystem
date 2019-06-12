package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.YesNo;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 银行mcc分类配置（乐富类商户专用）
 * Description: 
 * Company: com.yl.pay
 * @author haitao.liu
 */
@Entity
@Table(name = "Bank_Mcc_Class_Config")
public class BankMccClassConfig extends BaseEntity{

	private static final long serialVersionUID = -6547649814890224691L;
	
	private String bankInterfaceCode; 		//银行接口编号
	
	private String bankMcc;					//银行MCC
	
	private String bigBankMcc;				//大银行MCC（细分银联标准，与银行MCC关系为一对多）
	
	private String bigBankMccName;			//大银行mcc描述
	
	private String bankMccCategory;			//银行MCC行业类别
	
	private Status status;					//状态
	
	private YesNo isFlag;					//标识没有对应的MCC
	
	private Date createTime;				//创建时间
	
	

	@Column(name = "BANK_INTERFACE_CODE", length = 50)
	public String getBankInterfaceCode() {
		return bankInterfaceCode;
	}

	public void setBankInterfaceCode(String bankInterfaceCode) {
		this.bankInterfaceCode = bankInterfaceCode;
	}

	@Column(name = "BANK_MCC", length = 50)
	public String getBankMcc() {
		return bankMcc;
	}

	public void setBankMcc(String bankMcc) {
		this.bankMcc = bankMcc;
	}

	@Column(name = "BIG_BANK_MCC", length = 100)
	public String getBigBankMcc() {
		return bigBankMcc;
	}

	public void setBigBankMcc(String bigBankMcc) {
		this.bigBankMcc = bigBankMcc;
	}
	@Column(name = "BIG_BANK_MCC_NAME", length = 100)
	public String getBigBankMccName() {
		return bigBankMccName;
	}

	public void setBigBankMccName(String bigBankMccName) {
		this.bigBankMccName = bigBankMccName;
	}
	@Column(name = "BANK_MCC_CATEGORY", length = 100)
	public String getBankMccCategory() {
		return bankMccCategory;
	}
	
	public void setBankMccCategory(String bankMccCategory) {
		this.bankMccCategory = bankMccCategory;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(50)")
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(name = "CREATE_TIME",columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "IS_FLAG", columnDefinition = "VARCHAR(50)")
	public YesNo getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(YesNo isFlag) {
		this.isFlag = isFlag;
	}
	
	
	

}
