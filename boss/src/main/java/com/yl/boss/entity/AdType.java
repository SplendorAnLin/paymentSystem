package com.yl.boss.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 广告类型Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "ad_type")
public class AdType implements Serializable {
	private static final long serialVersionUID = -3642106671604459885L;

	private int id;
	private String englishName;
	private String chineseName;
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
	
	@Column(name = "englishName", length = 20)
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
	@Column(name = "chineseName", length = 20)
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	
	@Column(name = "status", length = 10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
