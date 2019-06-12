package com.yl.recon.api.core.bean;

import com.yl.utils.excel.ExcelField;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 *
 * @author AnLin
 * @since 2017/6/21
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -346935695810614182L;
	/**
	 * id
	 */
	private String code;
	/**
	 * 版本号
	 */
	private long version;
	/**
	 * 创建时间
	 */
	@ExcelField(name = "创建时间", column = "J", columnWidth = "20", formatDate = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
