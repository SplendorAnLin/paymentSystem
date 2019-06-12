package com.yl.boss.entity;

import com.yl.boss.enums.Status;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 聚合地区码和银联地区码 对应
 *
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "YL_ORG_2_UNION_PAY_ORG")
public class YlOrg2UnionPayOrg extends AutoIDEntity {

	private static final long serialVersionUID = 1589602473902152534L;
	private String ylCode;
	private String unionPayCode;
	private Status status;

	@Column(name = "YL_CODE", length = 10)
	public String getYlCode() {
		return ylCode;
	}

	public void setYlCode(String ylCode) {
		this.ylCode = ylCode;
	}

	@Column(name = "UNION_PAY_CODE", length = 10)
	public String getUnionPayCode() {
		return unionPayCode;
	}

	public void setUnionPayCode(String unionPayCode) {
		this.unionPayCode = unionPayCode;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 10)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
