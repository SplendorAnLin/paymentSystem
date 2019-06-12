package com.yl.pay.pos;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: 常量
 * Description:  常量
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class Constant {
	
	public static final String UN_BANK="UNKNOWNBANK";                   //未知银行的Code
	public static final String ALARM_PHONE_NO="13810314015,15210020251";//告警手机号
	public static final String BANKINTERFACE_ALARM_PHONE_NO="bankInterfaceAlarmPhoneNo";
	public static final String BIZ_ALARM_PHONE_NO="bizAlarmPhoneNo";
	
	public static final String STATUS_TRUE = "TRUE";					//状态可用
	public static final String STATUS_FALSE = "FALSE";					//状态不可用
	public static final int    BANK_MAX_TRX_TIME=2;						//一次交易走银行接口最大次数
	public static final String MESSAGE_CURRENCY_CODE = "156";			//币种代码
	
	
	public final static int FIELD_ID_START = 0;							//用于标识报文的域开始最小数	
	public final static int FIELD_ID_END = 64;							//用于标识报文的域结束最大数	
	public final static int LEN_BITMAP_BASE = 8;						//主位图的长度	
	public final static int LEN_BITMAP_EXTENDED = 8;					//扩展位图的长度	
	public final static int LEN_POS_MESSAGETYPE = 2;					//信息类型的长度
	public final static String CONTENT_POS_TPUD = "6000000042";			//TPUD	
	public final static int LEN_POS_TPUD = 5;							//TPUD的长度	
	public final static int LEN_POS_MESSAGE = 6;						//报文头长度	
	public final static int LEN_CONTENT = 2;			            	//报文长度	
	public final static String CONTENT_POS_Message = "602200000000";	//报文头
	public final static int LEN_POS_LRI = 3;							//拨号POS标识长度 或者 ip标识长度
	public final static String CONTENT_POS_LRI = "LRI";                 //拨号POS标识
	public final static int LEN_POS_PHONE = 8;							//拨号POS电话位长度
	public final static int LEN_POS_NODE = 6;							//拨号POS节点信息
	public final static String CONTENT_IP_LRI = "AIP";					//IP的标识AIP
	public final static int LEN_POS_IP = 1;								//IP长度标识
	public final static String BANK_INTERFACE_SEQUENCE_HEAD="SEQ_SYSTEMTRACKENUMBER_";   	//
	
	public static final String SYSTEM_DEFAULT_FLAG = "DEFAULT";     	//default
	public static final String SYSTEM = "SYSTEM";
	public static final String SYSTEM_EXCEPTION_RESPONSE = "96";
	
	public static final String SETTLE_START_TIME="23:50:00";
	public static final String SETTLE_END_TIME="00:00:00";
	
	public static final String POS_SEQUENCE = "SEQ_POS_RETRIVL_NUM"; 	//生成参考号SEQ
	public static final String SUCCESS_POS_RESPONSE = "00";				//POS返回码（成功）
	
	public static final String USER_ROLE_CUSTOMER="CUSTOMER";			//用户角色类型"商户"
	public static final String ACCOUNT_BIZ_TYPE_RECEIPT="RECEIPT";		//账户业务类型"收款"
	
	public static final String CARD_ACCEPTOR_NAME = "lefu";				//受卡方名称地址
	
	public static final String SMS_ALARM_CODE_1="TransCore1";			//短信报警平台编号
	public static final String SMS_ALARM_CODE_2="UnionPay_Now";			//
	public static final String SMS_ALARM_CODE_3="UnionPay_Collection";	
	public static final String SMS_ALARM_CODE_4="JmsMessage";
	
	public static final String LOCATION_SUCCESS_CODE = "00";			//未跨境和跨省
	public static final String LOCATION_BORDER_CODE="01";				//跨境（国外、香港、澳门、台湾）
	public static final String LOCATION_PRO_CODE = "02";				//跨省
	public static final String LOCATION_LFB_CODE ="03";    				//校验LFB移机（暂时>2500m）               
	
	public static final String WILD_CARD="WildCard";					//外卡
	
    public static final String NONE_IC_DATA="30";					    //无IC更新数据
    public static final String HAVE_IC_DATA="31";					    //有IC更新数据
    public static final String CYCLE_IC_DATA="32";					    //循环查询IC更新数据
    public static final String END_IC_DATA="33";					    //终结IC更新数据
    
    public static final String ISHUA_POS_TYPE = "N38,M35,C821,ME30";				//i刷现有POS型号
    public static final String POS_VERSION_NEW="V2014061001";			// POS最新软件版本号
    public static final String XINDALU_POS_VERSION="V2014062001";		// 新大陆POS最新软件版本号
    public static final String XINDALU_POS_TYPE="8080Y,GP730,SP60,8080YS,8080YST,GP730C";//新大陆的机型
    public static final String LOCATION_VERIFY_1="82";					//区域限制：澳门
    public static final String LOCATION_VERIFY_2="71";					//区域限制：台湾
    public static final String LOCATION_VERIFY_3="81";					//区域限制：香港
    public static final String LOCATION_ALLOW_COUNTRY="中国";				//允许交易的国家
    public static final String LOCATION_ALLOW_COUNTRY_CODE="000";       //允许交易的国家编号
    
    public static final String SYS_ROUTE_BANK_INTERFACE="P310000";		//系统路由银行接口
    
    public static final String TYPANS_NOT_RESET="SETTLE,BATCH_UP,SIGN_OFF,SIGN_IN";     //暂时交易需要重置需要重新签到

	public static final String TAG_SETTLE_CARD = "D1"; //结算卡号
	public static final String TAG_POS_SN = "D2"; //POS序列号
    public static final String SETTLE_ACCOUNT_WARN="本机结算账号末四位";
    public static final String POS_VERSION_NEW_51="V2014072500";			// POS最新软件版本号
    public static final String POS_VERSION_NEW_V221="V2014111700";			// POS最新软件版本号V2.2.1
    public static final String EXCEPTION_CATAGORY="SYSTEM_EXCEPTION";		//异常种类-系统异常
    public static final String CARD_MARK_1="6011";							//卡标识1，发现卡
    public static final String ACC_PRO_P_SETTLE_LFB="P_SETTLE_LFB";		//账户系统产品分类 - 乐付宝
    public static final String ACC_PRO_POS="POS";						//账户系统产品分类 - POS基础商户
	public static final String CUSTOMER_PRODUCT_1="A20131219001";		//乐付宝天天付
	public static final String CUSTOMER_PRODUCT_2="A20131219002";		//乐付宝时时付
	public static final String BUSINESS_TYPE_1="LFB";
	public static final String CHECK_POSCATI_LIST="61730920,61796395,61767472,62867623,61782862,82170145,82170255,82136657,63610463,63610260";//降级交易终端黑名单列表
	
	/**
	 * 压缩
	 */
	public static final String TLV_BCD = "DF0F,DF05,DF04,DF03,";
	/** 产品编码*/
	public static final String TLV_DA = "DA";
	/**产品ID*/
	public static final String TLV_DB="DB"; 
	/**平台下发的id*/
	public static final String TLV_DF0D = "DF0D"; 
	/**
	 * POS请求位置信息标记 -- 跨省
	 */
	public static final String POSREQUEST_LOCATION_KS="KS";
	public static final String C930E = "C930E";
	public static final String C930E_SOFT_VERSION = "V2015010100";
	public static final String PAX_SOFTVERSION = "V2014081500";
	
	public static final Map<String,String> resCodes=new HashMap<String,String>();
	
	static {
		
		//交易返回码 -> 返回码描述
		resCodes.put("00", "操作成功");
		resCodes.put("01", "POS序列号已存在");
		resCodes.put("96", "服务器内部错误");
	}

}
