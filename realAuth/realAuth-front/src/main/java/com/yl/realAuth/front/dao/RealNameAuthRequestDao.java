package com.yl.realAuth.front.dao;

import com.yl.realAuth.model.RealNameAuthRequest;

/**
 * 合作方请求信息业务接口实现
 * @author congxiang.bai
 * @since 2015年6月3日
 */
public interface RealNameAuthRequestDao {
	/**
	 * 合作方请求信息记录新增
	 * @param reaNameAuthRequest 实名认证请求信息
	 */
	void insertAuthRequest(RealNameAuthRequest reaNameAuthRequest);
	/**
	 * 根据合作方请求流水号查询合作方请求流水
	 * @param requestCode 合作方请求流水号
	 * @return ReaNameAuthRequest
	 */
	RealNameAuthRequest queryPartnerRequestByRequestCode(String requestCode,String receiver);
	
}
