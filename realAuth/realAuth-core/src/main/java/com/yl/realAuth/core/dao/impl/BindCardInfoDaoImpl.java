package com.yl.realAuth.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.core.dao.BindCardInfoDao;
import com.yl.realAuth.core.dao.mapper.BindCardInfoMapper;
import com.yl.realAuth.model.BindCardInfo;

@Repository("bindCardInfoDao")
public class BindCardInfoDaoImpl implements BindCardInfoDao {
	@Resource
	private BindCardInfoMapper bindCardInfoMapper;

	public List<BindCardInfo> findAlldynamicBindCardInfoBean(Map<String, Object> params,Page page) {
		return bindCardInfoMapper.findAlldynamicBindCardInfoBean(params,page);
	}
}
