package com.yl.pay.pos.api.bean;

public class BaseEntity implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
    private Integer optimistic;
    
    public Long getId() {
      return this.id;
    }
    
    public Integer getOptimistic() {
      return this.optimistic;
    }

    public void setId(Long id) {
		this.id = id;
	}

    public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}
}
