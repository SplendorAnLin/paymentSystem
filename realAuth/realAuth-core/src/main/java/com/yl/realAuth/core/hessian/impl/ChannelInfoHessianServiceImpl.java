package com.yl.realAuth.core.hessian.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.realAuth.core.service.ChannelInfoService;
import com.yl.realAuth.hessian.ChannelInfoHessianService;
import com.yl.realAuth.hessian.bean.ChannelInfoBean;

/**
 * 渠道信息
 * @author boyang.sun
 * @since 2016年3月11日
 */
@Service("channelInfoHessianService")
public class ChannelInfoHessianServiceImpl implements ChannelInfoHessianService {

	@Resource
	private ChannelInfoService channelInfoService;

	@Override
	public void addChannelInfo(ChannelInfoBean channelInfoBean) {
		channelInfoService.addChannelInfo(channelInfoBean);

	}

	@Override
	public void modifyChannelInfo(ChannelInfoBean channelInfoBean) {
		channelInfoService.modifyChannelInfo(channelInfoBean);
	}

}
