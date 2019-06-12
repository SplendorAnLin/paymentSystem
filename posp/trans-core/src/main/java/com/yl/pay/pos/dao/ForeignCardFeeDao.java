package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.ForeignCardFee;
import com.yl.pay.pos.enums.Status;

/**
 * 境外卡手续费
 * @author haitao.liu
 *
 */
public interface ForeignCardFeeDao {

	/**
	 * 根据通道所属行业类别和状态查询
	 * @param mccCategory
	 * @param status
	 * @return
	 */
	public ForeignCardFee findByMccCateGoryAndStatus(String mccCategory, Status status);
}
