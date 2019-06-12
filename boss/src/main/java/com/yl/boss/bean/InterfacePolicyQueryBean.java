package com.yl.boss.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;

/**
 * 接口策略查询Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class InterfacePolicyQueryBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -9142801286970469786L;

	/** 编号 */
	private String code;

	/** 创建时间 */
	private Date createTime;

	/** 策略名称 */
	private String name;

	/** 接口类型 */
	private String interfaceType;

	/** 接口策略配置 */
	private List<InterfacePolicyProfileBean> profiles;

	/** 接口策略状态 */
	private String status;
	
	/** 创建时间开始 */
	private Date createStartTime;
	/** 创建时间结束 */
	private Date createEndTime;
	
	/** 失效时间开始 */
	private Date expireStartTime;
	/** 失效时间结束 */
	private Date expireEndTime;
	
	/** 有效时间开始 */
	private Date effectStartTime;
	/** 有效时间结束 */
	private Date effectEndTime;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public List<InterfacePolicyProfileBean> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<InterfacePolicyProfileBean> profiles) {
		this.profiles = profiles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	public Date getExpireStartTime() {
		return expireStartTime;
	}

	public void setExpireStartTime(Date expireStartTime) {
		this.expireStartTime = expireStartTime;
	}

	public Date getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(Date expireEndTime) {
		this.expireEndTime = expireEndTime;
	}

	public Date getEffectStartTime() {
		return effectStartTime;
	}

	public void setEffectStartTime(Date effectStartTime) {
		this.effectStartTime = effectStartTime;
	}

	public Date getEffectEndTime() {
		return effectEndTime;
	}

	public void setEffectEndTime(Date effectEndTime) {
		this.effectEndTime = effectEndTime;
	}

	@Override
	public String toString() {
		return "InterfacePolicyQueryBean [code=" + code + ", createTime="
				+ createTime + ", name=" + name + ", interfaceType="
				+ interfaceType + ", profiles=" + profiles + ", status="
				+ status + ", createStartTime=" + createStartTime
				+ ", createEndTime=" + createEndTime + ", expireStartTime="
				+ expireStartTime + ", expireEndTime=" + expireEndTime
				+ ", effectStartTime=" + effectStartTime + ", effectEndTime="
				+ effectEndTime + "]";
	}

	

}
