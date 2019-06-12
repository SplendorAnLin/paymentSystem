package com.yl.client.front.service;

import java.util.List;
import java.util.Map;

public interface FeeInfoService {
	/**
	 * 获取商户的全部信息
	 * @param customerNo
	 * @return
	 */
	public List<Map<String, Object>> getCustomerAllFee(String customerNo);
	/**
	 * 收入费率
	 * @param customerNo
	 * @return
	 */
	public List<Map<String, Object>> getCustomerFeeIn(String customerNo);

	/**
	 * 产品信息
	 * @param customerNo
	 * @param payFee
	 * @return
	 */
	public List<Map<String, Object>> getCustomerFee(String customerNo, List<Map<String, Object>> payFee);
	/**
	 * 支出费率
	 * @param customerNo
	 * @return
	 */
	public List<Map<String, Object>> getCustomerFeeOut(String customerNo);
}
