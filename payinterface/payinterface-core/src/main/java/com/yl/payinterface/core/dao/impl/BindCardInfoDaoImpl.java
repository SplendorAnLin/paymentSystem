package com.yl.payinterface.core.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.payinterface.core.dao.BindCardInfoDao;
import com.yl.payinterface.core.dao.mapper.BindCardInfoMapper;
import com.yl.payinterface.core.model.BindCardInfo;
import com.yl.payinterface.core.utils.CodeBuilder;

@Repository("bindCardInfoDao")
public class BindCardInfoDaoImpl implements BindCardInfoDao {

	@Resource
	private BindCardInfoMapper bindCardInfoMapper;

	@Override
	public void updateFailed(String cardNo, String interfaceInfoCode) {
		bindCardInfoMapper.updateFailed(cardNo, interfaceInfoCode);
	}

	@Override
	public void save(BindCardInfo bindCardInfo) {
		bindCardInfo.setCode(CodeBuilder.build("APBCI", "yyyyMMdd", 6));
		bindCardInfo.setCreateTime(new Date());
		bindCardInfo.setVersion(System.currentTimeMillis());
		bindCardInfoMapper.save(bindCardInfo);
	}

	@Override
	public void update(BindCardInfo bindCardInfo) {
		bindCardInfoMapper.update(bindCardInfo);
	}

	@Override
	public BindCardInfo find(String cardNo, String interfaceInfoCode) {
		return bindCardInfoMapper.find(cardNo, interfaceInfoCode);
	}

	@Override
	public BindCardInfo findEffective(String cardNo, String interfaceInfoCode) {
		return bindCardInfoMapper.findEffective(cardNo, interfaceInfoCode);
	}

}
