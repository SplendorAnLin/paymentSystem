package com.yl.pay.pos.service;

import com.yl.pay.pos.bean.BankChannelFeeReturnBean;
import com.yl.pay.pos.enums.Status;

/**
 * 境外卡手续费service
 * @author haitao.liu
 *
 */
public interface IForeignCardFeeService {
	/**
	 * 根据通道所属行业类别计算境外卡
	 * @param mccCategory
	 * @param status
	 * @return
	 */
	public BankChannelFeeReturnBean findByMccCateGoryAndStatus(double transAmount, String mccCategory, Status status);
}
