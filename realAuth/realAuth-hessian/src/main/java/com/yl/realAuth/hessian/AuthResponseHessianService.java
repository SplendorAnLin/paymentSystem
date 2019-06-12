package com.yl.realAuth.hessian;

import com.yl.realAuth.hessian.bean.AuthResponseBean;
import com.yl.realAuth.hessian.exception.BusinessException;

/**
 * 实名认证支付接口回调业务系统处理
 * @author Shark
 * @since 2015年6月3日
 */
public interface AuthResponseHessianService {
	/**
	 * 实名认证支付接口回调
	 * @param authResponseBean 实名认证支付接口回调Bean
	 * @throws BusinessException
	 */
	public void callBack(AuthResponseBean authResponseBean) throws BusinessException;
}
