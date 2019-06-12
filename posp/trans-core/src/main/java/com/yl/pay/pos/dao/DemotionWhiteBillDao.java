package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.DemotionWhiteBill;

public interface DemotionWhiteBillDao {
	/**
	 * 根据银行编号、卡标识号、卡长度，卡标识长度查询降级交易拦截白名单
	 * @param bankCode
	 * @param verifyCode
	 * @return
	 */
	DemotionWhiteBill findByCodeAndLength(String bankCode, String verifyCode, Integer panLength, Integer verifyLength);
}
