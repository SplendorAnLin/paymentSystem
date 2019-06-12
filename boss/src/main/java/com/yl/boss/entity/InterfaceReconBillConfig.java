package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.agent.api.enums.Status;

/**
 * 对账单配置信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "INTERFACE_RECON_BILL_CONFIG")
public class InterfaceReconBillConfig extends AutoIDEntity{
	
	private static final long serialVersionUID = -1110732723341046761L;
	
	private String interfaceName;//接口名称
	private String interfaceCode;//接口编号
	private String reconBillUrl;//对账单路径
	private	String generateTime;//生成账单时间
	private	Date createTime;//创建时间
	private	Status status;//配置状态
	private String filePrefix;//前缀
	
	@Column(name = "INTERFACE_NAME", length = 100)
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	@Column(name = "INTERFACE_CODE", length = 80)
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	
	@Column(name = "RECON_BILL_URL", length = 100)
	public String getReconBillUrl() {
		return reconBillUrl;
	}
	public void setReconBillUrl(String reconBillUrl) {
		this.reconBillUrl = reconBillUrl;
	}
	
	@Column(name = "GENERATE_TIME", length = 10)
	public String getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(String generateTime) {
		this.generateTime = generateTime;
	}
	
	@Column(name = "CREATE_TIME",  columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", length = 10)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(name = "FILE_PREFIX", length = 100)
	public String getFilePrefix() {
		return filePrefix;
	}
	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}
	
}
