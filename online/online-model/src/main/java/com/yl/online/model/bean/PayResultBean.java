package com.yl.online.model.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * 支付结果Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class PayResultBean implements Serializable {

	private static final long serialVersionUID = -5936080419130429265L;
	private String type;
	private String url;
	private Map<String, String> params;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}
