package com.yl.realAuth.front.service;

import com.yl.realAuth.hessian.bean.AuthResponseResult;
import com.yl.realAuth.hessian.exception.BusinessException;
import com.yl.realAuth.model.RealNameAuthOrder;
import com.yl.realAuth.model.RealNameAuthRequest;

/**
 * 实名认证请求处理服务
 * @author congxiang.bai
 * @since 2015年6月3日
 */
public interface RealNameAuthRequestService {
	/**
	 * 实名认证请求信息处理
	 * @param reaNameAuthRequest 请求实体
	 * @throws BusinessException
	 */
	public AuthResponseResult execute(RealNameAuthRequest reaNameAuthRequest) throws BusinessException;
	
	/**
	 * 根据合作方请求流水号查询合作方请求流水
	 * @param requestCode 合作方请求流水号
	 * @return ReaNameAuthRequest
	 */
	RealNameAuthRequest queryPartnerRequestByRequestCode(String requestCode,String receiver);
	
	/**
	 * 处理通知合作方结果
	 * @param reaNameAuthOrder  订单实体
	 * @param reaNameAuthRequest  订单请求实体
	 * @return
	 * @throws BusinessException
	 */
	String createResponse(RealNameAuthOrder reaNameAuthOrder, RealNameAuthRequest reaNameAuthRequest) throws BusinessException ;
	
}
