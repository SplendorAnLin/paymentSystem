package com.yl.client.front.enums;

/**
 * app异常信息
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public enum AppExceptionEnum {
	
	//第一位数代表消息类别
	//1开头代表错误信息,7开头代表确认按钮的提示,8代表确认和取消按钮的提示
	//1开头代表错误信息,7开头代表确认按钮的提示,8代表确认和取消按钮的提示
	//1开头代表错误信息,7开头代表确认按钮的提示,8代表确认和取消按钮的提示
	//666666固定身份失效，999999固定系统异常，000000固定请求成功
	/**
	 * 登陆成功
	 */
	SUCCESS("000000", "请求成功"),
	/**
	 * 密码重置成功
	 */
	RESETSUCCESS("000001","新密码已发至手机,请注意查收"),
	/**
	 * 系统异常
	 */
	SYSERR("999999", "系统异常"),
	/**
	 * 认证信息失效
	 */
	AUTHERR("666666","身份认证失效,请重新登录"),
	/**
	 * 强制新版本检测
	 */
	UPAPP("888888","检测到新版本,请更新软件以获得更好体验！"),
	/**
	 * 账户余额查询失败
	 */
	ACCBLANCE("110001","账户余额查询失败"),
	/**
	 * 账户明细查询失败
	 */
	ACCOUNT("110002","账户明细查询失败"),
	/**
	 * 微信条码支付请求发起失败
	 */
	BARCODEPAY("110003","微信条码支付请求发起失败"),
	/**
	 * 提现复核发起失败
	 */
	GODRAWCASH("110004","提现复核发起失败"),
	/**
	 * 提现请求发起失败
	 */
	DRAWCASH("110005","提现请求发起失败"),
	/**
	 * 当日交易金额和笔数
	 */
	TODAY("110006","当日交易金额和笔数查询失败"),
	/**
	 * 充值请求失败
	 */
	APP_RECHARGE("110007","充值请求发起失败"),
	/**
	 * 登录失败
	 */
	LONGINERR("150001","登录失败,账号或密码错误"),
	/**
	 * 服务商费率获取失败
	 */
	AGENT_FEE_ERR("150002","服务商费率获取失败"),
	/**
	 * 商户费率获取失败
	 */
	CUSTOMER_FEE_ERR("150003","商户费率获取失败"),
	/**
	 * 修改密码请求发起失败
	 */
	UPDATEPASSWORD("150004","修改密码请求发起失败"),
	/**
	 * 交易发起失败
	 */
	PAY("160000", "交易发起失败"),
	/**
	 * 参数不存在
	 */
	DOESNOTEXIST("160001", "参数不存在"),
	/**
	 * 参数错误
	 */
	PARAMETERERROR("160002", "参数错误"),
	/**
	 * 签名错误
	 */
	SIGNATUREERROR("160003", "签名错误"),
	/**
	 * 支付方式错误
	 */
	PAYMENTWRONG("160004", "支付方式错误"),
	/**
	 * 商户不存在
	 */
	BUSINESSERROR("160005", "商户不存在"),
	/**
	 * 商户状态异常
	 */
	BUSINESSSTATUSISABNORMAL("160006", "商户状态异常"),
	/**
	 * 商户未开通此业务
	 */
	BUSINESSDOESNOTOPENTHISBUSINESS("160007", "商户未开通此业务"),
	/**
	 * 商户密钥不存在
	 */
	THEKEYDOESNOTEXIST("160008", "商户密钥不存在"),
	/**
	 * 商户交易不在可用时间段
	 */
	TIMEERROR("160009", "商户交易不在可用时间段"),
	/**
	 * 商户交易单笔金额超过上限
	 */
	AMOUNTCAP("160010", "商户交易单笔金额超过上限"),
	/**
	 * 商户交易单笔金额不足
	 */
	SINGLEAMOUNTERROR("160011", "商户交易单笔金额不足"),
	/**
	 * 商户当日交易金额超过上限
	 */
	AMOUNTCAPDAY("160012", "商户当日交易金额超过上限"),
	/**
	 * 商户交易金额不符合设置值
	 */
	AMOUNTERROR("160013", "商户交易金额不符合设置值"),
	/**
	 * 路由不存在
	 */
	THEROUTEERROR("160014", "路由不存在"),
	/**
	 * 费率类型错误
	 */
	FEE_TYPE_ERROR("160015","费率类型错误"),
	/**
	 * 可用余额太少
	 */
	AMOUNT_FEE_ERROR("160016","可用金额不足以提现"),
	/**
	 * 快速入网失败
	 */
	NETWORK("170000", "快速入网失败"),
	/**
	 * 验证码已存在
	 */
	VERIFY("199993","请不要频繁获取验证码!"),
	/**
	 * 验证码过期
	 */
	VERIFYERR("199994","验证码错误或已过期"),
	/**
	 * 参数错误
	 */
	PARAMSERR("199996", "参数错误"),
	/**
	 * 返回参数为空
	 */
	RETURNEMPTY("199997","返回参数为空"),
	/**
	 * 返回错误
	 */
	AUTHEMPTY("199995","无操作权限"),
	/**
	 * 返回错误
	 */
	RETURNERR("199998","返回异常"),
	/**
	 * 设备类型不匹配
	 */
	CLIENTTYPEERR("199999","暂不支持该设备类型!");
	private final String code;
	private final String message;
	
	//第一位数代表消息类别
	//1开头代表错误信息,7开头代表确认按钮的提示,8代表确认和取消按钮的提示
	//1开头代表错误信息,7开头代表确认按钮的提示,8代表确认和取消按钮的提示
	//1开头代表错误信息,7开头代表确认按钮的提示,8代表确认和取消按钮的提示
	//666666固定身份失效，999999固定系统异常，000000固定请求成功
	//第二位1业务类型，9系统级，5帐号

	AppExceptionEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getMessage(String code) {
		for (AppExceptionEnum t : AppExceptionEnum.values()) {
			if (code.equals(t.getCode())) {
				return t.getMessage();
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static String getErrMsg(String code) {
		return code + ":" + getMessage(code);
	}
}
