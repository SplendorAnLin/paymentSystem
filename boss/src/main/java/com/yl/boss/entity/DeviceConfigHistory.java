package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 设备配置历史Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name="device_config_history")
public class DeviceConfigHistory extends AutoIDEntity{
		private String operator;
		private Date updateTime;
		private String updateKey;
		@Column(name="operator")
		public String getOperator() {
			return operator;
		}
		public void setOperator(String operator) {
			this.operator = operator;
		}
		@Column(name="update_time")
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		@Column(name="update_key")
		public String getUpdateKey() {
			return updateKey;
		}
		public void setUpdateKey(String updateKey) {
			this.updateKey = updateKey;
		}

}
