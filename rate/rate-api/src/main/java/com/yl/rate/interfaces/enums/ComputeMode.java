package com.yl.rate.interfaces.enums;

/**
 * 聚合支付有限公司
 *
 * @author 邱健
 * @version V1.0.0
 * @create 2017年11月30
 * @desc 计算方式
 **/
public enum ComputeMode {

	ROUND("四舍五入"), ROUND_UP("全进"), ROUND_DOWN("全舍"),FIXED("单笔");
	private final String message;

	ComputeMode(String message) {
		this.message = message;
	}

}
