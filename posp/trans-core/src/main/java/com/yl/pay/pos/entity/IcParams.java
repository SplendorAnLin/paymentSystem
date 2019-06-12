package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * IC卡参数下载
 * @author haitao.liu
 *
 */
@Entity
@Table(name = "IC_PARAMS")
public class IcParams extends  BaseEntity{

	private static final long serialVersionUID = 1L;

	private String aid;								//aid
	
	private String asi;								//应用选择指示符
	
	private String appVersion;						//应用版本号
	
	private String tacDefault;						//TAC－缺省
	
	private String tacOnline;						//TAC－联机
	
	private String tacRefuse;						//TAC－拒绝
		
	private String minAmount;						//终端最低限额
	
	private String randomThreshold;					//偏置随机选择的阈值
	
	private String maxRandomPercentage;				//偏置随机选择的最大目标百分数
	
	private String randomPercentage;				//随机选择的目标百分数
	
	private String ddol;							//缺省DDO
	
	private String pinOnlineAbility;				//终端联机PIN支持
	
	private String electronicCashMinAmount;			//终端电子现金交易限额
	
	private String noncontactOfflineMinAmount;		//非接触读写器脱机最低限额
	
	private String noncontactMinAmount;				//非接触读写器交易限额
	
	private String cvmLimit;						//读写器持卡人验证方法
	
	private Date createTime;						//创建时间	
	
	private Integer seqIndex;						//索引号

	/**
	 * 
	 * @return
	 */
	@Column(name = "AID", length = 40)
	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
	@Column(name = "ASI", length = 30)
	public String getAsi() {
		return asi;
	}

	public void setAsi(String asi) {
		this.asi = asi;
	}
	@Column(name = "APP_VERSION", length = 30)
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	@Column(name = "TAC_DEFAULT", length = 30)
	public String getTacDefault() {
		return tacDefault;
	}

	public void setTacDefault(String tacDefault) {
		this.tacDefault = tacDefault;
	}
	@Column(name = "TAC_ONLINE", length = 30)
	public String getTacOnline() {
		return tacOnline;
	}

	public void setTacOnline(String tacOnline) {
		this.tacOnline = tacOnline;
	}
	@Column(name = "TAC_REFUSE", length = 30)
	public String getTacRefuse() {
		return tacRefuse;
	}

	public void setTacRefuse(String tacRefuse) {
		this.tacRefuse = tacRefuse;
	}
	@Column(name = "MIN_AMOUNT", length = 30)
	public String getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(String minAmount) {
		this.minAmount = minAmount;
	}
	@Column(name = "RANDOM_THRESHOLD", length = 30)
	public String getRandomThreshold() {
		return randomThreshold;
	}

	public void setRandomThreshold(String randomThreshold) {
		this.randomThreshold = randomThreshold;
	}
	@Column(name = "MAX_RANDOM_PERCENTAGE", length = 30)
	public String getMaxRandomPercentage() {
		return maxRandomPercentage;
	}

	public void setMaxRandomPercentage(String maxRandomPercentage) {
		this.maxRandomPercentage = maxRandomPercentage;
	}
	@Column(name = "RANDOM_PERCENTAGE", length = 30)
	public String getRandomPercentage() {
		return randomPercentage;
	}

	public void setRandomPercentage(String randomPercentage) {
		this.randomPercentage = randomPercentage;
	}
	@Column(name = "DDOL", length = 30)
	public String getDdol() {
		return ddol;
	}

	public void setDdol(String ddol) {
		this.ddol = ddol;
	}
	@Column(name = "PIN_ONLINE_ABILITY", length = 30)
	public String getPinOnlineAbility() {
		return pinOnlineAbility;
	}

	public void setPinOnlineAbility(String pinOnlineAbility) {
		this.pinOnlineAbility = pinOnlineAbility;
	}
	@Column(name = "ELECTRONIC_CASH_MINAMOUNT", length = 30)
	public String getElectronicCashMinAmount() {
		return electronicCashMinAmount;
	}

	public void setElectronicCashMinAmount(String electronicCashMinAmount) {
		this.electronicCashMinAmount = electronicCashMinAmount;
	}
	@Column(name = "NONCONTACT_OFFLINE_MIN_AMOUNT", length = 30)
	public String getNoncontactOfflineMinAmount() {
		return noncontactOfflineMinAmount;
	}

	public void setNoncontactOfflineMinAmount(String noncontactOfflineMinAmount) {
		this.noncontactOfflineMinAmount = noncontactOfflineMinAmount;
	}
	@Column(name = "NONCONTACT_MIN_AMOUNT", length = 30)
	public String getNoncontactMinAmount() {
		return noncontactMinAmount;
	}

	public void setNoncontactMinAmount(String noncontactMinAmount) {
		this.noncontactMinAmount = noncontactMinAmount;
	}
	@Column(name = "CVM_LIMIT", length = 30)
	public String getCvmLimit() {
		return cvmLimit;
	}

	public void setCvmLimit(String cvmLimit) {
		this.cvmLimit = cvmLimit;
	}
	@Column(name = "CREATE_TIME", length =30,columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "seq_Index", length = 30)
	public Integer getSeqIndex() {
		return seqIndex;
	}

	public void setSeqIndex(Integer seqIndex) {
		this.seqIndex = seqIndex;
	}
	
	
	
	
	
}
