package com.yl.payinterface.core.bean;

import java.util.Map;

import com.lefu.hessian.bean.JsonBean;

/**
 * 资金通道网银消费响应结果Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class InternetbankSalesResponseBean implements JsonBean {

	private static final long serialVersionUID = -2308194332194320921L;

	/** 资金通道编码 */
	private String interfaceCode;
	/** 交易处理方式(同步\异步) */
	private boolean isSynchronize;
	/** 资金通道响应参数 */
	private Map<String, String> internetResponseMsg;

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public boolean isSynchronize() {
		return isSynchronize;
	}

	public void setSynchronize(boolean isSynchronize) {
		this.isSynchronize = isSynchronize;
	}

	public Map<String, String> getInternetResponseMsg() {
		return internetResponseMsg;
	}

	public void setInternetResponseMsg(Map<String, String> internetResponseMsg) {
		this.internetResponseMsg = internetResponseMsg;
	}

	@Override
	public String toString() {
		return "InternetbankSalesResponseBean [interfaceCode=" + interfaceCode + ", isSynchronize=" + isSynchronize + ", internetResponseMsg=" + internetResponseMsg
				+ "]";
	}

}
