package com.yl.boss.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 设备配置Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name="DEVICE_CONFIG")
public class DeviceConfig extends AutoIDEntity{
	private static final long serialVersionUID = -4524499744674868130L;
	private String configKey;
	
	@Column(name="CONFIG_KEY")
	public String getConfigKey() {
		return configKey;
	}
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	




	
}
