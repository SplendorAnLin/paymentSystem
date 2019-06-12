package com.yl.realAuth.hessian;

import com.yl.realAuth.hessian.bean.ChannelInfoBean;

/**
 * 渠道信息
 *
 * @author Shark
 * @since 2016年3月11日
 */
public interface ChannelInfoHessianService {

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
