package com.yl.realAuth.core.service;

import com.yl.realAuth.hessian.bean.ChannelInfoBean;
import com.yl.realAuth.model.ChannelInfo;

public interface ChannelInfoService {

	/**
	 * 根据渠道编号查询渠道信息
	 * @param channelCode
	 * @return
	 */
	public ChannelInfo findByChannelCode(String channelCode);

	/**
	 * 根据渠道编号和支持银行查询渠道信息
	 * @param channelCode
	 * @param cardType
	 * @param providerCode
	 * @return
	 */
	// public ChannelInfo findByChannelCodeAndSupportCardTypeInfo(String channelCode, String cardType, String providerCode);

	/**
	 * 添加渠道信息
	 * @param channelInfoBean
	 */
	public void addChannelInfo(ChannelInfoBean channelInfoBean);

	/**
	 * 修改渠道信息
	 * @param channelInfoBean
	 */
	public void modifyChannelInfo(ChannelInfoBean channelInfoBean) ;
}
