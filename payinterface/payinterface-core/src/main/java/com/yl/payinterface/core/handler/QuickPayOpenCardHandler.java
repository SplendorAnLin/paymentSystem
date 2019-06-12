/**   
 * @Package com.yl.payinterface.core.handler 
 * @Description TODO(用一句话描述该文件做什么) 
 * @author Administrator   
 * @date 2017年11月18日 下午7:59:04 
 */
package com.yl.payinterface.core.handler;

import java.util.Map;

import com.yl.payinterface.core.bean.QuickPayOpenCardResponseBean;

/**
 * @ClassName QuickPayOpenCardHandler
 * @Description 开卡（聚合处理）类
 * @author 聚合支付
 * @date 2017年11月18日 下午7:59:04
 */
public interface QuickPayOpenCardHandler {

	/**
	 * 
	 * @Description 发送验证码
	 * @param params
	 * @return
	 * @date 2017年11月19日
	 */
	QuickPayOpenCardResponseBean sendOpenCardVerifyCode(Map<String, String> params);

	/**
	 * 
	 * @Description 开通快捷支付
	 * @param params
	 * @return
	 * @date 2017年11月19日
	 */
	QuickPayOpenCardResponseBean openCardInfo(Map<String, String> params);
}
