package com.yl.client.front.enums;

/**
 * 参数类型
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public enum ParamType {
	/**
	 * 商户登录
	 */
	CUSTLOGIN("001","customer","login"),
	/**
	 * 商户退出登陆
	 */
	OUTLOGIN("0001","customer","outLogin"),
	/**
	 * 代理商登录
	 */
	AGENTLOGIN("002","agent","login"),
	/**
	 * 获取商户信息
	 */
	CUSTINFO("003","boss","getCustomer"),
	/**
	 * 提现
	 */
	DRAWCASH("004","dpay","DrawCash"),
	/**
	 * 余额信息
	 */
	ACCBALANCE("005","account","findBalance"),
	/**
	 * 刷新登陆信息
	 */
	TRADE("006","customer","getLoginInfo"),
	/**
	 * 查询当日交易金额and笔数
	 */
	TODAY("007","online","today"),
	/**
	 * 充值
	 */
	RECHARGE("008","online","recharge"),
	/**
	 * 查询提现订单
	 */
	SELECTREQUEST("009","dpay","selectRequest"),
	/**
	 * 查询交易订单
	 */
	SELECTTRADEORDER("010","online","selectTradeOrder"),

	/**
	 * 账户明细
	 */
	ACCBDETAILS("011","account","findAccountChange"),
	
	/**
	 * 商户密码修改
	 */
	CUSTOMERPASSWORD("012","customer","appUpdatePassword"),

	/**
	 * 查询提现订单详情
	 */
	SELECTREQUESTDETAILED("013","dpay","selectRequestDetailed"),
	
	/**
	 * 	 * 首页 - 七天趋势图
	 */
	WEEKLYSALES("014","boss","weeklySales"),
	/**
	 * 查询交易订单详情
	 */
	SELECTTRADEORDERDETAILED("015","online","selectTradeOrderDetailed"),
	
	/**
	 * 提现复核
	 */
	GODRAWCASH("016","dpay","goDrawCash"),
	
	/**
	 * 微信条码支付
	 */
	BARCODEPAY("017","online","barcodePay"),
	
	/**
	 * 查询商户结算信息
	 */
	GETCUSTOMERSETTLE("0008","boss","getCustomerSettle"),
	
	/**
	 * 查询商户费率信息
	 */
	GETCUSTOMERFEELIST("0009","boss","getCustomerFeeList"),
	
	/**
	 * 查询广告
	 */
	GETADALL("0010","boss","getAdAll"),
	
	/**
	 * 查询收款码
	 */
	GETQRCODE("0011","boss","getQRcode"),
	
	/**
	 * 查询客户端信息
	 */
	GETPROTOLALL("0012","boss","getProtolAll"),
	
	/**
	 * 商户费率获取
	 */
	GETCUSTOMERFEE("0013","boss","getCustomerFee"),
	
	/**
	 * 意见反馈新增
	 */
	USERFEEDBACKADD("0014","boss","userFeedbackAdd"),
	/**
	 * 代理商费率获取
	 */
	GETAGENTFEE("0015","boss","getAgentFee"),
	/**
	 * 修改操作员密码
	 */
	UPDATEOPERPWD("0016","customer","updateOperPwd"),
	
	/**
	 * 查询APP帮助文档信息
	 */
	GETPROTOCOLMANAGEMENTS("0017","customer","queryProtocolManagements"),
	/**
	 * 获取欢迎页广告
	 */
	GETIMGAD("018","boss","getWelcomeAd"),
	/**
	 * APP 绑卡
	 */
	ADDTRANSCARD("020", "boss", "addTansCard"),
	/**
	 * APP 解绑
	 */
	UNLOCKTANSCARD("021", "boss", "unlockTansCard"),
	/**
	 * APP 查询卡信息
	 */
	FINDBYCUSTNO("022", "boss", "findByCustNo"),
	/**
	 * 检测交易银行卡是否重复
	 */
	CHECKTRANSCARD("023", "boss", "checkTransCard"),
	/**
	 * 认证支付
	 */
	AUTHPAY("024", "customer", "authpay"),
	/**
	 * 设备列表
	 */
	DEVICES("025","customer","getDevices"),
	/**
	 * 保存商户头像
	 */
	SAVECUSTIMG("026","customer","saveCustImg"),
	/**
	 * 获取验证码
	 */
	GETVERIFYCODE("028","customer","getVerifyCode"),
	/**
	 * 重置密码
	 */
	RESETPWD("027","customer","resetPwd"),
	/**
	 * 快捷支付
	 */
	QUICK("029","customer","quick"),
	/**
	 * App 水牌获取
	 */
	INITIALIZATION("030", "boss", "initialization"),
	/**
	 * App POS 申请
	 */
	POSAPPLICATION("031", "customer", "appApplication"),
	/**
	 * APP 水牌申请
	 */
	APPDEVICE("032", "customer", "appDevice"),
	/**
	 * APP 水牌菜单
	 */
	GETDEVICESINFO("033", "customer", "getDevicesInfo"),
	/**
	 * 商户状态查询
	 */
	QUERYCUSTOMERSTATUS("034", "customer", "queryCustomerStatus"),
	/**
	 * 预开通商户录入结算卡信息
	 */
	CUSTSETTLEMENTCARD("035", "customer", "custSettleCard"),
	/**
	 * 预开通商户修改结算卡信息
	 */
	UPDATECREATESETTLE("036", "customer", "updateCreateSettle"),
	/**
	 * 查询商户结算卡信息
	 */
	QUERYSETTLE("037", "customer", "querySettle"),
	/**
	 * 查询商户结算卡信息
	 */
	UPDATECUSTOMERBASEINFO("038", "customer", "updateCustomerBaseInfo"),
	
	/**
	 * 查询银行商户
	 */
	FINDBANKCUSTOMERBYPAGE("039", "oneCustomerMultiCode", "findBankCustomerByPage"),
	/**
	 * 查询MCC
	 */
	FINDMCC("040", "oneCustomerMultiCode", "findMcc"),
	/**
	 * 查询省
	 */
	FINDPROVINCE("041", "oneCustomerMultiCode", "findProvince"),
	/**
	 * 查询市
	 */
	FINDCITYBYPROVINCE("042", "oneCustomerMultiCode", "findCityByProvince"),
	/**
	 * 用户关注商家
	 */
	FOLLOWBANKCUSTOMER("043", "oneCustomerMultiCode", "followBankCustomer"),
	/**
	 * 微信基础信息获取
	 */
	WECHATINFOGET("044", "customer", "wechatInfo"),

	/**
	 * App更新接口
	 */
	UPAPP("999","boss","upApp");
	
	private final String code;
	private final String sys;
	private final String type;

	ParamType(String code, String sys,String type) {
		this.code = code;
		this.sys = sys;
		this.type=type;
	}

	public static String getSys(String code) {
		for (ParamType t : ParamType.values()) {
			if (code.equals(t.getCode())) {
				return t.getSys();
			}
		}
		return null;
	}
	
	public static String getType(String code) {
		for (ParamType t : ParamType.values()) {
			if (code.equals(t.getCode())) {
				return t.getType();
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getSys() {
		return sys;
	}

	public String getType() {
		return type;
	}
}
