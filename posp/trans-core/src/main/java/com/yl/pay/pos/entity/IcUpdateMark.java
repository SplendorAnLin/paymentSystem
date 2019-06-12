package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.YesNo;

import javax.persistence.*;
import java.util.Date;

/**
 * IC卡更新参数或公钥标志
 * @author haitao.liu
 *
 */
@Entity
@Table(name = "Ic_Update_Mark")
public class IcUpdateMark extends BaseEntity{

	private String posCati;						//终端号
	
	private String updateType;					//更新类别
	
	private Integer updateIndex;				//更新标识位
	
	private Integer totalNumber;				//参数或公钥总数
	
	private YesNo isRepeat;						//是否重复
	
	private Date lastUpdateParamsTime;//最后更新参数时间
	
	private Date lastUpdateKeyTime;//最后更新参数时间

	@Column(name = "pos_Cati", length = 30)
	public String getPosCati() {
		return posCati;
	}

	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}

	@Column(name = "update_Type", length = 30)
	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	@Column(name = "update_Index", length = 30)
	public Integer getUpdateIndex() {
		return updateIndex;
	}

	public void setUpdateIndex(Integer updateIndex) {
		this.updateIndex = updateIndex;
	}
	@Column(name = "total_Number", length = 30)
	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "is_Repeat", columnDefinition = "VARCHAR(20)")
	public YesNo getIsRepeat() {
		return isRepeat;
	}

	public void setIsRepeat(YesNo isRepeat) {
		this.isRepeat = isRepeat;
	}
	@Column(name = "last_Update_Params_Time", columnDefinition = "DATETIME")
	public Date getLastUpdateParamsTime() {
		return lastUpdateParamsTime;
	}

	public void setLastUpdateParamsTime(Date lastUpdateParamsTime) {
		this.lastUpdateParamsTime = lastUpdateParamsTime;
	}
	@Column(name = "last_Update_Key_Time", columnDefinition = "DATETIME")
	public Date getLastUpdateKeyTime() {
		return lastUpdateKeyTime;
	}

	public void setLastUpdateKeyTime(Date lastUpdateKeyTime) {
		this.lastUpdateKeyTime = lastUpdateKeyTime;
	}
	
	
}
