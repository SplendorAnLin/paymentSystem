package com.yl.online.gateway.quartz.model;

/**
 * 常量增量
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
public class VectorConstant extends VectorScale {
	/** 向量-增量 */
	private int vector;

	/** 基础值 */
	private int baseLine;

	public int getVector() {
		return vector;
	}

	public void setVector(int vector) {
		this.vector = vector;
	}

	public int getBaseLine() {
		return baseLine;
	}

	public void setBaseLine(int baseLine) {
		this.baseLine = baseLine;
	}

	@Override
	public String toString() {
		return "VectorConstant [vector=" + vector + ", baseLine=" + baseLine + "]";
	}

}
