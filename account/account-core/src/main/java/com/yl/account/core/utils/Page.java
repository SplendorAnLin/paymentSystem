package com.yl.account.core.utils;

import java.io.Serializable;

public class Page implements Serializable {
	private static final long serialVersionUID = -4221387216744433913L;
	private int showCount;	//显示总数
	private int totalPage;	//总页数
	private int totalResult;	//总条数
	private int currentPage;	//当前页数
	private int pageCode;	//当前查询编码

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

	public int getPageCode() {
		this.pageCode = (getCurrentPage() - 1) * this.showCount;
		return this.pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

}