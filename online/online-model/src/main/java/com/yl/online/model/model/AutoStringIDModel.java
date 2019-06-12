package com.yl.online.model.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 自动字符串主键父类
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class AutoStringIDModel implements Serializable {

	private static final long serialVersionUID = -8650605248341509578L;
	/** 主键 */
	private String id;
	/** 编号 */
	private String code;
	/** 版本号 */
	private Long version;
	/** 创建时间 */
	private Date createTime;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
