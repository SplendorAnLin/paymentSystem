package com.yl.pay.pos.service;


public interface IIcParamsService {
	/**
	 * 根据终端更新IC参数
	 * @param posCati
	 * @return
	 */
	public String findParamsByPosCati(String posCati); 
	
	/**
	 * 根据AID查询IC参数
	 * @param aid
	 * @return
	 */
	public String findByAid(String aid);
	
	/**
	 * 查询IC参数是否需要更新
	 * @return
	 */
	public boolean queryUpdateFlag(String posCati, String updateType);
}
