package com.yl.cachecenter.model;

import java.io.Serializable;

/**
 * 乐富地区码匹配银联地区码
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class LefuCodeToUnionPayCode implements Serializable{

	private static final long serialVersionUID = 8092454459336004785L;
	
	/** 乐富地区码 */
	private String lefuCode;
	/** 银联地区码 */
	private String unionPayCode;
	
	public String getLefuCode() {
		return lefuCode;
	}
	public void setLefuCode(String lefuCode) {
		this.lefuCode = lefuCode;
	}
	public String getUnionPayCode() {
		return unionPayCode;
	}
	public void setUnionPayCode(String unionPayCode) {
		this.unionPayCode = unionPayCode;
	}
	
}
