package com.yl.boss.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yl.online.trade.hessian.bean.PartnerRouterProfileBean;

/**
 * 合作方路由查询Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class PartnerRouterQueryBean implements Serializable{
 	private static final long serialVersionUID = -4846277208817257840L;
	/** 主键 */
	private int id;
	/**  */
	private String code;
	/** 支付时间 */
	private String version;
	/** 创建时间 */
	private Date createTime;
	/** 合作方角色 */
	private String partnerRole;
	/** 合作方编码 */
	private String partnerCode;
	/** 合作方路由配置 */
	private List<PartnerRouterProfileBean> profiles;
	/** 路由状态 */
	private String status;
	/** 创建时间 */
	private Date createStartTime;
	/** 创建时间 */
	private Date createEndTime;
	
	
	public PartnerRouterQueryBean() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getPartnerRole() {
		return partnerRole;
	}


	public void setPartnerRole(String partnerRole) {
		this.partnerRole = partnerRole;
	}


	public String getPartnerCode() {
		return partnerCode;
	}


	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}


	

	public List<PartnerRouterProfileBean> getProfiles() {
		return profiles;
	}


	public void setProfiles(List<PartnerRouterProfileBean> profiles) {
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
	 
	
	
	
	
}
