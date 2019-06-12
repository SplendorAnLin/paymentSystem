package com.yl.payinterface.core.handle.impl.remit.ylzf420101.bean;

import java.io.Serializable;

/**
 * @ClassName YLZF420101QueryBean
 * @Description 聚合支付查询Bean
 * @author Administrator
 * @date 2017年11月27日 下午3:33:43
 */
public class YLZF420101QueryBean implements Serializable {
	private static final long serialVersionUID = 3071734576542195820L;

	private String merid;

	private String orderid;

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@Override
	public String toString() {
		return "YLZF420101QueryBean [merid=" + merid + ", orderid=" + orderid + "]";
	}

}
