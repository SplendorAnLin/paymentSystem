package com.yl.customer.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 自动基础实体
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月8日
 * @version V1.0.0
 */
@MappedSuperclass

public class AutoIDEntity implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
    private Integer optimistic;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    public Long getId() {
      return this.id;
    }

    @Version
    @Column
    public Integer getOptimistic() {
      return this.optimistic;
    }

    public void setId(Long id) {
		this.id = id;
	}

    public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof AutoIDEntity))
			return false;
		AutoIDEntity castOther = (AutoIDEntity) other;
		return new org.apache.commons.lang3.builder.EqualsBuilder().append(id, castOther.getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new org.apache.commons.lang3.builder.HashCodeBuilder().append(id).toHashCode();
	}	
}
