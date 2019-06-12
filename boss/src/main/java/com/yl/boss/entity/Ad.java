package com.yl.boss.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 广告Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
@Entity
@Table(name = "ad")
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
	 * 所属OEM
	 */
	private String oem;
	
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
	 * 广告修改时间
	 */
	private Timestamp updateTime;
	/**
	 * 广告状态
	 */
	private String status;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name = "adType", length = 10)
	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "imageUrl", length = 100)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(name = "imageClickUrl", length = 100)
	public String getImageClickUrl() {
		return imageClickUrl;
	}

	public void setImageClickUrl(String imageClickUrl) {
		this.imageClickUrl = imageClickUrl;
	}

	@Column(name = "createTime", columnDefinition = "DATETIME")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "updatetime", columnDefinition = "DATETIME")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "status", length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "OEM", length = 20)
	public String getOem() {
		return oem;
	}

	public void setOem(String oem) {
		this.oem = oem;
	}

	public Ad() {
		super();
	}

	public Ad(int id, int adCode, String adType, String name, String imageUrl, String imageClickUrl,
			Timestamp createTime,  String status,String oem) {
		super();
		this.id = id;
		this.adType = adType;
		this.name = name;
		this.imageUrl = imageUrl;
		this.oem=oem;
		this.imageClickUrl = imageClickUrl;
		this.createTime = createTime;
		this.status = status;
	}
}
