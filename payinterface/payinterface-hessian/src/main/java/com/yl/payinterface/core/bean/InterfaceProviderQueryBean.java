package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口提供方
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class InterfaceProviderQueryBean implements Serializable {

	private static final long serialVersionUID = -1646748116499825649L;
	/** 主键 */
	private String id;
	/** 编号 */
	private String code;
	/** 版本号 */
	private Long version;
	/** 创建时间 */
	private Date createTime;
	/** 全称 */
	private String fullName;
	/** 简称 */
	private String shortName;
	/** 最后修改时间 */
	private Date lastModifyTime;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
