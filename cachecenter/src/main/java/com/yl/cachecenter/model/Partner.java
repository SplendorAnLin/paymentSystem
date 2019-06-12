package com.yl.cachecenter.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 合伙人
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Partner {
	@NotBlank
	private String code;
	@NotBlank
	private String name;
	private String nickname;

	public String getCode() {
		return code;
	}

	public void setCode(String partner) {
		this.code = partner;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
			
		this.nickname = nickname;
	}

	public String getName() {
		return name;
	}

	public void setFullName(String fullName) {
		this.name = fullName;
	}

}
