package com.yl.payinterface.core.hessian;

import com.yl.payinterface.core.bean.QuickPayOpenCardRequestBean;
import com.yl.payinterface.core.bean.QuickPayOpenCardResponseBean;

/**
 * @ClassName QuickPayOpenCardHessianService
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年11月20日 上午10:49:12
 */
public interface QuickPayOpenCardHessianService {

	/**
	 * 
	 * @Description 发送验证码
	 * @param params
	 * @return
	 * @date 2017年11月19日
	 */
	QuickPayOpenCardResponseBean sendOpenCardVerifyCode(QuickPayOpenCardRequestBean requestBean);

	/**
	 * 
	 * @Description 开通快捷支付
	 * @param params
	 * @return
	 * @date 2017年11月19日
	 */
	QuickPayOpenCardResponseBean openCardInfo(QuickPayOpenCardRequestBean requestBean);
}
