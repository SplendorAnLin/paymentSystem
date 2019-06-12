package com.yl.dpay.core.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.dpay.core.entity.QuickPayAutoRequest;

public interface QuickPayAutoRequestDao {

	public void insert(QuickPayAutoRequest quickPayAutoRequest);
	public QuickPayAutoRequest query(@Param("requestNo")String requestNo);
}
