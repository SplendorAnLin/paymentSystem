package com.yl.pay.pos.api.interfaces;

import com.yl.pay.pos.api.bean.Customer;
import com.yl.pay.pos.api.bean.CustomerFee;
import com.yl.pay.pos.api.bean.MccInfo;
import com.yl.pay.pos.api.bean.Pos;
import com.yl.pay.pos.api.bean.SecretKey;
import com.yl.pay.pos.api.bean.Shop;

public interface InfoSync {
	/**
	 * 同步商户信息
	 * @param Customer
	 * @return
	 */
	boolean customerInfoSync(Customer Customer);
	/**
	 * 同步pos信息
	 * @param pos
	 * @return
	 */
	boolean posInfoSync(Pos pos);
	/**
	 * 同步网点信息
	 * @param shop
	 * @return
	 */
	boolean shopInfoSync(Shop shop);
	/**
	 * 同步密钥信息
	 * @param secretKey
	 * @return
	 */
	boolean secretKeySync(SecretKey secretKey);
	/**
	 * 同步Mcc信息
	 * @param mccInfo
	 * @return
	 */
	boolean mccInfoSync(MccInfo mccInfo);
	/**
	 * 同步商户费率信息
	 * @param customerFee
	 * @return
	 */
	boolean customerFeeSync(CustomerFee customerFee);
}
