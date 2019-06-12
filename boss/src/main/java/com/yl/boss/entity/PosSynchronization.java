package com.yl.boss.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yl.boss.enums.Status;

@Entity
@Table(name = "pos_synchronization")
public class PosSynchronization implements Serializable {

	private static final long serialVersionUID = -4924471878350263932L;
	
	private int id;
	
	private String customerNo;
	
	private String posCati;
	
	private Status status;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Column(name = "POS_CATI", length = 30)
	public String getPosCati() {
		return posCati;
	}

	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(10)")
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	

}
