package com.yl.boss.utils;

import java.io.Serializable;

/**
 * 分页工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class Page implements Serializable {
	private static final long serialVersionUID = -4221387216744433913L;
	private int showCount;
	private int totalPage;
	private int totalResult;
	private int currentPage;

	public Page() {
		this.showCount = 6;
	}

	public int getTotalPage() {
		if (this.totalResult % this.showCount == 0)
			this.totalPage = (this.totalResult / this.showCount);
		else
			this.totalPage = (this.totalResult / this.showCount + 1);
		return this.totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalResult() {
		return this.totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getCurrentPage() {
		if (this.currentPage <= 0)
			this.currentPage = 1;
		if (this.currentPage > getTotalPage())
			this.currentPage = getTotalPage();
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getShowCount() {
		return this.showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

}