package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Title: 银行接口
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK_INTERFACE")
public class BankInterface implements Serializable{

	private String code;			//接口编码
	private Integer optimistic;		//版本标识
	private String name;			//接口名称
	private Bank bank;				//所属银行
	private Date createTime;		//创建时间
	private Integer scale;			//权重/优先级
	private Status status;			//状态
	private Integer averageRespTime;//平均响应时间（毫秒）
	
	@Id
	@Column(name = "CODE", length = 20)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
    @Version
    @Column(columnDefinition = "INT", length = 11)
    public Integer getOptimistic() {
      return this.optimistic;
    }

    public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}
	
	@Column(name = "NAME", length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "BANK", columnDefinition = "VARCHAR(30)")
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "SCALE", columnDefinition = "int", length = 11)
	public Integer getScale() {
		return scale;
	}
	public void setScale(Integer scale) {
		this.scale = scale;
	}
	
	@Column(name = "AVERAGE_RESP_TIME", columnDefinition = "int", length = 11)
	public Integer getAverageRespTime() {
		return averageRespTime;
	}
	public void setAverageRespTime(Integer averageRespTime) {
		this.averageRespTime = averageRespTime;
	}	
}


