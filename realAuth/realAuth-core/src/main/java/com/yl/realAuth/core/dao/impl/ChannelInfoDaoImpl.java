package com.yl.realAuth.core.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.realAuth.core.dao.ChannelInfoDao;
import com.yl.realAuth.core.dao.mapper.ChannelInfoMapper;
import com.yl.realAuth.hessian.bean.ChannelInfoBean;
import com.yl.realAuth.model.ChannelInfo;

@Repository("channelInfoDao")
public class ChannelInfoDaoImpl implements ChannelInfoDao{

	@Resource
	private ChannelInfoMapper channelInfoMapper;

	@Override
	public ChannelInfo findByChannelCode(String channelCode) {
		return channelInfoMapper.findByChannelCode(channelCode);
	}

	@Override
	public void insert(ChannelInfo channelInfo) {
		channelInfoMapper.insert(channelInfo);
	}

	/*@Override
	public ChannelInfo findByChannelCodeAndSupportCardTypeInfo(String channelCode, String cardType, String providerCode) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public void modifyChannelInfo(ChannelInfoBean channelInfoBean) {
		channelInfoMapper.modifyChannelInfo(channelInfoBean);
	}
	
	
}
