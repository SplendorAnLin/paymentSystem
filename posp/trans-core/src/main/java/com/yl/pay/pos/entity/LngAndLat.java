/**
 * 
 */
package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Title:基站信息
 * Description: 
 * Copyright: Copyright (c)2014 
 * Company: com.yl.pay
 * 
 * @author zhendong.wu
 */

@Entity
@Table(name = "LNG_AND_LAT")
public class LngAndLat extends BaseEntity {

	private String posCati;		//终端号
	private Double lng; 		//经度
	private Double lat; 		//维度
	private Date createTime; 	//创建时间
	
	private Boolean isSave=false;//是否保存
	
	
	/**
	 * @param lng
	 * @param lat
	 */
	public LngAndLat(double lng, double lat) {
		this.lng=lng;
		this.lat=lat;
	}
	

	public LngAndLat() {
		super();
	}

	@Column(name = "POS_CATI", length = 20)
	public String getPosCati() {
		return posCati;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	/**
	 * 经度
	 * @return
	 */
	@Column(name = "LNG")
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	/**
	 * 维度
	 * @return
	 */
	@Column(name = "LAT")
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Transient
	public Boolean getIsSave() {
		return isSave;
	}

	public void setIsSave(Boolean isSave) {
		this.isSave = isSave;
	}
	
	
}
