package com.yl.account.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 自动字符串主键父类
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AutoStringIDModel implements Serializable, Cloneable {

	private static final long serialVersionUID = 6552704069882326973L;

	/**
	 * id
	 */
	private Long id;
	/** 编号 */
	private String code;
	/** 版本号 */
	private Long version;
	/** 创建时间 */
	private Date createTime;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
