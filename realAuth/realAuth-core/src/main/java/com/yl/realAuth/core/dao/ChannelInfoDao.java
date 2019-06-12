package com.yl.realAuth.core.dao;

import com.yl.realAuth.hessian.bean.ChannelInfoBean;
import com.yl.realAuth.model.ChannelInfo;

public interface ChannelInfoDao {

	/**
	 * 根据渠道编号查询渠道信息
	 * @param channelCode
	 * @author Shark
	 * @return
	 */
	public ChannelInfo findByChannelCode(String channelCode);

	/**
	 * 保存
	 * @param channelInfo
	 */
	public void insert(ChannelInfo channelInfo);

	/**
	 * 根据渠道编号和支持银行查询渠道信息
	 * @param channelCode
	 * @param cardType
	 * @param providerCode
	 * @author Shark
	 * @return
	 */
	// public ChannelInfo findByChannelCodeAndSupportCardTypeInfo(String channelCode, String cardType, String providerCode);

	/**
	 * 修改渠道信息
	 * @param channelInfoBean
	 */
	public void modifyChannelInfo(ChannelInfoBean channelInfoBean) ;
}
