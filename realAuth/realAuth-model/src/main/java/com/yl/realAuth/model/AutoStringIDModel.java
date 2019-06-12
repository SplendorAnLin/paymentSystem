package com.yl.realAuth.model;

import java.io.Serializable;
import java.util.Date;

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

}
