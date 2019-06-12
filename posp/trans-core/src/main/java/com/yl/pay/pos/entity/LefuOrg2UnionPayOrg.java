package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 乐富地区码与银联地区码对应表
 * @author haitao.liu
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "LEFU_ORG_2_UNION_PAY_ORG")
public class LefuOrg2UnionPayOrg extends BaseEntity {

	private static final long serialVersionUID = 1589602473902152534L;
	private String lefuCode;
	private String unionPayCode;
	private Status status;

	@Column(name = "LEFU_CODE", length = 10)
	public String getLefuCode() {
		return lefuCode;
	}

	public void setLefuCode(String lefuCode) {
		this.lefuCode = lefuCode;
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
