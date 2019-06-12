package com.yl.boss.service;

import com.yl.boss.entity.License;

/**
 * QRCode业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface QRCodeService {
	
	void sweepTheNetwork(License license);
	
	License getInfo(int i);
	
	void updateNetwork(License license);
}