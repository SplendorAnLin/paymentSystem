package com.yl.boss.api.interfaces;

import com.yl.boss.api.bean.License;

/**
 * 扫码入网远程服务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月19日
 * @version V1.0.0
 */
public interface QRCodeInterface {
	boolean sweepTheNetwork(License license);
}