package com.yl.dpay.core.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.dpay.core.Utils.CodeBuilder;
import com.yl.dpay.core.dao.QuickPayAutoRequestDao;
import com.yl.dpay.core.entity.QuickPayAutoRequest;
import com.yl.dpay.core.mybatis.mapper.QuickPayAutoRequestMapper;


@Repository("quickPayAutoRequestDao")
public class QuickPayAutoRequestDaoImpl implements QuickPayAutoRequestDao {

	@Resource
	private QuickPayAutoRequestMapper quickPayAutoRequestMapper;

	@Override
	public void insert(QuickPayAutoRequest quickPayAutoRequest) {
		quickPayAutoRequest.setCode(CodeBuilder.build("QPAR", "yyyyMMdd", 6));
		quickPayAutoRequest.setCreateDate(new Date());
		quickPayAutoRequest.setOptimistic(0);
		quickPayAutoRequestMapper.insert(quickPayAutoRequest);
	}
	
	public QuickPayAutoRequest query(String requestNo){
		return quickPayAutoRequestMapper.query(requestNo);
	}

}
