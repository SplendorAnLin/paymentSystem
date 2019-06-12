package com.yl.boss.api.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 广告Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class Ad implements Serializable {
	
	private static final long serialVersionUID = -7540531238414469601L;

	/**
	 * 编号
	 */
	private int id;
	
	/**
	 * 广告类型
	 */
	private String adType;
	
	/**
	 * 广告名称
	 */
	private String name;
	
	/**
	 * 广告图片地址
	 */
	private String imageUrl;
	
	/**
	 * 广告链接地址
	 */
	private String imageClickUrl;
	
	/**
	 * 广告创建时间，默认为系统当前时间
	 */
	private Timestamp createTime;
	/**
	 * oem
	 */
	private String oem;
	/**
	 * 广告修改时间
	 */
	private Timestamp updateTime;
	/**
	 * 广告状态
	 */
	private String status;

	
	public String getOem() {
		return oem;
	}

	public void setOem(String oem) {
		this.oem = oem;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageClickUrl() {
		return imageClickUrl;
	}

	public void setImageClickUrl(String imageClickUrl) {
		this.imageClickUrl = imageClickUrl;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Ad() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ad(int id, int adCode, String adType, String name, String imageUrl, String imageClickUrl,
			Timestamp createTime,  String status) {
		super();
		this.id = id;
		this.adType = adType;
		this.name = name;
		this.imageUrl = imageUrl;
		this.imageClickUrl = imageClickUrl;
		this.createTime = createTime;
		this.status = status;
	}
}
