package com.yl.boss.dao;

import com.yl.boss.entity.License;

/**
 * QRCode数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface QRCodeDao {
	void sweepTheNetwork(License license);
	
	License getInfo(int Id);
	
	void updateNetwork(License license);
}