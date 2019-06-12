package com.yl.online.trade;

import java.io.IOException;
import java.util.Properties;

/**
 * 交易系统全局常量
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public class Constants {
	/** 应用名称，用于各服务间通信 */
	public static final String APP_NAME = "online-trade";
	/** 服务名称，用于商户开通服务 */
	public static final String SERVICE_NAME = "online-trade";
	/** Hessian密钥 */
	public static final String HESSIAN_KEY = "123456";

	/** 分隔符 **/
	public static final String SEPATOR = "&";
	/** MQ分隔符*/
	public static final String DELIMITER = ":";
	
	/** 报警手机号*/
	public static final String ALARM_PHONE_NO = "15010695051,15801674684,15210020251,18612902024";

	/** MQ主题 **/
	public static final String TOPIC = "account-tally-theme";
	
	/** MQ 分润主题 **/
	public static String SHARE_TOPIC = "yl-share-topic";

	/** 推送到数据中心的主题*/
	public static final String DATA_CENTER_TOPIC = "yl-data-topic";
	
	/** 推送到数据中心payment标题*/
	public static final String PAYMENT_TAG = "online_payment_tag";
	
	/** 推送到数据中心order标题*/
	public static final String ORDER_TAG = "online_tardeorder_tag";
	
	/** MQ 自动代付主题 **/
	public static String DPAY_TOPIC = "yl-dpay-topic";
	/** 快捷积分通道编号  **/
	public static String QUICKPAY_INTERFACEINFO_FEN = "QUICKPAY_UnionPay-120002";

	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(Constants.class.getClassLoader().getResourceAsStream("producer-client.properties"));
			SHARE_TOPIC = prop.getProperty("SHARE_TOPIC");
			DPAY_TOPIC = prop.getProperty("DPAY_TOPIC");
		} catch (IOException e) {}
	}
	
}
