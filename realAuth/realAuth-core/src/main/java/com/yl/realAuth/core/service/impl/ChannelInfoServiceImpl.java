package com.yl.realAuth.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.realAuth.core.dao.ChannelInfoDao;
import com.yl.realAuth.core.service.ChannelInfoService;
import com.yl.realAuth.enums.AuthBusiType;
import com.yl.realAuth.hessian.bean.ChannelInfoBean;
import com.yl.realAuth.model.ChannelInfo;

@Service("channelInfoService")
public class ChannelInfoServiceImpl implements ChannelInfoService {

	@Resource
	private ChannelInfoDao channelInfoDao;

	@Override
	public ChannelInfo findByChannelCode(String channelCode) {
		return channelInfoDao.findByChannelCode(channelCode);
	}

	// @Override
	// public ChannelInfo findByChannelCodeAndSupportCardTypeInfo(String
	// channelCode, String cardType, String providerCode) {
	// return null;
	// }

	@Override
	public void addChannelInfo(ChannelInfoBean channelInfoBean) {
		ChannelInfo channelInfo = new ChannelInfo();
		channelInfo.setChannelName(channelInfoBean.getChannelName());
		channelInfo.setChannelType(AuthBusiType.valueOf(channelInfoBean.getChannelType().name()));
		channelInfo.setInterfaceInfoCode(channelInfoBean.getInterfaceInfoCode());
		channelInfo.setStatus(channelInfoBean.getStatus());
		channelInfo.setSupportName(channelInfoBean.getSupportName());
		channelInfo.setMustName(channelInfoBean.getMustName());
		channelInfo.setSupportMobNo(channelInfoBean.getSupportMobNo());
		channelInfo.setMustMobNo(channelInfoBean.getMustMobNo());
		channelInfo.setSupportCertNo(channelInfoBean.getSupportCertNo());
		channelInfo.setMustCertNo(channelInfoBean.getMustCertNo());
		/*com.lefu.auth.trade.remote.hessian.bean.CardTypeInfo[] hessianCardTypeInfo = channelInfoBean.getSupportCardTypeInfos();
		CardTypeInfo[] cardTypeInfos = new CardTypeInfo[channelInfoBean.getSupportCardTypeInfos().length];
		for (int i = 0; i < cardTypeInfos.length; i++) {
			CardTypeInfo cardTypeInfo = new CardTypeInfo();
			cardTypeInfo.setCardType(CardType.valueOf(hessianCardTypeInfo[i].getCardType().name()));
			cardTypeInfo.setSupportProviders(hessianCardTypeInfo[i].getSupportProviders());
			cardTypeInfos[i] = cardTypeInfo;
		}
		channelInfo.setSupportCardTypeInfos(cardTypeInfos);*/
		channelInfoDao.insert(channelInfo);
	}

	@Override
	public void modifyChannelInfo(ChannelInfoBean channelInfoBean) {
		channelInfoDao.modifyChannelInfo(channelInfoBean);
	}

}
