package com.yl.boss.enums;

/**
 * 操作员的登录状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public enum LoginStatus {

	 SUCCESS,					//登录成功
	 PASSWORD_EXP,				//密码到期
	 PASSWORD_ERROR,			//密码错误
	 USERNAME_INVALID,			//用户名无效
	 NON_LICENSED_TIME,			//非许可时间
	 LOCK,						//加锁
	 AGAINST_REPEAT_LOGIN;		//禁止重复登录
}
